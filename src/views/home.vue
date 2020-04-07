<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <!--<yshop-info />-->
      <panel-group />
      <panel-group-t />

      <el-row :gutter="32">
        <el-col :xs="16" :sm="16" :lg="8">
          <order-count></order-count>
        </el-col>
        <el-col :xs="16" :sm="16" :lg="8">

          <div class="chart-wrapper">
            <p>本月成交额</p>
            <bar-chart />
          </div>
        </el-col>
        <el-col :xs="16" :sm="16" :lg="8">
          <div class="chart-wrapper">
            <p>本月订单数</p>
            <pie-chart />
          </div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from './dashboard/PanelGroup'
import PanelGroupT from './dashboard/PanelGroupT'
import PieChart from './dashboard/BarChartT'
import BarChart from './dashboard/BarChart'
import { count } from '@/api/visits'
import YshopInfo from './dashboard/YshopInfo'
import OrderCount from './dashboard/OrderCount'

/**
   * 记录访问，只有页面刷新或者第一次加载才会记录
   */
count().then(res => {
})

export default {
  name: 'Dashboard',
  components: {
    PanelGroup,
    PanelGroupT,
    PieChart,
    BarChart,
    YshopInfo,
    OrderCount
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 18px 22px 22px 22px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }
</style>
