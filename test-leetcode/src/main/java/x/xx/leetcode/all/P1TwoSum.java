package x.xx.leetcode.all;

import x.xx.util.StringUtil;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target，
 * 找出 candidates 中所有可以使数字和为 target 的组合。
 * - candidates 中的数字可以无限制重复被选取。
 * - 所有数字（包括 target）都是正整数。
 * - 解集不能包含重复的组合。
 */
public class P1TwoSum {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int f = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if(nums[j] == f) return new int[] {i, j};
            }
        }
        return null;
    }


    public static void main(String[] args) {
        int[] ret = new P1TwoSum().twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(StringUtil.join(ret));
    }
}
