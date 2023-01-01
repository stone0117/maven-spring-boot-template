package com.stone.mapper;

import com.stone.domain.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
* Created by stone on 2023/01/01
*
* @author stone
*/
public interface UserMapper extends DefaultBaseMapper<User>{

  <T> T selectByPrimaryKey(@Param("id") Serializable id);

  <T, R> Page<R> queryForList(@Param("page") Page<R> page, @Param(Constants.WRAPPER) Wrapper<T> wrapper);
}