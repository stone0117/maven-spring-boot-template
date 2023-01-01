package com.stone.mybatisplus.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.stream.Collectors;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class FindAll extends AbstractMethod {

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    String fields = tableInfo.getFieldList().stream().map(TableFieldInfo::getColumn).collect(Collectors.joining(","));
    String script = String.format("select %s from %s", fields, tableInfo.getTableName());
    // String    script    = String.format("select %s from %s", tableInfo.getAllSqlSelect(), tableInfo.getTableName());
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
    return this.addSelectMappedStatementForTable(mapperClass, "findAll", sqlSource, tableInfo);
  }
}
