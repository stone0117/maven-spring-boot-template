package com.stone.service.impl;

import com.stone.domain.User;
import com.stone.domain.PageResult;
import com.stone.mapper.UserMapper;
import com.stone.pojo.qo.UserQueryObject;
import com.stone.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* Created by stone on 2023/01/01
*
* @author stone
*/
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public <T> T selectByPrimaryKey(Serializable id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public <T> PageResult<T> queryForList(UserQueryObject qo) {
    QueryWrapper<User> queryWrapper = Wrappers.query();
    // queryWrapper
    //     .like(StringUtils.isNotBlank(qo.getKeyword()), "e.name", qo.getKeyword())
    //     .or()
    //     .like(StringUtils.isNotBlank(qo.getKeyword()), "e.email", qo.getKeyword())
    //     .eq(StringUtils.isNotBlank(qo.getDept_id()), "e.dept_id", qo.getDept_id());
    Page<T>       page       = userMapper.queryForList(new Page<>(qo.getCurrentPage(), qo.getPageSize()), queryWrapper);
    PageResult<T> pageResult = new PageResult<>(page);
    return pageResult;
  }

}