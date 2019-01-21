package cn.blueshit.cn.test.function;

import org.junit.Test;

/**
 * Created by zhaoheng on 19/1/21.
 */
public class ComplementTest {


    @Test
    public void test() {

        int i = -1 << 8;
        int q = 1 << 8;
        System.out.println(~37);
        //数据的表示方法都是以补码的形式表示
        System.out.println(Integer.toBinaryString(-38));
        //如果补码的符号位为“1”，表示是一个负数，求原码的操作可以是：符号位为1，其余各位取反，然后再整个数加1。
        System.out.println(Integer.toBinaryString(i));
        //已知一个补码为11111001，则原码是10000111（-7）：因为符号位为“1”，表示是一个负数，所以该位不变，仍为   “1”；其余7位1111001取反后为0000110；再加1，所以是10000111。
        //10000111
        System.out.println(Integer.toBinaryString(i).length());
    }
}
