package com.stone.mybatisplus.enums;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public interface BaseEnum {

  String DEFAULT_VALUE_NAME = "value";

  String DEFAULT_LABEL_NAME = "label";

  default Integer getValue() {
    Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
    if (field == null) { return null; }
    try {
      field.setAccessible(true);
      return Integer.parseInt(field.get(this).toString());
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  default String getLabel() {
    Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
    if (field == null) { return null; }
    try {
      field.setAccessible(true);
      return field.get(this).toString();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * ???
   * @param enumClass
   * @param value
   * @param <T>
   * @return
   */
  static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, Integer value) {
    if (value == null) { throw new IllegalArgumentException("DisplayedEnum value should not be null"); }
    if (enumClass.isAssignableFrom(BaseEnum.class)) { throw new IllegalArgumentException("illegal DisplayedEnum type"); }
    T[] enums = enumClass.getEnumConstants();
    for (T t : enums) {
      BaseEnum displayedEnum = (BaseEnum) t;
      if (displayedEnum.getValue().equals(value)) { return (T) displayedEnum; }
    }
    throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
  }

  /**
   * ???
   * @param enums
   * @param value
   * @param <T>
   * @return
   */
  static <T> T valueOfEnum(T[] enums, Integer value) {

    for (T t : enums) {
      BaseEnum displayedEnum = (BaseEnum) t;
      if (displayedEnum.getValue().equals(value)) { return (T) displayedEnum; }
    }
    throw new IllegalArgumentException("cannot parse integer: " + value + " to ");
  }
}
