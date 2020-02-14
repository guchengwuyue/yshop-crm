<template>
  <el-row :gutter="10" class="panel-group">
    <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
      <div class="card-panel">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-weight: bolder">项目信息</span>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">yshop基于当前流行技术组合的前后端分离商城系统：</span>
            SpringBoot2+Jpa+MybatisPlus+SpringSecurity
            的前后端分离的商城系统，
            包含商城、拼团、砍价、商户管理、 秒杀、优惠券、积分、分销、会员等功能。
          </div>
          <div class="text item">
            <span style="font-weight: bolder">技术选型：</span>
            SpringBoot2、mybatis、MyBatis-Plus
            、SpringSecurity、JPA 、Druid 、Slf4j 、Fastjson
            、JWT 、Redis 、Quartz 、Mysql 、swagger 、WxJava
            、Lombok 、Hutool
            、Mapstruct 、Redisson 、Rocketmq
          </div>
          <div class="text item">
            <span style="font-weight: bolder">QQ群：</span>964166879
          </div>
          <div class="text item">
            <span style="font-weight: bolder">gitee：</span><a target="_blank" href="https://gitee.com/guchengwuyue/yshopmall">访问码云</a>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">github：</span><a target="_blank" href="https://github.com/guchengwuyue/yshopmall">访问github</a>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">公众号演示：</span><img width="100" height="100" src="https://image.dayouqiantu.cn/qrcode_for_gh_95df5a2881cc_258.jpg">
          </div>
          <div class="text item">
            <span style="font-weight: bolder">小程序演示：</span>关注上面公众号之后，选择yshop小程序体验即可
          </div>
          <div class="text item">
            <span style="font-weight: bolder">H5演示：</span><a target="_blank" href="https://h5.yixiang.co">https://h5.yixiang.co</a> <br>
            <span style="font-weight: bolder">H5测试号</span>：hupeng/123456,也可以自行注册
          </div>
        </el-card>
      </div>
    </el-col>
    <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
      <div class="card-panel">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-weight: bolder">开源+VIP说明</span>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">开源版：</span>是整个商城的管理后台（后台已经封装好了图片素材库、编辑器、配置等等组件），
            它可以用于所有的Web应用程序，如网站商城管理后台，网站会员中心，CMS，CRM，OA等等，
            当然，您也可以对它进行深度定制，以做出更强系统。
          </div>
          <div class="text item">
            <span style="font-weight: bolder">VIP版(JAVA++H5+公众号端)：</span>包括了开源版，还包括了移动端、移动端API
          </div>
          <div class="text item">
            1、VIP为终身制包括JAVA端+H5公众号端，后期免费升级迭代及其技术支持。！<br>
            2、价格：vip会员价248元(JAVA+H5+公众号版)，后续的系统升级vip价格会有相应的调整的，所以早上车更优惠哦！<br>
            3、VIP购买淘宝地址：https://item.taobao.com/item.htm?id=607532386325<br>
            4、加入vip群：931426444,请输入你的淘宝订单编号，即可入群<br>
            5、群里面获取私服git<br>
            6、如果需要小程序需要单独购买看下面<br>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">VIP版如果需要小程序需要另外单独购买：</span>
          </div>
          <div class="text item">
            1、小程序是mpvue2.0框架开发<br>
            2、价格：100元，只是单独的小程序！购买小程序请务必要购买上面VIP套餐<br>
            3、购买淘宝地址：https://item.taobao.com/item.htm?id=612007949053<br>
            4、群里面获取私服git<br>
          </div>
        </el-card>
      </div>
    </el-col>
    <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
      <div class="card-panel">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-weight: bolder">更新日志</span>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-02-11）yshop1.9.6版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1、H5(公众号)端新增商品海报功能
          </div>
          <div class="text item">
            2、管理后台配置新增排序与可隐藏选项，同时移动端接口相应更新
          </div>
          <div class="text item">
            3、去除小程序首页新闻轮播图标识
          </div>
          <div class="text item">
            4、修复小程序图片路径写死了问题(只能修改vue中的url,css中的需要自行替换)#I18KRS
          </div>
          <div class="text item">
            5、修改小程序登录成功后，跳到转个人中心问题 #I194VT
          </div>
          <div class="text item">
            6、修复小程序申请退款页面图文#I197FG
          </div>
          <div class="text item">
            7、修复砍价不能下单问题
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-02-07）yshop1.9.5版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.H5(公众号)新增手机号绑定#I18IVP
          </div>
          <div class="text item">
            2.小程序新增手机号绑定接口(小程序待对接)
          </div>
          <div class="text item">
            3.H5(公众号)端新增我的账户信息管理(头像等信息修改)
          </div>
          <div class="text item">
            4.修复H5注册NULL问题
          </div>
          <div class="text item">
            5.修复小程序未付款订单支付问题#I18ZV5
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-02-06）yshop1.9.4版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.后台砍价列表显示调整
          </div>
          <div class="text item">
            2.后台会员新增用户来源
          </div>
          <div class="text item">
            3.小程序新增普通二维码功能
          </div>
          <div class="text item">
            4.修复小程序砍价海报问题
          </div>
          <div class="text item">
            5.修复小程序首页滚动新闻不显示的问题
          </div>
          <div class="text item">
            6.修复小程序个人中心积分列表问题
          </div>
          <div class="text item">
            7.修复小程序购物车删除问题#I19176
          </div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>
<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .text {
    font-size: 12px;
  }

  .item {
    margin-bottom: 10px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: auto;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
