package com.stone.mybatisplus.typeHandler;

import com.stone.mybatisplus.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
// @MappedTypes(value = {
//     GenderEnum.class,
//     SeasonEnum.class,
//     StateEnum.class,
//     WeekEnum.class
// })
@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
public class EnumTypeHandler extends BaseTypeHandler<BaseEnum> {

  private Class<BaseEnum> type;

  public EnumTypeHandler() {}

  public EnumTypeHandler(Class<BaseEnum> type) {
    if (type == null) { throw new IllegalArgumentException("Type argument cannot be null"); }
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
    ps.setInt(i, parameter.getValue());
  }

  @Override
  public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return convert(rs.getInt(columnName));
  }

  @Override
  public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return convert(rs.getInt(columnIndex));
  }

  @Override
  public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return convert(cs.getInt(columnIndex));
  }

  private BaseEnum convert(int status) {
    BaseEnum[] objs = type.getEnumConstants();
    for (BaseEnum em : objs) {
      if (em.getValue() == status) { return em;}
    }
    return null;
  }
}