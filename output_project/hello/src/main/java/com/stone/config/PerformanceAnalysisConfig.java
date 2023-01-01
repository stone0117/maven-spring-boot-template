package com.stone.config;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
// @Profile(value = {"dev", "test"})
// 使用 p6spy 更详细
@Profile(value = {"test"})
@Configuration
public class PerformanceAnalysisConfig {
  @Bean
  public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
    PerformanceMonitorInterceptor performanceMonitorInterceptor = new PerformanceMonitorInterceptor() {
      @Override
      public Object invoke(MethodInvocation invocation) throws Throwable {
        Log logger = getLoggerForInvocation(invocation);
        return invokeUnderTrace(invocation, logger);
      }

      @Override
      public Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        String    name      = createInvocationTraceName(invocation);
        StopWatch stopWatch = new StopWatch(name);
        stopWatch.start(name);
        try {
          return invocation.proceed();
        } finally {
          stopWatch.stop();
          System.out.printf("StopWatch '%s': running time = %s 毫秒 [ %s 秒 ]\n", stopWatch.getId(), stopWatch.getTotalTimeMillis(), stopWatch.getTotalTimeSeconds());
        }
      }
    };
    return performanceMonitorInterceptor;
  }

  // Spring Aspect @PointCut（execution表达式）
  // https://blog.csdn.net/q258523454/article/details/104180475
  // execution(* com.sample.service.impl..*.*(..))
  // execution（）	表达式的主体；
  // 第一个”*“符号	表示返回值的类型任意；
  // com.sample.service.impl	AOP所切的服务的包名，即，我们的业务部分
  // 包名后面的”..“	表示当前包及子包
  // 第二个”*“	表示类名，*即所有类。此处可以自定义，下文有举例
  // .*(..)	表示任何方法名，括号表示参数，两个点表示任何参数类型
  // @Pointcut("execution(public * cn.wolfcode.mapper.*.*(..)) || execution(public * cn.wolfcode.service..*.*(..))")
  // public void contactControllerMonitor() {}

  @Bean
  public Advisor performanceMonitorAdvisor(PerformanceMonitorInterceptor performanceMonitorInterceptor) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    // pointcut.setExpression("execution(public * com.stone.mapper.*.*(..)) || execution(public * com.stone.service..*.*(..))");
    pointcut.setExpression("execution(public * com.stone.mapper.*.*(..))");
    return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor);
  }
}
