package com.stone.config;

import com.stone.utils.freemarker.ToPrettyJson;
import com.stone.utils.freemarker.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Profile("freemarker")
@Configuration
public class FreeMarkerConfig {

// @ConfigurationProperties(prefix = "spring.freemarker")
@Bean
public FreeMarkerConfigurer freeMarkerConfigurer() {

  FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();

freeMarkerConfigurer.setDefaultEncoding("UTF-8");
freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/");
Properties settings = new Properties();
settings.setProperty("classic_compatible", "true");
freeMarkerConfigurer.setFreemarkerSettings(settings);

Map<String, Object> variables = new HashMap<>();
variables.put("toJson", new ToPrettyJson());
variables.put("toString", new ToString());
freeMarkerConfigurer.setFreemarkerVariables(variables);
return freeMarkerConfigurer;
}

@Bean
public ViewResolver viewResolver() {
FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
freeMarkerViewResolver.setExposeSessionAttributes(true);
freeMarkerViewResolver.setSuffix(".ftl");
freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
return freeMarkerViewResolver;
}
}
