package x.xx.leetcode.all;

/**
 * @date 2020/12/12 10:34
 */
public class P376 {
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if(n < 2) return n;
        int prevDiff = nums[1] - nums[0];
        int r = prevDiff != 0 ? 2 : 1;
        for(int i = 2; i < n; i++){
            int diff = nums[i] - nums[i-1];
            if(diff > 0 && prevDiff <= 0 || diff < 0 && prevDiff >= 0) {
                r ++;
                prevDiff = diff;
            }
        }
        return r;
    }
}
