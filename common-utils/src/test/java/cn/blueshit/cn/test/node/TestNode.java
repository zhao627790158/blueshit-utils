package cn.blueshit.cn.test.node;

/**
 * Created by zhaoheng on 2016/7/11.
 */
public class TestNode {


    public static Node reverse(Node head) {
        if (null == head) {
            return head;
        }
        //前一个节点
        Node pre = head;
        //当前节点
        Node cur = head.getNextNode();
        Node next;//下一个节点
        while (null != cur) {
            next = cur.getNextNode();
            cur.setNextNode(pre);
            pre = cur;
            cur = next;
        }
        //将原链表的头节点的下一个节点置为null，再将反转后的头节点赋给head
        head.setNextNode(null);
        head = pre;
        return head;//反转链表
    }

    public static Node reverse2(Node head) {
        if (null == head) {
            return head;
        }
        Node pre = head;
        Node cur = head.getNextNode();
        Node temp = null;
        while (null != cur) {
            //保留下一节点
            temp = cur.getNextNode();
            //反转指针
            cur.setNextNode(pre);
            //移动指针
            pre = cur;
            cur = temp;
        }
        //将原链表的头节点的下一个节点置为null，再将反转后的头节点赋给head
        head.setNextNode(null);
        head = pre;
        return head;
    }


    public static void main(String[] args) {
        Node head = new Node(0);
        Node tmp = null;
        Node cur = null;
        // 构造一个长度为10的链表，保存头节点对象head
        for (int i = 1; i < 10; i++) {
            tmp = new Node(i);
            if (1 == i) {
                head.setNextNode(tmp);
            } else {
                cur.setNextNode(tmp);
            }
            cur = tmp;
        }
        //打印反转前的链表
        Node h = head;
        while (null != h) {
            System.out.print(h.getRecord() + " ");
            h = h.getNextNode();
        }
        //调用反转方法
        head = reverse2(head);
        System.out.println("\n**************************");
        //打印反转后的结果
        while (null != head) {
            System.out.print(head.getRecord() + " ");
            head = head.getNextNode();
        }
    }

}
