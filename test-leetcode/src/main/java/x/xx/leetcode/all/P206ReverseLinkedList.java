package x.xx.leetcode.all;

import x.xx.leetcode.common.ListNode;

import java.util.List;

/**
 * 反转一个单链表。
 *  输入: 1->2->3->4->5->NULL
 *  输出: 5->4->3->2->1->NULL
 */
public class P206ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, cur = head, tmp;
        while(cur != null){
            tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }
}
