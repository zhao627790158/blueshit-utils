package cn.zh.blueshit.httpclient;

import cn.zh.blueshit.httpclient.spring.MultiThreadHttpClient3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoheng on 2016/5/25.
 */
public class CallHttp {
    private static final Logger log = LoggerFactory.getLogger(CallHttp.class);

    private static final String REST_META_TYPE = "application/json";

    private static final String ENCODE = "utf-8";

    private MultiThreadHttpClient3 httpClient;



   /* public Object callRemoteBase(Object dateObj, RequestVo requestVo, Class resultClass) {

        String jsonData = "";

        PostMethod method = null;

        if(dateObj!=null){

            if (dateObj instanceof String) {
                jsonData = dateObj.toString();
            } else {
                jsonData = GsonUtils.toJson(dateObj);
            }
        }

        log.info("Call remote start....." + jsonData);
        Object result = null;
        try {

            method = getPost(requestVo, jsonData);

            httpClient.executeMethod(method);

            byte[] rsbyte = method.getResponseBody();
            if (rsbyte != null) {

                String jsonResult = new String(rsbyte, ENCODE);
                log.info("Call remote end....." + jsonResult);
                if (StringUtils.isNotEmpty(jsonResult)) {

                    result = GsonUtils.fromJson(jsonResult, resultClass);

                }

                if(result==null){
                    result = jsonResult;
                }
            }
        } catch (Exception e) {
            log.error("Call core exception, jsonData::" + jsonData, e);
        }
        return result;
    }

    private static PostMethod getPost(RequestVo requestVo, String data) throws UnsupportedEncodingException {

        PostMethod method = new PostMethod(requestVo.getRequestUrl());
        method.setRequestEntity(new StringRequestEntity(data, REST_META_TYPE,ENCODE));

        String token = requestVo.getToken();

        if (token != null) {
            method.addRequestHeader(TokenCheck.TOKENHEAD, token);
        }

        return method;
    }
*/

    public MultiThreadHttpClient3 getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(MultiThreadHttpClient3 httpClient) {
        this.httpClient = httpClient;
    }
}
