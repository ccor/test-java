package x.xx.leetcode.all;

import x.xx.util.StringUtil;

/**
 * 在本问题中，有根树指满足以下条件的有向图。
 * 该树只有一个根节点，所有其他节点都是该根节点的后继。
 * 每一个节点只有一个父节点，除了根节点没有父节点。
 *
 * 输入一个有向图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。
 * 附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 *
 * 结果图是一个以边组成的二维数组。
 * 每一个边 的元素是一对 [u, v]，用以表示有向图中连接顶点 u 和顶点 v 的边，其中 u 是 v 的一个父节点。
 *
 * 返回一条能删除的边，使得剩下的图是有N个节点的有根树。
 * 若有多个答案，返回最后出现在给定二维数组的答案。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/redundant-connection-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class P685RedundantConnectionII {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        UnionFind uf = new UnionFind(n + 1);
        // 记录每个节点的父节点,初始值为自己
        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        int conflict = -1;
        int cycle = -1;
        for (int i = 0; i < n; i++) {
            int p1 = edges[i][0], p2 = edges[i][1];
            // 已经记录过父节点了，说明有两个父节点，标记冲突的边
            if(parent[p2] != p2) {
                conflict = i;
            }else{
                // 记录父节点
                parent[p2] = p1;
                // 查找环,是否有相同根节点，记录导致环的边
                if (uf.find(p1) == uf.find(p2)) cycle = i;
                // 没有，则在并查集合并该边联通的两个节点
                else uf.union(p1, p2);
            }
        }
        // 由于假设题是有解的
        // 没有冲突边，则返回导致环的边
        if(conflict < 0) {
            return edges[cycle];
        }else{
            // 有冲突边 [u, v]，另一条边是[parent[v], v]
            int[] conflictEdge = edges[conflict];
            if(cycle >= 0) {
                // 如果还存在环，就是另一边（注意不一定是导致环的边，因为导致环的边可能是环中任何一条边）
                return new int[] {parent[conflictEdge[1]], conflictEdge[1]};
            }else {
                // 不在环，就返回靠后的冲突边
                return conflictEdge;
            }
        }
    }

    public static void main(String[] args) {
        int[] r =new P685RedundantConnectionII()
                .findRedundantDirectedConnection(StringUtil.ofIntArr2("[[2,1],[3,1],[4,2],[1,4]]"));
        System.out.println(StringUtil.join(r));
    }

    /**
     * 并查集
     */
    class UnionFind {
        // 以节点值标识的集合
        int[] ancestor;

        UnionFind(int n) {
            ancestor = new int[n];
            for (int i = 0; i < n; i++) {
                ancestor[i] = i;
            }
        }

        // 合并集合
        void union(int a, int b) {
            ancestor[find(a)] = find(b);
        }

        // 找集合（以祖先节点标识）
        int find(int index) {
            if (ancestor[index] != index) {
                ancestor[index] = find(ancestor[index]);
            }
            return ancestor[index];
        }
    }
}
