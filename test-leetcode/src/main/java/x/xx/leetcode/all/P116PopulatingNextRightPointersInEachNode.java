package x.xx.leetcode.all;

public class P116PopulatingNextRightPointersInEachNode {
    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        if (root == null) return null;
        Node node = lnk(root);
        while (node != null) {
            node = lnk(node);
        }
        return root;
    }

    public Node lnk(Node node) {
        Node head = node.left;
        while (node != null && node.left != null) {
            node.left.next = node.right;
            if (node.next != null) {
                node.right.next = node.next.left;
            }
            node = node.next;
        }
        return head;
    }
}
