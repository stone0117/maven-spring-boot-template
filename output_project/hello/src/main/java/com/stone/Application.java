package com.stone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by stone on 2022/03/25
 *
 * @author stone
 */
@MapperScan(basePackages = {"com.stone.mapper"})
// 默认扫描所在目录的子目录
@SpringBootApplication
public class Application {
  public static void main(String[] args) {

    // SpringApplication   springApplication = new SpringApplication(Application.class);
    // Map<String, Object> properties  = new LinkedHashMap<>();
    // properties.put("server.port", 8080);
    // springApplication.setDefaultProperties(properties);
    // springApplication.run(args);

    SpringApplication.run(Application.class);
  }
}