package com.stone.mybatisplus.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public enum SeasonEnum implements BaseEnum {
  SPRING("春天", 0),
  SUMMER("夏天", 1),
  AUTUMN("秋天", 2),
  WINTER("冬天", 3);

  private String label;

  @JsonValue
  private Integer value;

  SeasonEnum(String label, Integer value) {
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

  public static SeasonEnum getByValue(Integer value) {
    for (SeasonEnum seasonEnum : values()) {
      if (seasonEnum.getValue().equals(value)) { return seasonEnum;}
    }
    return null;
  }
}
