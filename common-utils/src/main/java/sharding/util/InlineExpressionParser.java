package sharding.util;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import groovy.lang.GString;
import groovy.lang.GroovyShell;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * Created by zhaoheng on 18/10/17.
 * 内联表达式解析器
 * <p>
 * List<String> expected = new InlineExpressionParser("t_${['new','old']}_order_${1..2}, t_config").evaluate();
 * assertThat(expected.size(), is(5));
 * assertThat(expected, hasItems("t_new_order_1", "t_new_order_2", "t_old_order_1", "t_old_order_2", "t_config"));
 */
@RequiredArgsConstructor
public class InlineExpressionParser {

    private static final char SPLITTER = ',';

    private final String inlineExpression;


    public List<String> evaluate() {
        if (null == inlineExpression) {
            return Collections.emptyList();
        }
        return flatten(evaluate(split()));
    }

    private List<String> flatten(final List<Object> segments) {
        List<String> result = new ArrayList<>();
        for (Object each : segments) {
            if (each instanceof GString) {
                result.addAll(assemblyCartesianSegments((GString) each));
            } else {
                result.add(each.toString());
            }
        }
        return result;
    }

    private List<String> assemblyCartesianSegments(final GString segment) {
        Set<List<String>> cartesianValues = getCartesianValues(segment);
        List<String> result = new ArrayList<>(cartesianValues.size());
        for (List<String> each : cartesianValues) {
            result.add(assemblySegment(each, segment));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Set<List<String>> getCartesianValues(final GString segment) {
        List<Set<String>> result = new ArrayList<>(segment.getValues().length);
        for (Object each : segment.getValues()) {
            if (null == each) {
                continue;
            }
            if (each instanceof Collection) {
                result.add(Sets.newLinkedHashSet(Collections2.transform((Collection<Object>) each, new Function<Object, String>() {

                    @Override
                    public String apply(final Object input) {
                        return input.toString();
                    }
                })));
            } else {
                result.add(Sets.newHashSet(each.toString()));
            }
        }
        //笛卡尔积
       /* List<String> expected = new InlineExpressionParser("t_order_${0..2},t_order_item_${0..1}").evaluate();
        assertThat(expected, hasItems("t_order_0", "t_order_1", "t_order_2", "t_order_item_0", "t_order_item_1"));
        */
        return Sets.cartesianProduct(result);
    }

    private String assemblySegment(final List<String> cartesianValue, final GString segment) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < segment.getStrings().length; i++) {
            result.append(segment.getStrings()[i]);
            if (i < cartesianValue.size()) {
                result.append(cartesianValue.get(i));
            }
        }
        return result.toString();
    }

    private List<Object> evaluate(List<String> inlineExpressions) {
        List<Object> result = new ArrayList<>(inlineExpressions.size());
        GroovyShell shell = new GroovyShell();
        for (String each : inlineExpressions) {
            StringBuilder expression = new StringBuilder(each);
            if (!each.startsWith("\"")) {
                expression.insert(0, "\"");
            }
            if (!each.endsWith("\"")) {
                expression.append("\"");
            }
            result.add(shell.evaluate(expression.toString()));
        }
        return result;
    }


    /**
     * 解析表达式
     * t_order_${[0, 1, 2]},t_order_item_${[0, 2]}
     *
     * @return [t_order_${[0, 1, 2]},t_order_item_${[0, 2]}]
     */
    private List<String> split() {
        List<String> result = new ArrayList<>();
        StringBuilder segment = new StringBuilder();
        int bracketsDepth = 0;
        for (int i = 0; i < inlineExpression.length(); i++) {
            char each = inlineExpression.charAt(i);
            switch (each) {
                case SPLITTER:
                    //如果在{ 内
                    if (bracketsDepth > 0) {
                        segment.append(each);
                    } else {
                        result.add(segment.toString().trim());
                        segment.setLength(0);
                    }
                    break;
                case '$':
                    if ('{' == inlineExpression.charAt(i + 1)) {
                        bracketsDepth++;
                    }
                    segment.append(each);
                    break;
                case '}':
                    if (bracketsDepth > 0) {
                        bracketsDepth--;
                    }
                    segment.append(each);
                    break;
                default:
                    segment.append(each);
                    break;
            }
        }
        if (segment.length() > 0) {
            result.add(segment.toString().trim());
        }
        return result;
    }
}
