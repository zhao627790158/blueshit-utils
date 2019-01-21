package cn.blueshit.cn.test.function;

public interface LogicRule<T> {

    LogicRule NO_RULE = new LogicRule() {
        @Override
        public boolean mapping(Object context) {
            return true;
        }
    };

    boolean mapping(T context);
}
