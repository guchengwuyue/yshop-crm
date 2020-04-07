<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            会员总数
          </div>
          <count-to :start-val="0" :end-val="count.userCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="order" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            订单总数
          </div>
          <count-to :start-val="0" :end-val="count.orderCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            总金额
          </div>
          <count-to :start-val="0" :end-val="count.priceCount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="shopping" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            商品总数
          </div>
          <count-to :start-val="0" :end-val="count.goodsCount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
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
        monthPrice: 0, monthCount: 0, lastWeekCount: 0, lastWeekPrice: 0,
        userCount: 0, orderCount: 0, priceCount: 0, goodsCount: 0}
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

      this.count.userCount = res.userCount
      this.count.orderCount = res.orderCount
      this.count.priceCount = res.priceCount
      this.count.goodsCount = res.goodsCount
    })
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 108px;
      cursor: pointer;
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

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
