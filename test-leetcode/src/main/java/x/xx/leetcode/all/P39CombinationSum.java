package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P39CombinationSum {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates,0, target, new ArrayList<>());
        return ans;
    }

    void dfs(int[] nums, int b, int t, List<Integer> arr) {
        for (int i = b; i < nums.length; i++) {
            if(nums[i] < t) {
                arr.add(nums[i]);
                dfs(nums, i,t - nums[i], arr);
                arr.remove(arr.size() - 1);
            }else if(nums[i] == t) {
                arr.add(nums[i]);
                ans.add(new ArrayList<>(arr));
                arr.remove(arr.size() - 1);
                return;
            }else return;
        }
    }

    public static void main(String[] args) {
        System.out.println(new P39CombinationSum().combinationSum(new int[]{2,3,6,7}, 7));
        System.out.println(new P39CombinationSum().combinationSum(new int[]{2,3,5}, 8));
    }

}
