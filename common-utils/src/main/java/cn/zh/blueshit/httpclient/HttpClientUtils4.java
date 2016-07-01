/*
package cn.zh.blueshit.httpclient;

*/

/**
 * 此处解释下MaxtTotal和DefaultMaxPerRoute的区别：
 * 1、MaxtTotal是整个池子的大小；
 * 2、DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；比如：
 * MaxtTotal=400 DefaultMaxPerRoute=200
 * 而我只连接到http://sishuok.com时，到这个主机的并发最多只有200；而不是400；
 * 而我连接到http://sishuok.com 和 http://qq.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是DefaultMaxPerRoute。
 */
/*
public class HttpClientUtils4 {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils4.class);

    */
/**
     * http请求异常
     *//*

    //超时异常
    private static final String HTTP_CLIENT_ERROR = "ERROR";
    //域名解析异常
    private static final String HTTP_UNKNOWN_HOST_ERROR = "UNKNOWN_HOST_ERROR";
    //连接异常
    private static final String HTTP_CONNECTION_ERROR = "CONNECTION_ERROR";


    //设置请求超时2秒钟 根据业务调整
    private static final Integer CONNECTION_TIMEOUT = 2 * 1000;
    //设置等待数据超时时间2秒钟 根据业务调整
    private static final Integer SO_TIMEOUT = 2 * 1000;
    //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置
    private static final Long CONN_MANAGER_TIMEOUT = 1000L;

    private static final DefaultHttpClient httpClient = getHttpClient();

    public static DefaultHttpClient getInstance() {
        return httpClient;
    }

    private static DefaultHttpClient getHttpClient() {

        HttpParams params = new BasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        //在提交请求之前 测试连接是否可用
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
        //该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 ()
        params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        //不做重定向
        params.setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
        params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

        PoolingClientConnectionManager conMgr = new PoolingClientConnectionManager();
        conMgr.setMaxTotal(5000);//设置最大连接数
        //是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
        //设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。@see HttpClientUtils4
        conMgr.setDefaultMaxPerRoute(conMgr.getMaxTotal() / 10);
        //设置访问协议
        conMgr.getSchemeRegistry().register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        conMgr.getSchemeRegistry().register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        DefaultHttpClient httpClient = new DefaultHttpClient(conMgr, params);
        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, true));
        return httpClient;
    }


    */
/**
     * 向指定的url发送 post数据
     * 模拟form表单
     * @param url
     * @param params
     * @return
     *//*

    public static String postDataToUri(String url,Map<String,String> params){
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            data.add(new BasicNameValuePair(key, params.get(key)));
        }
        return postDataToUri(url,data);
    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param data
     * @return
     *//*

    public static String postDataToUri(String url, List<NameValuePair> data) {
        return postDataToUri(url, data, (Header[]) null);
    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param data
     * @param encoding
     * @return
     *//*

    public static String postDataToUri(String url, List<NameValuePair> data, String encoding) {
        return postDataToUri(url, data, null, encoding);
    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param data
     * @return
     *//*

    public static String postDataToUri(String url, List<NameValuePair> data, Header[] headers) {
        return postDataToUri(url, data, headers, null);
    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param data
     * @param encoding
     * @return
     *//*

    public static String postDataToUri(String url, List<NameValuePair> data, Header[] headers, String encoding) {
        HttpEntity entity = null;
        if (CollectionUtils.isNotEmpty(data)) {
            try {
                entity = new UrlEncodedFormEntity(data, encoding);
            } catch (Exception e) {
                throw new RuntimeException("data to url encoded form entity exception", e);
            }
        }
        return postDataToUri(url, entity, headers, encoding, null);

    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param json
     * @param encoding
     * @param timeout
     * @return
     *//*

    public static String postJsonData(String url, String json, String encoding, Integer timeout) {
        HttpEntity entity = null;
        if (StringUtils.isNotBlank(json)) {
            try {
                entity = new StringEntity(json, encoding);
            } catch (Exception e) {
                throw new RuntimeException("data to url encoded form entity exception", e);
            }
        }
        return postDataToUri(url, entity, new Header[]{new BasicHeader("Content-Type", "application/json")}, encoding, timeout);
    }

    */
