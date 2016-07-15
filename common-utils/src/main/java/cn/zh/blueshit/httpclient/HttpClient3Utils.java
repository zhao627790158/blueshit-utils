package cn.zh.blueshit.httpclient;

import cn.zh.blueshit.resteasy.TokenCheck;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaoheng on 2016/6/14.
 */
public class HttpClient3Utils {
    private final static Logger logger = LoggerFactory.getLogger(HttpClient3Utils.class);
    private final static String ENCODE = "UTF-8";

    public static final String REST_META_TYPE = "application/json";

    public static PostMethod getPost(String url, String token, String data) throws UnsupportedEncodingException {
        return getPost(url, token, data, ENCODE);
    }

    public static PostMethod getPost(String url, String token, String data, String encode) throws UnsupportedEncodingException {
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(data, REST_META_TYPE, encode));
        if (token != null) {
            method.addRequestHeader(TokenCheck.TOKENHEAD, token);
        }
        return method;
    }
    public static String executeGet(String url) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        getMethod.addRequestHeader("Content-Type", " application/x-www-form-urlencoded;charset=UTF-8");
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Method failed: " + getMethod.getStatusLine());
            }
          return getMethod.getResponseBodyAsString(); // 读取为字节数组
        } catch (HttpException e) {
            logger.error("Please check your provided http address!", e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("网络发生错误，请检查网络!", e.getMessage());
            throw e;
        } finally {
            //在释放后连接并不总是会关闭
            getMethod.releaseConnection();
            //将idleTimeout设为0可以确保链接被关闭。
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
    }
    public static String executePost(String url,String token,String data) throws  Exception {
        String result = null;
        PostMethod method = getPost(url, token, data);
        HttpClient httpClient =  new  HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            int status = httpClient.executeMethod(method);
            if  (status != HttpStatus.SC_OK) {
                logger.error( "Method failed: " + method.getStatusLine());
            }
            StringBuffer resultBuffer = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), method.getResponseCharSet()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                resultBuffer.append(inputLine);
            }
            in.close();
            result = resultBuffer.toString().trim();
        } catch (Exception e) {
            logger.error("http请求异常，url=" + url);
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
                //将idleTimeout设为0可以确保链接被关闭。
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
            }
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        final String url = "http://10.13.22.47/bl/seq/getNextVal?sequenceName=seq_orderinfo";
        String fileUrl = "D:\\workspace\\jmtext.txt";
        final FileWriter writer = new FileWriter(fileUrl, true);
        final CountDownLatch countDownLatch = new CountDownLatch(500);
        System.out.println("start----"+System.currentTimeMillis());
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread("worker-" + i) {
                public void run() {
                    try {
                        for(int j=0;j<1000;j++){
                            String dataFromUrl = HttpClient3Utils.executeGet(url);
                            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                            writer.write(dataFromUrl+"\n");
                        }
                    } catch (Exception var2) {
                        var2.getStackTrace();
                    }
                    countDownLatch.countDown();
                }
            };
            thread.start();
        }
        countDownLatch.await();
        writer.close();
        System.out.println("end----"+System.currentTimeMillis());
    }

}
