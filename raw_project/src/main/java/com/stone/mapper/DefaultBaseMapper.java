package com.stone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by stone on 2022/03/25
 *
 * @author stone
 */
public interface DefaultBaseMapper<T> extends BaseMapper<T> {

  /**
   * 功能描述: 逻辑删除带自动填充功能
   *
   * @param entity
   *
   * @return
   */
  @SuppressWarnings("MybatisPlusMapperMethodInspection")
  int deleteByIdWithFill(T entity);

  /**
   * 恢复逻辑删除
   *
   * @param entity
   *
   * @return
   */
  int recoveryById(T entity);

  /**
   * 恢复逻辑删除
   *
   * @param id
   *
   * @return
   */
  int recoveryById(Serializable id);

  /**
   * 管理员获取所有行, 逻辑删除的也获取
   *
   * @return
   */
  List<T> findAll();

  /**
   * 所有字段详细信息
   *
   * @return
   */
  List<Map<String, String>> showFullColumns();
}
