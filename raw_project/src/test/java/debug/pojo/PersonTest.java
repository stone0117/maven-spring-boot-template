package debug.pojo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
/**
* Created by stone on 2020/12/28*
* @author stone*/
public class PersonTest {
  private static final Logger logger = LogManager.getLogger(PersonTest.class);

  private String  name;
  private Integer age;
  private Date    birthday;

  private String[]            hobbies;
  private List<String>        friends;
  private Set<String>         strings;
  private Map<String, String> map;
  private Properties          properties;

  public PersonTest(String name, Integer age, Date birthday) {
    this.name     = name;
    this.age      = age;
    this.birthday = birthday;
  }

  public PersonTest() {
    logger.info(String.format("%-25s ---> 对象创建了", getPointer()));
  }

  private String getPointer() {
    return this.getClass().getSimpleName() + "<0x" + Integer.toHexString(this.hashCode()) + ">";
  }

  public void run() {
    logger.info(String.format("%-25s ---> run方法执行了", getPointer()));
  }

  public void init() {
    logger.info(String.format("%-25s ---> 对象初始化了", getPointer()));
  }

  public void destroy() {
    logger.info(String.format("%-25s ---> 对象销毁了", getPointer()));
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setHobbies(String[] hobbies) {
    this.hobbies = hobbies;
  }

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }

  public void setStrings(Set<String> strings) {
    this.strings = strings;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PersonTest.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("age=" + age)
        .add("birthday=" + birthday)
        .add("hobbies=" + Arrays.toString(hobbies))
        .add("friends=" + friends)
        .add("strings=" + strings)
        .add("map=" + map)
        .add("properties=" + properties)
        .toString();
  }
}
