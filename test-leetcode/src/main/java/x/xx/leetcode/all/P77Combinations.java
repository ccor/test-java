package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.List;

public class P77Combinations {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        if(k > n) return ans;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        dfs(nums, 0, k , new ArrayList<>());
        return ans;
    }
    void dfs(int[] nums, int i, int k, List<Integer> a){
        if(k == 0) ans.add(new ArrayList<>(a));
        int n = nums.length;
        for (int j = i; j < n; j++) {
            if(n - j >= k -1) {
                a.add(nums[j]);
                dfs(nums, j + 1, k - 1, a);
                a.remove(a.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        P77Combinations app = new P77Combinations();
        System.out.println(app.combine(4, 2));
    }
}
