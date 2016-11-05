package cn.blueshit.cn.test.datastructures.heap;

import lombok.Builder;
import lombok.Data;

import javax.swing.text.html.HTML;
import java.util.Random;

/**
 * Created by zhaoheng on 16/11/5.
 */
@Data
public class Heap {

    static Random random = new Random();
    private Node[] heapArray;
    private int maxSize;
    private int currentSize;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.heapArray = new Node[maxSize];
    }

    public static void main(String[] args) {
        Heap heap = new Heap(10);
        heap.insert(Node.builder().id(9).build());
        heap.insert(Node.builder().id(19).build());
        heap.insert(Node.builder().id(29).build());
        heap.insert(Node.builder().id(39).build());
        heap.insert(Node.builder().id(49).build());
        heap.insert(Node.builder().id(59).build());
        heap.insert(Node.builder().id(69).build());
        heap.insert(Node.builder().id(169).build());
        heap.insert(Node.builder().id(269).build());
        heap.insert(Node.builder().id(369).build());
        heap.display();
        heap.remote();
        heap.remote();
        heap.remote();
        System.out.println("-----------");
        heap.display();
        for (int i = 0; i < 10; i++) {
            System.out.println(heap.remote().getId());
        }
    }


    //大顶堆
    public void insert(Node node) {
        //插入到数据末尾
        if (currentSize == maxSize) {
            System.out.println("已经到达末尾,不能插入新数据了");
            return;
        }

        this.heapArray[currentSize] = node;
        //向上筛选
        this.upNode(currentSize);
        currentSize++;
    }

    public void upNode(int index) {
        int parent = (index - 1) / 2;
        Node bottom = heapArray[index];
        while (index > 0 && heapArray[parent].getId() < bottom.getId()) {
            //如果父节点比 插入节点大 父节点下沉
            this.heapArray[index] = this.heapArray[parent];
            index = parent;
            parent = (index - 1) / 2;
        }
        this.heapArray[index] = bottom;
    }

    public Node remote() {
        Node root = this.heapArray[0];
        //把最后一个节点移动到根位置
        currentSize--;
        this.heapArray[0] = heapArray[currentSize];
        heapArray[currentSize] = null;
        //向下筛选
        this.downNode(0);
        return root;
    }

    public Node peek() {
        return heapArray[0];
    }

    public void downNode(int index) {
        int largeChildIndex = -1;
        Node top = heapArray[index];
        //因为不需要比较叶子节点
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            if (rightChild < currentSize && this.heapArray[leftChild].getId() < this.heapArray[rightChild].getId()) {
                largeChildIndex = rightChild;
            } else {
                largeChildIndex = leftChild;
            }
            if (top.getId() >= heapArray[largeChildIndex].getId()) {
                break;
            }
            this.heapArray[index] = this.heapArray[largeChildIndex];
            index = largeChildIndex;
        }
        this.heapArray[index] = top;
    }

    public void display() {
        for (Node node : heapArray) {
            if (null != node) {
                System.out.println(node.getId());
            }
        }
    }

}
