package com.stone.web.debug;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.StringJoiner;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class DebugInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    debug(request, response, handler);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

  private void debug(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    response.setHeader("pragma", "no-cache");
    response.setHeader("cache-control", "no-cache");
    response.setHeader("expires", "0");

    StringBuilder stringBuilder = new StringBuilder();
    //noinspection StringRepeatCanBeUsed
    for (int i = 0; i < 80; i++) { stringBuilder.append("□");}
    System.out.println(stringBuilder);

    System.out.println();
    String xmlHttpRequest = request.getHeader("x-requested-with");
    System.out.printf("### JDK version %s | %s | %s | %s\n", System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"), request.getServletContext().getServerInfo());
    System.out.printf("### %s %s\n", request.getServletPath(), xmlHttpRequest != null && xmlHttpRequest.length() > 0 ? "|【 ajax请求 】" : "");
    System.out.printf("%s %s\n", request.getMethod().toUpperCase(), request.getRequestURL());
    String contentType = request.getHeader("Content-Type");

    if ("post".equals(request.getMethod().toLowerCase())) {
      System.out.printf("Content-Type: %s\n\n", contentType);

      if (contentType != null && contentType.contains("x-www-form-urlencoded")) {
        StringJoiner joiner = new StringJoiner("&");
        request.getParameterMap().forEach((k, v) -> {
          if (v.length == 1) {
            joiner.add(String.format("%s=%s", k, Arrays.stream(v).findFirst().get()));
          }
          else {
            Arrays.stream(v).forEach(s -> joiner.add(String.format("%s=%s", k, s)));
          }
        });
        System.out.println(joiner.toString());
      }
      else if (contentType != null && contentType.contains("multipart/")) {
        //
      }
      else {
        ServletInputStream inputStream = request.getInputStream();
        String             content     = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        System.out.println(content);
      }
    }
    else {
      request.getParameterMap().forEach((k, v) -> {
        if (v.length == 1) {
          System.out.printf("%s=%s\n", k, Arrays.stream(v).findFirst().get());
        }
        else {
          System.out.printf("%s=%s\n", k, Arrays.toString(v));
        }
      });
    }

    System.out.println();

    // Enumeration<String> headerNames = request.getHeaderNames();
    // for (; headerNames.hasMoreElements(); ) {
    //   String key   = headerNames.nextElement();
    //   String value = request.getHeader(key);
    //   System.out.printf("%s : %s\n", key, value);
    // }

    try {
      Method method = (Method) handler.getClass().getMethod("getMethod").invoke(handler);
      System.out.println();
      System.out.println(method);
    } catch (Exception ignored) {}

    stringBuilder = new StringBuilder();
    //noinspection StringRepeatCanBeUsed
    for (int i = 0; i < 80; i++) { stringBuilder.append("■");}
    System.out.println(stringBuilder);
  }
}

