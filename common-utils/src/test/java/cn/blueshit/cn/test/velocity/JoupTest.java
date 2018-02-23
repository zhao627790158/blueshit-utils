package cn.blueshit.cn.test.velocity;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by zhaoheng on 17/4/28.
 */
public class JoupTest {


    String url = "https://wiki.sankuai.com/pages/viewpage.action?pageId=645804252";

    String cookie = "";

    @Test
    public void test() {
        List<String> test = Lists.newArrayList();
        Splitter.on(";").trimResults().omitEmptyStrings().split(cookie).forEach(test::add);
        Map<String, String> coMap = test.stream().filter(t -> t.split("=").length > 1).collect(Collectors.toMap(t -> t.split("=")[0], t -> t.split("=")[1]));
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .cookies(coMap)
                    .get();

            //获取标题
            String title = doc.title();
            System.out.println(title);//输出：Google
            //data(key,value)是该URL要求的参数
            //userAgent制定用户使用的代理类型
            //cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
            //连接超时时间
            //post或者get方法
            /*doc = Jsoup.connect("http://www.xxxxx.com/")
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
*/

            System.out.println(doc.getElementsByTag("table").get(0).getElementsByTag("tbody").get(0).children().get(0).child(1).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){

    }
}
