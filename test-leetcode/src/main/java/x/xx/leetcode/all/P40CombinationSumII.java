package x.xx.leetcode.all;

import java.util.*;

public class P40CombinationSumII {
    List<List<Integer>> ans = new ArrayList<>();
    Set<String> set = new HashSet<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>());
        return ans;
    }

    void dfs(int[] nums, int t, int i, List<Integer> a) {
        for (int j = i; j < nums.length; j++) {
            if (nums[j] <= t) {
                a.add(nums[j]);
                System.out.println(a);
                if (nums[j] == t && set.add(join(a))) {
                    ans.add(new ArrayList<>(a));
                }
                dfs(nums, t - nums[j], j + 1, a);
                a.remove(a.size() - 1);
            } else {
                return;
            }
        }
    }

    String join(List<Integer> a) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : a) {
            sb.append(i).append(",");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new P40CombinationSumII().combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
    }
}
