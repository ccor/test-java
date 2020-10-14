package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写一个程序，通过填充空格来解决数独问题。
 *
 * 一个数独的解法需遵循如下规则：
 *
 * 数字1-9在每一行只能出现一次。
 * 数字1-9在每一列只能出现一次。
 * 数字1-9在每一个以粗实线分隔的3x3宫内只能出现一次。
 * 空白格用'.'表示。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sudoku-solver
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class P37SudokuSolver {
    boolean[][] line = new boolean[9][9];
    boolean[][] column = new boolean[9][9];
    boolean[][][] block = new boolean[3][3][9];
    boolean valid = false;
    List<int[]> spaces = new ArrayList<>();

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[]{i, j});
                } else {
                    int d = board[i][j] - '0' - 1;
                    line[i][d] = column[j][d] = block[i / 3][j / 3][d] = true;
                }
            }
        }
        dfs(board, 0);
    }

    void dfs(char[][] board, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] p = spaces.get(pos);
        int i = p[0], j = p[1];
        for (int d = 0; d < 9 && !valid; d++) {
            if (!line[i][d] && !column[j][d] && !block[i / 3][j / 3][d]) {
                line[i][d] = column[j][d] = block[i / 3][j / 3][d] = true;
                board[i][j] = (char) ('0' + d + 1);
                dfs(board, pos + 1);
                line[i][d] = column[j][d] = block[i / 3][j / 3][d] = false;
            }
        }
    }
}
