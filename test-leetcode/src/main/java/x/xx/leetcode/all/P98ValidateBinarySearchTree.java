package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

/**
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @date 2021/4/3 12:52
 */
public class P98ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    boolean validate(TreeNode node, TreeNode upper, TreeNode lower) {
        return node == null || ((upper == null || node.val < upper.val)
                && (lower == null || node.val > lower.val)
                && validate(node.left, node, lower)
                && validate(node.right, upper, node));
    }
}
