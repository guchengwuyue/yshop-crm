// 导入api接口模块
import imageSrc from '@/assets/canvasImg/index.js'
export const panelList = [
  // 小程序
  [
    {
      title: '基础组件',
      classList: [
        {
          title: '店铺头部',
          icon: imageSrc.shopIcon,
          iconH: imageSrc.shopIconH,
          type: 'header',
          componentContent: {
            title: 'Yshop'
          }
        },
        {
          title: '搜索商品',
          icon: imageSrc.shopIcon,
          iconH: imageSrc.shopIconH,
          type: 'search',
          componentContent: {}
        },
        {
          title: '轮播图',
          icon: imageSrc.textIcon,
          iconH: imageSrc.textIconH,
          type: 'banner',
          componentContent: {
            bannerData: [
              {
                pic: require('@/assets/canvasImg/cereshop/banner.png'),
                name: '0',
                sort: 0,
                url: '/',
                status: 1
              }
            ]
          }
        },
        // 滚动新闻
        {
          title: '滚动新闻',
          icon: imageSrc.brandIcon,
          iconH: imageSrc.brandIconH,
          type: 'noticeBar',
          componentContent: {
            roll: [
              {
                uniapp_url: '/pages/shop/news/NewsList/index',
                url: '/news_list',
                info:
                  'yshop基于springboot2+Mybatisplus商城系统，3.0版本重构了代码，新增了运费模板、sku单独管理、商品券等',
                wxapp_url: '/pages/shop/news/NewsList/main'
              }
            ]
          }
        },
        // 菜单
        {
          title: '菜单',
          icon: imageSrc.textImgIcon,
          iconH: imageSrc.textImgIconH,
          type: 'menu',
          componentContent: {
            menus: [
              {
                imageArr: [
                  'https://image.dayouqiantu.cn/5e85bfa61251d.png'
                ],
                uniapp_url: '/pages/shop/GoodsList/index',
                name: '全部商品',
                id: 180,
                pic: 'https://image.dayouqiantu.cn/all.png',
                sort: 9,
                url: '/goods_list',
                wxapp_url: '/pages/shop/GoodsClass/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/news.png'],
                uniapp_url: '/pages/shop/news/NewsList/index',
                name: '图文资讯',
                id: 196,
                pic: 'https://image.dayouqiantu.cn/xw.png',
                sort: 8,
                url: '/news_list',
                wxapp_url: '/pages/shop/news/NewsList/main',
                status: 1
              },
              {
                imageArr: [
                  'https://image.dayouqiantu.cn/5e4e939507b5e.png'
                ],
                uniapp_url: '/pages/shop/GoodsCollection/index',
                name: '我的收藏',
                id: 197,
                pic: 'https://image.dayouqiantu.cn/colle.png',
                sort: 7,
                url: '/collection',
                wxapp_url: '/pages/shop/GoodsCollection/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/coupon.png'],
                uniapp_url: '',
                name: '优惠券',
                id: 199,
                pic: 'https://image.dayouqiantu.cn/cou.png',
                sort: 6,
                url: '/user/get_coupon',
                wxapp_url: '/pages/user/coupon/GetCoupon/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/pink.png'],
                uniapp_url: '/pages/activity/GoodsGroup/index',
                name: '拼团专区',
                id: 200,
                pic:
                  'https://image.dayouqiantu.cn/62ac09d2914d36c65b9b59d2147d809a.png',
                sort: 5,
                url: '/activity/group',
                wxapp_url: '/pages/activity/GoodsGroup/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/sign.png'],
                uniapp_url: '/pages/user/signIn/Sign/index',
                name: '积分签到',
                id: 209,
                pic:
                  'https://image.dayouqiantu.cn/29ea4acebbf99e7eaf6f85af2b6d79ae.png',
                sort: 4,
                url: '/user/sign',
                wxapp_url: '/pages/user/signIn/Sign/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/sekill.png'],
                uniapp_url: '/pages/activity/GoodsSeckill/index',
                name: '秒杀专区',
                id: 216,
                pic:
                  'https://image.dayouqiantu.cn/b0344c148141b50d68db9722708ea49e.png',
                sort: 3,
                url: '/activity/goods_seckill',
                wxapp_url: '/pages/activity/GoodsSeckill/main',
                status: 1
              },
              {
                imageArr: ['https://image.dayouqiantu.cn/bargin.png'],
                uniapp_url: '/pages/activity/GoodsBargain/index',
                name: '砍价专区',
                id: 217,
                pic: 'https://image.dayouqiantu.cn/bar.png',
                sort: 2,
                url: '/activity/bargain',
                wxapp_url: '/pages/activity/GoodsBargain/main',
                status: 1
              }
            ]
          }
        },
        // 广告
        {
          title: '广告',
          icon: imageSrc.twListIcon,
          iconH: imageSrc.twListIconH,
          type: 'adv',
          componentContent: {
            detail: {
              list: [
                {
                  image: 'https://wx.yixiang.co/static/images/index001.png',
                  url: '',
                  uniapp_url: '',
                  wxapp_url: '',
                  path_type: 1
                },
                {
                  image: 'https://wx.yixiang.co/static/images/index002.png',
                  url: '',
                  uniapp_url: '',
                  wxapp_url: '',
                  path_type: 1
                },
                {
                  image: 'https://wx.yixiang.co/static/images/index003.png',
                  url: '',
                  uniapp_url: '',
                  wxapp_url: '',
                  path_type: 1
                }
              ],
              name: '',
              style: 3
            }
          }
        },
        // 超值拼团
        {
          title: '超值拼团',
          icon: imageSrc.customIcon,
          iconH: imageSrc.customIconH,
          type: 'groupon',
          componentContent: {}
        },
        // 热门榜单
        {
          title: '热门榜单',
          icon: imageSrc.customIcon,
          iconH: imageSrc.customIconH,
          type: 'hotCommodity',
          componentContent: {}
        },
        // 秒杀
        // 新品
        {
          // title: '秒杀',
          title: '首发新品',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'firstNewProduct',
          componentContent: {}
        },
        // 精品推荐
        {
          title: '精品推荐',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'productsRecommended',
          componentContent: {}
        },
        // 促销单品
        {
          title: '促销单品',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'promoteProduct',
          componentContent: {}
        },
        // 直播
        {
          title: '直播',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'live',
          componentContent: {}
        },
        // 为您推荐
        {
          title: '为您推荐',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'promotionGood',
          componentContent: {}
        }
      ]
    }
  ],
  // PC
  [
    // 店铺组件
    {
      title: '店铺组件',
      type: 1,
      classList: [
        // 个人中心导航
        {
          title: '个人中心',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'NavPersonal',
          componentContent: {
            txtRoute: [
              {
                'title': '优惠卷',
                'des': '优惠卷', // 展示内容
                'linkTo': '',
                'pic': '',
                'notice': 0,
                'editors': [
                  {
                    'editorTitle': '优惠卷',
                    'editorDes': '', // 编辑内容
                    'editorUrl': '',
                    'editorPic': ''
                  }
                ]
              },
              {
                'title': '会员中心',
                'des': '会员中心',
                'linkTo': '',
                'pic': '',
                'notice': 0,
                'editors': [
                  {
                    'editorTitle': '会员中心',
                    'editorDes': '' // 编辑内容
                  }
                ]
              },
              {
                'title': '我的订单',
                'des': '我的订单',
                'linkTo': '',
                'pic': '',
                'notice': 0,
                'editors': [
                  {
                    'editorTitle': '我的订单',
                    'editorDes': '' // 编辑内容
                  }
                ]
              }
            ],
            cartNum: 4
          }
        },
        // 专区导航
        {
          title: '专区导航',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'NavType',
          componentContent: {
            typeRoute: [
              {
                'title': '首页',
                'des': '首页',
                'linkTo': '/indexpage/index',
                'pic': ''
              },
              {
                'title': '全部商品',
                'des': '全部商品',
                'linkTo': '/indexpage/productlist',
                'pic': ''
              },
              {
                'title': '秒杀专区',
                'des': '秒杀专区',
                'linkTo': '/indexpage/seckill',
                'pic': ''
              },
              {
                'title': '拼团专区',
                'des': '拼团专区',
                'linkTo': '/indexpage/combination',
                'pic': ''
              },
              {
                'title': '领卷中心',
                'des': '领卷中心',
                'linkTo': '/indexpage/getcoupon',
                'pic': ''
              },
              {
                'title': '图文详情',
                'des': '图文详情',
                'linkTo': '/indexpage/noticedetail',
                'pic': ''
              }
            ]
          }
        },
        // 联系方式
        {
          title: '联系方式',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'Footer',
          componentContent: {
            publicList: [
              {
                'title': '客服电话',
                'des': '400 800 2100'
              },
              {
                'title': '营业时间',
                'des': '9:00-23:00'
              },
              {
                'title': '微信公众号',
                'des': '微信公众号',
                'pic': '/static/img/erweima@2x.png',
                'linkTo': ''
              },
              {
                'title': '小程序码',
                'des': '小程序码',
                'pic': '/static/img/xcxm.png',
                'linkTo': ''
              }
            ]
          }
        },
        // 轮播图
        {
          title: '轮播图',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'PcBanner',
          componentContent: {
            bannerList: [
              {
                title: '轮播1',
                des: '',
                pic: 'https://image.dayouqiantu.cn/1.jpg',
                url: '',
                linkTo: ''
              },
              {
                title: '轮播2',
                des: '',
                pic: 'https://image.dayouqiantu.cn/5c9f05aee5059.jpg',
                url: '',
                linkTo: ''
              }
            ]
          }
        },
        // 新品上新
        {
          title: '新品上新',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'ProductNew',
          componentContent: {
            'product': [
                {
                  'title': '新款商品1',
                  'id': '1',
                  'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                  'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                  'price': '123'
                },
                {
                  'title': '新款商品2',
                  'id': '1',
                  'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                  'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                  'price': '123'
                },
                {
                  'title': '新款商品3',
                  'id': '1',
                  'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                  'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                  'price': '123',
                  'type': 0
                },
                {
                  'title': '新款商品4',
                  'id': '1',
                  'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                  'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                  'price': '123'
                }
            ]
          }
        },
        // 人气爆款
        {
          title: '人气爆款',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'ProductHot',
          componentContent: {
            'product': [
              {
                'title': '爆款商品1',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123'
              },
              {
                'title': '爆款商品2',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123'
              },
              {
                'title': '爆款商品3',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123',
                'type': 0
              },
              {
                'title': '爆款商品4',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123'
              }
            ]
          }
        },
        // 秒杀专区
        {
          title: '秒杀专区',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'ProductSeckill',
          componentContent: {
            'product': [
              {
                'title': '秒杀商品1',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123'
              },
              {
                'title': '秒杀商品2',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123'
              },
              {
                'title': '秒杀商品3',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': '123',
                'type': 0
              }
            ]
          }
        },
        // 拼团专区
        {
          title: '拼团专区',
          icon: imageSrc.videoIcon,
          iconH: imageSrc.videoIconH,
          type: 'ProductComb',
          componentContent: {
            'product': [
              {
                'title': '拼团商品1',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': 123,
                'people': 3,
                'sales': 521
              },
              {
                'title': '拼团商品2',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': 123,
                'people': 3,
                'sales': 521
              },
              {
                'title': '拼团商品3',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': 123,
                'people': 3,
                'sales': 521
              },
              {
                'title': '拼团商品4',
                'id': '1',
                'pic': 'https://wxapi.yixiang.co/api/file/pic/20210222213116280067.jpeg',
                'storeName': '测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品',
                'price': 123,
                'people': 3,
                'sales': 521
              }
            ]
          }
        }
        // // 折扣专区
        // {
        //   title: '折扣专区',
        //   icon: imageSrc.videoIcon,
        //   iconH: imageSrc.videoIconH,
        //   type: 'ProductDiscount',
        //   componentContent: {}
        // },
        // // 砍价专区
        // {
        //   title: '砍价专区',
        //   icon: imageSrc.videoIcon,
        //   iconH: imageSrc.videoIconH,
        //   // iconClass: 'el-icon-shopping-bag-1',
        //   type: 'ProductBargin',
        //   componentContent: {}
        // }
      ]
    }
  ]
]
export default panelList
