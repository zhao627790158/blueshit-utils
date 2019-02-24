package algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 快速选择算法
 * 快速选择是一种从无序列表找到第k小元素的选择算法。它从原理上来说与快速排序有关。同样地，它在实际应用是一种高效的算法，具有很好的平均时间复杂度，然而最坏时间复杂度则不理想。
 * 快速选择及其变种是实际应用中最常使用的高效选择算法
 */
public class QuickSelect {


    public static void main(String[] args) {
        int[] arr = {4, 2, 3, 1};
        int k = 4;
        //第k小元素的选择算法
        int i = quickSelect(arr, k - 1);
        System.out.println(arr[i]);
        //快速排序
        quickSort(arr, 0, arr.length - 1);
        System.out.println(JSON.toJSON(arr));
        int[] arr1 = {4, 2, 3, 1};
        quick1(arr1, 0, arr1.length - 1);

        System.out.println(JSON.toJSON(arr1));
    }

    /**
     * 快速选择的总体思路与快速排序一致，选择一个元素作为基准来对元素进行分区，
     * 将小于和大于基准的元素分在基准左边和右边的两个区域。不同的是，快速选择并不递归访问双边，
     * 而是只递归进入一边的元素中继续寻找。这降低了平均时间复杂度，从O(n log n)至O(n)，不过最坏情况仍然是O(n²)。
     * <p>
     */
    // find the kth **smallest** element in an unsorted array
    public static int quickSelect(int[] nums, int k) {
        int i = 0;
        int j = nums.length - 1;

        while (i <= j) {
            int partitionIdx = partition(nums, i, j);

            if ((k - 1) == partitionIdx) {
                return nums[partitionIdx];
            } else if ((k - 1) < partitionIdx) {
                j = partitionIdx - 1;
            } else {
                i = partitionIdx + 1;
            }
        }

        return 0;
    }

    // same as qucik sort
    public static int partition(int[] nums, int start, int end) {
        if (start == end) {
            return start;
        }

        int pivot = nums[start];

        while (start < end) {
            // find first smaller element from right
            while (start < end && nums[end] >= pivot) {
                end--;
            }
            nums[start] = nums[end];
            // find first larger element from left
            while (start < end && nums[start] <= pivot) {
                start++;
            }
            nums[end] = nums[start];
        }

        nums[start] = pivot;

        return start;
    }

    //另外一种方式
    public static void quick1(int[] nums, int start, int end) {
        if (start < end) {
            int middle = findMiddle(nums, start, end);
            quick1(nums, start, middle - 1);
            quick1(nums, middle + 1, end);
        }
    }

    public static int findMiddle(int[] nums, int start, int end) {
        int temp = nums[end];
        int left = start;
        int right = end - 1;

        while (true) {
            //从左面找第一个比参照物大的数据
            while (left < end && nums[left] <= temp) {
                left++;
            }
            if (left == end) {
                return left;
            }
            //从右面找第一个比参照物小的数据
            while (right >= start && nums[right] >= temp) {
                right--;
            }
            if (right == start - 1) {
                //参照物是最小值也出现了交叉
                //和下面 left >= right相同
                //do nothing
            }

            //比较左右指针有无交叉
            if (left < right) {
                //如果没有交叉交换左右指针数据
                int d = nums[left];
                nums[left] = nums[right];
                nums[right] = d;
            } else {
                //如果出现交叉，交换左指针和参照物的值，停止
                int d = nums[left];
                nums[left] = nums[end];
                nums[end] = d;
                break;
            }
        }
        return left;
    }

    public static void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        //找到分区点位置
        int partition = partition(nums, left, right);
        quickSort(nums, left, partition - 1);
        quickSort(nums, partition + 1, right);
    }

}
