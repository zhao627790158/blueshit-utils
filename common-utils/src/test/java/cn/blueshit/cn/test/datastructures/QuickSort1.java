package cn.blueshit.cn.test.datastructures;

/**
 * Created by zhaoheng on 18/10/9.
 */
public class QuickSort1 {


    public static void main(String[] args) {
        int a[] = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};
        new QuickSort1().sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            //找到基准值,,取区间第一个数为基准
            int middle = getMiddle(arr, left, right);
            this.quickSort(arr, left, middle - 1);
            this.quickSort(arr, middle + 1, right);
        }
    }

    private int getMiddle(int[] arr, int left, int right) {
        //第一个数为基准
        int temp = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            if (left < right) {
                //填坑换位
                arr[left] = arr[right];
                left++;
            }
            while (left < right && arr[left] < temp) {
                left++;
            }
            if (left < right) {
                //填right的坑
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = temp;
        return left;
    }


}
