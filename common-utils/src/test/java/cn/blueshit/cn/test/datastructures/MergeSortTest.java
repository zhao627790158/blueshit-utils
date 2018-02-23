package cn.blueshit.cn.test.datastructures;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;


/**
 * Created by zhaoheng on 18/2/22.
 */
public class MergeSortTest {

    private static final String testFinal = StringUtils.EMPTY;


    @Test
    public void testSort() {
        int a[] = new int[100];
        for (int i = 0; i < 100; i++) {
            a[i] = RandomUtils.nextInt(1000);
        }
        Arrays.sort(a);
        ArrayUtils.reverse(a);
        for (int j : a) {
            System.out.println(j);
        }
    }



    public static void main(String[] args) {

    }
}
