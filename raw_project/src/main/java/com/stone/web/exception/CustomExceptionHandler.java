package com.stone.web.exception;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/import com.stone.utils.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 对控制器进行增强处理
*/
@ControllerAdvice
public class CustomExceptionHandler {
/**
* 该方法是用于捕获并处理某种异常
* e : 现在出现的异常对象
* method : 现在出现异常的那个处理方法
*/
@Autowired
private ObjectMapper objectMapper;

@ExceptionHandler(value = {RuntimeException.class})
public String exceptionHandler(RuntimeException e, HandlerMethod method, HttpServletResponse response) {
//方便开发的时候找bug
e.printStackTrace();
//如果原本控制器的方法是返回jsonresult数据,现在出异常也应该返回jsonresult
//获取当前出现异常的方法,判断是否有ResponseBody注解,有就代表需要返回jsonresult
if (method.hasMethodAnnotation(ResponseBody.class)) {
try {
response.setContentType("application/json;charset=UTF-8");
String jsonString = objectMapper.writeValueAsString(JsonResult.defaultError());
response.getWriter().print(jsonString);
} catch (IOException exception) {
exception.printStackTrace();
}
return null;
}
// 如果原本控制器的方法是返回视图页面,现在也应该返回视图页面
return "error";
}

@ExceptionHandler(LogicException.class)
@ResponseBody
public Object handlerLogicException(LogicException e) {
e.printStackTrace();
return JsonResult.defaultError();
}
}
