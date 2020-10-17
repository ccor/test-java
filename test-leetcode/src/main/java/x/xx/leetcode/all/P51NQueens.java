package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.List;

public class P51NQueens {
    List<List<String>> list = new ArrayList<>();
    public List<List<String>> solveNQueens(int n) {
        int[] columnIndex = new int[n];
        for (int i = 0; i < n; ++i)
            columnIndex[i] = i;
        permutation(columnIndex, n, 0);
        return list;
    }
    void permutation(int columnIndex[], int length, int index) {
        if (index == length) {
            if (check(columnIndex, length)) {
                printQueen(columnIndex, length);
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
    void printQueen(int[] columnIndex, int length) {
        List<String> arr = new ArrayList<>();
        list.add(arr);
        for (int i = 0; i < length; ++i){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < length; j ++) {
                sb.append(j == columnIndex[i] ? "Q" : ".");
            }
            arr.add(sb.toString());
        }
//            System.out.print(String.format("%d\t", columnIndex[i]));

    }
}
