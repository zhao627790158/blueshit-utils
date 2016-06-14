package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.DescribeLVBResponse;
import cn.zh.blueshit.common.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 2016/6/9.
 */
public class TestGson {

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

        List<Map<String,String>> testList =(List<Map<String, String>>)GsonUtils.fromJson(str,new TypeToken<List<Map<String,String>>>(){}.getType());

    }

    @org.junit.Test
    public void testLive() {
        String testStr = "{\"code\":0,\"message\":\"\",\"all_count\":\"2\",\"channelSet\":[{\"channel_id\":\"16093104850682827704\",\"channel_name\":\"\\u6d4b\\u8bd5\\u76f4\\u64ad\",\"channel_status\":\"0\",\"create_time\":\"2016-05-18 10:35:21\"},{\"channel_id\":\"16093425727654976792\",\"channel_name\":\"\\u6211\\u7684\\u6d4b\\u8bd5\\u9891\\u9053\",\"channel_status\":\"0\",\"create_time\":\"2016-06-14 12:37:31\"}]}";

        DescribeLVBResponse describeLVBResponse =GsonUtils.fromJson(testStr,DescribeLVBResponse.class);

    }
}
