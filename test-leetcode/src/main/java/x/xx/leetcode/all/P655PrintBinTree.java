package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一棵二叉树的根节点 root ，请你构造一个下标从 0 开始、大小为 m x n 的字符串矩阵 res ，用以表示树的 格式化布局 。构造此格式化布局矩阵需要遵循以下规则：
 *
 * 树的 高度 为 height ，矩阵的行数 m 应该等于 height + 1 。
 * 矩阵的列数 n 应该等于 2^height+1 - 1 。
 * 根节点 需要放置在 顶行 的 正中间 ，对应位置为 res[0][(n-1)/2] 。
 * 对于放置在矩阵中的每个节点，设对应位置为 res[r][c] ，将其左子节点放置在 res[r+1][c-2^height-r-1] ，右子节点放置在 res[r+1][c+2^height-r-1] 。
 * 继续这一过程，直到树中的所有节点都妥善放置。
 * 任意空单元格都应该包含空字符串 "" 。
 * 返回构造得到的矩阵 res 。
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date 2022/8/22 14:52
 */
public class P655PrintBinTree {

    public List<List<String>> printTree(TreeNode root) {
        int h = getHeight(root);
        int n = (1 << (h + 1)) - 1;
        int m = h + 1;
        List<List<String>> res = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            List<String> list = new ArrayList<>();
            res.add(list);
            for(int j = 0; j < n; j++) {
                list.add("");
            }
        }
        dfs(res, root, 0, (n-1) / 2, h);
        return res;
    }

    void dfs(List<List<String>> res, TreeNode node, int r, int c, int h) {
        res.get(r).set(c, String.valueOf(node.val));
        if(node.left != null) dfs(res, node.left, r + 1, c - (1 << (h - r - 1)), h);
        if(node.right != null) dfs(res, node.right, r + 1, c + (1 << (h - r - 1)), h);
    }

    int getHeight(TreeNode node){
        int h = 0;
        if(node.left != null)
            h =  Math.max(h, getHeight(node.left) + 1);
        if(node.right != null)
            h =  Math.max(h, getHeight(node.right) + 1);
        return h;

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        System.out.println(new P655PrintBinTree().printTree(root));
    }
}
