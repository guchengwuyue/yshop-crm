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
            包含商城、拼团、砍价、商户管理、 秒杀、优惠券、积分、分销、会员、充值、门店核销等功能。
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
          <div class="text item">
            <span style="font-weight: bolder;color: red">如果想体验手机端商户管理与订单核销功能，记得去后台会员管理，搜索找到自己，
            然后编辑设置商户管理员开启即可，然后刷新个人中心即可体验</span>
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
            2、价格：vip会员价298元(JAVA+H5+公众号版)，后续的系统升级vip价格会有相应的调整的，所以早上车更优惠哦！<br>
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
            <span style="font-weight: bolder">（2020-03-07）yshop2.0版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1、优化代码结构与名称，修改get请求参数其统一继承分页参数
          </div>
          <div class="text item">
            2、新增redis监听未付款30分钟取消功能与7天自动收货功能，mq队列作为备选(注释掉)
          </div>
          <div class="text item">
            3、新增门店到店核销功能
          </div>
          <div class="text item">
            4、新增分销全局开关#I19HB1
          </div>
          <div class="text item">
            5、积分新增消费限制#I19TUR
          </div>
          <div class="text item">
            6、新增充值功能#I18V5D
          </div>
          <div class="text item">
            7、后台菜单调整，新增财务模块
          </div>
          <div class="text item">
            8、优化后台配置赋值写法
          </div>
          <div class="text item">
            9、修复管理后台新增表单之后如果直接再新增数据导致默认数据缺失问题#I1AFBK
          </div>
          <div class="text item">
            10、修复提交购物车可能查询多条数据的问题
          </div>
          <div class="text item">
            11、后台登陆背景图固定#I1A0LS
          </div>
          <div class="text item">
            12、增加常量与枚举优化硬编码问题
          </div>
          <div class="text item">
            13、微信支付、公众号、模板消息修改
          </div>
          <div class="text item">
            14、修复代码生成器不全的问题#I1AIO4
          </div>
          <div class="text item">
            15、修复营销产品拼团等轮播图不能修改的问题#I1AHXR
          </div>
          <div class="text item">
            16、移除yshop-monitor模块
          </div>
          <div class="text item">
            17、修复退货理由文字错误#I1AQ7D
          </div>
          <div class="text item">
            18、修复新增分类的图片自动显示上次一次的图片#I1AQBK
          </div>
          <div class="text item">
            19、新增微信jssdk接口返回所需js权限
          </div>
          <div class="text item">
            20、新增余额充值变动模板消息通知
          </div>
          <div class="text item">
            21、首页数据缓存优化
          </div>
          <div class="text item">
            22、新增未支付订单显示到期时间
          </div>
          <div class="text item">
            23、修复加入购物车购买后，后台订单中的商品信息数据重复#I1AXNX
          </div>
          <div class="text item">
            24、修复后台-管理商品-规格属性 属性无法删除问题#I1AYL2
          </div>
          <div class="text item">
            25、修复公众号商品直接微信分享标题不显示的问题#I1AX0R
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-02-24）yshop修复优化版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.修复小程序积分支付功能#I1A03I
          </div>
          <div class="text item">
            2.修复小程序砍价功能上面时间没有进行倒计时#I1A037
          </div>
          <div class="text item">
            3.优化小程序底层导航栏适配ios#I1A1QX
          </div>
          <div class="text item">
            4.修复小程序返回的问题#I1A036
          </div>
          <div class="text item">
            5.修复小程序地址管理#I1A03U
          </div>
          <div class="text item">
            6.调整小程序首页样式修改与H5保持同步
          </div>
          <div class="text item">
            7.H5首页数据加载顺序调整
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-02-20）yshop1.9.10版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.小程序新增了商品详情海报功能
          </div>
          <div class="text item">
            2.小程序新增了手机号绑定功能
          </div>
          <div class="text item">
            3.修复小程序选择规格的问题#I19PD7
          </div>
          <div class="text item">
            4.修改移动端二维码海报生成拆分为H5端与小程序端
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
