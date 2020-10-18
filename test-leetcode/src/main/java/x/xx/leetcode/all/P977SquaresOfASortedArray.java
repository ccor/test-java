package x.xx.leetcode.all;

import java.util.Arrays;

public class P977SquaresOfASortedArray {
    public int[] sortedSquares(int[] A) {
        int[] ans = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            ans[i] = A[i] * A[i];
        }
        Arrays.sort(ans);
        return ans;
    }

    public int[] sortedSquares2(int[] A) {
        int[] ans = new int[A.length];
        int l = 0, r = A.length - 1, i = r;
        while (l <= r) {
            ans[i--] = -A[l] > A[r] ? A[l] * A[l++] : A[r] * A[r--];
        }
        return ans;
    }
}
