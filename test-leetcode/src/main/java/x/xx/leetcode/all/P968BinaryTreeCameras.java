package x.xx.leetcode.all;

import x.xx.leetcode.common.TreeNode;

public class P968BinaryTreeCameras {
    int ans = 0;
    public int minCameraCover(TreeNode root) {
        if(dfs(root) == 0) ans++;
        return ans;
    }

    //0=>这个结点待覆盖
    //1=>这个结点已经覆盖
    //2=>这个结点上安装了相机
    int dfs(TreeNode node){
        if(node == null) return 1;
        int l = dfs(node.left), r = dfs(node.right);
        if(l == 0 || r == 0){
            ans ++;
            return 2;
        }
        if(l == 1 && r == 1){
            return 0;
        }
        if(l + r >= 3){
            return 1;
        }
        return -1;
    }

}
