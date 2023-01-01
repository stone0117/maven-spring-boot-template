package spring.test;

import debug.pojo.PersonTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* Created by stone on 2020/12/28*
* @author stone*/
public class BeanTest {

  private ApplicationContext applicationContext;

  @Before
  public void init() {
    applicationContext = new ClassPathXmlApplicationContext("config/bean-test.xml");
  }

  @After
  public void close() throws Exception {
    applicationContext.getClass().getMethod("close").invoke(applicationContext);
  }

  @Test
  public void testBean() throws Exception {
    PersonTest person1 = applicationContext.getBean("person1", PersonTest.class);
    PersonTest person2 = applicationContext.getBean("person2", PersonTest.class);
    PersonTest person3 = applicationContext.getBean("person3", PersonTest.class);
    PersonTest person4 = applicationContext.getBean("person4", PersonTest.class);
    System.out.println(person1);
    System.out.println(person2);
    System.out.println(person3);
    System.out.println(person4);
  }

  @Test
  public void testScope() throws Exception {
    PersonTest p1 = applicationContext.getBean("person4", PersonTest.class);
    PersonTest p2 = applicationContext.getBean("person4", PersonTest.class);
    System.out.println(p1 == p2);
  }

  @Test
  public void testLife() throws Exception {
    PersonTest p5 = applicationContext.getBean("person5", PersonTest.class);
    PersonTest p6 = applicationContext.getBean("person6", PersonTest.class);
    p5.run();
    p6.run();
  }

  @Test
  public void testInjection() throws Exception {
    PersonTest person7 = applicationContext.getBean("person7", PersonTest.class);
    System.out.println(person7);

    PersonTest person8 = applicationContext.getBean("person8", PersonTest.class);
    System.out.println(person8);

    PersonTest person9 = applicationContext.getBean("person9", PersonTest.class);
    System.out.println(person9);
  }
}
