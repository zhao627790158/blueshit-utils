package algorithm;

public class QuickSelect20190221 {

    /**
     * 快速选择是一种从无序列表找到第k小元素的选择算法。它从原理上来说与快速排序有关。同样地，它在实际应用是一种高效的算法，具有很好的平均时间复杂度，然而最坏时间复杂度则不理想。
     * 快速选择及其变种是实际应用中最常使用的高效选择算法。
     * 快速选择的总体思路与快速排序一致，选择一个元素作为基准来对元素进行分区
     * ，将小于和大于基准的元素分在基准左边和右边的两个区域。不同的是，快速选择并不递归访问双边，
     * 而是只递归进入一边的元素中继续寻找。这降低了平均时间复杂度，从O(n log n)至O(n)，不过最坏情况仍然是O(n2)。
     */

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 10, 4, 3, 4};
        System.out.println(quickSelect(arr, 7));
    }


    //取kth
    public static int quickSelect(int[] nums, int k) {
        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int partition = partition(nums, i, j);
            if ((k - 1) == partition) {
                //如果kth的下标正好和分区位置相同
                return nums[partition];
            } else if ((k - 1) < partition) {
                //kth在左面分区
                j = partition - 1;
            } else {
                i = partition + 1;
            }
        }
        return 0;

    }

    public static int partition(int[] test, int left, int right) {
        if (left >= right) {
            return left;
        }
        int i = left;
        int j = right;
        int temp = test[i];
        while (i < j) {
            while (i < j && test[j] >= temp) {
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
