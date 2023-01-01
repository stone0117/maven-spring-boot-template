package com.stone.web.debug;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class DebugFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    String             header         = servletRequest.getHeader("content-type");

    if (header != null && header.contains("x-www-form-urlencoded")) {
      filterChain.doFilter(servletRequest, response);
    }
    else if (header != null && header.contains("multipart/form-data")) {
      filterChain.doFilter(servletRequest, response);
    }
    else {
      DebugHttpServletRequestWrapper myRequestWrapper = new DebugHttpServletRequestWrapper((HttpServletRequest) request);
      filterChain.doFilter(myRequestWrapper, response);
    }
  }

  @Override
  public void destroy() {

  }
}
