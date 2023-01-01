function application_context_xml(context) {
  let {
        groupId,
        groupId2Folder,
      } = context
  return `<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
						               http://www.springframework.org/schema/beans/spring-beans.xsd
						               http://www.springframework.org/schema/mvc
						               http://www.springframework.org/schema/mvc/spring-mvc.xsd
					                 http://www.springframework.org/schema/tx
                           http://www.springframework.org/schema/tx/spring-tx.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd
						               http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">

  <!-- 配置组件包扫描的位置, 让 IoC DI 注解起作用, 即 @Autowired @Repository @Service @Component @Controller 起作用 -->
  <context:component-scan base-package="${groupId}.service" />

  <!-- 读取db.properties配置文件到Spring容器中 -->
  <context:property-placeholder location="classpath:db.properties" />

  <!-- 配置 阿里巴巴的 druid 数据源（连接池） -->
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
    <!-- SpringEL 语法 \${key} -->
    <property name="driverClassName" value="\${jdbc.driverClassName}" />
    <property name="url" value="\${jdbc.url}" />
    <!-- \${username}如果key是username，name 默认spring框架调用的当前操作系统的账号 解决方案：可以统一给key加一个前缀 -->
    <property name="username" value="\${jdbc.username}" />
    <property name="password" value="\${jdbc.password}" />
    <property name="maxActive" value="\${jdbc.maxActive}" />
    <!-- 开启Druid的监控统计功能,StatFilter可以和其他的Filter配置使用 -->
    <!-- <property name="filters" value="stat,log4j2"/> -->
    <!-- <property name="filters" value="log4j2" />-->
    <!-- proxyFilters属性配置,通过bean的方式配置 -->
    <!--
    <property name="proxyFilters">
      <list>
        <ref bean="log-filter" />
      </list>
    </property>
    -->
  </bean>

  <!-- 创建SqlSessionFactory MyBatis会话工厂对象 -->
  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">

    <!-- 注意其他配置 -->
    <property name="plugins">
      <array>
        <bean class="com.github.pagehelper.PageInterceptor">
          <property name="properties">
            <!--使用下面的方式配置参数，一行配置一个，下面配的是合理化分页 -->
            <!-- 参数说明: https://pagehelper.github.io/docs/howtouse/ -->
            <value>
              pageSizeZero=true
              reasonable=true
            </value>
          </property>
        </bean>
      </array>
    </property>

    <!-- 注入数据源 -->
    <property name="dataSource" ref="dataSource" />
    <!-- 读取映射文件 ,MyBatis的纯注解不用配置 -->
    <property name="mapperLocations">
      <array>
        <!-- 配置单个映射文件 -->
        <!-- <value>classpath:cn/wolfcode/mapper/PermissionMapper.xml</value> -->
        <!-- 配置多个映射文件使用 * 通配符 -->
        <value>classpath:${groupId2Folder}/mapper/*Mapper.xml</value>
      </array>
    </property>
    <!-- 配置mybatis-confg.xml主配置文件（注配置文件可以保留一些个性化配置，缓存，日志，插件） -->
    <property name="configLocation" value="classpath:mybatis-config.xml" />
    <!-- 配置别名，使用包扫描, 即 EmployeeMapper.xml 中 可以省略全限定类名 -->
    <property name="typeAliasesPackage" value="${groupId}.domain" />
  </bean>

  <!--
    SqlSession 不用单独创建，每次做crud操作都需要Mapper接口的代理对象 而代理对象的创建又必须有 SqlSession对象创建
    Spring在通过MyBatis创建 Mapper接口代理对象的时候，底层自动把SqlSession会话对象创建出来
  -->

  <!--
    创建UserMapper接口的代理对象,创建单个代理对象 参考桥梁包：org.mybatis.spring.mapper.MapperFactoryBean<T>
    此类就是创建 Mapper 代理对象的类
  -->
  <!--
    使用包扫描创建代理对象，包下面所有Mapper接口统一创建代理对象 使用桥梁包下面 ： org.mybatis.spring.mapper.MapperScannerConfigurer
    可以包扫描创建所有映射接口的代理对象
  -->
  <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <!-- 配置SqlSessionFactoryBean的名称 -->
    <property name="basePackage" value="${groupId}.mapper" />
    <!-- 可选，如果不写，Spring启动时候。容器中。自动会按照类型去把SqlSessionFactory对象注入进来 -->
    <!-- <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/> -->
  </bean>

  <!-- 1.配置事务管理器 -->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <!-- 注入数据源 -->
    <property name="dataSource" ref="dataSource" />
  </bean>

  <!--
    proxy-target-class="true" 与proxy-target-class="false"的区别
    <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>
  　　 注意：proxy-target-class属性值决定是基于接口的还是基于类的代理被创建。如果proxy-target-class 属性值被设置为true，
      那么基于类的代理将起作用（这时需要cglib库）。如果proxy-target-class属值被设置为false或者这个属性被省略，那么标准的JDK 基于接口的代理将起作用。
      即使你未声明 proxy-target-class="true" ，但运行类没有继承接口，spring也会自动使用CGLIB代理。
      高版本spring自动根据运行类选择 JDK 或 CGLIB 代理
  -->
  <!--  注解方式起作用 -->
  <!--  <tx:annotation-driven transaction-manager="transactionManager" />   -->

  <!--
    2.配置事务的细节
    配置事务通知/增强
  -->
  <tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!-- 配置属性 -->
    <tx:attributes>
      <!-- DQL  -->
      <tx:method name="select*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <tx:method name="query*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <tx:method name="get*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <tx:method name="find*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <tx:method name="count*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <tx:method name="list*" read-only="true" isolation="REPEATABLE_READ" propagation="REQUIRED" />
      <!-- 其他 -->
      <tx:method name="*" read-only="false" isolation="REPEATABLE_READ" propagation="REQUIRED" />
    </tx:attributes>
  </tx:advice>

  <!--
    3.使用AOP将事务切到Service层
  -->
  <aop:config>
    <!-- 配置切入点 -->
    <aop:pointcut id="txPointcut" expression="execution(* ${groupId}.service..*.*(..))" />
    <!-- 配置切面= 切入点+通知 -->
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut" />
  </aop:config>

  <aop:aspectj-autoproxy proxy-target-class="true" />

</beans>`
}

module.exports = application_context_xml