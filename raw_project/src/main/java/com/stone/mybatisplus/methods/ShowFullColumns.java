package com.stone.mybatisplus.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.HashMap;
import java.util.Map;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class ShowFullColumns extends AbstractMethod {

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    String    script    = String.format("SHOW FULL COLUMNS FROM %s", tableInfo.getTableName());
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
    // return this.addSelectMappedStatementForTable(mapperClass, "showFullColumns", sqlSource, tableInfo);
    return this.addSelectMappedStatementForOther(mapperClass, "showFullColumns", sqlSource, ((Map<String, String>) new HashMap<String, String>()).getClass());
  }
}
