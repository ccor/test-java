package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.List;

public class P52NQueensII {
    int ans = 0;
    public int totalNQueens(int n) {
        int[] columnIndex = new int[n];
        for (int i = 0; i < n; ++i)
            columnIndex[i] = i;
        permutation(columnIndex, n, 0);
        return ans;
    }

    void permutation(int columnIndex[], int length, int index) {
        if (index == length) {
            if (check(columnIndex, length)) {
                ans ++;
            }
        } else {
            for (int i = index; i < length; ++i) {
                int temp = columnIndex[i];
                columnIndex[i] = columnIndex[index];
                columnIndex[index] = temp;
                permutation(columnIndex, length, index + 1);
                temp = columnIndex[index];
                columnIndex[index] = columnIndex[i];
                columnIndex[i] = temp;
            }
        }
    }
    boolean check(int[] columnIndex, int length) {
        for (int i = 0; i < length; ++i) {
            for (int j = i + 1; j < length; ++j) {
                if ((i - j == columnIndex[i] - columnIndex[j])
                        || (j - i == columnIndex[i] - columnIndex[j]))
                    return false;
            }
        }
        return true;
    }
}
