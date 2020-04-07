<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="today" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">今日订单数</div>
          <count-to :start-val="0" :end-val="count.todayCount" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="ic-yesterday" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">昨日订单数</div>
          <count-to :start-val="0" :end-val="count.proCount" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="seven" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">近七天订单数</div>
          <count-to :start-val="0" :end-val="count.lastWeekCount" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="monthlyview" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">本月订单数</div>
          <count-to :start-val="0" :end-val="count.monthCount" :duration="3600" class="card-panel-num" />
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
        monthPrice: 0, monthCount: 0, lastWeekCount: 0, lastWeekPrice: 0 }
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
</style>
