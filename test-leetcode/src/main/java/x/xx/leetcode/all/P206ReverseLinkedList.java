package x.xx.leetcode.all;

import x.xx.leetcode.common.ListNode;

import java.util.List;

public class P206ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while(head != null){
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