/**
     * 向指定URI POST数据
     *
     * @param url
     * @param entity
     * @param encoding
     * @return
     *//*

    public static String postDataToUri(String url, HttpEntity entity, Header[] headers, String encoding, Integer timeout) {
        //解决POST中文乱码问题
        HttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            if (StringUtils.isBlank(encoding)) {
                encoding = Constants.ENCODING;
            }

            if (timeout != null) {
                post.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout / 2);
                post.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }

            post.setHeader("Accept-Charset", encoding);
            if (entity != null) {
                post.setEntity(entity);
            }
            if (ArrayUtils.isNotEmpty(headers)) {
                for (Header header : headers) {
                    post.addHeader(header);
                }
            }
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("Post Data Failure: url : {}, data : {}, headers : {} ,status line : {}", url, entityToString(entity), headers, response.getStatusLine());
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e1) {
                }
                return HTTP_CLIENT_ERROR;
            }
            return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
        } catch (Exception ex) {
            log.error("Post Data Exception: url : {}, data : {}, headers : {}", url, entityToString(entity), headers, ex);
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());//会自动释放连接
                } catch (IOException e1) {
                }
            }
            //如果域名解析错误等
            if (ex instanceof UnknownHostException || ex instanceof PortUnreachableException || ex instanceof NoRouteToHostException) {
                return HTTP_UNKNOWN_HOST_ERROR;
            }
            //如果连接失败
            if (ex instanceof ConnectException) {
                return HTTP_CONNECTION_ERROR;
            }
            //超时异常
            if (ex instanceof SocketTimeoutException) {
                return HTTP_CLIENT_ERROR;
            }

            return HTTP_CLIENT_ERROR;

        }
    }

    private static String entityToString(HttpEntity entity) {
        if (entity == null) {
            return null;
        }
        try {
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //这是组装头部
    public static Header[] assemblyHeader(Map<String,String> headers){
        Header[] allHeader= new BasicHeader[headers.size()];
        int i  = 0;
        for (String str :headers.keySet()) {
            allHeader[i] = new BasicHeader(str,headers.get(str));
            i++;
        }
        return allHeader;
    }

    public static String getDataFromUrl(String url,Map<String,String> headerMap) {
        return getDataFromUrl(url,assemblyHeader(headerMap));
    }

    public static String getDataFromUrl(String url, Header[] headers) {
        return getDataFromUriWithAuth(url, null, null, headers, null);
    }

    public static String getDataFromUrl(String url,Header[] headers,Integer timeout) {
        return getDataFromUriWithAuth(url,null,null,headers,timeout);
    }

    public static String getDataFromUri(String url, String encoding, Header[] headers, Integer timeout) {
        return getDataFromUriWithAuth(url, encoding, null, headers, timeout);
    }

    public static String getDataFromUriWithAuth(String url, String encoding, String authorizationCode, Header[] headers, Integer timeout) {
        HttpResponse response = null;
        StatusLine statusLine = null;
        Header[] respHeaders = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            if (ArrayUtils.isNotEmpty(headers)) {
                httpGet.setHeaders(headers);
            }
            if (StringUtils.isBlank(encoding)) {
                encoding = Constants.ENCODING;
            }
            if (timeout != null) {
                httpGet.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
                httpGet.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }
            httpGet.setHeader("Accept-Charset", encoding);
            //httpGet.setHeader("User-Agent", "Java");
            if (StringUtils.isNotBlank(authorizationCode)) {
                httpGet.addHeader("Authorization", authorizationCode);
            }
            response = httpClient.execute(httpGet);
            statusLine = response.getStatusLine();
            respHeaders = response.getAllHeaders();

            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                log.error("Get Data Failure: url : {}, authorizationCode : {}, request headers : {}, response status : {}, response headers : {}", url, authorizationCode, Arrays.toString(headers), statusLine, Arrays.toString(response.getAllHeaders()));
                try {
                    EntityUtils.consume(response.getEntity());//会自动释放连接
                } catch (IOException e1) {
                }
                return HTTP_CLIENT_ERROR;
            }
            return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
        } catch (Exception ex) {
            log.error("Get Data Exception: url : {}, authorizationCode : {}, request headers : {}, response status : {}, response headers : {}, {}", url, authorizationCode, Arrays.toString(headers), statusLine, Arrays.toString(respHeaders), ex);
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());//会自动释放连接
                } catch (IOException e1) {
                }
            }
            //如果域名解析错误等
            if (ex instanceof UnknownHostException || ex instanceof PortUnreachableException || ex instanceof NoRouteToHostException) {
                return HTTP_UNKNOWN_HOST_ERROR;
            }
            //如果连接失败
            if (ex instanceof ConnectException) {
                return HTTP_CONNECTION_ERROR;
            }
            //超时异常
            if (ex instanceof SocketTimeoutException) {
                return HTTP_CLIENT_ERROR;
            }

            return HTTP_CLIENT_ERROR;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final String url = "http://www.baidu.com";
        final CountDownLatch countDownLatch = new CountDownLatch(100);
        System.out.println("start----"+System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread("worker-" + i) {
                public void run() {
                    try {
                        String dataFromUrl = HttpClientUtils4.getDataFromUrl(url,null,5000);
                        System.out.println(Thread.currentThread().getName() + ":" +dataFromUrl.substring(10));
                    } catch (Exception var2) {
                        var2.getStackTrace();
                    }
                    countDownLatch.countDown();
                }
            };
            thread.start();
        }
        countDownLatch.await();
        System.out.println("end----"+System.currentTimeMillis());
        //HttpClientUtils4.postJsonData("url", "{test:1,sbd:23}",null,null);
    }


}*/
