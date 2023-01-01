package com.stone.config;

import com.stone.mybatisplus.methods.FindAll;
import com.stone.mybatisplus.methods.RecoveryById;
import com.stone.mybatisplus.methods.ShowFullColumns;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

import java.util.List;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Configuration
public class MybatisPlusConfig {

/** 自定义SQL注入器 */
@Bean
public DefaultSqlInjector sqlInjector() {
return new DefaultSqlInjector() {
@Override
public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
List<AbstractMethod> methodList = super.getMethodList(mapperClass);
// 增加根据id逻辑删除数据, 并带字段填充功能
methodList.add(new LogicDeleteByIdWithFill());
methodList.add(new FindAll());
methodList.add(new ShowFullColumns());
methodList.add(new RecoveryById());
return methodList;
}
};
}

/**
* 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
*/
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//向Mybatis过滤器链中添加分页拦截器
// 分页插件
PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
// 合理化: 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
paginationInnerInterceptor.setOverflow(true);
interceptor.addInnerInterceptor(paginationInnerInterceptor);
// 乐观锁插件
interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//还可以添加i他的拦截器
return interceptor;
}

// 过期方法
// @Bean
// public ConfigurationCustomizer configurationCustomizer() {
// return configuration -> configuration.setUseDeprecatedExecutor(false);
// }

// mybatis-plus 3.4.0 以下的版本
// // 分页插件
// @Bean
// public PaginationInterceptor paginationInterceptor() {
//   PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//   return paginationInterceptor;
// }
//
// // 乐观锁插件
// @Bean
// public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//   OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
//   return optimisticLockerInterceptor;
// }
}
