package com.stone.mybatisplus.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public enum WeekEnum implements BaseEnum{
  MON("星期一",1),
  TUE("星期二",2),
  WED("星期三",3),
  THU("星期四",4),
  FRI("星期五",5),
  SAT("星期六",6),
  SUN("星期日",7);

  private String label;

  @JsonValue
  private Integer value;

  WeekEnum(String label, Integer value) {
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

  public static WeekEnum getByValue(Integer value) {
    for (WeekEnum weekEnum : values()) {
      if (weekEnum.getValue().equals(value)) { return weekEnum;}
    }
    return null;
  }
}
