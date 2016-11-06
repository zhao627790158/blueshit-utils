package cn.blueshit.cn.test.datastructures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by zhaoheng on 16/11/6.
 */
public class Tree234 {


}

@Data
@Builder
class DataItem {
    public long dData;

    public void displayItem() {
        System.out.print("/" + dData);
    }
}

class Node2 {
    private static final int ORDER = 4;
    //存放多个识别数据项
    private DataItem itemArray[] = new DataItem[ORDER - 1];//该节点中存放数据项的数组，每个节点最多存放三个数据项

    //子节点
    private Node2 childArray[] = new Node2[ORDER]; //存储子节点的数组，最多四个子节点

    private int numItems; //表示该节点存有多少个数据项
    private Node2 parent;//父节点

    //连接子节点
    public void connectChild(int childNum, Node2 child) {
        childArray[childNum] = child;
        if (child != null) {
            child.parent = this;
        }
    }

    //断开与子节点的连接，并返回该子节点
    public Node2 disconnectChild(int childNum) {
        Node2 tempNode = childArray[childNum];
        childArray[childNum] = null;
        return tempNode;
    }

    public Node2 getChild(int childNum) {
        return childArray[childNum];
    }

    public Node2 getParent() {
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
