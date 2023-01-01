package com.stone.config;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Arrays;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Configuration
// 开启自动配置对象属性功能 // 为什么没配置也生效??? @SpringBootApplication 中已经开启了
// @EnableConfigurationProperties
public class DruidConfig {

@Bean(initMethod = "init", destroyMethod = "close")
@ConfigurationProperties(prefix = "spring.datasource.druid")
public DruidDataSource dataSource() throws SQLException {
DruidDataSource druidDataSource = new DruidDataSource();
// druidDataSource.setFilters("stat,wall");
// druidDataSource.setFilters("log4j2"); // 不能加这个, 不然是默认的, 自定义的不起作用
//noinspection ArraysAsListWithZeroOrOneArgument
druidDataSource.setProxyFilters(Arrays.asList(log4J2Filter()));
return druidDataSource;
}

@Bean
public Log4j2Filter log4J2Filter() {
Log4j2Filter log4j2Filter = new Log4j2Filter();
// log4j2Filter.setConnectionCloseAfterLogEnable = true
// log4j2Filter.setConnectionCommitAfterLogEnable = true
// log4j2Filter.setConnectionConnectAfterLogEnable = true
// log4j2Filter.setConnectionConnectBeforeLogEnable = true
// log4j2Filter.setConnectionLogEnabled = true
// log4j2Filter.setConnectionLogErrorEnabled = true
// log4j2Filter.setConnectionLoggerName = "druid.sql.Connection"
// log4j2Filter.setConnectionRollbackAfterLogEnable = true
// log4j2Filter.setDataSourceLogEnabled = true
// log4j2Filter.setDataSourceLoggerName = "druid.sql.DataSource"
// log4j2Filter.setResultSetCloseAfterLogEnable = true
// log4j2Filter.setResultSetLogEnabled = true
// log4j2Filter.setResultSetLogErrorEnabled = true
// log4j2Filter.setResultSetLoggerName = "druid.sql.ResultSet"
// log4j2Filter.setResultSetNextAfterLogEnable = true
// log4j2Filter.setResultSetOpenAfterLogEnable = true
// log4j2Filter.setStatementCloseAfterLogEnable = true
// log4j2Filter.setStatementCreateAfterLogEnable = true
// log4j2Filter.setStatementExecutableSqlLogEnable = false
// log4j2Filter.setStatementExecuteAfterLogEnable = true
// log4j2Filter.setStatementExecuteBatchAfterLogEnable = true
// log4j2Filter.setStatementExecuteQueryAfterLogEnable = true
// log4j2Filter.setStatementExecuteUpdateAfterLogEnable = true
// log4j2Filter.setStatementLogEnabled = true
// log4j2Filter.setStatementLogErrorEnabled = true
// log4j2Filter.setStatementLoggerName = "druid.sql.Statement"
// log4j2Filter.setStatementLogSqlPrettyFormat = false
// log4j2Filter.setStatementParameterClearLogEnable = true
// log4j2Filter.setStatementParameterSetLogEnable = true
// log4j2Filter.setStatementPrepareAfterLogEnable = true
// log4j2Filter.setStatementPrepareCallAfterLogEnable = true

// setConnection
log4j2Filter.setConnectionLogEnabled(false);                                       // true
log4j2Filter.setConnectionLoggerName("druid.sql.Connection");                     // "druid.sql.Connection"
log4j2Filter.setConnectionCloseAfterLogEnabled(true);                             // true
log4j2Filter.setConnectionCommitAfterLogEnabled(true);                            // true
log4j2Filter.setConnectionConnectAfterLogEnabled(true);                           // true
log4j2Filter.setConnectionConnectBeforeLogEnabled(true);                          // true
log4j2Filter.setConnectionLogErrorEnabled(true);                                  // true
log4j2Filter.setConnectionRollbackAfterLogEnabled(true);                          // true

// setDataSource
log4j2Filter.setDataSourceLogEnabled(false);                                       // true
log4j2Filter.setDataSourceLoggerName("druid.sql.DataSource");                     // "druid.sql.DataSource"

// setResultSet
log4j2Filter.setResultSetLogEnabled(true);                                       // true
log4j2Filter.setResultSetLoggerName("druid.sql.ResultSet");                       // "druid.sql.ResultSet"
log4j2Filter.setResultSetCloseAfterLogEnabled(true);                              // true
log4j2Filter.setResultSetLogErrorEnabled(true);                                   // true
log4j2Filter.setResultSetNextAfterLogEnabled(true);                               // true
log4j2Filter.setResultSetOpenAfterLogEnabled(true);                               // true

// setStatement
log4j2Filter.setStatementLogEnabled(true);                                        // true
log4j2Filter.setStatementLoggerName("druid.sql.Statement");                       // "druid.sql.Statement"
log4j2Filter.setStatementCloseAfterLogEnabled(false);                              // true
log4j2Filter.setStatementCreateAfterLogEnabled(false);                             // true
log4j2Filter.setStatementExecutableSqlLogEnable(false);                           // false
log4j2Filter.setStatementExecuteAfterLogEnabled(true);                            // true
log4j2Filter.setStatementExecuteBatchAfterLogEnabled(true);                       // true
log4j2Filter.setStatementExecuteQueryAfterLogEnabled(true);                       // true
log4j2Filter.setStatementExecuteUpdateAfterLogEnabled(true);                      // true
log4j2Filter.setStatementLogErrorEnabled(true);                                   // true
log4j2Filter.setStatementSqlPrettyFormat(false);                                  // false
log4j2Filter.setStatementParameterClearLogEnable(false);                           // true
log4j2Filter.setStatementParameterSetLogEnabled(false);                            // true
log4j2Filter.setStatementPrepareAfterLogEnabled(false);                            // true
log4j2Filter.setStatementPrepareCallAfterLogEnabled(true);                        // true

return log4j2Filter;
}

// @Bean
// public ServletRegistrationBean druidStatViewServlet() {
//   //ServletRegistrationBean提供类的进行注册
//   ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//   //添加初始化参数：initParams
//   //白名单：
//   servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//   servletRegistrationBean.addInitParameter("allow", "localhost");
//   //IP黑名单（同时存在时，deny优先于allow）
//   //如果满足deny，就提示：sorry，you are not permitted to view this page
//   // servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
//   //登录查看信息的账号和密码
//   servletRegistrationBean.addInitParameter("loginUsername", "admin");
//   servletRegistrationBean.addInitParameter("loginPassword", "admin");
//   servletRegistrationBean.addInitParameter("resetEnable", "false");
//   return servletRegistrationBean;
// }
//
// @Bean
// public FilterRegistrationBean druidStatFilter() {
//   FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//   //添加过滤规则
//   filterRegistrationBean.addUrlPatterns("/*");
//   //添加需要忽略的格式信息
//   filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png, *.css,*.ico,/druid/*");
//   return filterRegistrationBean;
// }
}
