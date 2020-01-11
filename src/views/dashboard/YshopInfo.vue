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
            <span style="font-weight: bolder">H5演示：</span><a target="_blank" href="https://h5.dayouqiantu.cn">https://h5.dayouqiantu.cn</a> <br />
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
            <span style="font-weight: bolder">VIP版：</span>包括了开源版，还包括了移动端(H5+公众号)、小程序(mpvue)即将发布、移动端API
          </div>
          <div class="text item">
            1、VIP为终身制、拥有全部源代码及其后续的代码（免费）升级及其技术支持。目前商城优惠券、会员、秒杀、分销、砍价等都已经完成！<br />
            2、价格：vip会员价228元，当然当前价格是整个价格，后续的系统升级vip价格会有相应的调整的，所以早上车更优惠哦！<br />
            3、VIP购买淘宝地址：https://item.taobao.com/item.htm?id=607532386325<br />
            4、加入vip群：931426444,请输入你的淘宝订单编号，即可入群<br />
            5、群里面获取私服git<br />
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
            <span style="font-weight: bolder">（2020-01-11）yshop1.8.1版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.修复库存问题#I17Z5X
          </div>
          <div class="text item">
            2.修复评价百分比精度问题#I17UQY
          </div>
          <div class="text item">
            3.修复评价列表问题#I17TZC
          </div>
          <div class="text item">
            4.移动端图片上传修改为统一处理
          </div>
          <div class="text item">
            <span style="font-weight: bolder">（2020-01-10）yshop1.8版本 发布更新如下：</span>
          </div>
          <div class="text item">
            1.新增小程序支付与登录,后台新增小程序跳转页面,
          </div>
          <div class="text item">
            2.后台新增图片素材管理
          </div>
          <div class="text item">
            3.本地上传与七牛云上传合并，修改一个yml即可两者随意切换
          </div>
          <div class="text item">
            4.升级swagger-bootstrap,swaager统一配置迁移到common(防止注解冲突)
          </div>
          <div class="text item">
            5.后台优化操作显示
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
