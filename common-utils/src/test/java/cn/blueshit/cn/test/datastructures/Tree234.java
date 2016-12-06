package cn.blueshit.cn.test.datastructures;

import lombok.Builder;
import lombok.Data;

/**
 * Created by zhaoheng on 16/11/6.
 * 可以转换为红黑树
 * 1,把只包含一个识别数据项和2个子节点的节点,转换为红黑树的黑色节点
 * 2,把只包含两个识别数据项和3个子节点的节点,转化成一个子节点和一个父节点
 *      哪个节点作为子节点或者父节点都可以,子节点有两个自己的子节点.这两个节点要求是原本相邻的子节点.父节点有另一个子节点,子节点涂成红色,父节点黑色
 * 3,把只包含3个数据项和4个子节点的节点,转化成一个父节点就是原本中间的识别数据项,两个节点和4个子孙节点,原来前面的两个子节点作为最小项的子节点
 * ,原来后面的两个子节点作为最大项的子节点,子节点涂层红色,父节点黑色
 */
public class Tree234 {

    private Node234 root = new Node234();


    public int find(int key) {
        Node234 current = root;
        int childIndex = -1;
        while (true) {
            int item = current.findItem(key);
            if (item >= 0) {
                return childIndex;
            } else if (current.isLeaf()) {
                return -1;
            } else {
                current = this.getNextChild(current, key);
            }

        }
    }

    private Node234 getNextChild(Node234 current, int key) {
        int j;
        //assumes node is not empty, not full and not leaf
        int numItems = current.getNumItems();
        for (j = 0; j < numItems; j++) {
            if (key < current.getItem(j).dData) {
                return current.getChild(j);
            }
        }
        return current.getChild(j);
    }

    //insert a DataItem
    public void insert(int data) {
        Node234 currentNode = root;
        DataItem tempItem = new DataItem(data);
        while (true) {
            if (currentNode.isFull()) {
                split(currentNode); //if node is full, split it
                currentNode = currentNode.getParent();  //back up
                currentNode = getNextChild(currentNode, data);  //search once
            } else if (currentNode.isLeaf()) { //if node if leaf
                break;  //go insert
            } else {
                currentNode = getNextChild(currentNode, data);
            }
        }
        currentNode.insertItem(tempItem);
    }


    public void split(Node234 currentNode) {
        //assumes node is full
        DataItem itemB, itemC;  //存储要分裂节点的后两个DataItem
        Node234 parent, child2, child3;   //存储要分裂节点的父节点和后两个child
        int itemIndex;
        itemC = currentNode.removeItem();
        itemB = currentNode.removeItem();   //remove items from this node
        child2 = currentNode.disconnectChild(2);
        child3 = currentNode.disconnectChild(3); //remove children from this node
        Node234 newRight = new Node234(); //make a new node
        //如果是根节点需要创建新的根节点,并维护
        if (currentNode == root) {
            root = new Node234(); //make a new root
            parent = root;  //root is our parent
            root.connectChild(0, currentNode);//connect currentNode to parent
        } else {
            parent = currentNode.getParent();
        }
        //中间数据移动到父节点
        itemIndex = parent.insertItem(itemB);   //insert B to parent
        //维护parent节点的child节点的关系
        int n = parent.getNumItems();   //total items
        for (int j = n - 1; j > itemIndex; j--) {
            //先断开 再重新加入
            Node234 temp = parent.disconnectChild(j);
            parent.connectChild(j + 1, temp);
        }
        //将新的兄弟节点加入父节点
        parent.connectChild(itemIndex + 1, newRight);
        //维护新的兄弟节点
        newRight.insertItem(itemC);
        newRight.connectChild(0, child2);
        newRight.connectChild(1, child3);
    }

    public void recDisplayTree(Node234 thisNode, int level, int childNumber) {
        System.out.print("level = " + level + " child = " + childNumber + " ");
        thisNode.displayNode();
        //call ourselves for each child of this node
        int numItems = thisNode.getNumItems();
        for (int j = 0; j < numItems + 1; j++) {
            Node234 nextNode = thisNode.getChild(j);
            if (nextNode != null) {
                recDisplayTree(nextNode, level + 1, j);
            } else
                continue;
        }
    }

}

@Data
@Builder
class DataItem {
    public long dData;

    public void displayItem() {
        System.out.print("/" + dData);
    }
}

class Node234 {
    private static final int ORDER = 4;
    //存放多个识别数据项
    private DataItem itemArray[] = new DataItem[ORDER - 1];//该节点中存放数据项的数组，每个节点最多存放三个数据项

    //子节点
    private Node234 childArray[] = new Node234[ORDER]; //存储子节点的数组，最多四个子节点

    private int numItems; //表示该节点存有多少个数据项
    private Node234 parent;//父节点

    //连接子节点
    public void connectChild(int childNum, Node234 child) {
        childArray[childNum] = child;
        if (child != null) {
            child.parent = this;
        }
    }

    //断开与子节点的连接，并返回该子节点
    public Node234 disconnectChild(int childNum) {
        Node234 tempNode = childArray[childNum];
        childArray[childNum] = null;
        return tempNode;
    }

    public Node234 getChild(int childNum) {
        return childArray[childNum];
    }

    public Node234 getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return (childArray[0] == null);
    }

    public int getNumItems() {
        return numItems;
    }

    public DataItem getItem(int index) {
        return itemArray[index];
    }

    public boolean isFull() {
        return (numItems == ORDER - 1);
    }

    public int findItem(long key) {
        for (int j = 0; j < ORDER - 1; j++) {
            if (itemArray[j] == null) {
                break;
            } else if (itemArray[j].dData == key) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 给本节点新加入一个识别数据项
     *
     * @param newItem
     * @return
     */
    public int insertItem(DataItem newItem) {
        //维护存放的识别数据项的个数
        numItems++;
        //assumes node is not full
        long newKey = newItem.dData;
        for (int j = ORDER - 2; j >= 0; j--) {  //start on right
            if (itemArray[j] == null) {      //item is null
                continue;                   //get left one cell
            } else {                          //not null
                long itsKey = itemArray[j].dData;   //get its key
                if (newKey < itsKey) {                //if it's bigger
                    itemArray[j + 1] = itemArray[j];  //shift it right
                } else {
                    itemArray[j + 1] = newItem;       //insert new item
                    return j + 1;                     //return index to new item
                }
            }
        }
        itemArray[0] = newItem;
        return 0;
    }

    public DataItem removeItem() {
        //assumes node not empty
        DataItem tempItem = itemArray[numItems - 1];  //save item
        itemArray[numItems - 1] = null;               //disconnect it
        numItems--;
        return tempItem;
    }

    public void displayNode() {
        for (int i = 0; i < numItems; i++) {
            itemArray[i].displayItem();
        }
        System.out.println("/");
    }

}
