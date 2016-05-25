package cn.zh.blueshit.httpclient.spring;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaoheng on 2016/5/25.
 */
public class MultiThreadHttpClient3 extends HttpClient implements DisposableBean, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadHttpClient3.class);

    public static int DEFAULT_TIMEOUT = 2000;
    public static int DEFAULT_MAX_CONNECTIONS = 8000;
    public static int DEFAULT_PERHOST_MAX_CONNECTIONS = 2000;
    public static int DEFAULT_READ_TIMEOUT = 2000;
    public static int DEFAULT_LOCK_TIMEOUT = 2000;
    private int timeout;
    private int readTimeout;
    private int waitTimeout;
    private int perhostMaxConnection;
    private int totalMaxConnection;
    private MultiThreadedHttpConnectionManager connectionManager;

    public MultiThreadHttpClient3() {
        this.timeout = DEFAULT_TIMEOUT;
        this.readTimeout = DEFAULT_READ_TIMEOUT;
        this.waitTimeout = DEFAULT_LOCK_TIMEOUT;
        this.perhostMaxConnection = DEFAULT_PERHOST_MAX_CONNECTIONS;
        this.totalMaxConnection = DEFAULT_MAX_CONNECTIONS;
    }

    @Override
    public void destroy() throws Exception {
        if (this.connectionManager != null) {
            this.connectionManager.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.connectionManager == null) {
            this.connectionManager = new MultiThreadedHttpConnectionManager();
            logger.info("设置最大并发连接数:" + this.totalMaxConnection);
            logger.info("设置主机最大并发连接数:" + this.perhostMaxConnection);
            HttpConnectionManagerParams params = this.connectionManager.getParams();
            params.setConnectionTimeout(this.timeout);
            params.setDefaultMaxConnectionsPerHost(this.perhostMaxConnection);
            params.setMaxTotalConnections(this.totalMaxConnection);
            params.setSoTimeout(this.readTimeout);
            this.setHttpConnectionManager(this.connectionManager);
        }
        this.getParams().setConnectionManagerTimeout((long)this.waitTimeout);
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWaitTimeout() {
        return waitTimeout;
    }

    public void setWaitTimeout(int waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    public int getPerhostMaxConnection() {
        return perhostMaxConnection;
    }

    public void setPerhostMaxConnection(int perhostMaxConnection) {
        this.perhostMaxConnection = perhostMaxConnection;
    }

    public int getTotalMaxConnection() {
        return totalMaxConnection;
    }

    public void setTotalMaxConnection(int totalMaxConnection) {
        this.totalMaxConnection = totalMaxConnection;
    }

    public MultiThreadedHttpConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(MultiThreadedHttpConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public static void main(String[] args) throws Exception {
        final MultiThreadHttpClient3 client3 = new MultiThreadHttpClient3();
        client3.afterPropertiesSet();
        short size = 100;
        for (int i = 0; i < 1; ++i) {
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            for(int y = 0; y < size; ++y) {
                Thread thread =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetMethod method = new GetMethod("https://www.baidu.com");
                        try {
                            client3.executeMethod(method);
                            method.releaseConnection();
                            System.out.println(Thread.currentThread().getName() + ":" + method.getStatusCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                },"worker-"+y);
                thread.start();
            }
            countDownLatch.await();
            System.out.println("执行完毕!");
        }

    }
}
