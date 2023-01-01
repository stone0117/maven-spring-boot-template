package com.stone.aop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Component //把当前类注入到spring容器中
@Aspect    //把当前类标识为一个切面供容器读取
public class TestAopConfig {
  public String stringify(Object object) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd");
    // Gson   gson       = gsonBuilder.setPrettyPrinting().create();
    Gson   gson       = gsonBuilder.create();
    String jsonString = gson.toJson(object);
    return jsonString;
  }

  /**
   * 指定切入点
   * 对贴有自定义注解@RequiredPermission的方法，进行切面
   */
  @Pointcut("@annotation(com.stone.annotation.TestLog)")
  private void pointcut() {}

  /**
   * 在方法出现异常时会执行的代码
   * 可以访问到异常对象，可以指定在出现特定异常时在执行通知代码
   */
  // @AfterThrowing(value = "useMethod()", throwing = "ex")
  // public void afterMethod(JoinPoint joinPoint, Exception ex) {
  //   String methodName = joinPoint.getSignature().getName();
  //   System.out.println("The method " + methodName + " occurs exception: " + ex);
  // }

  /**
   * 对切入点进行增强
   */
  @Around(value = "pointcut()")
  public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    Object    target     = proceedingJoinPoint.getTarget();
    Signature signature  = proceedingJoinPoint.getSignature();
    String    methodName = signature.getName();
    Class<?>  aClass     = target.getClass();
    System.out.printf("%s.%s(%s.java:10)%n", aClass.getName(), methodName, aClass.getSimpleName());

    for (Object arg : proceedingJoinPoint.getArgs()) {
      if (arg instanceof Model) { continue;}
      System.out.println(stringify(arg));
    }
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    // dlog(methodName, "methodName");
    // dlog(target.getClass().getName(), "target.getClass().getName()");

    // 拦截的控制器
    // proceedingJoinPoint.getTarget(); // com.stone.web.controller.EmployeeController@23fb0ddb
    // // 和上面的有什么区别
    // proceedingJoinPoint.getThis(); // com.stone.web.controller.EmployeeController@23fb0ddb
    // proceedingJoinPoint.getSignature(); // String com.stone.web.controller.EmployeeController.list(Model,EmployeeQueryObject)
    // proceedingJoinPoint.getKind(); // method-execution
    // // url
    // // proceedingJoinPoint.proceed(); // employee/list
    // proceedingJoinPoint.toLongString(); // execution(public java.lang.String com.stone.web.controller.EmployeeController.list(org.springframework.ui.Model,com.stone.qo.EmployeeQueryObject))
    // proceedingJoinPoint.toShortString(); // execution(EmployeeController.list(..))
    // proceedingJoinPoint.toString(); // execution(String com.stone.web.controller.EmployeeController.list(Model,EmployeeQueryObject))

    long   start  = System.currentTimeMillis();
    try {
      Object result = proceedingJoinPoint.proceed();
      long   end    = System.currentTimeMillis();
      System.out.println("【 共耗时：" + (end - start) + "毫秒 】");
      return result;
    } catch (Exception e) {
      long   end    = System.currentTimeMillis();
      System.out.println("【 共耗时：" + (end - start) + "毫秒 】");
      throw e;
    }
  }
}