package x.xx.leetcode.all;

import sun.jvm.hotspot.gc_implementation.parallelScavenge.PSYoungGen;
import x.xx.util.StringUtil;

import java.util.*;

public class P79WordSearch {
    Map<Character, List<int[]>> letters = new HashMap<>();
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                List<int[]> pos = letters.computeIfAbsent(c, key -> new ArrayList<>());
                pos.add(new int[]{i, j, 0});
            }
        }
        List<List<int[]>> list = new ArrayList<>();
        for (char c : word.toCharArray()) {
            List<int[]> a = letters.get(c);
            if(a == null) return false;
            list.add(a);
        }
        return dfs(list, 0, null);
    }

    boolean dfs(List<List<int[]>> list, int i, int[] prev) {
        if (i == list.size()) return true;
        List<int[]> pos = list.get(i);
        for (int[] p : pos) {
            if (prev != null && !near(prev, p)) continue;
            if (p[2] == 0) {
                p[2] = 1;
                if (dfs(list, i + 1, p)) return true;
                p[2] = 0;
            }
        }
        return false;
    }

    boolean near(int[] p1, int[] p2) {
        return  (Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1])) == 1;
    }

    public static void main(String[] args) {
//        boolean r = new P79WordSearch().exist(new char[][]{
//                {'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}
//        }, "ABCB");
//        System.out.println(r);
//        boolean r = new P79WordSearch().exist(new char[][]{
//                {'b'}, {'a'}, {'b'}, {'b'}, {'a'}
//        }, "baa");
//        System.out.println(r);

        boolean r = new P79WordSearch().exist(new char[][]{
                {'a', 'b'}, {'c', 'd'}
        }, "abcd");
        System.out.println(r);
    }
}
