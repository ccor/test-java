package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class P94BinaryTreeInorderTraversal {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while(node !=null || !stack.isEmpty()){
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node.val);
            node = node.right;
        }
        return list;
    }


    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        dfs(root, list);
        return list;
    }
    void dfs(TreeNode node, List<Integer> list) {
        if(node.left != null){
            dfs(node.left, list);
        }
        list.add(node.val);
        if(node.right != null){
            dfs(node.right, list);
        }
    }
}
