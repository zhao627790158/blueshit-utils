package sharding.jdbc.adapter.invocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharding.exception.ShardingJdbcException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class JdbcMethodInvocation {

    @Getter
    private final Method method;

    @Getter
    private final Object[] arguments;

    /**
     * Invoke JDBC method.
     *
     * @param target target object
     */
    public void invoke(final Object target) {
        try {
            method.invoke(target, arguments);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new ShardingJdbcException("Invoke jdbc method exception", ex);
        }
    }
}