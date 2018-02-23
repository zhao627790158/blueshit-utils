package cn.blueshit.cn.test.filter;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by zhaoheng on 18/2/5.
 */
public class IpSortTest {

    private List<String> serveList = Lists.newArrayList();

    static {

    }


    @Test
    public void testIpSort() throws Exception {
        /**返回本地主机。*/
        InetAddress addr = InetAddress.getLocalHost();
        /**返回 IP 地址字符串（以文本表现形式）*/
        String ip = addr.getHostAddress();
        System.out.println(ip);

        byte[] address = addr.getAddress();
        for (byte i : address) {
            System.out.println(i);
            System.out.println(i & 0xFF);
        }
        Integer x = 172;
        System.out.println(x.byteValue());


    }

}
