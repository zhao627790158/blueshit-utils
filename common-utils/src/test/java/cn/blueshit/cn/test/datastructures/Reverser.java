package cn.blueshit.cn.test.datastructures;

import java.util.Stack;

/**
 * Created by zhaoheng on 16/11/2.
 */
public class Reverser {

    private static final Stack<Character> mystack = new Stack();
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) {
        String testString = "(3+2)/5-((7+8)*4-5)";
        String s = doTransfer(testString);
        System.out.println(s);
    }


    public static String doTransfer(String str) {

        //字符串数组转字符数组
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == '+' || c == '-') {
                doOperation(c, 1);
            } else if (c == '*' || c == '/') {
                doOperation(c, 2);
            } else if (c == '(') {
                mystack.push(c);
            } else if (c == ')') {
                doRightBracket();
            } else {
                stringBuilder.append(c);
            }
        }
        while (!mystack.isEmpty()) {
            stringBuilder.append(mystack.pop());
        }
        return stringBuilder.toString();
    }

    private static void doOperation(char c, int level) {
        while (!mystack.isEmpty()) {
            Character peek = mystack.peek();
            if (peek == '(') {
                break;
            } else {
                //如果栈中的优先级比传入的大,将占中的加入到字符串中
                if (CharLevel.paser(peek).getLevel() > level) {
                    stringBuilder.append(mystack.pop());
                } else {
                    break;
                }
            }
        }
        mystack.push(c);
    }

    private static void doRightBracket() {
        while (!mystack.isEmpty()) {
            Character peek = mystack.peek();
            if (peek == '(') {
                mystack.pop();
                break;
            } else {
                stringBuilder.append(mystack.pop());
            }
        }
    }

    private enum CharLevel {
        PLUS('+', 1),
        SUBTRACT('-', 1),
        MULTIPLY('*', 2),
        DIVISION('/', 2);

        private char c;
        private int level;

        CharLevel(char c, int level) {
            this.c = c;
            this.level = level;
        }

        private static CharLevel paser(char c) {
            for (CharLevel charLevel : CharLevel.values()) {
                if (charLevel.getC() == c) {
                    return charLevel;
                }
            }
            return null;
        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

}
