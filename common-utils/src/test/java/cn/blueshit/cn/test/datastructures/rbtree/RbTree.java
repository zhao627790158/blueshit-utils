package cn.blueshit.cn.test.datastructures.rbtree;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhaoheng on 16/11/5.
 */
public class RbTree {

    private RbNode root;


    public static void main(String[] args) {
        RbTree tree = new RbTree();
       /* tree.insert(6, 2);
        tree.insert(5, 2);
        tree.insert(8, 2);
        tree.insert(3, 2);
        tree.insert(7, 2);
        tree.insert(9, 2);*/

        tree.insert(1, 2);
        tree.insert(2, 2);
        tree.insert(3, 2);
        tree.insert(4, 2);
        tree.insert(5, 2);
        tree.insert(6, 2);
        tree.preNode(tree.root);
    }

    /**
     * 加入新节点后对红黑树进行校验并调整
     *
     * @param nowNode
     */
    public void afterInsert(RbNode nowNode) {
        //1,如果插入的是根节点,那么违反规则2,就直接把节点修改为黑色
        if (nowNode.getParent() == null) {
            nowNode.setRed(false);
            root = nowNode;
        } else if (!nowNode.getParent().isRed()) {
            //2,如果插入的节点的父节点是黑色的,说明符合规则,什么也不做
        } else if (nowNode.getParent().isRed()) {
            RbNode grandParent = nowNode.getParent().getParent();
            RbNode uncle = null;
            if (null != grandParent) {
                //如果nownode的父节点是祖父节点的左节点那么 叔叔节点就是祖父节点的右节点, 反之则是左节点
                uncle = (grandParent.getLeftChild() == nowNode.getParent()) ? grandParent.getRightChild() : grandParent.getLeftChild();
            }
            if (null != uncle && uncle.isRed()) {
                //3,如果插入的节点的父节点是红色,且祖父节点的另一个节点(叔叔节点)也是红色,那么: 将祖父节点变成红色,而父和叔节点变成黑色,然后设置祖父节点为当前节点,然后重新开始判断.
                grandParent.setRed(true);
                nowNode.getParent().setRed(false);
                uncle.setRed(false);
                nowNode = grandParent;
                this.afterInsert(nowNode);
            } else if (null != uncle && !uncle.isRed()
                    && nowNode == nowNode.getParent().getLeftChild()
                    && nowNode.getParent() == grandParent.getLeftChild()) {
                //4,如果插入节点的父节点是红色,而叔叔节点是黑色,且插入节点是其父的左子节点,而父节点是祖父节点的左子节点,那么: 把父节点变成黑色,祖父节点变为红色,然后对祖父节点进行右旋,然后重新判断
                nowNode.getParent().setRed(false);
                grandParent.setRed(true);
                this.rightRotate(grandParent);
                this.afterInsert(nowNode);
            } else if (null != uncle && !uncle.isRed()
                    && nowNode.getParent() == grandParent.getRightChild()
                    && nowNode == nowNode.getParent().getRightChild()) {
                //5,如果插入的父节点是红色,而叔叔节点是黑色,且插入节点是其父的右子节点,而父节点是祖父节点的右子节点,那么: 把父节点变成黑色,祖父节点变为红色,然后对祖父节点进行左旋,然后重新判断.
                nowNode.getParent().setRed(false);
                grandParent.setRed(true);
                this.leftRotate(grandParent);
                this.afterInsert(nowNode);
            } else if (null != uncle && !uncle.isRed()
                    && nowNode == nowNode.getParent().getRightChild()
                    && nowNode.getParent() == grandParent.getLeftChild()) {
                //6,如果插入节点的父节点是红色,而叔叔节点是黑色,且插入节点是其父节点的右子节点,而父节点是祖父节点的左子节点,那么: 把当前节点的父节点作为新的当前节点,对新的当前节点进行左旋,然后重新开始判断.
              /*  RbNode oldParent = nowNode.getParent();
                //左旋之后 新的节点可能会发生变化 保险起见 保存下当前节点
                this.leftRotate(oldParent);
                this.afterInsert(oldParent);*/
                nowNode = nowNode.getParent();
                this.leftRotate(nowNode);
                this.afterInsert(nowNode);
            } else if (null != uncle && !uncle.isRed()
                    && nowNode == nowNode.getParent().getLeftChild()
                    && nowNode.getParent() == grandParent.getRightChild()) {
                //7,如果插入节点的父节点是红色,而叔叔节点是黑色,切插入节点是其父的左子节点,而父节点是祖父节点的右子节点,那么:把当前节点的父节点当做新的当前节点,对新的当前节点进行右旋,然后重新开始判断.
                nowNode = nowNode.getParent();
                this.rightRotate(nowNode);
                this.afterInsert(nowNode);
               /* RbNode oldParent = nowNode.getParent();
                this.rightRotate(oldParent);
                this.afterInsert(oldParent);*/
            }
        }
    }

