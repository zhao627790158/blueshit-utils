package cn.blueshit.cn.test.datastructures.rbtree;

import lombok.Builder;
import lombok.Data;

/**
 * Created by zhaoheng on 16/11/5.
 * 用来红黑
 */
@Data
@Builder
public class RbNode {

    private int id;
    private int data;
    private RbNode leftChild;
    private RbNode rightChild;

    //颜色
    private boolean red;

    //父节点
    private RbNode parent;


}
