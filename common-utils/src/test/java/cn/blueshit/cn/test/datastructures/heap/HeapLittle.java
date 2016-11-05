package cn.blueshit.cn.test.datastructures.heap;

import com.google.common.base.Stopwatch;
import lombok.Data;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoheng on 16/11/5.
 * top K问题
 * 1亿个数中找出最大or最小的10个 使用最小堆
 *
 * 可能是想让了解map reduce
 * top K问题很适合采用MapReduce框架解决，http://blog.csdn.net/zyq522376829/article/details/47686867
 */
@Data
public class HeapLittle {

    static Random random = new Random();
    private Node[] heapArray;
    private int maxSize;
    private int currentSize;

    public HeapLittle(int maxSize) {
        this.maxSize = maxSize;
        this.heapArray = new Node[maxSize];
    }

    public static void main(String[] args) {
        HeapLittle heap = new HeapLittle(10);
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
        System.out.println("-----------");

        Stopwatch startedTwo = Stopwatch.createStarted();
        for (int i = 0; i < 100000000; i++) {
            int tempNum = RandomUtils.nextInt(Integer.MAX_VALUE);
            if (heap.peek().getId() < tempNum) {
                heap.remote();
                heap.insert(Node.builder().id(tempNum).build());
            }
        }
        for (int i = 0; i < heap.maxSize; i++) {
            Node remote = heap.remote();
            if (null == remote) {
                break;
            }
            System.out.println(remote.getId());
        }
        System.out.println(startedTwo.elapsed(TimeUnit.MILLISECONDS));


    }


    //小顶堆
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
        while (index > 0 && heapArray[parent].getId() > bottom.getId()) {
            //如果父节点比 插入节点小 父节点下沉
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
        int littleChildIndex = -1;
        Node top = heapArray[index];
        //因为不需要比较叶子节点
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            if (rightChild < currentSize && this.heapArray[leftChild].getId() > this.heapArray[rightChild].getId()) {
                littleChildIndex = rightChild;
            } else {
                littleChildIndex = leftChild;
            }
            if (top.getId() < heapArray[littleChildIndex].getId()) {
                break;
            }
            this.heapArray[index] = this.heapArray[littleChildIndex];
            index = littleChildIndex;
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
