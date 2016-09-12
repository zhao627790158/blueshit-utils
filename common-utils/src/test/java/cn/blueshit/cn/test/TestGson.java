package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.DescribeLVBResponse;
import cn.zh.blueshit.common.GsonUtils;
import cn.zh.blueshit.httpclient.HttpClient3Utils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.junit.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by zhaoheng on 2016/6/9.
 */
public class TestGson {


    @org.junit.Test
    public void test4() {
        float datePrice = 900;
        int floorPrice = (int) Math.floor((datePrice / 100 + 1) * 1.01);
        int ceilingPrice = (int) Math.floor((datePrice / 100 + 1) * 1.1);
        System.out.println("floor:" + floorPrice);
        System.out.println("cell:" + ceilingPrice);
        if (ceilingPrice - floorPrice > 0) {
            System.out.println((new Random().nextInt(ceilingPrice - floorPrice) + floorPrice) * 100);
        }

        List<Integer> test = Lists.newArrayList(1, 4, 6, 18, 4);
        Collections.sort(test, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (Integer i : test) {
            System.out.println(i);
        }
    }


    @org.junit.Test
    public void test3() {
        String descTemplate = "含%s早%s";
        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        String[] tdArr = {"a", "b", "c"};
        String result = MessageFormat.format("<tr bgcolor='#cef'><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td></tr>", list.toArray());
        System.out.println(result);

    }

    @org.junit.Test
    public void testGson() {
        /*User user = new User();

        user.setUserName("blueShit senior ");
        long t1 = System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            user.setUserId(i);
           System.out.print(GsonUtils.toJson(user)+"\n");
        }
        System.out.println(System.currentTimeMillis()-t1);*/
        Map<String, String> map = new HashMap<String, String>();
        map.put("channel_id", "16093104850682827704");
        map.put("channel_name", "\\u6d4b\\u8bd5\\u76f4\\u64ad");
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("channel_id", "16093104850682827704");
        map1.put("channel_name", "\\u6d4b\\u8bd5\\u76f4\\u64ad");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(map);
        list.add(map1);

        String str = GsonUtils.toJson(list);
        System.out.println(str);

        List<Map<String, String>> testList = (List<Map<String, String>>) GsonUtils.fromJson(str, new TypeToken<List<Map<String, String>>>() {
        }.getType());

    }

    @org.junit.Test
    public void testLive() throws Exception {
        String testStr = "{\"code\":0,\"message\":\"\",\"all_count\":\"2\",\"channelSet\":[{\"channel_id\":\"16093104850682827704\",\"channel_name\":\"\\u6d4b\\u8bd5\\u76f4\\u64ad\",\"channel_status\":\"0\",\"create_time\":\"2016-05-18 10:35:21\"},{\"channel_id\":\"16093425727654976792\",\"channel_name\":\"\\u6211\\u7684\\u6d4b\\u8bd5\\u9891\\u9053\",\"channel_status\":\"0\",\"create_time\":\"2016-06-14 12:37:31\"}]}";

        DescribeLVBResponse describeLVBResponse = GsonUtils.fromJson(testStr, DescribeLVBResponse.class);
        String url = "http://www.jd.com";
        System.out.println(System.currentTimeMillis());
        String s = HttpClient3Utils.executeGet(url);
        System.out.println(System.currentTimeMillis());
        System.out.println(s);
        System.out.println(System.currentTimeMillis());
        URL realUrl = new URL(url);
        URLConnection connection = null;
        connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 设置链接主机超时时间
        connection.setConnectTimeout(1000);
        // 建立实际的连接
        connection.connect();

        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        String line;
        String result = "";
        while ((line = in.readLine()) != null) {
            result += line;
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(result);
        if (in != null) {
            in.close();
        }
    }

    @org.junit.Test
    public void testGsonOne() {

        String testString = "{\"message\":\"\",\"all_count\":\"2\",\"channelSet\":[{\"channel_name\":\"测试直播\",\"create_time\":\"2016-05-18 10:35:21\",\"channel_status\":\"0\",\"channel_id\":\"16093104850682827704\"},{\"channel_name\":\"我的测试频道\",\"create_time\":\"2016-06-14 12:37:31\",\"channel_status\":\"0\",\"channel_id\":\"16093425727654976792\"}],\"code\":0}";
        String channelString = "{\"message\":\"\",\"channelInfo\":[{\"channel_name\":\"我的测试频道\",\"channel_describe\":\"channelDescribe我的测试频道\",\"flv_downstream_address\":\"http://2918.liveplay.myqcloud.com/live/2918_b4e4dd8131e911e6a2cba4dcbef5e35a.flv\",\"hls_downstream_address\":\"http://2918.liveplay.myqcloud.com/2918_b4e4dd8131e911e6a2cba4dcbef5e35a.m3u8\",\"upstream_list\":[{\"sourceID\":\"2918_b4e4dd8131e911e6a2cba4dcbef5e35a\",\"sourceType\":\"1\",\"sourceName\":\"ddd\",\"sourceAddress\":\"rtmp://2918.livepush.myqcloud.com/live/2918_b4e4dd8131e911e6a2cba4dcbef5e35a?bizid=2918\"}],\"channel_status\":\"0\",\"rtmp_downstream_address\":\"rtmp://2918.liveplay.myqcloud.com/live/2918_b4e4dd8131e911e6a2cba4dcbef5e35a\",\"player_id\":\"1147\",\"resolution\":null,\"password\":null,\"channel_id\":\"16093425727654976792\"}],\"code\":0}";


    }
}
