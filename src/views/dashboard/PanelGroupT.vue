<template>
  <div class="divBox">
      <el-row :gutter="24" class="dashboard-console-grid">
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/shop/cate' }">
              <i class="el-icon-goods" style="color: #69c0ff" />
              <p>商品管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/member/member' }">
              <i class="el-icon-user" style="color: #95de64" />
              <p>会员管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/order/order' }">
              <i class="el-icon-s-order" style="color: #ff9c6e" />
              <p>订单管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/syscoupon/coupon' }">
              <i class="el-icon-s-ticket" style="color: #b37feb" />
              <p>电子券管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/activity/combination' }">
              <i class="el-icon-postcard" style="color: #ffd666" />
              <p>营销管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/promoter/promoterconfig' }">
              <i class="el-icon-notebook-1" style="color: #5cdbd3" />
              <p>分销管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/price/extract' }">
              <i class="el-icon-s-finance" style="color: #ff85c0" />
              <p>财务管理</p>
            </router-link>
          </el-card>
        </el-col>
        <el-col v-bind="grid" class="ivu-mb">
          <el-card :bordered="false">
            <router-link :to="{ path: '/store/storeinfo' }">
              <i class="el-icon-office-building" style="color: #ffc069" />
              <p>门店管理</p>
            </router-link>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>
<script>
import CountTo from 'vue-count-to'
import { gett } from '@/api/visits'
export default {
  components: {
    CountTo
  },
  data() {
    return {
      count: { todayPrice: 0, todayCount: 0, proPrice: 0, proCount: 0,
        monthPrice: 0, monthCount: 0, lastWeekCount: 0, lastWeekPrice: 0 },
        grid: {
        xl: 3,
        lg: 3,
        md: 6,
        sm: 8,
        xs: 8,
      },
    }
  },
  mounted() {
    gett().then(res => {
      this.count.todayPrice = res.todayPrice
      this.count.todayCount = res.todayCount
      this.count.proCount = res.proCount
      this.count.proPrice = res.proPrice

      this.count.monthPrice = res.monthPrice
      this.count.monthCount = res.monthCount
      this.count.lastWeekCount = res.lastWeekCount
      this.count.lastWeekPrice = res.lastWeekPrice
    })
  }
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
  i {
    font-size: 32px;
  }
  a {
    display: block;
    color: inherit;
  }
  p {
    margin-top: 8px;
  }
}
</style>
