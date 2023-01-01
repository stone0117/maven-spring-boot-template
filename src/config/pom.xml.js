const {pReadTextFile, velocityRender} = require('../utils')
const path                            = require('path')

const config = {
  '000:spring-boot'           : [
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-web',
      'exclusions': [
        {
          'groupId'   : 'org.springframework.boot',
          'artifactId': 'spring-boot-starter-logging',
        },
      ],
      'comment'   : 'spring-boot web相关包',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter',
      'exclusions': [
        {
          'groupId'   : 'org.springframework.boot',
          'artifactId': 'spring-boot-starter-logging',
        },
      ],
      'comment'   : 'spring-boot 核心包',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-log4j2',
      'comment'   : '引入log4j2依赖',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-test',
      'comment'   : 'spring-boot 测试包',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-aop',
      'comment'   : '支持使用 Spring AOP 和 AspectJ 进行切面编程, tx:annotation-driven 就是支持事务注解的（@Transactional） 。https://www.cnblogs.com/alice-cj/p/10417773.html',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-configuration-processor',
      'comment'   : 'spring默认使用yml中的配置，但有时候要用传统的xml或properties配置，就需要使用spring-boot-configuration-processor了',
      'optional'  : 'true',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-jdbc',
      'comment'   : 'Java访问数据库的标准规范',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-data-redis',
      'comment'   : 'redis',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-data-mongodb',
      'comment'   : 'mongodb',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-data-elasticsearch',
      'comment'   : 'Elasticsearch',
      'ignore'    : true,
    },
    {
      'groupId'   : 'mysql',
      'artifactId': 'mysql-connector-java',
      'comment'   : '连接mysql数据库的驱动',
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-freemarker',
      'comment'   : 'freemarker',
      'ignore'    : false,
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-starter-thymeleaf',
      'comment'   : 'thymeleaf',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.springframework.boot',
      'artifactId': 'spring-boot-devtools',
      'comment'   : 'devtools',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.projectlombok',
      'artifactId': 'lombok',
      'comment'   : '<optional>true</optional>表示两个项目之间依赖不传递',
      'optional'  : 'true',
    },
    {
      'groupId'   : 'com.github.pagehelper',
      'artifactId': 'pagehelper-spring-boot-starter',
      'version'   : '1.3.0',
      'comment'   : 'PageHelper',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.mybatis.spring.boot',
      'artifactId': 'mybatis-spring-boot-starter',
      'version'   : '2.1.3',
      'scope'     : '',
      'comment'   : 'mybatis-spring-boot-starter',
      'ignore'    : false,
    },
    {
      'groupId'   : 'com.baomidou',
      'artifactId': 'mybatis-plus-boot-starter',
      'version'   : '3.4.0',
      'scope'     : '',
      'comment'   : 'mybatis-plus-boot-starter',
      'ignore'    : false,
    },
    {
      'groupId'   : 'com.alibaba',
      'artifactId': 'druid-spring-boot-starter',
      'version'   : '1.1.10',
      'scope'     : '',
      'comment'   : 'spring boot 阿里巴巴数据库连接池: druid',
    },
    {
      'groupId'   : 'org.apache.httpcomponents',
      'artifactId': 'httpclient',
      'scope'     : '',
      'comment'   : 'https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.apache.httpcomponents',
      'artifactId': 'httpmime',
      'scope'     : '',
      'comment'   : 'https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime',
      'ignore'    : true,
    },

  ],
  '001:json'                  : [
    {
      'groupId'   : 'com.alibaba',
      'artifactId': 'fastjson',
      'version'   : '1.2.73',
      'scope'     : '',
      'comment'   : 'jackson和Gson spring-boot管理了, 版本引错欲仙欲死 - -!!!',
    },
  ],
  '002:tool'                  : [
    {
      'groupId'   : 'cn.hutool',
      'artifactId': 'hutool-all',
      'version'   : '5.5.7',
      'scope'     : '',
      'comment'   : 'https://mvnrepository.com/artifact/cn.hutool/hutool-all',
      'ignore'    : true,
    },
    {
      'groupId'   : 'commons-fileupload',
      'artifactId': 'commons-fileupload',
      'version'   : '1.3.1',
      'scope'     : '',
      'comment'   : 'https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload',
    },
    {
      'groupId'   : 'commons-io',
      'artifactId': 'commons-io',
      'version'   : '2.8.0',
      'scope'     : '',
      'comment'   : 'https://mvnrepository.com/artifact/commons-io/commons-io',
    },
  ],
  '003:poi'                   : [
    {
      'groupId'   : 'org.apache.poi',
      'artifactId': 'poi',
      'version'   : '4.1.2',
      'comment'   : 'https://mvnrepository.com/artifact/org.apache.poi/poi',
      'ignore'    : true,
    },
    {
      'groupId'   : 'org.apache.poi',
      'artifactId': 'poi-ooxml',
      'version'   : '4.1.2',
      'comment'   : 'https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml',
      'ignore'    : true,
    },
  ],
  '004:spider'                : [
    {
      'groupId'   : 'org.jsoup',
      'artifactId': 'jsoup',
      'version'   : '1.13.1',
      'comment'   : 'https://mvnrepository.com/artifact/org.jsoup/jsoup',
      'ignore'    : true,
    },
  ],
  '005:performance'           : [
    {
      'groupId'   : 'p6spy',
      'artifactId': 'p6spy',
      'version'   : '3.8.7',
      'comment'   : 'https://mvnrepository.com/artifact/p6spy/p6spy',
    },
  ],
  '006:mybatis-plus-generator': [
    {
      'groupId'   : 'com.baomidou',
      'artifactId': 'mybatis-plus-generator',
      'version'   : '3.4.0',
      'scope'     : 'test',
      'comment'   : 'https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-generator',
    },
  ],
  '007:redis'                 : [
    {
      'groupId'   : 'redis.clients',
      'artifactId': 'jedis',
      'comment'   : 'https://mvnrepository.com/artifact/redis.clients/jedis',
      'ignore'    : true,
    },
  ],
  // '008:shiro'                 : [
  //   {
  //     'groupId'   : 'org.apache.shiro',
  //     'artifactId': 'shiro-spring-boot-web-starter',
  //     'version'   : '1.7.0',
  //     'comment'   : 'https://mvnrepository.com/artifact/org.apache.shiro/shiro-spring-boot-web-starter',
  //     'ignore'    : '${context.useShiro?string(\'false\',\'true\')}',
  //   },
  //   {
  //     'groupId'   : 'net.mingsoft',
  //     'artifactId': 'shiro-freemarker-tags',
  //     'version'   : '1.0.1',
  //     'comment'   : 'Freemarker 的 shiro 标签库',
  //     'ignore'    : '${context.useShiro?string(\'false\',\'true\')}',
  //   },
  //   {
  //     'groupId'   : 'org.apache.shiro',
  //     'artifactId': 'shiro-ehcache',
  //     'version'   : '1.7.0',
  //     'comment'   : 'https://mvnrepository.com/artifact/org.apache.shiro/shiro-ehcache',
  //     'ignore'    : '${context.useShiro?string(\'false\',\'true\')}',
  //   },
  // ],
  '009:swagger2': [
    {
      'groupId'   : 'io.springfox',
      'artifactId': 'springfox-swagger2',
      'version'   : '2.9.2',
      'comment'   : 'swagger2',
      'ignore'    : true,
    },
    {
      'groupId'   : 'io.springfox',
      'artifactId': 'springfox-swagger-ui',
      'version'   : '2.9.2',
      'comment'   : 'swagger2',
      'ignore'    : true,
    },
  ],
}

