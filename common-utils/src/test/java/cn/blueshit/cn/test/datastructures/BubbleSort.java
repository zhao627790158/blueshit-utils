package cn.blueshit.cn.test.datastructures;

/**
 * Created by zhaoheng on 16/11/1.
 * 冒泡排序
 */
public class BubbleSort {

    private static final int[] test = new int[]{49, 38, 65};
    ;

    public static void bubbleSort(int[] a, MySort mySort) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                }
            }
        }
    }

    public static void bubbleSort2(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }


    }

    public static void print() {
        for (int i : test) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        bubbleSort(test, new littleSort());
        print();
        System.out.println("---");
        bubbleSort2(test);
        print();
    }
}

interface MySort {
}

class bigSort implements MySort {
}

class littleSort implements MySort {
}