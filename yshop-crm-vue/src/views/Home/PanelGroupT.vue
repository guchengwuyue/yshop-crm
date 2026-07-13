<template>
  <div class="divBox" v-show="isShow">
      <el-row :gutter="24" class="dashboard-console-grid">
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/product/store-product' }">
               <Icon icon="ep:goods" style="color: #69c0ff" :size="35" />
              <p>商品管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/member/user' }">
              <Icon icon="ep:user" style="color: #95de64" :size="35" />
              <p>会员管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/order/store-order' }">
              <Icon icon="ep:list" style="color: #ff9c6e" :size="35" />
              <p>订单管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/mall/coupons' }">
              <Icon icon="ep:ticket" style="color: #b37feb" :size="35" />
              <p>优惠券管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/shop/web-print' }">
               <Icon icon="ep:postcard" style="color: #ffd666" :size="35" />
              <p>打印机管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/shop/shop' }" >
               <Icon icon="ep:notebook" style="color: #5cdbd3" :size="35" />
              <p>门店管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/pay/merchant-details' }">
              <Icon icon="ep:money" style="color: #ff85c0" :size="35" />
              <p>支付管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/message/wechat-templateo' }">
              <Icon icon="ep:message" style="color: #ffc069" :size="35" />
              <p>消息管理</p>
            </router-link>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>
<script setup lang="ts">
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache()


const grid =ref({
  xl: 3,
  lg: 3,
  md: 6,
  sm: 8,
  xs: 8,
})



const user = wsCache.get(CACHE_KEY.USER)

const isShow = ref(true)
if(user.user.shopId > 0) {
  isShow.value = false
}

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    cursor: pointer;
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


.ivu-mb {
  margin-bottom: 10px;
}
.divBox {
  // padding: 0 20px !important;
}

.dashboard-console-grid {
  text-align: center;
  .ivu-card-body {
    padding: 0;
  }
  Icon {
    font-size: 32px;
  }
  a {
    display: block;
    color: inherit;
    text-decoration: none;
  }
  p {
    margin-top: 8px;
    font-size: 14px;
  }
}
</style>
