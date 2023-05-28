'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://8.130.114.187:8222"',
  OSS_PATH:'"https://edu-6-go.oss-cn-hangzhou.aliyuncs.com"'
})
