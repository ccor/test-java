package x.xx.leetcode.all;

import x.xx.util.StringUtil;

import java.util.*;

/**
 *
 */
public class P347TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(o -> map.getOrDefault(o, 0)));
        for(int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for (int i : map.keySet()) {
            if(queue.size() == k) {
                if(map.get(queue.peek()) < map.get(i)){
                    queue.poll();
                    queue.offer(i);
                }
            }else {
                queue.offer(i);
            }
        }
        int[] ret = new int[k];
        for (int i = 0; i < k; i++) {
            ret[i] = queue.poll();
        }
        return ret;
    }

    public static void main(String[] args) {
        int [] ret = new P347TopKFrequentElements().topKFrequent(new int[] {3,0,1,0}, 1);
        System.out.println(StringUtil.join(ret));
    }
}
