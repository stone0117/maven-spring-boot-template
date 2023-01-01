function application_properties(context) {
  let {
        groupId,
        groupId2Folder,
        dbName,
        host,
        user,
        password,
        database,
        tableName
      } = context
  return `# SpringBoot项目接口第一次访问慢的问题
# SpringBoot的接口第一次访问都很慢，通过日志可以发现，dispatcherServlet不是一开始就加载的，有访问才开始加载的，即懒加载。
spring.mvc.servlet.load-on-startup=1
# 静态资源路径 修改
#spring.resources.static-locations=
# 使用哪个环境的配置
spring.profiles.active=dev,freemarker
# spring.profiles.include=freemarker
# spring.profiles.include=thymeleaf
# spring.profiles.active=prod
# spring.profiles.active=test
# ContextPath配置
server.servlet.context-path=/
# server.servlet.context-path=/car-business-spring-boot
# Springboot中获取本机IP、端口号和Context-path，项目启动后输出路径
# https://blog.csdn.net/djzhao627/article/details/105674827
#server.port=8080
server.tomcat.uri-encoding=utf-8
server.servlet.session.timeout=1800
#////////////////////////////////////////////////////////////////////////////
#///////////////// log4j2 ///////////////////////////////////////////////////
#////////////////////////////////////////////////////////////////////////////
# https://blog.csdn.net/qq_38262266/article/details/108677179
# spring-boot log4j2 配置详情
logging.config=classpath:log4j2.xml
# logging.level.${groupId}=trace
#////////////////////////////////////////////////////////////////////////////
#///////////////// Mybatis //////////////////////////////////////////////////
#////////////////////////////////////////////////////////////////////////////
mybatis.config-location=classpath:mybatis-config.xml
mybatis.mapper-locations=classpath:${groupId2Folder}/mapper/*Mapper.xml
mybatis.type-aliases-package=${groupId}.domain
#////////////////////////////////////////////////////////////////////////////
#///////////////// Mybatis-Plus /////////////////////////////////////////////
#////////////////////////////////////////////////////////////////////////////
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
# 是否开启 LOGO
mybatis-plus.global-config.banner=false
# 主键自增
mybatis-plus.global-config.db-config.id-type=auto
# 逻辑删除
mybatis-plus.global-config.db-config.logic-not-delete-value=0
mybatis-plus.global-config.db-config.logic-delete-value=1
# mybatis-plus.mapper-locations=classpath:${groupId2Folder}/mapper/*Mapper.xml
# 类型转换
mybatis-plus.type-handlers-package=${groupId}.typeHandler
mybatis-plus.type-enums-package=${groupId}.enums
#////////////////////////////////////////////////////////////////////////////
#///////////////// Druid ////////////////////////////////////////////////////
#////////////////////////////////////////////////////////////////////////////
# Loading class 'com.mysql.jdbc.Driver'. This is deprecated. The new driver class is 'com.mysql.cj.jdbc.Driver'.
# The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
# spring.datasource.driverClassName=com.mysql.jdbc.Driver
##### JDBC 配置 #######
#spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.db-type=mysql
spring.datasource.druid.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.druid.url=jdbc:mysql://${host}:3306/${dbName}?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.druid.username=${user}
spring.datasource.druid.password=${password}`
}
function application_dev_properties(context) {
  let {
        groupId,
        groupId2Folder,
        dbName,
        host,
        user,
        password,
        database,
        tableName
      } = context
    return `server.port=9090
# 设置环境
# https://www.liaoxuefeng.com/wiki/1252599548343744/1282388483112993
spring.datasource.druid.driverClassName=com.p6spy.engine.spy.P6SpyDriver
spring.datasource.druid.url=jdbc:p6spy:mysql://${host}:3306/${dbName}?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
# https://blog.csdn.net/jx520/article/details/105898539
# 默认 true 为 true 时 validationQuery 必须设为非空字符串
# 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-borrow=false
# 默认 false 为 true 时 validationQuery 必须设为非空字符串
# 【建议】配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
# 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
spring.datasource.druid.test-while-idle=false
# 默认 true 为 true 时 validationQuery 必须设为非空字符串
# 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-return=false
# 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，
# testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
# spring.datasource.druid.validation-query=select 1

# spring.profiles.include=freemarker
# spring.profiles.include=thymeleaf`
}
function application_local_properties(context) {
  let {
        groupId,
        groupId2Folder,
        dbName,
        host,
        user,
        password,
        database,
        tableName
      } = context
  return `server.port=8080
# 设置环境
# https://www.liaoxuefeng.com/wiki/1252599548343744/1282388483112993
spring.datasource.druid.driverClassName=com.p6spy.engine.spy.P6SpyDriver
spring.datasource.druid.url=jdbc:p6spy:mysql://${host}:3306/${dbName}?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
# https://blog.csdn.net/jx520/article/details/105898539
# 默认 true 为 true 时 validationQuery 必须设为非空字符串
# 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-borrow=false
# 默认 false 为 true 时 validationQuery 必须设为非空字符串
# 【建议】配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
# 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
spring.datasource.druid.test-while-idle=false
# 默认 true 为 true 时 validationQuery 必须设为非空字符串
# 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-return=false
# 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，
# testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
# spring.datasource.druid.validation-query=select 1

# spring.profiles.include=freemarker
# spring.profiles.include=thymeleaf`
    
}
module.exports = {
  application_properties,
  application_dev_properties,
  application_local_properties,
}