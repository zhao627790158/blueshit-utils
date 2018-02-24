package cn.blueshit.cn.test.filter.filter2;

public class SensitiveFilter implements Filter {
    @Override  
    public void doFilter(Request request, Response response,FilterChain chain) {  
        request.setRequestStr(request.getRequestStr().replace("被就业", "就业").replace("敏感", "") + "---SensitiveFilter");  
        chain.doFilter(request, response, chain);  
        response.setResponseStr(response.getResponseStr() + "---SensitiveFilter");  
    }  
}  