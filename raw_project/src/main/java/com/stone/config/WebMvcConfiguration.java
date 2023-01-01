package com.stone.config;

import com.stone.web.debug.DebugInterceptor;
import com.stone.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
/**
* 配置拦截器
* 1. 声明该类是一个java配置类
* 2. 实现WebMvcConfigurer接口
*/
// 在spring boot中使用@EnableWebMvc 一定要注意的问题！
// https://blog.csdn.net/u012280292/article/details/95480663
// @EnableWebMvc // 配置了 反而报错了...蛋疼
// 都扫描到了为什么还需要做这一步? 因为在@SpringBootApplication没有执行 根配置类?
// @Import(value = {DruidConfig.class})
// 在主配置类中关联XML配置
// @ImportResource(locations = {"classpath:applicationContext.xml"})
// @Configuration
// public class WebMvcConfiguration extends WebMvcAutoConfiguration implements WebMvcConfigurer {
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

@Autowired(required = false)
private LoginInterceptor    loginInterceptor;

@Autowired(required = false)
private DebugInterceptor debugInterceptor;

// 重写父类提供的跨域请求处理的接口
@Override
public void addCorsMappings(CorsRegistry registry) {
registry
.addMapping("/**")
// spring 2.3
// .allowedOrigins("*")
// spring 2.4
.allowedOriginPatterns("*")
.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
.allowedHeaders("*")
// 控制器得有设置响应头, 例如:
// response.setHeader("username", "stone");
// response.setHeader("password", "123");
// 之后前端就可以通过XMLHttpRequest对象 获取响应的信息
// var allResponseHeaders = XMLHttpRequest.getAllResponseHeaders()
// var username = XMLHttpRequest.getResponseHeader("username")
// var password = XMLHttpRequest.getResponseHeader("password")
.exposedHeaders("username", "password")
// 如果用的是 jQuery 需要设置 xhrFields  : {withCredentials: true}, crossDomain: true,
// 如果用的是 axios  需要设置 axios.defaults.withCredentials = true
// 跨域cookies生效
.allowCredentials(true)
// 浏览器的同源策略，就是出于安全考虑，浏览器会限制从脚本发起的跨域HTTP请求（比如异步请求GET, POST, PUT, DELETE, OPTIONS等等），
// 所以浏览器会向所请求的服务器发起两次请求，第一次是浏览器使用OPTIONS方法发起一个预检请求，第二次才是真正的异步请求，第一次的预检请求获知服务器是否允许该跨域请求：
// 如果允许，才发起第二次真实的请求；如果不允许，则拦截第二次请求。
// Access-Control-Max-Age用来指定本次预检请求的有效期，单位为秒，，在此期间不用发出另一条预检请求。
// 例如：
// resp.addHeader("Access-Control-Max-Age", "0")，   表示每次异步请求都发起预检请求，也就是说，发送两次请求。
// resp.addHeader("Access-Control-Max-Age", "1800")，表示隔30分钟才发起预检请求。也就是说，发送两次请求
.maxAge(1800);
}

// @Override
// public void addCorsMappings(CorsRegistry registry) {
//   // 添加映射路径
//   registry.addMapping("/**")
//           // 放行哪些原始域
//           .allowedOrigins("*")
//           // 是否发送Cookie信息
//           .allowCredentials(true)
//           // 放行哪些原始域(请求方法)
//           .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//           // 放行哪些原始域(头部信息)
//           .allowedHeaders("*")
//           // 暴露哪些头部信息(因为跨域访问默认不能获取全部头部信息)
//           .exposedHeaders("Header1", "Header2");
// }

@Override
public void addInterceptors(InterceptorRegistry registry) {

if (debugInterceptor != null) {
registry
.addInterceptor(debugInterceptor)
.addPathPatterns("/**")
.excludePathPatterns(Arrays.asList(
"/favicon.ico",
"/css/**",
"/js/**",
"/img/**",
"/images/**",
"/font/**"
));
}

if (loginInterceptor != null) {
registry
.addInterceptor(loginInterceptor)
.addPathPatterns("/**")
.excludePathPatterns(Arrays.asList(
"/favicon.ico",
"/css/**",
"/js/**",
"/img/**",
"/images/**",
"/font/**"
));
}

}
}

