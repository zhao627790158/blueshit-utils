package cn.blueshit.cn.test.http;

import cn.zh.blueshit.httpclient.HttpsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * Created by zhaoheng on 17/4/1.
 */
public class TestHttps {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpsUtils.getHttpClient();
        try {
            String url = "https://www.baidu.com";
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            String result = null;
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity, Charset.defaultCharset());
            } else {
                result = HttpsUtils.readHttpResponse(response);
            }
            System.out.println(result);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }

        }


    }

}


