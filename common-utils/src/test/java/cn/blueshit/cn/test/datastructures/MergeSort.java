package cn.blueshit.cn.test.datastructures;

/**
 * Created by zhaoheng on 16/11/3.
 */
public class MergeSort implements BlSort {


    public static void main(String[] args) {
        int[] data = new int[]{5, 3, 6, 2, 1, 9, 4, 8, 7};
        print(data);
        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(data);
        System.out.println("排序后的数组：");
        print(data);
    }

    public void mergeSort(int[] array) {
        int[] temp = new int[array.length];
        doMergeSort(array, temp, 0, array.length - 1);
    }

    public void doMergeSort(int[] array, int[] temp, int lowBound, int highBound) {
        //递归方法必须有的出口
        if (lowBound >= highBound) {
            return;
        }

        int middle = (lowBound + highBound) / 2;
        //左面排序
        doMergeSort(array, temp, lowBound, middle);
        //右面排序
        doMergeSort(array, temp, middle + 1, highBound);
        merge(array, temp, lowBound, middle, middle + 1, highBound);

    }

    public void merge(int[] array, int[] temp, int lowBound, int leftHigh, int rightLow, int highBound) {

        int count = lowBound;
        //记录temp开始的最小位置,最后拷贝回原数组使用
        int tempLeftIndex = lowBound;
        while (lowBound <= leftHigh && rightLow <= highBound) {
            if (array[lowBound] < array[rightLow]) {
                temp[count++] = array[lowBound++];
            } else {
                temp[count++] = array[rightLow++];
            }
        }
        //分别处理左右两边剩下的数据
        while (lowBound <= leftHigh) {
            temp[count++] = array[lowBound++];
        }
        while (rightLow <= highBound) {
            temp[count++] = array[rightLow++];
        }
        //将temp中的数据拷贝回原数组中
        while (tempLeftIndex <= highBound) {
            array[tempLeftIndex] = temp[tempLeftIndex++];
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }


    @Override
    public int[] sort(int[] array) {
        mergeSort(array);
        return array;
    }
}
