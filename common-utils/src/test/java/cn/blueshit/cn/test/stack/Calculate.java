package cn.blueshit.cn.test.stack;

import java.util.Stack;

/**
 * Created by zhaoheng on 16/10/4.
 */
public class Calculate {


    public static void main(String[] args) {
        String test = "32+5331*/*/5-";
        Calculate calculate = new Calculate();
        int calculate1 = calculate.calculate(test);
        System.out.println(calculate1);
    }

    public int calculate(String str) {
        Stack<Integer> stack = new Stack<Integer>();
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (c >= '0' && c <= '9') {
                stack.push((int) (c - '0'));
            } else {
                //如果是操作符,从栈中取两个值进行计算,然后把结果压栈
                int num2 = stack.pop();
                int num1 = stack.pop();
                int temp = 0;
                if (c == '+') {
                    temp = num1 + num2;
                } else if (c == '-') {
                    temp = num1 - num2;
                } else if (c == '*') {
                    temp = num1 * num2;
                } else if (c == '/') {
                    temp = num1 / num2;
                }
                stack.push(temp);
            }
        }
        return stack.pop();
    }

}
