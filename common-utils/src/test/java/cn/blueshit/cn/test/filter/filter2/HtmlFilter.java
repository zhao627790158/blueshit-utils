package cn.blueshit.cn.test.filter.filter2;

public class HtmlFilter implements Filter {
    @Override  
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.setRequestStr(request.getRequestStr().replace('<', '[').replace('>', ']') + "\n---HtmlFilter");
        response.setResponseStr("res-html");
        chain.doFilter(request, response, chain);  
        response.setResponseStr(response.getResponseStr() + "\n---HtmlFilter");
    }  
}  