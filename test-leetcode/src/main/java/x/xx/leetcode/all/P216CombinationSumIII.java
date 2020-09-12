package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.List;

/**
 * 找出所有相加之和为 n 的 k 个数的组合。
 * 组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 */
public class P216CombinationSumIII {
    List<List<Integer>> ans = new ArrayList<>();
    int[] nums = new int[]{1,2,3,4,5,6,7,8,9};
    public List<List<Integer>> combinationSum3(int k, int n) {

        dfs(n, k, 0, new ArrayList<>());
        return ans;
    }

    void dfs(int t, int k, int i, List<Integer> a) {
        if( t== 0 && k == 0)  ans.add(new ArrayList<>(a));
        if( t < 0 || k < 0) return;
        for (int j = i; j < 9; j++) {
            if(nums[j] <= t && k > 0){
                a.add(nums[j]);
                dfs(t - nums[j], k - 1, j + 1, a);
                a.remove(a.size() - 1);
            }else{
                return;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new P216CombinationSumIII().combinationSum3(3, 7));
    }
}
