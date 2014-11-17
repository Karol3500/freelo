package org.freelo.model.files.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Class for PL encoding filter
public class encodeFilter implements Filter {

    private String encoding = "UTF-8";

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);
        chain.doFilter(req, res);
    }

    public void destroy() {
    }
}