package com.stone.web.debug;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.Filter;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Profile(value = {"dev"})
@Configuration
public class DebugConfig {

  @Bean
  public FilterRegistrationBean debugFilterRegistration(Filter debugFilter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(debugFilter);
    registration.addUrlPatterns("/*");
    registration.setName("debugFilter");
    return registration;
  }

  @Bean
  public Filter debugFilter() {
    return new DebugFilter();
  }

  @Bean
  public DebugInterceptor debugInterceptor() {
    return new DebugInterceptor();
  }
}
