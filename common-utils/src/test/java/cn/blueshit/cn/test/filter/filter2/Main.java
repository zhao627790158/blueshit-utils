package cn.blueshit.cn.test.filter.filter2;

public class Main {
    public static void main(String[] args) {
        String msg = "大家好:),<script>,敏感,被就业";
        Request request = new Request();
        request.setRequestStr(msg);
        Response response = new Response();
        response.setResponseStr("response");

        FilterChain fc = new FilterChain();
        fc.addFilter(new HtmlFilter()).addFilter(new SensitiveFilter());
        fc.doFilter(request, response, fc);
        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());
    }
}  