package algorithm;

import java.util.Arrays;

public class HeapSortTest {


    private static int[] array = {1, 3, 5, 6, 2, 10, 298, 23, 83947, 34};
    private static int len = array.length;

    public static void main(String[] args) {
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    //构建大顶堆
    public static void bulidMaxHeap(int[] arr) {
        for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
            //重新调整节点 i
            heapify(arr, i);
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void heapify(int[] arr, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int large = i;

        if (left < len && arr[left] > arr[large]) {
            large = left;
        }

        if (right < len && arr[right] > arr[large]) {
            large = right;
        }

        //如果最大值有变更，就是子节点大于父节点的话
        //就调整位置
        if (large != i) {
            int temp = arr[i];
            arr[i] = arr[large];
            arr[large] = temp;
            //需要调整 交换之后的节点以及其子节点
            //只交换的值，large的位置还没有变
            heapify(arr, large);
        }
    }

    public static void heapSort(int[] arr) {
        bulidMaxHeap(arr);
        for (int i = len - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            len--;
            heapify(arr, 0);
        }

    }

}