    //右旋
    public void rightRotate(RbNode node) {
        //当前节点
        RbNode oldLeft = node.getLeftChild();
        //当前节点的左节点的右子节点
        RbNode oldLeftRight = null;
        if (null != oldLeft) {
            oldLeftRight = oldLeft.getLeftChild().getRightChild();
        }
        if (null != node.getParent()) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(oldLeft);
            } else {
                node.getParent().setRightChild(oldLeft);
            }
            if (null != oldLeft) {
                oldLeft.setParent(node.getParent());
            }
        } else {
            oldLeft.setParent(null);
            oldLeft.setRed(false);
            root = oldLeft;
        }
        if (oldLeft != null) {
            oldLeft.setRightChild(node);
        }
        node.setParent(oldLeft);
        node.setLeftChild(oldLeftRight);
        if (null != oldLeftRight) {
            oldLeftRight.setParent(node);
        }
    }

    public void leftRotate(RbNode node) {
        RbNode oldRight = node.getRightChild();
        RbNode oldRightLeft = null;
        if (null != oldRight) {
            oldRightLeft = oldRight.getLeftChild();
        }
        if (null != node.getParent()) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(oldRight);
            } else {
                node.getParent().setRightChild(oldRight);
            }
            if (null != oldRight) {
                oldRight.setParent(node.getParent());
            }
        } else {
            oldRight.setParent(null);
            oldRight.setRed(false);
            root = oldRight;
        }
        if (null != oldRight) {
            oldRight.setLeftChild(node);
        }
        node.setParent(oldRight);
        node.setRightChild(oldRightLeft);
        if (null != oldRightLeft) {
            oldRightLeft.setParent(node);
        }
    }

    public RbNode find(int key) {
        RbNode current = root;
        while (null != current && current.getId() != key) {
            if (current.getId() > key) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return current;
    }


    public void insert(int id, int data) {
        RbNode nodeNew = RbNode.builder().id(id).data(data).red(true).build();
        if (null == root) {
            root = nodeNew;
        } else {
            //查找要插入的位置
            RbNode current = root;
            RbNode parent = null;
            while (true) {
                parent = current;
                if (id < current.getId()) {
                    current = current.getLeftChild();
                    if (null == current) {
                        parent.setLeftChild(nodeNew);
                        //设置父节点
                        nodeNew.setParent(parent);
                        break;
                    }
                } else {
                    current = current.getRightChild();
                    if (null == current) {
                        parent.setRightChild(nodeNew);
                        //设置父节点
                        nodeNew.setParent(parent);
                        break;
                    }
                }
            }
        }
        //开始调整
        this.afterInsert(nodeNew);
    }

    public void preNode(RbNode node) {
        if (null != node) {
            String pid = StringUtils.EMPTY;
            if (null != node.getParent()) {
                pid = "" + node.getParent().getId();
            }
            System.out.println(node.getId() + "--parent:" + pid + "---isred:" + node.isRed());
            preNode(node.getLeftChild());
            preNode(node.getRightChild());
        }
    }

}
