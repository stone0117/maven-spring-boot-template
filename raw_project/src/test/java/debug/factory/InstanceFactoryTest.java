package debug.factory;

import debug.pojo.PersonTest;

/**
* Created by stone on 2020/12/28*
* @author stone*/
public class InstanceFactoryTest {
  public PersonTest getPerson() {
    return new PersonTest();
  }
}
