package x.xx.leetcode.all;

/**
 * 一个 n x n 的二维网络 board 仅由 0 和 1 组成 。每次移动，你能任意交换两列或是两行的位置。
 *
 * 返回 将这个矩阵变为 “棋盘”所需的最小移动次数。如果不存在可行的变换，输出 -1。
 *
 * “棋盘” 是指任意一格的上下左右四个方向的值均与本身不同的矩阵。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/transform-to-chessboard
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date 2022/8/23 14:31
 */
public class P782TransformToChessboard {
    public int movesToChessboard(int[][] board) {
        int n = board.length;
        int mask = (1 << n) -1;
        int r1 = -1, r2 = -1, c1 = -1, c2 = -1;
        for(int i = 0; i < n; i ++) {
            int a = 0, b = 0;
            for (int j = 0; j < n; j++) {
                if(board[i][j] == 1) a |= (1 << j);
                if(board[j][i] == 1) b |= (1 << j);
            }
            if(r1 == -1) {
                r1 = a;
                r2 = a ^ mask;
                if(Integer.bitCount(r1) + Integer.bitCount(r2) != n) {
                    return -1;
                }
            } else if(a != r1 && a != r2) {
                return -1;
            }
            if(c1 == -1) {
                c1 = b;
                c2 = b ^ mask;
                if(Integer.bitCount(r1) + Integer.bitCount(r2) != n) {
                    return -1;
                }
            } else if(b != c1 && b != c2) {
                return -1;
            }
        }
        int t = 0;
        for(int i = 0; i < n; i+=2) {
            t |= (1 << i);
        }
        int s1 = step(r1, r2, t);
        int s2 = step(c1, c2, t);
        if(s1 > -1 && s2 > -1) {
            return s1 + s2;
        }

        return -1;
    }

    int step(int a, int b, int t) {
        int tn = Integer.bitCount(t);
        int s = -1;
        if(Integer.bitCount(a) == tn)
            s = Integer.bitCount(a ^ t) / 2;
        if(Integer.bitCount(b) == tn)
            s = s > -1 ? Math.min( s, Integer.bitCount(b ^ t) / 2) : Integer.bitCount(b ^ t) / 2;
        return s;
    }

    public static void main(String[] args) {
        int i = new P782TransformToChessboard().movesToChessboard(new int[][] { {0, 1, 1}, {0, 1, 1}, {1, 0, 0}});
        System.out.println(i);
        int j = new P782TransformToChessboard().movesToChessboard(new int[][] { {0,1,1,0}, {0,1,1,0}, {1,0,0,1}, {1,0,0,1}});
        System.out.println(j);
    }
}
