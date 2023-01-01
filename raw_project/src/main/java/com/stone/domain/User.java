package com.stone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

  import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

  import java.util.Date;

import java.util.StringJoiner;
import java.io.Serializable;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Data
// 字符串字段如果经常使用Like, 可以直接在字段上设置注解
// @TableField(condition=SqlCondition.LIKE)
// 逻辑删除, 配置全局逻辑删除设置
// @TableLogic 贴上注解就可以
@TableName(value = "t_user")
public class User implements Serializable {
private static final long serialVersionUID = 1L;
      /** 用户ID */
      // @TableId(value = "user_id", type = IdType.AUTO)
      @TableId(value = "user_id")
      private Long userId;
      /** 用户姓名 */
      private String userName;
      /** 用户联系方式 */
      private String userPhone;
      /** 用户昵称 */
      private String userNick;
      /** 性别: 0 女 1 男 2 保密 */
      private Integer gender;
      /** 注册时间 */
          // 入参格式化 [前端 -> 后端 2008-08-08 08:08:08 转成 Fri Aug 08 08:08:08 CST 2008]
          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
          // 出参格式化 [后端 -> 前端 Fri Aug 08 08:08:08 CST 2008 转成 2008-08-08 08:08:08]
          @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date registerTime;
      /** 微信 */
      private String wechat;

}

