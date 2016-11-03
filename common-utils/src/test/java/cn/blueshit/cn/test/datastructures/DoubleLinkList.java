package cn.blueshit.cn.test.datastructures;

import lombok.Builder;
import lombok.Data;

/**
 * Created by zhaoheng on 16/11/3.
 */
public class DoubleLinkList {

    private LinkNode first;

    private LinkNode last;


    public void insertFirst(int id) {
        LinkNode newLinkNode = LinkNode.builder().id(id).build();
        if (isEmpty()) {
            first = last = newLinkNode;
        } else {
            first.setPrevious(newLinkNode);
        }
        newLinkNode.setNext(first);
        first = newLinkNode;
    }

    public void insertLast(int id) {
        LinkNode newLinkNode = LinkNode.builder().id(id).build();
        if (isEmpty()) {
            first = last = newLinkNode;
        } else {
            last.setNext(newLinkNode);
        }
        newLinkNode.setPrevious(last);
        last = newLinkNode;
    }

    public LinkNode remoteFirst() {
        LinkNode oldFirst = first;
        if (!isEmpty()) {
            if (first.getNext() == null) {
                first = last = null;
            } else {
                first.getNext().setPrevious(null);
                first = first.getNext();
            }
        }
        return oldFirst;
    }

    public boolean isEmpty() {
        return first == null;
    }

}


@Data
@Builder
class LinkNode {
    private int id;

    private LinkNode previous;

    private LinkNode next;


}
