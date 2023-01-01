package com.stone.utils;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class Utils {
  private Utils() {}

  /**
   * servlet调试工具
   *
   * @param req
   * @param resp
   * @param filterConfig
   */
  public static void debug(HttpServletRequest req, HttpServletResponse resp, FilterConfig filterConfig) {
    // 告诉客户端不缓存
    resp.setHeader("pragma", "no-cache");
    resp.setHeader("cache-control", "no-cache");
    resp.setHeader("expires", "0");

    StringBuilder stringBuilder = new StringBuilder();
    //noinspection StringRepeatCanBeUsed
    for (int i = 0; i < 80; i++) { stringBuilder.append("□");}
    System.out.println(stringBuilder);

    System.out.println();
    System.out.printf("### JDK version %s | %s | %s | %s\n", System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"), filterConfig.getServletContext().getServerInfo());
    System.out.printf("### %s\n", req.getServletPath());
    System.out.printf("%s %s\n", req.getMethod().toUpperCase(), req.getRequestURL());
    String contentType = req.getHeader("Content-Type");

    if ("post".equals(req.getMethod().toLowerCase())) {
      System.out.printf("Content-Type: %s\n\n", contentType);
    } else {
      System.out.println();
    }
    req.getParameterMap().forEach((k, v) -> {
      if (v.length == 1) {
        System.out.printf("%s=%s\n", k, Arrays.stream(v).findFirst().get());
      } else {
        System.out.printf("%s=%s\n", k, Arrays.toString(v));
      }
    });
    System.out.println();

    stringBuilder = new StringBuilder();
    //noinspection StringRepeatCanBeUsed
    for (int i = 0; i < 80; i++) { stringBuilder.append("■");}
    System.out.println(stringBuilder);
  }

  /**
   * 字符串判空 不空为true
   *
   * @param str
   *
   * @return
   */
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * 字符串判空
   *
   * @param str
   *
   * @return
   */
  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(str.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 下载
   *
   * @param resp
   * @param sourcePath
   * @param downloadFileName
   *
   * @throws IOException
   */
  public static void writeToOutputStream(HttpServletResponse resp, Path sourcePath, String downloadFileName) throws IOException {
    resp.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
    Files.copy(sourcePath, resp.getOutputStream());
  }

  /**
   * 防止中文乱码, 获取文件名
   *
   * @param req
   * @param filename
   *
   * @return
   */
  public static String getDownloadFileName(HttpServletRequest req, String filename) {
    String userAgent = req.getHeader("User-Agent");
    try {
      return userAgent.contains("MISE") ? URLEncoder.encode(filename, "UTF-8") : new String(filename.getBytes(), StandardCharsets.ISO_8859_1);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return filename;
    }
  }

  /**
   * 获取tomcat版本
   *
   * @param serverInfo
   *
   * @return
   */
  public static String getTomcatVersion(String serverInfo) {
    final String  regex   = "(?<=Apache Tomcat\\/)\\d";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    final Matcher matcher = pattern.matcher(serverInfo);
    if (matcher.find()) {
      return matcher.group(0);
    } else {
      return "unknown";
    }
  }

  /**
   * tomcat7获取上传的文件名
   *
   * @param part
   *
   * @return
   */
  public static String getSubmittedFileName(Part part) {
    String  contentDisposition = part.getHeader("Content-Disposition");
    String  regex              = "(?<=filename=\").*(?=\")";
    Pattern pattern            = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher            = pattern.matcher(contentDisposition);
    if (matcher.find()) {
      return matcher.group(0);
    } else {
      return "";
    }
  }

  /**
   * 获取扩展名
   *
   * @param filename
   *
   * @return
   */
  public static String getExtension(String filename) {
    if (filename == null) { return null;}
    int index = indexOfExtension(filename);
    if (index == -1) {
      return "";
    } else {
      return filename.substring(index + 1);
    }
  }

  /**
   * 字符串重复
   *
   * @param str
   * @param repeat
   *
   * @return
   */
  public static String repeat(String str, int repeat) {
    if (str == null) {
      return null;
    } else if (repeat <= 0) {
      return "";
    } else {
      int inputLength = str.length();
      if (repeat != 1 && inputLength != 0) {
        if (inputLength == 1 && repeat <= 8192) {
          return padding(repeat, str.charAt(0));
        } else {
          int outputLength = inputLength * repeat;
          switch (inputLength) {
            case 1:
              char ch = str.charAt(0);
              char[] output1 = new char[outputLength];

              for (int i = repeat - 1; i >= 0; --i) {
                output1[i] = ch;
              }

              return new String(output1);
            case 2:
              char ch0 = str.charAt(0);
              char ch1 = str.charAt(1);
              char[] output2 = new char[outputLength];

              for (int i = repeat * 2 - 2; i >= 0; --i) {
                output2[i]     = ch0;
                output2[i + 1] = ch1;
                --i;
              }

              return new String(output2);
            default:
              StringBuilder buf = new StringBuilder(outputLength);

              for (int i = 0; i < repeat; ++i) {
                buf.append(str);
              }

              return buf.toString();
          }
        }
      } else {
        return str;
      }
    }
  }

  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
    } else {
      char[] buf = new char[repeat];

      for (int i = 0; i < buf.length; ++i) {
        buf[i] = padChar;
      }

      return new String(buf);
    }
  }

  private static int indexOfExtension(String filename) {
    if (filename == null) { return -1;}
    int extensionPos  = filename.lastIndexOf(".");
    int lastSeparator = indexOfLastSeparator(filename);
    return (lastSeparator > extensionPos ? -1 : extensionPos);
  }

  private static int indexOfLastSeparator(String filename) {
    if (filename == null) { return -1;}
    int lastUnixPos    = filename.lastIndexOf('/');
    int lastWindowsPos = filename.lastIndexOf('\\');
    return Math.max(lastUnixPos, lastWindowsPos);
  }

   /**
   * @param resultSet
   *
   * @throws SQLException
   */
  public static void showRow(ResultSet resultSet) throws SQLException {

    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    // 数据库列的索引都是 从1开始的 , 第一列
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
      stringBuilder.append(String.format("%-20s", resultSet.getObject(i + 1)));
    }
    System.out.println(stringBuilder);
  }

  public static void showAllRow(ResultSet resultSet) throws SQLException {
    while (resultSet.next()) { showRow(resultSet);}
  }

  /**
   * @param resultSet
   *
   * @throws SQLException
   */
  public static void showColumnLabel(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    StringBuilder     stringBuilder     = new StringBuilder();

    StringBuilder repeatLine = new StringBuilder("-");
    for (int i = 0; i < 120; i++) {
      repeatLine.append("-");
    }
    for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) { stringBuilder.append(String.format("%-20s", resultSetMetaData.getColumnName(i + 1)));}

    System.out.println(repeatLine.toString());
    System.out.println(stringBuilder);
    System.out.println(repeatLine.toString());
  }
}
