package algorithm;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 一个数列，先升序后降序，返回最大值的下标。
 * 限制条件是 arr[i] > arr[i-1];arr[i] > arr[i+1]
 */
public class BinarySearchMeituan {


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 5, 9, 10, 6, 4, 3, 2};
        System.out.println(arr.length);
        System.out.println(getMax(arr, 0, arr.length - 1));
    }

    private static void search(int[] arr) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(arr));
    }

    /**
     * 使用二分查找法
     *
     * @param arr   : 先升序在降序的数组
     * @param low   : 数组最小的索引
     * @param high: 数组最大的索引
     * @return : 返回数组中的最大值
     */
    public static int getMax(int[] arr, int low, int high) {

        while (low < high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] > arr[mid + 1]) {
                high = mid;
            } else if (arr[mid] < arr[mid + 1]) {
                low = mid + 1;
            } else  //arr[mid]和arr[mid+1]相等的情况
            {
                if (mid - 1 >= low) //防止数组范围越界  [low~high]
                {
                    if (arr[mid - 1] > arr[mid]) {
                        high = mid - 1;
                    } else if (arr[mid - 1] < arr[mid]) {
                        low = mid + 1;
                    } else //如果arr[mid-1] 和 arr[mid] 相等，即 arr[mid-1]，arr[mid],arr[mid+1]都相等，
                    //那么就不能确定最大值在mid左边还是在mid右边，所以分别对mid左边和mid右边递归求最大
                    //值，然后比较
                    {
                        int one = getMax(arr, low, mid - 1);
                        int two = getMax(arr, mid + 1, high);

                        return one > two ? one : two;
                    }
                } else { //如果 mid-1 < low说明  mid和low相等了。所以能够得出arr[low] == arr[high]
                    return arr[low];
                }
            }
        }

        return arr[low];
    }

}
