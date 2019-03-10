package algorithm;


import java.util.Arrays;

public class MinimumPathSum {

    public static void main(String[] args) {
        // [1,3,1],
        //  [1,5,1],
        //  [4,2,1]
        int[][] temp = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(temp[2][2]);
        int i = minPathSum(temp);
        System.out.println(i);
        test();
    }


    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == 0) {
                    if (j == 0) {
                        dp[i][j] = grid[0][0];
                    } else {
                        dp[i][j] = dp[i][j - 1] + grid[i][j];
                    }
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + grid[i][j];
                }
            }

        }
        return dp[m - 1][n - 1];
    }


    /**
     * [1,2,3,4,5]
     * k=2
     * [4,5,1,2,3]
     */

    public void rotate(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            move(nums);
        }
    }


    public static void test() {
        int[] temp = {1, 2, 3, 4, 5};
        int k = 3;
        for (int i = 0; i < k; i++) {
            move(temp);
        }
        System.out.println(Arrays.toString(temp));

    }

    public static void move(int[] temp) {
        int size = temp.length;
        int tail = temp[size - 1];
        for (int i = size - 1; i > 0; i--) {
            temp[i] = temp[i - 1];
        }
        temp[0] = tail;
    }

}
