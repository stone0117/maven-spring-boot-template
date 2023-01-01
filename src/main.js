const moment                               = require('moment')
const path                                 = require('path')
const fs                                   = require('fs')
const fse                                  = require('fs-extra')
const {pinyin}                             = require('pinyin-pro')
const {tsconfigJson, packageJson, pom_xml} = require('./config')
const {showFullColumns}                    = require('./utils/mysql-tools.js')
const rootConfig                           = require('../zzzzzzzz')
const {generateDynamicFiles}               = require('./utils/dynamic-files.js')

const {
        upperFirstLatter,
        makeFolder,
        touchFile,
        pReadTextFile,
        pWriteTextFile,
        velocityRender,
        pExec, capitalize,
      }                       = require('./utils')
const camelCase               = require('camelcase')
const application_context_xml = require('./config/applicationContext.xml')
const spring_mvc_xml          = require('./config/spring.mvc.xml')
const {
        application_properties,
        application_dev_properties,
        application_local_properties,
      }                       = require('./config/application.properties')

// const rootPath = '/Users/stone/git_repository/vue-generator-node'
const rootPath = path.resolve(__dirname, '../')

function toEnglish(name) {
  name = name.replace(/(?:[\u3400-\u4DB5\u4E00-\u9FEA\uFA0E\uFA0F\uFA11\uFA13\uFA14\uFA1F\uFA21\uFA23\uFA24\uFA27-\uFA29]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])+/img, function (content) {
    return pinyin(content, {toneType: 'none'}).replace(/\s/img, '_').toLocaleLowerCase()
  })
  return name
}

