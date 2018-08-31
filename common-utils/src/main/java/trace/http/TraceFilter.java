package trace.http;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhaoheng on 18/7/11.
 */
public class TraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Exception exception = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String action = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
            if (!Helper.matchExclude(action)) {
                String method = httpServletRequest.getMethod();
                String spanName = action + "." + method;
                Helper.startTrace((HttpServletRequest) request, (HttpServletResponse) response, spanName, null, 0);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
