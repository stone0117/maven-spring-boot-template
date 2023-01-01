package com.stone.utils.mp;
/**
 * REST API 错误码接口
 *
 * @author hubin
 * @since 2018-06-05
 */
public interface IErrorCode {

  /**
   * 错误编码 -1、失败 0、成功
   */
  long getCode();

  /**
   * 错误描述
   */
  String getMsg();
}
