package cn.zh.blueshit.jar;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

/**
 * Created by zhaoheng on 19/1/2.
 */
public class ConsoleStart {


    public static void main(String[] args) {
        System.out.println("console start ...... " + args);
        boolean empty = CollectionUtils.isEmpty(new ArrayList());
        System.out.println(empty);
    }

}
