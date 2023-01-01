package com.stone.mybatisplus.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public enum GenderEnum implements BaseEnum {

  MALE("男", 0),
  FEMALE("女", 1),
  UNKNOWN("未知", 3);

  private String label;

  @JsonValue
  private Integer value;

  GenderEnum(String label, Integer value) {
    this.label = label;
    this.value = value;
  }

  @Override
  public Integer getValue() {
    return value;
  }

  @Override
  public String getLabel() {
    return label;
  }

  public static GenderEnum getByValue(Integer value) {
    for (GenderEnum genderEnum : values()) {
      if (genderEnum.getValue().equals(value)) { return genderEnum;}
    }
    return null;
  }
}
