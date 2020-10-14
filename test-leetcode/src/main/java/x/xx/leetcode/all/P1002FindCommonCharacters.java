package x.xx.leetcode.all;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P1002FindCommonCharacters {
    public List<String> commonChars(String[] A) {
        Map<String, Integer> map = new HashMap<>();
        // 计数第一组字符
        for (char c : A[0].toCharArray()) {
            map.merge(String.valueOf(c), 1, Integer::sum);
        }
        for (int i = 1; i < A.length; i ++) {
            // 后续每组字符与前一次
            Map<String, Integer> map2 = new HashMap<>();
            for (char c : A[i].toCharArray()) {
                String k = String.valueOf(c);
                // 对前一组计数中出现的字符计数开始-1
                Integer n = map.computeIfPresent(k, (s, j) -> j - 1);
                // 若存在且在前一组计数未减到小于0，则在新的计数组中+1
                if(n != null && n >= 0) map2.merge(k, 1, Integer::sum);
            }
            // 使用最新的计数
            map = map2;
        }
        List<String> ans = new ArrayList<>();
        for(String k : map.keySet()){
            int i = map.get(k);
            while(i > 0){
                ans.add(k);
                i --;
            }
        }
        return ans;
    }
}
