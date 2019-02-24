package algorithm;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 10, 4, 3, 4};
        quick(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        arr = new int[]{1, 20, 2, 4, 5, 6};
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void bubbleSort(int[] array) {
        int n = array.length - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void quick(int[] test, int start, int end) {
        if (start >= end) {
            return;
        }
        int partition = partition(test, start, end);
        quick(test, start, partition - 1);
        quick(test, partition + 1, end);
    }

    public static int partition(int[] test, int left, int right) {
        if (left >= right) {
            return left;
        }
        int i = left;
        int j = right;
        int temp = test[i];
        while (i < j) {
            while (i < j && test[j] > temp) {
                j--;
            }
            test[i] = test[j];
            while (i < j && test[i] <= temp) {
                i++;
            }
            test[j] = test[i];
        }
        test[i] = temp;
        return i;
    }

}
