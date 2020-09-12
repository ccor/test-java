package x.xx.leetcode.common;

import java.util.ArrayList;
import java.util.List;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }
    public static ListNode of(int[] arr){
        if(arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode node = head;
        for (int i = 1; i < arr.length; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return head;
    }

    public List<Integer> toList(){
        int c = 0;
        List<Integer> list = new ArrayList<>();
        list.add(val);
        ListNode node = next;
        while(node != null && c < 1000){
            list.add(node.val);
            node = node.next;
        }
        return list;
    }
}
