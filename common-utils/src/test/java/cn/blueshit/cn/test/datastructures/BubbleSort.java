package cn.blueshit.cn.test.datastructures;

/**
 * Created by zhaoheng on 16/11/1.
 * 冒泡排序
 */
public class BubbleSort {

    private static final int[] test = new int[]{1, 2, 4, 6, 7, 4, 4, 9, 7, 7, 232};

    public static void bubbleSort(int[] a, MySort mySort) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j > a.length; j++) {
                mySort.sort(a[i], a[j]);
            }
        }
    }

    public static void print() {
        for (int i : test) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        //从大到小
        bubbleSort(test, new bigSort());
        print();
        System.out.println("----------");
        //从小到到
        bubbleSort(test, new littleSort());
        print();
    }
}

interface MySort {
    void sort(int i, int j);
}

class bigSort implements MySort {
    public void sort(int i, int j) {
        if (j > i) {
            int temp = j;
            j = i;
            i = temp;
        }

    }
}

class littleSort implements MySort {
    public void sort(int i, int j) {
        if (i > j) {
            int temp = j;
            j = i;
            i = temp;
        }

    }
}