package sharding.jdbc.adapter;

import sharding.exception.ShardingJdbcException;
import sharding.jdbc.adapter.invocation.JdbcMethodInvocation;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zhaoheng on 18/10/17.
 */
public class WrapperAdapter implements Wrapper {

    private final Collection<JdbcMethodInvocation> jdbcMethodInvocations = new ArrayList<>();

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return (T) this;
        }
        throw new SQLException(String.format("[%s] cannot be unwrapped as [%s]", getClass().getName(), iface.getName()));
    }


    @Override
    public final boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }


    /**
     * 记录方法调用.
     * ??为什么要做这一步
     *
     * @param targetClass   目标类
     * @param methodName    方法名称
     * @param argumentTypes 参数类型
     * @param arguments     参数
     */
    public final void recordMethodInvocation(final Class<?> targetClass, final String methodName, final Class<?>[] argumentTypes, final Object[] arguments) {
        try {
            jdbcMethodInvocations.add(new JdbcMethodInvocation(targetClass.getMethod(methodName, argumentTypes), arguments));
        } catch (final NoSuchMethodException ex) {
            throw new ShardingJdbcException(ex);
        }
    }

    /**
     * 回放记录的方法调用.
     * ??为什么要做这一步
     *
     * @param target 目标对象
     */
    public final void replayMethodsInvocation(final Object target) {
        for (JdbcMethodInvocation each : jdbcMethodInvocations) {
            each.invoke(target);
        }
    }

    protected void throwSQLExceptionIfNecessary(final Collection<SQLException> exceptions) throws SQLException {
        if (exceptions.isEmpty()) {
            return;
        }
        SQLException ex = new SQLException();
        for (SQLException each : exceptions) {
            ex.setNextException(each);
        }
        throw ex;
    }

}
