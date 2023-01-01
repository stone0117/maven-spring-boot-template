const path = require('path')

function resolve(dir) {
  return path.join(__dirname, 'output_project', dir)
}

module.exports = {
  context: {
    destinationPath: destinationPath(),
    dbName         : 'zhongtian',
    host           : 'localhost',
    user           : 'root',
    password       : 'admin',
    tableName      : '',
    //
    javaVersion: '1.8',
    encoding   : 'UTF-8',
    //
    // groupId  : 'cn.cindy',
    groupId  : 'com.stone',
    version  : '1.0-SNAPSHOT',
    packaging: 'war',
  },
  tables : [
    // 't_order',
    // 't_search',
    // 't_shipping_address',
    't_user',
  ],
}

if (require.main === module) {
  const {spawn, exec} = require('child_process')
  spawn('node', [path.resolve(__dirname, './src/main.js')], {cwd: __dirname, stdio: 'inherit'})
}

function destinationPath() {
  const s = resolve('hello')
  //
  return s
}
