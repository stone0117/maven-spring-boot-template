package com.stone.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Setter
@Getter
@NoArgsConstructor
public class JsonResult<T> {
public static final int    CODE_SUCCESS     = 200;
public static final String MSG_SUCCESS      = "操作成功";
public static final int    CODE_NOLOGIN     = 401;
public static final String MSG_NOLOGIN      = "请先登录";
public static final int    CODE_ERROR       = 500;
public static final String MSG_ERROR        = "系统异常，请联系管理员";
// 参数异常
public static final int    CODE_ERROR_PARAM = 501;

// 区分不同结果, 而不再是true或者false
private int code;

private String msg;

// 除了操作结果之后, 还行携带数据返回
private T data;

public JsonResult(int code, String msg, T data) {
this.code = code;
this.msg  = msg;
this.data = data;
}

public static <T> JsonResult<T> success(T data) {
return new JsonResult<>(CODE_SUCCESS, MSG_SUCCESS, data);
}

public static <T> JsonResult<T> success() {
return new JsonResult<>(CODE_SUCCESS, MSG_SUCCESS, null);
}

public static <T> JsonResult<T> error(int code, String msg, T data) {
return new JsonResult<>(code, msg, data);
}

public static <T> JsonResult<T> defaultError() {
return new JsonResult<>(CODE_ERROR, MSG_ERROR, null);
}

public static <T> JsonResult<T> noLogin() {
return new JsonResult<>(CODE_NOLOGIN, MSG_NOLOGIN, null);
}
}
