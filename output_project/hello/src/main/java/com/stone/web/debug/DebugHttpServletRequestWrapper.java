package com.stone.web.debug;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class DebugHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private byte[] body;

  private HttpServletRequest delegate;

  public DebugHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    body     = IOUtils.toByteArray(request.getInputStream());
    delegate = request;
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream bais = new ByteArrayInputStream(body);
    return new ServletInputStream() {

      @Override
      public int read() throws IOException {
        return bais.read();
      }

      @Override
      public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean isReady() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public void setReadListener(ReadListener arg0) {
        // TODO Auto-generated method stub
      }
    };
  }

  @Override
  public String getParameter(String key) {
    return delegate.getParameter(key);
  }
}
