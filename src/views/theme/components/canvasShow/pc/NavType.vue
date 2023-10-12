/**
    画布_顶部路由组件
*/
<template>
  <div class="canvasNavType">
    <div class="wrapper">
      <div class="TPNleft">
          <div>
            <router-link to="/">
              <img src="../../../../../assets/canvasImg/logo.png" style="width:58px;height:23px;" alt="logo">
            </router-link>
          </div>
      </div>
      <div class="TPNright">
          <div class="content"
          v-for="item in componentContent.typeRoute"
          :key="item.value"
          @click="routLinkTo(item.route)">
              <div class="personalText"
              v-if="item.des != ''"
              :class="{active_path : activeRoute === item.linkTo }">
                  {{ item.des }}
              </div>
          </div>
          <div class="searchItem">
            <input type="text" placeholder="请输入"><button>搜索</button>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TopTypeNav',
  props: {
    componentContent: Object
  },
  data () {
    return {
      arr: {
        'type': [
          {
            'value': 0,
            'des': '首页',
            'route': '/indexpage/index'
          },
          {
            'value': 1,
            'des': '全部商品',
            'route': '/indexpage/productlist'
          },
          {
            'value': 2,
            'des': '秒杀专区',
            'route': '/indexpage/seckill'
          },
          {
            'value': 3,
            'des': '拼团专区',
            'route': '/indexpage/combination'
          },
          // {
          //   'value': 4,
          //   'des': '折扣专区',
          //   'route': '/indexpage/discount'
          // },
          {
            'value': 5,
            'des': '领券中心',
            'route': '/indexpage/getcoupon'
          },
          {
            'value': 6,
            'des': '图文详情',
            'route': '/indexpage/noticedetail'
          }
        ]
      },
      activeRoute: '/indexpage/index'
    }
  }
}
</script>

<style lang="scss" scoped>
.canvasNavType{
  height: 100px;
  line-height: 100px;
  background-color: #FFFFFF;
  .wrapper{
    margin: auto;
    div{
      display: inline-block;
    }
    .TPNleft{
      cursor: pointer;
      margin-left: 135px;
    }
    .TPNright{
      float: right;
      margin-right: 135px;
      cursor: pointer;
      color: #666666;
      font-size: 16px;
      font-family: PingFang SC;
      font-weight: 400;
      .content{
          margin-right: 30px;
          .personalText{
            height: 80px;
          }
          .active_path{
            border-bottom: 2px solid #F26E47;
          }
      }
      .searchItem{
        margin: 0;
        padding: 0;
        input{
          width: 200px;
          height: 34px;
          padding-left: 20px;
          background-color: #F6F6F8;
        }
        button{
          font-size: 16px;
          width: 62px;
          height: 34px;
          color: #FFFFFF;
          background-color: #333333;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
