package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class P133PathSumII {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null) return ans;
        dfs(root, sum, new ArrayList<>());
        return ans;
    }

    void dfs(TreeNode node, int sum, List<Integer> list) {
        sum -= node.val;
        list.add(node.val);
        if(node.left == null && node.right == null) {
            if(sum == 0) {
                ans.add(new ArrayList<>(list));
            }
        }else{
            if(node.left != null){
                dfs(node.left, sum, list);
                list.remove(list.size()-1);
            }
            if(node.right != null) {
                dfs(node.right, sum, list);
                list.remove(list.size()-1);
            }
        }
    }
}
