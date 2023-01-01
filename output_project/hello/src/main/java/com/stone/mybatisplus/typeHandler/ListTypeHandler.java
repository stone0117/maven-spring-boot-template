package com.stone.mybatisplus.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
// @MappedTypes(value = {
//     List.class
// })
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListTypeHandler extends BaseTypeHandler<List<Long>> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<Long> parameter, JdbcType jdbcType) throws SQLException {
    StringJoiner joiner = new StringJoiner(",");
    for (Long aLong : parameter) {
      joiner.add(aLong.toString());
    }
    ps.setString(i, joiner.toString());
  }

  @Override
  public List<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String listString = rs.getString(columnName);

    if (listString == null) { return null;}

    String[] strings = listString.split(",");
    return Arrays.stream(strings).map(Long::parseLong).collect(Collectors.toList());
  }

  @Override
  public List<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String listString = rs.getString(columnIndex);

    if (listString == null) { return null;}

    String[] strings = listString.split(",");
    return Arrays.stream(strings).map(Long::parseLong).collect(Collectors.toList());
  }

  @Override
  public List<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String listString = cs.getString(columnIndex);

    if (listString == null) { return null;}

    String[] strings = listString.split(",");
    return Arrays.stream(strings).map(Long::parseLong).collect(Collectors.toList());
  }
}