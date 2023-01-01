package com.stone.mybatisplus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.function.Predicate;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryById extends AbstractMethod {

  /**
   * 字段筛选条件
   */
  @Setter
  @Accessors(chain = true)
  private Predicate<TableFieldInfo> predicate;

  private Predicate<TableFieldInfo> getPredicate() {
    Predicate<TableFieldInfo> noLogic = t -> !t.isLogicDelete();
    if (predicate != null) {
      return noLogic.and(predicate);
    }
    return noLogic;
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

    String sql = "";
    if (tableInfo.isWithLogicDelete()) {
      String column              = tableInfo.getLogicDeleteFieldInfo().getColumn();
      String logicNotDeleteValue = tableInfo.getLogicDeleteFieldInfo().getLogicNotDeleteValue();
      String logicDeleteValue    = tableInfo.getLogicDeleteFieldInfo().getLogicDeleteValue();
      String idColumn            = tableInfo.getKeyColumn();
      String idProperty          = tableInfo.getKeyProperty();
      sql = String.format(
          "UPDATE %s SET %s=%s WHERE %s=#{%s} AND %s=%s",
          tableInfo.getTableName(),
          column,
          logicNotDeleteValue,
          idColumn,
          idProperty,
          column,
          logicDeleteValue
      );
    }
    else {
      // "UPDATE %s SET %s=%s WHERE %s=#{%s} AND %s=%s",
      SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
      final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
      String sqlSet = this.filterTableFieldInfo(tableInfo.getFieldList(), getPredicate(), i -> i.getSqlSet(true, ENTITY_DOT), NEWLINE);
      sqlSet = SqlScriptUtils.convertSet(sqlSet);
      sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
    }
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
    return addUpdateMappedStatement(mapperClass, modelClass, "recoveryById", sqlSource);
  }
}
