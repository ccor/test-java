package x.xx.leetcode.all;

import x.xx.leetcode.common.ListNode;

import java.util.List;

public class P25ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode p = new ListNode(0);
        p.next = head;
        dfs(p, head, k);
        return p.next;
    }
    void dfs(ListNode p, ListNode head, int k) {
        ListNode tail = head;
        int i = 0;
        // 尝试往后遍历k个
        while(i < k && tail != null){
            tail = tail.next;
            i ++;
        }

        //如果遍历了k个
        if(i == k) {
            ListNode curr = head;
            ListNode prev = null;
            while(curr != tail) {
                ListNode t = curr.next;
                curr.next = prev;
                prev = curr;
                curr = t;
            }
            // 把前一段的尾部指针连到当前的头结点（反转过的）
            if(p != null) p.next = prev;
            //继续向后
            dfs(head, tail, k);
        }else{
            // 如果不够k个，把前一个尾指针连到当前头结点
            if(p != null) p.next = head;
        }

    }

    public static void main(String[] args) {
        ListNode node = new P25ReverseNodesInKGroup().reverseKGroup(ListNode.of(new int[]{1,2,3,4,5}), 2);
        System.out.println(node.toList());

    }
}
