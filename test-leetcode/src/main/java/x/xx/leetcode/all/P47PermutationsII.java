package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 */
public class P47PermutationsII {
    boolean[] vis;
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<Integer> perm = new ArrayList<Integer>();
        vis = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, 0, new ArrayList<Integer>());
        return ans;
    }

    private void backtrack(int[] nums, int idx, ArrayList<Integer> list) {
        if(idx == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 当重复数字时，第一个未填入的数字
            if(vis[i] || (i > 0 && nums[i] == nums[i-1] && !vis[i-1])){
                continue;
            }
            list.add(nums[i]);
            vis[i] = true;
            backtrack(nums, idx + 1, list);
            vis[i] = false;
            list.remove(idx);
        }
    }
}