!async function () {
  const config = rootConfig.context
  ////////////////////////////////////////////////////////////////////////////
  ///////////////// 拷贝基础项目 ///////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////
  try {
    await fse.copy(path.join(rootPath, 'raw_project'), config.destinationPath)
    console.log('raw_project copy success!')
  } catch (err) {console.error(err)}
  ////////////////////////////////////////////////////////////////////////////
  ///////////////// 个性化设置 ////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////
  let name = path.basename(config.destinationPath)

  const groupId        = config.groupId
  // const groupId2Folder = 'cn/zhongtian'
  const groupId2Folder = groupId.replace(/\./g, '/')

  const now    = moment().format('YYYY/MM/DD')
  const author = 'stone'

  const authorString = `/**
* Created by ${author} on ${now}
*
* @author ${author}
*/`
  const context = {
    now         : now,
    author      : author,
    authorString: authorString,
    ///
    templatePath   : path.resolve(__dirname, './templates'),
    destinationPath: config.destinationPath,
    // name       : toEnglish(name),
    // description: name,
    groupId2Folder: groupId2Folder,
    groupId       : groupId,
    artifactId    : name,
    version       : config.version,
    packaging     : config.packaging,
    name          : `${name} Maven Webapp`,
    url           : 'http://www.example.com',
    encoding      : config.encoding,
    javaVersion   : config.javaVersion,
    finalName     : name,
    /////////////////////////////
    dbName   : config.dbName,
    host     : config.host,
    user     : config.user,
    password : config.password,
    database : config.dbName,
    tableName: config.tableName,
    /////////////////////////////
    className                         : '',
    domain                            : '',
    fields                            : [],
    fieldsStringValue                 : '',
    fieldsWithoutPrimaryKey           : [],
    fieldsWithoutPrimaryKeyStringValue: '',
  }

  pom_xml:{
    const content = await pom_xml(context)
    await pWriteTextFile(path.join(config.destinationPath, 'pom.xml'), content)
  }
  log4j2_xml:{
    const dest     = path.join(config.destinationPath, '/src/main/resources', 'log4j2.xml')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./log4j2.xml.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }

  //   rebel_xml:{
  //     await pWriteTextFile(path.join(config.destinationPath, 'src/main/resources', 'rebel.xml'), `<?xml version="1.0" encoding="UTF-8"?>
  // <!--
  //   This is the JRebel configuration file. It maps the running application to your IDE workspace, enabling JRebel reloading for this project.
  //   Refer to https://manuals.jrebel.com/jrebel/standalone/config.html for more information.
  // -->
  // <application generated-by="intellij" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.zeroturnaround.com" xsi:schemaLocation="http://www.zeroturnaround.com http://update.zeroturnaround.com/jrebel/rebel-2_3.xsd">
  //
  //   <id>t001-hello-maven</id>
  //
  //   <classpath>
  //     <dir name="${path.join(config.destinationPath, 'target/classes')}">
  //     </dir>
  //   </classpath>
  //
  //   <web>
  //     <link target="/">
  //       <dir name="${path.join(config.destinationPath, 'src/main/webapp')}">
  //       </dir>
  //     </link>
  //   </web>
  //
  // </application>`)
  //   }

  move_folder:{
    src_main_java:{
      const src  = path.join(config.destinationPath, '/src/main/java/com/stone')
      const dest = path.join(config.destinationPath, '/src/main/java/' + groupId2Folder)
      if (src !== dest) {
        fse.moveSync(src, dest, {overwrite: true})
        fse.removeSync(path.dirname(src))
      }
    }

    src_main_resources:{
      const src  = path.join(config.destinationPath, '/src/main/resources/com/stone')
      const dest = path.join(config.destinationPath, '/src/main/resources/' + groupId2Folder)
      if (src !== dest) {
        fse.moveSync(src, dest, {overwrite: true})
        fse.removeSync(path.dirname(src))
      }
    }
  }

  /******************************************/
  /**** 静态文件 *****************************/
  /******************************************/
  db_properties:{
    //   host     : 'localhost',
    //   user     : 'root',
    //   password : 'admin',
    //   database : 'zhongtian',
    //   tableName: 't_user',
    const dest    = path.join(config.destinationPath, '/src/main/resources/', 'db.properties')
    const content = `jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://${context.host}:3306/${context.dbName}?characterEncoding=utf8
jdbc.username=${context.user}
jdbc.password=${context.password}
jdbc.maxActive=10`
    await pWriteTextFile(dest, content)
  }

  application_properties:{
    const dest = path.join(config.destinationPath, '/src/main/resources/', 'application.properties')
    await pWriteTextFile(dest, application_properties(context))
  }
  application_dev_properties:{
    const dest = path.join(config.destinationPath, '/src/main/resources/config', 'application-dev.properties')
    await pWriteTextFile(dest, application_dev_properties(context))
  }
  application_local_properties:{
    const dest = path.join(config.destinationPath, '/src/main/resources/config', 'application-local.properties')
    await pWriteTextFile(dest, application_local_properties(context))
  }

  application_java:{
    //
    const dest = path.join(config.destinationPath, '/src/main/java', context.groupId2Folder, 'Application.java')
    // language=TEXT
    await pWriteTextFile(dest, `package com.stone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by stone on 2022/03/25
 *
 * @author stone
 */
@MapperScan(basePackages = {"${groupId}.mapper"})
// 默认扫描所在目录的子目录
@SpringBootApplication
public class Application {
  public static void main(String[] args) {

    // SpringApplication   springApplication = new SpringApplication(Application.class);
    // Map<String, Object> properties  = new LinkedHashMap<>();
    // properties.put("server.port", 8080);
    // springApplication.setDefaultProperties(properties);
    // springApplication.run(args);

    SpringApplication.run(Application.class);
  }
}`)
  }

  const tables = rootConfig.tables

  for (let tableName of tables) {
    const cloneContext     = deepClone(context)
    cloneContext.tableName = tableName
    cloneContext.domain    = computedDomain(cloneContext.tableName)
    cloneContext.className = capitalize(cloneContext.domain)

    mysql:{
      cloneContext.fields                             = await showFullColumns(cloneContext)
      cloneContext.fieldsStringValue                  = cloneContext.fields.map(item => item.Field).join(',')
      cloneContext.fieldsWithoutPrimaryKey            = cloneContext.fields.filter(item => item.Key !== 'PRI')
      cloneContext.fieldsWithoutPrimaryKeyStringValue = cloneContext.fieldsWithoutPrimaryKey.map(item => item.Field).join(',')
    }
    /******************************************/
    /*** 动态文件 ******************************/
    /******************************************/
    dynamic_files:{
      await generateDynamicFiles(cloneContext)
    }
  }

  handle_package:{
    src_main_java: {
      let src_main_java = '/src/main/java/'
      let s             = path.join(config.destinationPath, src_main_java)
      let list          = await listDir(s)

      for (let listElement of list) {
        try {
          let content      = await pReadTextFile(listElement)
          let finalContent = content.replace(/^package.*;/ig, item => {
            let s1 = `${listElement}`.replace(config.destinationPath, '').replace(src_main_java, '')
            let s2 = path.dirname(s1)
            return 'package ' + s2.replace(/\//g, '.') + ';'
          })

          finalContent = finalContent.replace(/com\.stone/img, context.groupId)

          await pWriteTextFile(listElement, finalContent)
          // console.log('修改 package 成功', '\t', listElement)
          // process.nextTick(async () => {
          //
          // })
        } catch (err) {
          console.error('err = ', err)
        }
      }
    }

    src_main_resources:{
      let src_main_java = '/src/main/resources/' + groupId2Folder
      let s             = path.join(config.destinationPath, src_main_java)
      let list          = await listDir(s)

      for (let listElement of list) {
        try {
          let content = await pReadTextFile(listElement)
          content     = content.replace(/com\.stone/img, context.groupId)

          await pWriteTextFile(listElement, content)
          // console.log('修改 xml 成功', '\t', listElement)
          // process.nextTick(async () => {
          //
          // })
        } catch (err) {
          console.error('err = ', err)
        }
      }
    }

  }
  return

  index_html:{
    const dest     = path.join(config.destinationPath, '/src/main/webapp', 'index.html')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./index.html.vm`)) + ''
    const content  = velocityRender(vmString, {
      domains: tables.map(item => computedDomain(item)),
    })
    await pWriteTextFile(dest, content)
  }

  // handle_package:{
  //   src_main_java: {
  //     let src_main_java = '/src/main/java/'
  //     let s             = path.join(config.destinationPath, src_main_java)
  //     let list          = await listDir(s)
  //
  //     for (let listElement of list) {
  //       try {
  //         let content      = await pReadTextFile(listElement)
  //         let finalContent = content.replace(/^package.*;/ig, item => {
  //           let s1 = `${listElement}`.replace(config.destinationPath, '').replace(src_main_java, '')
  //           let s2 = path.dirname(s1)
  //           return 'package ' + s2.replace(/\//g, '.') + ';'
  //         })
  //
  //         finalContent = finalContent.replace(/com\.stone/img, context.groupId)
  //
  //         await pWriteTextFile(listElement, finalContent)
  //         // console.log('修改 package 成功', '\t', listElement)
  //         // process.nextTick(async () => {
  //         //
  //         // })
  //       } catch (err) {
  //         console.error('err = ', err)
  //       }
  //     }
  //   }
  //
  //   src_main_resources:{
  //     let src_main_java = '/src/main/resources/' + groupId2Folder
  //     let s             = path.join(config.destinationPath, src_main_java)
  //     let list          = await listDir(s)
  //
  //     for (let listElement of list) {
  //       try {
  //         let content = await pReadTextFile(listElement)
  //         content     = content.replace(/com\.stone/img, context.groupId)
  //
  //         await pWriteTextFile(listElement, content)
  //         // console.log('修改 xml 成功', '\t', listElement)
  //         // process.nextTick(async () => {
  //         //
  //         // })
  //       } catch (err) {
  //         console.error('err = ', err)
  //       }
  //     }
  //   }
  //
  // }
  // pom_xml:{
  //   const vmString = await pReadTextFile(path.resolve(__dirname, `./templates/pom.xml.vm`)) + ''
  //   const content  = velocityRender(vmString, context)
  //   await pWriteTextFile(path.join(config.destinationPath, 'pom.xml'), content)
  // }
  //
  // README_md:{
  //   const vmString = await pReadTextFile(path.resolve(__dirname, `./templates/README.md.vm`)) + ''
  //   const content  = velocityRender(vmString, context)
  //   await pWriteTextFile(path.join(config.destinationPath, 'README.md'), content)
  // }
  //
  // npm_install:{
  //   const cmd = `cd "${config.destinationPath}" && npm --registry=https://registry.npm.taobao.org install`
  //   console.log(`cmd = `, cmd)
  //   await pExec(cmd)
  // }
}()

function readdirPromisify(dir) {
  return new Promise((resolve, reject) => {
    fs.readdir(dir, (err, list) => {
      if (err) {
        reject(err)
      }
      resolve(list)
    })
  })
}

function statPromisify(dir) {
  return new Promise((resolve, reject) => {
    fs.stat(dir, (err, stats) => {
      if (err) {
        reject(err)
      }
      resolve(stats)
    })
  })
}

function listDir(dir) {
  return statPromisify(dir).then(stats => {
    if (stats.isDirectory()) {
      return readdirPromisify(dir).then(list =>
        Promise.all(list.map(item =>
          listDir(path.resolve(dir, item)),
        )),
      ).then(subtree => [].concat(...subtree))
    }
    else {
      return [dir]
    }
  })
}

function computedDomain(tableName) {
  if (tableName.startsWith('t_')) {
    return camelCase(tableName.substring(2))
  }
  else {
    return camelCase(tableName)
  }
}

function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'deepClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    }
    else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}
