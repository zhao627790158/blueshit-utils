package cn.blueshit.cn.test.datastructures;

/**
 * Created by zhaoheng on 16/11/4.
 */
public class QuickSort implements BlSort {


    public static void main(String[] args) {
        int a[] = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};
        new QuickSort().sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

    }

    public int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }


    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            //找到第一个的位置
            int middle = getMiddle(arr, left, right);
            //对坑左面排序 递归调用
            quickSort(arr, left, middle - 1);
            //对坑右面排序
            quickSort(arr, middle + 1, right);
        }
    }

    public int getMiddle(int[] arr, int left, int right) {
        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            //一定要加上lef<right才能做left++操作
            if (left < right) {
                arr[left] = arr[right];
                left++;
            }
            while (left < right && arr[left] < temp) {
                left++;
            }
            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = temp;
        return left;
    }

}