function exclusions(item) {

  if (item.exclusions && item.exclusions.length > 0) {

    let exclusionList = item.exclusions.map(item => {
      return `        <exclusion>
          <groupId>${item.groupId}</groupId>
          <artifactId>${item.artifactId}</artifactId>
        </exclusion>`
    }).join('\n')

    return `\n      <exclusions>
${exclusionList}
      </exclusions>`
  }
  else {
    return ''
  }
}

function scope(item) {
  return item.scope ? `\n        <scope>${item.scope}</scope>` : ''
}

function version(item) {
  return item.version ? `\n        <version>${item.version}</version>` : ''
}

function dependencies(config) {

  let list = []
  for (let [key, value] of Object.entries(config)) {
    // console.log(key, '=', value)

    let r = value.filter(item => {
      if (!!item.ignore) {
        return false
      }
      else {
        return true
      }
    })

    let innerList = []
    for (let [index, item] of r.entries()) {
      innerList.push(`    <!-- ${item.comment} -->
    <dependency>
      <groupId>${item.groupId}</groupId>
      <artifactId>${item.artifactId}</artifactId> ${exclusions(item)} ${scope(item)} ${version(item)}
    </dependency>`)
    }

    list.push(`    <!-- 
    ////////////////////////////////////////////////////////////////////////////
    ///////////////// ${key} /////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    -->
${innerList.join('\n')}`)
  }

  return list.join('\n')
}

async function pom_xml(context) {
  const {
          groupId,
          artifactId,
          version,
          packaging,
          name,
          encoding,
          javaVersion,
          finalName,
        } = context

  return `<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.3</version>
    <relativePath />
  </parent>

  <groupId>${groupId}</groupId>
  <artifactId>${artifactId}</artifactId>
  <version>${version}</version>
  <packaging>jar</packaging>

  <name>${artifactId}</name>
  <description>project for Spring Boot</description>

  <properties>
    <java.version>${javaVersion}</java.version>
    <maven.compiler.source>${javaVersion}</maven.compiler.source>
    <maven.compiler.target>${javaVersion}</maven.compiler.target>
    <project.build.sourceEncoding>${encoding}</project.build.sourceEncoding>
    <project.reporting.outputEncoding>${encoding}</project.reporting.outputEncoding>

  </properties>
  
  <dependencies>
${dependencies(config)}

    <dependency>
      <groupId>com.stone</groupId>
      <artifactId>stoneutils</artifactId>
      <version>1.2-SNAPSHOT</version>
    </dependency>
  </dependencies>

  <build>
    <finalName>${finalName}</finalName>
    <plugins>
      <plugin>
        <groupId>org.zeroturnaround</groupId>
        <artifactId>jrebel-maven-plugin</artifactId>
        <version>1.1.10</version>
        <executions>
          <execution>
            <id>generate-rebel-xml</id>
            <phase>process-resources</phase>
            <goals>
              <goal>generate</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <!-- spring-boot 打包插件 -->
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludes>
            <exclude>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
            </exclude>
          </excludes>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>`
}

module.exports = pom_xml

if (require.main === module) {

  dependencies(config)

  // const name = 't005-hello-maven'
  //
  // pom_xml({
  //   // name       : toEnglish(name),
  //   // description: name,
  //   groupId    : 'com.stone',
  //   artifactId : name,
  //   version    : '1.0-SNAPSHOT',
  //   packaging  : 'war',
  //   name       : `${name} Maven Webapp`,
  //   url        : 'http://www.example.com',
  //   encoding   : 'UTF-8',
  //   javaVersion: '1.8',
  //   finalName  : name,
  // })
}
