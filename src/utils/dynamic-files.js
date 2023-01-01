const path                                                        = require('path')
const resources_mapper_xml                                        = require('../config/resources.mapper.xml.js')
const spring_mvc_xml                                              = require('../config/spring.mvc.xml.js')
const application_context_xml                                     = require('../config/applicationContext.xml.js')
const {pWriteTextFile, pReadTextFile, velocityRender, capitalize} = require('./index')
const fse = require('fs-extra')


async function generateDynamicFiles(context) {
  const destinationPath = context.destinationPath

  domain:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'domain', context.className + '.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./domain.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  mapper:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'mapper', context.className + 'Mapper.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./mapper.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  resources_mapper:{
    const dest = path.join(destinationPath, '/src/main/resources/', context.groupId2Folder, 'mapper', context.className + 'Mapper.xml')
    // const vmString = await pReadTextFile(path.join(context.templatePath, `./mapper.xml.vm`)) + ''
    // const content  = velocityRender(vmString, context)
    const content = resources_mapper_xml(context)

    await pWriteTextFile(dest, content)
  }
  //
  qo:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'pojo/qo', context.className + 'QueryObject.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./qo.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  service:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'service', context.className + 'Service.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./service.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  service_impl:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'service/impl', context.className + 'ServiceImpl.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./serviceImpl.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  controller:{
    const dest     = path.join(destinationPath, '/src/main/java/', context.groupId2Folder, 'web/controller', context.className + 'Controller.java')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./controller.java.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }
  //
  list_ftl:{
    fse.ensureDirSync(path.join(destinationPath, '/src/main/resources/templates',context.domain))
    const dest = path.join(destinationPath, '/src/main/resources/templates',context.domain, 'list.ftl')
    const vmString = await pReadTextFile(path.join(context.templatePath, `./list.ftl.vm`)) + ''
    const content  = velocityRender(vmString, context)
    await pWriteTextFile(dest, content)
  }

}

module.exports = {
  generateDynamicFiles,
}
