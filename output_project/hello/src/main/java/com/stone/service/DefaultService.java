package com.stone.service;

/**
 * Created by stone on 2022/03/25
 *
 * @author stone
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import cn.cindy.mapper.DefaultBaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DefaultService<T> extends IService<T> {

  default boolean removeByIdWithFill(T entity) {
    int count = ((DefaultBaseMapper<T>) getBaseMapper()).deleteByIdWithFill(entity);
    return SqlHelper.retBool(count);
  }

  default boolean recoveryById(T entity) {
    int count = ((DefaultBaseMapper<T>) getBaseMapper()).recoveryById(entity);
    return SqlHelper.retBool(count);
  }

  default boolean recoveryById(Serializable id) {
    int count = ((DefaultBaseMapper<T>) getBaseMapper()).recoveryById(id);
    return SqlHelper.retBool(count);
  }

  default List<T> findAll() {
    return ((DefaultBaseMapper<T>) getBaseMapper()).findAll();
  }

  default List<Map<String, String>> showFullColumns() {
    return ((DefaultBaseMapper<T>) getBaseMapper()).showFullColumns();
  }
}
