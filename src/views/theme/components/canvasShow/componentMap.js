const componentMap = [
  // 手机端
  new Map([
  ['header', () => import('@/views/theme/components/canvasShow/mobile/Header.vue')], // 店铺头部
  ['search', () => import('@/views/theme/components/canvasShow/mobile/Search.vue')], // 搜索商品
  ['banner', () => import('@/views/theme/components/canvasShow/mobile/Banner.vue')], // 轮播图
  ['noticeBar', () => import('@/views/theme/components/canvasShow/mobile/NoticeBar.vue')], // 滚动新闻
  ['menu', () => import('@/views/theme/components/canvasShow/mobile/Menu.vue')], // 菜单
  ['adv', () => import('@/views/theme/components/canvasShow/mobile/Adv.vue')], // 广告
  ['groupon', () => import('@/views/theme/components/canvasShow/mobile/Groupon.vue')], // 超值拼团
  [
    'hotCommodity',
    () => import('@/views/theme/components/canvasShow/mobile/HotCommodity.vue')
  ], // 热门榜单
  [
    'firstNewProduct',
    () => import('@/views/theme/components/canvasShow/mobile/FirstNewProduct.vue')
  ], // 秒杀
  [
    'productsRecommended',
    () => import('@/views/theme/components/canvasShow/mobile/ProductsRecommended.vue')
  ], // 精品推荐
  [
    'promoteProduct',
    () => import('@/views/theme/components/canvasShow/mobile/PromoteProduct.vue')
  ], // 促销单品
  ['live', () => import('@/views/theme/components/canvasShow/mobile/Live.vue')], // 直播
  [
    'promotionGood',
    () => import('@/views/theme/components/canvasShow/mobile/PromotionGood.vue')
  ] // 为您推荐
]),
  // PC
  new Map([
    // pc单商户改版
    ['NavPersonal', () => import('@/views/theme/components/canvasShow/pc/NavPersonal.vue')], // 个人中心
    ['NavType', () => import('@/views/theme/components/canvasShow/pc/NavType.vue')], // 分类导航
    ['PcBanner', () => import('@/views/theme/components/canvasShow/pc/Banner.vue')], // 轮播图
    ['ProductNew', () => import('@/views/theme/components/canvasShow/pc/ProductNew.vue')], // 新品首发
    ['ProductHot', () => import('@/views/theme/components/canvasShow/pc/ProductHot.vue')], // 人气爆款
    ['ProductSeckill', () => import('@/views/theme/components/canvasShow/pc/ProductSeckill.vue')], // 秒杀专区
    ['ProductComb', () => import('@/views/theme/components/canvasShow/pc/ProductComb.vue')], // 拼团专区
    ['ProductDiscount', () => import('@/views/theme/components/canvasShow/pc/ProductDiscount.vue')], // 折扣专区
    ['ProductBargin', () => import('@/views/theme/components/canvasShow/pc/ProductBargin.vue')], // 砍价专区
    ['Footer', () => import('@/views/theme/components/canvasShow/pc/Footer.vue')] // 底部版权相关
  ])
]

export default componentMap
