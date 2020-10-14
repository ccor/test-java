package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
 */
public class P673AverageOfLevelsInBinaryTree {
    public List<Double> averageOfLevels(TreeNode root) {
        LinkedList<TreeNode> q = new LinkedList<>();
        List<Double> ans = new ArrayList<>();
        q.add(root);
        bfs(q, ans);
        return ans;
    }

    void bfs(LinkedList<TreeNode> q, List<Double> ans) {
        double sum = 0, n = q.size();
        for (int i = 0; i < n; i++) {
            TreeNode node = q.poll();
            sum += node.val;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        ans.add(sum / n);
        if (q.size() > 0) bfs(q, ans);
    }

    public static void main(String[] args) {

    }
}
