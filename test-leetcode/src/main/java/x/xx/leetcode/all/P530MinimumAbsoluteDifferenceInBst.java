package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.LinkedList;

public class P530MinimumAbsoluteDifferenceInBst {
    public int getMinimumDifference(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        int pval = -1, ans = Integer.MAX_VALUE;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (pval != -1) {
                int d = node.val - pval;
                if (d < ans) ans = d;
            }
            pval = node.val;
            node = node.right;
        }
        return ans;
    }
}
