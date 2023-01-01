package com.stone.mybatisplus.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public enum StateEnum implements BaseEnum {
  NORMAL("正常", 0),
  DISABLE("冻结", 1);

  private String label;

  @JsonValue
  private Integer value;

  StateEnum(String label, Integer value) {
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

  public static StateEnum getByValue(Integer value) {
    for (StateEnum stateEnum : values()) {
      if (stateEnum.getValue().equals(value)) { return stateEnum;}
    }
    return null;
  }
}