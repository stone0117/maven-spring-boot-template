package com.stone.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Configuration
public class JsonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    // jsonString -> Object 的时候 枚举字段 获取 toString中返回的值
    return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
  }

  @Bean
  public Gson gsonConverter() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd");
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    return gson;
  }
}
