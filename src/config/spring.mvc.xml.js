function spring_mvc_xml(context) {
  let {
        groupId,
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
  <context:component-scan base-package="${groupId}.web.controller,${groupId}.exception,${groupId}.config" />
  <!-- @RequestMapping 等 spring注解 起作用 -->
  <!--  <mvc:annotation-driven conversion-service="conversionService"/>-->
  <mvc:annotation-driven />
  <!-- 静态资源访问注解 -->
  <mvc:default-servlet-handler />

  <!--配置视图解析器 配置这个，那么 Spring MVC 找视图的路径就是：前缀 + 逻辑视图名（处理方法设置或返回视图名） + 后缀名 -->
  <!--
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
      &lt;!&ndash; 视图前缀 &ndash;&gt;
      <property name="prefix" value="/WEB-INF/views/" />
      &lt;!&ndash; 视图后缀 &ndash;&gt;
      <property name="suffix" value=".jsp" />
    </bean>
  -->

  <!-- 注册 FreeMarker 配置类 -->
  <!-- <bean class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer"> -->
  <bean class="${groupId}.config.CustomFreeMarkerConfig">
    <!-- 配置 FreeMarker 的文件编码 -->
    <property name="defaultEncoding" value="UTF-8" />
    <!-- 配置 FreeMarker 寻找模板的路径 -->
    <property name="templateLoaderPath" value="/WEB-INF/views/" />
    <property name="freemarkerSettings">
      <props>
        <!-- 兼容模式 ，配了后不需要另外处理空值问题，时间格式除外 -->
        <prop key="classic_compatible">true</prop>
      </props>
    </property>

    <property name="freemarkerVariables">
      <map>
        <entry key="toJson" value-ref="toPrettyJson" />
        <entry key="toString" value-ref="toPrettyString" />
      </map>
    </property>
  </bean>

  <bean id="toPrettyJson" class="${groupId}.utils.ToPrettyJson" />
  <bean id="toPrettyString" class="${groupId}.utils.ToString" />

  <!-- 注册 FreeMarker 视图解析器 -->
  <bean class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
    <!-- 是否把session中的attribute复制到模板的属性集中，可以使用FreeMarker的表达式来访问并显示-->
    <property name="exposeSessionAttributes" value="true" />
    <!-- 配置逻辑视图自动添加的后缀名 -->
    <property name="suffix" value=".ftl" />
    <!-- 配置响应头中 Content-Type 的指 -->
    <property name="contentType" value="text/html;charset=UTF-8" />
  </bean>

  <!--
    方法1:
    在封装参数类的 Date 类型的字段上贴 @DateTimeFormat
    public class User {
    // 增加下面这个字段，并贴注解
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
        // 省略 setter getter toString
    }

    方法2:
    public ModelAndView hello(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        logger.info("date = " + date);
        return null;
    }
    方法3:
        xml配置
  -->
  <!--配置日期转换器 -->
  <!--
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
      <property name="converters">
        <set>
          <bean class="${groupId}.utils.StringToDateConverter" />
        </set>
      </property>
    </bean>
  -->

  <!-- 配置上传解析器，注意上传解析器这个 bean 名称是固定的，必须为multipartResolver。有中文乱码的问题...-->
  <!--  <bean id="multipartResolver" class="org.springframework.web.multipart.support.StandardServletMultipartResolver" />-->
  <!-- 文件上传解析器 id必须是multipartResolver-->
  <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!--最大上传文件大小 10M-->
    <property name="maxUploadSize" value="#{1024*1024*10}" />
  </bean>

  <mvc:interceptors>
    <mvc:interceptor>
      <mvc:mapping path="/**" />
      <mvc:exclude-mapping path="/static/**" />
      <mvc:exclude-mapping path="/css/**" />
      <mvc:exclude-mapping path="/js/**" />
      <mvc:exclude-mapping path="/img/**" />
      <bean class="${groupId}.web.interceptor.CorsInterceptor" />
    </mvc:interceptor>

    <mvc:interceptor>
      <mvc:mapping path="/**" />
      <mvc:exclude-mapping path="/static/**" />
      <mvc:exclude-mapping path="/css/**" />
      <mvc:exclude-mapping path="/js/**" />
      <mvc:exclude-mapping path="/img/**" />
      <bean class="${groupId}.web.interceptor.EncodingInterceptor" />
    </mvc:interceptor>
  </mvc:interceptors>

  <import resource="applicationContext.xml" />
  <!-- <import resource="spring-shiro.xml" /> -->
</beans>`
}

module.exports = spring_mvc_xml