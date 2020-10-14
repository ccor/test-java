package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class P106ConstructBinaryTreeFromInorderAndPostorderTraversal {
    int idx;
    int[] postorder;
    Map<Integer, Integer> idxMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.postorder = postorder;
        idx = postorder.length - 1;
        for (int i = 0; i < inorder.length; i++) {
            idxMap.put(inorder[i], i);
        }
        return helper(0, inorder.length - 1);
    }

    TreeNode helper(int l, int r) {
        if(l > r) return null;
        TreeNode root =  new TreeNode(postorder[idx]);
        int p = idxMap.get(postorder[idx]);
        idx --;
        // 注意，需要先创建右子树
        root.right = helper(p + 1, r);
        root.left = helper(l , p - 1);
        return root;
    }
}
