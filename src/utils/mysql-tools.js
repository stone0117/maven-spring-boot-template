const mysql        = require('mysql')
const camelCase    = require('camelcase')
const {capitalize} = require('./index')

function analysisType(type) {
  let toLowerCaseType = type.toLowerCase()
  if (toLowerCaseType.includes('varchar')) { return 'String'}
  if (toLowerCaseType.includes('char')) { return 'String'}
  if (toLowerCaseType.includes('text')) { return 'String'}
  if (toLowerCaseType.includes('blob')) { return 'byte[]'}
  // if (toLowerCaseType.includes("bigint")) { return "java.math.BigInteger";}
  if (toLowerCaseType.includes('bigint')) { return 'Long'}
  if (toLowerCaseType.includes('integer')) { return 'Integer'}
  if (toLowerCaseType.includes('tinyint')) { return 'Integer'}
  if (toLowerCaseType.includes('mediumint')) { return 'Integer'}
  if (toLowerCaseType.includes('smallint')) { return 'Integer'}
  if (toLowerCaseType.includes('int')) { return 'Integer'}
  if (toLowerCaseType.includes('bit')) { return 'Boolean'}
  if (toLowerCaseType.includes('float')) { return 'Float'}
  if (toLowerCaseType.includes('double')) { return 'Double'}
  if (toLowerCaseType.includes('decimal')) { return 'java.math.BigDecimal'}
  if (toLowerCaseType.includes('datetime')) { return 'java.util.Date'}
  if (toLowerCaseType.includes('timestamp')) { return 'java.sql.Timestamp'}
  if (toLowerCaseType.includes('date')) { return 'java.util.Date'}
  if (toLowerCaseType.includes('year')) { return 'java.util.Date'}
  if (toLowerCaseType.includes('time')) { return 'java.sql.Time'}

  return 'Object'
}

function showFullColumns(context) {

  return new Promise((resolve, reject) => {
    const {host, user, password, dbName, tableName} = context
    var connection                                    = mysql.createConnection({
      host    : host,
      user    : user,
      password: password,
      database: dbName,
    })
    connection.connect()
    connection.query(`SHOW FULL COLUMNS FROM ${tableName}`, function (error, results, fields) {
      if (error) {
        throw error
      }
      console.log('results = ', results)

      resolve(results.map(item => {
        item.javaType        = analysisType(item.Type)
        item.javaField       = camelCase(item.Field)
        item.getFunctionName = 'get'+capitalize(camelCase(item.Field))
        item.setFunctionName = 'set'+capitalize(camelCase(item.Field))

        if(item.Key ==='PRI'){
          context.primaryField = item
        }
        return item
      }))

      connection.end()
    })
  })
}

module.exports = {
  showFullColumns,
}