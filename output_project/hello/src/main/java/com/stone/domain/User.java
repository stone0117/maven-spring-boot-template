package com.stone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.util.StringJoiner;
import java.io.Serializable;

/**
* Created by stone on 2023/01/01
*
* @author stone
*/
@Data
// 字符串字段如果经常使用Like, 可以直接在字段上设置注解
// @TableField(condition=SqlCondition.LIKE)
// 逻辑删除, 配置全局逻辑删除设置
// @TableLogic 贴上注解就可以
@TableName(value = "t_user")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

            /**  */
      // @TableId(value = "id", type = IdType.AUTO)
      @TableId(value = "id")
      private Integer id;
                /**  */
      private String name;
                /**  */
      private Integer age;
                /**  */
      private String gender;
                /**  */
      private String email;
                /**  */
      private String phone;
                /**  */
      private java.util.Date birthday;
                /**  */
      private java.util.Date entryDate;
                /**  */
      private String job;
                /**  */
      private Double salary;
                /**  */
      private String resume;
                /**  */
      private String country;
      }