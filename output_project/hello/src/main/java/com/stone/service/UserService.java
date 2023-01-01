package com.stone.service;

import com.stone.domain.PageResult;
import com.stone.domain.User;
import com.stone.pojo.qo.UserQueryObject;

import java.io.Serializable;

/**
* Created by stone on 2023/01/01
*
* @author stone
*/
public interface UserService extends DefaultService<User> {

  <T> T selectByPrimaryKey(Serializable id);

  <T> PageResult<T> queryForList(UserQueryObject qo);
}