package cn.blueshit.cn.test.stack;

import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.util.resources.hr.CalendarData_hr;

import java.util.Stack;

/**
 * 中缀表达式转后缀表达式
 * Created by zhaoheng on 16/10/4.
 */
public class PostInfix {

    public static void main(String[] args) {
        PostInfix postInfix = new PostInfix();
        String s = postInfix.doTransfer("(3+2)/5*(3/3*1)-5");
        System.out.println(s);

        System.out.println((3 + 2) / 5 * (3 / 3 * 1) - 5);
    }


    /**
     * 中缀表达式转后缀表达式
     *
     * @param str
     * @return
     */
    public String doTransfer(String str) {
        StringBuilder builder = new StringBuilder();
        //操作符栈
        Stack<Character> myStack = new Stack();
        //1字符串转换为字符数组
        char[] cs = str.toCharArray();
        //2对每个字符进行判断并执行相应的操作
        for (int i = 0; i < cs.length; i++) {

            char c = cs[i];
            //2.4如果是操作符,就要分级别操作
            if (c == '+' || c == '-') {
                doOpereation(myStack, builder, c, 1);
            } else if (c == '*' || c == '/') {
                doOpereation(myStack, builder, c, 2);
            } else if (c == '(') {
                //2.2 如果是左括号,压栈
                myStack.push(c);
            } else if (c == ')') {
                //2.3 如果是右括号,弹栈道输出中,直到遇到(
                doRightBracket(myStack, builder);
            } else {
                //2.1 如果是操作数,直接加入到输出
                builder.append(c);
            }
        }
        //3, 把栈中的操作符依次弹出到输出中
        while (!myStack.isEmpty()) {
            builder.append(myStack.pop());
        }

        return builder.toString();
    }

    /**
     * 按级别处理操作符
     *
     * @param c
     * @param level
     */
    private void doOpereation(Stack<Character> stack, StringBuilder builder, char c, int level) {
        //1:依次从栈顶获取一个值
        while (!stack.isEmpty()) {
            char topC = stack.pop();
            //2:用这个值传入的数据进行比较
            //2.2: 如果栈顶的数据时(,不动,就是把它压回来
            if (topC == '(') {
                stack.push(topC);
                break;
            } else {
                int topLevel = 0;
                //暂时只支持+-*/
                if (topC == '+' || topC == '-') {
                    topLevel = 1;
                } else {
                    topLevel = 2;
                }
                if (topLevel > level) {
                    //如果栈顶的数据优先级大于等于传入的数据类型,就输出
                    builder.append(topC);
                } else {
                    //如果栈顶的优先级别 小于传入的数据级别, 不动
                    stack.push(topC);
                    break;
                }
            }
        }
        //3:找到位置后,把传入的操作符压入
        stack.push(c);
    }

    /**
     * 处理右括号
     */
    private void doRightBracket(Stack<Character> stack, StringBuilder buffer) {
        while (!stack.isEmpty()) {
            char topC = stack.pop();
            if (topC == '(') {
                break;
            } else {
                buffer.append(topC);
            }
        }
    }

}
