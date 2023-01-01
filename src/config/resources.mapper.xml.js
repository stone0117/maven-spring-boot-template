function resources_mapper_xml(context) {
  let {
        fields,
        fieldsStringValue,
        fieldsWithoutPrimaryKey,
        fieldsWithoutPrimaryKeyStringValue,
        primaryField,
        tableName,
        groupId,
        className,
      } = context
  return `<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--@ignoreSql-->
<!--
  上面的注释表示 关闭 mybatis-helper插件的功能
-->
<!--$sql0-->
<!--
  告诉 mybatis-helper $ 代表什么意思, 数字 或者 字段
-->

<mapper namespace="${groupId}.mapper.${className}Mapper">

  <resultMap id="BaseResultMap" type="${groupId}.domain.${className}">
    <!--@Table ${tableName}-->
    ${fields.map(item => {
    if (item.Key === 'PRI') {
      return `<id column="${item.Field}" property="${item.javaField}" />`
    }
    else {
      return `<result column="${item.Field}" property="${item.javaField}" />`
    }
  }).join('\n')}
  </resultMap>

  <resultMap id="ExtResultMap" type="${groupId}.domain.${className}" extends="BaseResultMap">

  </resultMap>

  <sql id="Base_Column_List">
    ${fieldsStringValue}
  </sql>

  <select id="selectByPrimaryKey" resultMap="BaseResultMap">
    SELECT
    <include refid="Base_Column_List" />
    FROM ${tableName} WHERE ${primaryField.Field} = #{id}
  </select>

  <select id="queryForList" resultMap="BaseResultMap">
    SELECT
    <include refid="Base_Column_List" />
    FROM ${tableName} \${ew.customSqlSegment} <!--$sql-->
  </select>

  <!-- <select id="selectAll" resultMap="BaseResultMap"> -->
  <!-- 如果配置了 \${ew.sqlSelect} 这个, 那必须得有 queryWrapper.select(Customer.class,tableFieldInfo -> true); 这种代码 -->
  <!--@ignoreSql-->
  <!-- SELECT \${ew.sqlSelect} FROM ${tableName} \${ew.customSqlSegment} -->
  <!-- </select> -->

</mapper>`
}

module.exports = resources_mapper_xml