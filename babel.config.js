const plugins = ['@vue/babel-plugin-transform-vue-jsx']
// 生产环境移除 console
if (process.env.NODE_ENV === 'production') {
  plugins.push('transform-remove-console')
}
module.exports = {
  plugins: plugins,
  presets: [
    '@vue/app'
  ]
}
