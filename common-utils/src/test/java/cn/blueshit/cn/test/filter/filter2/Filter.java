package cn.blueshit.cn.test.filter.filter2;

public interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);  
}  