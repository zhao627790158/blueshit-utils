package cn.blueshit.cn.test.current;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhaoheng on 16/8/21.
 * 通过cas来维护包含多个变量的不变性条件
 */
public class CasNumberRange {


    private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));

    public int getLower() {
        return values.get().getLower();
    }

    public int getUpper() {
        return values.get().getUpper();
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.getUpper()) {
                throw new IllegalArgumentException("");
            }
            IntPair intPair = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, intPair)) {
                return;
            }
        }
    }
    public void setUpper(int i){
        while(true) {
            IntPair old = values.get();
            if(i<old.getLower()) {
                throw new IllegalArgumentException(" illegal execption");
            }
            IntPair intPair = new IntPair(old.getLower(), i);
            if (values.compareAndSet(old, intPair)) {
                return;
            }
        }
    }

    @Data
    private static class IntPair {
        private final int lower;
        private final int upper;

        private IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }


}
