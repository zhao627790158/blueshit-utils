package algorithm;

import java.util.Arrays;

/**
 * Dynamic  programming
 */
public class DP {


    //自底向上记录求解过的值
    public static int fib(int n) {
        if (n <= 0) {
            return n;
        }
        int[] memory = new int[n + 1];
        memory[0] = 0;
        memory[1] = 1;
        for (int i = 2; i <= n; i++) {
            memory[i] = memory[i - 1] + memory[i - 2];
        }
        return memory[n];
    }

    public static void main(String[] args) {
        int[] temp = {3, 1, 2, 2, 1};
        int i = 0;
        for (int t : temp) {
            System.out.println("1--" + i);
            i ^= t;
            System.out.println("2--" + i);
        }
        System.out.println("----" + i);
        System.out.println(recOpt(temp, temp.length - 1));
        System.out.println(dpOpt(temp));
        int[] temp1 = {3, 34, 4, 12, 5, 2};

        System.out.println(recSubSet(temp1, temp1.length - 1, 9));
    }

    /**
     * 4，1，2，3，8，5，6
     * <p>
     * 递归的方式
     * 选中不相邻的数字，使其加起来最大
     * 递归出口
     * opt(0)=0
     * opt(1) = max(arr[0],arr[1])
     * 方程:
     * max (选:opt(i-2) + arr[i],不选:opt(i-1))
     */
    public static int recOpt(int[] arr, int index) {
        if (index == 0) {
            return arr[0];
        } else if (index == 1) {
            return Math.max(arr[0], arr[1]);
        } else {
            int a = recOpt(arr, index - 2) + arr[index];
            int b = recOpt(arr, index - 1);
            return Math.max(a, b);
        }
    }

    public static int dpOpt(int[] arr) {
        int[] opt = new int[arr.length];
        Arrays.fill(opt, 0);
        opt[0] = 0;
        opt[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < arr.length; i++) {
            int a = opt[i - 2] + arr[i];
            int b = opt[i - 1];
            opt[i] = Math.max(a, b);
        }
        return opt[arr.length - 1];
    }


    /**
     * 数组中是否存在2个值的和 =s
     * arr[ 3,34,4,12,5,2]
     * S=9
     * <p>
     * 选：subset(arr,i-1,s -arr[i])
     * 不选：subset(arr,i-1,s)
     * 出口条件：
     * s ==0 return true;
     * i==0 return arr[0]==s
     * arr[i]>s return subset(arr,i-1,s)
     * <p>
     * arr:给定的数组
     * i:下标
     * s:目标值
     */
    public static boolean recSubSet(int arr[], int i, int s) {
        if (s == 0) {
            return true;
        } else if (i == 0) {
            return arr[0] == s;
        } else if (arr[i] > s) {
            return recSubSet(arr, i - 1, s);
        } else {
            //选
            boolean a = recSubSet(arr, i - 1, s - arr[i]);
            //不选
            boolean b = recSubSet(arr, i - 1, s);
            return a || b;
        }

    }

}
