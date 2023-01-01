package com.stone.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
// @Configuration
public class TransactionConfig {

  // 事务管理器
  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(dataSource);
    return dataSourceTransactionManager;
  }

  // 创建事务通知
  @Bean(name = "txAdvice")
  public TransactionInterceptor txAdvice(DataSourceTransactionManager transactionManager) {
    Properties properties = new Properties();
    // PROPAGATION_REQUIRED如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
    properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("write*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("batch*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("create*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("do*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("edit*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("execute*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("validate*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("export*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("import*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("process*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("publish*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("remove*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("submit*", "PROPAGATION_REQUIRED,-Exception");
    properties.setProperty("set*", "PROPAGATION_REQUIRED,-Exception");

    properties.setProperty("onAuthenticationSuccess", "PROPAGATION_REQUIRED,-Exception");

    properties.setProperty("select*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("query*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("find*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("count*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("list*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("load*", "PROPAGATION_REQUIRED,-Exception,readOnly");
    properties.setProperty("search*", "PROPAGATION_REQUIRED,-Exception,readOnly");

    // 其他
    // properties.setProperty("*", "readOnly");
    properties.setProperty("*", "PROPAGATION_REQUIRED,-Exception");

    TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
    transactionInterceptor.setTransactionManager(transactionManager);
    transactionInterceptor.setTransactionAttributes(properties);
    // 设置事务超时时间
    // transactionManager.setDefaultTimeout(30);
    return transactionInterceptor;
  }

  // 使用BeanNameAutoProxyCreator来创建AOP代理
  // (根据代理bean所需的事务拦截器和指定需要代理的Bean的名称表达式做匹配)
  @Bean
  public BeanNameAutoProxyCreator txProxy() {
    BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
    // 设置拦截链名字(这些拦截器是有先后顺序的)
    creator.setInterceptorNames("txAdvice");
    // 设置要创建代理的那些Bean的名字
    creator.setBeanNames("*Service", "*ServiceImpl");
    // CGLib代理
    creator.setProxyTargetClass(true);
    return creator;
  }
}
