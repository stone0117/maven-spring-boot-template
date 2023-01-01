package debug.factory;

import debug.pojo.PersonTest;

/**
* Created by stone on 2020/12/28*
* @author stone*/
public class StaticFactoryTest {
  public static PersonTest getPerson() {
    return new PersonTest();
  }
}
