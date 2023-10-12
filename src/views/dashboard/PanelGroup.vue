<template>
  <div>
    <div class="divBox">
      <el-row :gutter="24" class="baseInfo">
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <div slot="header" class="acea-row row-between-wrapper">
              <span>会员总数</span>
              <el-tag type="success">全平台</el-tag>
            </div>
            <div class="content" v-if="count">
              <span class="content-number spBlock mb15"><count-to :start-val="0" :end-val="count.userCount" :duration="2600" class="card-panel-num" /></span>
              <el-divider></el-divider>
              <div class="acea-row row-between-wrapper">
                <span class="content-time">今日订单数</span>
                <span>{{ count.todayCount }} 单</span>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <div slot="header" class="acea-row row-between-wrapper">
              <span>订单总数</span>
              <el-tag type="success">全平台</el-tag>
            </div>
            <div class="content" v-if="count">
              <span class="content-number spBlock mb15"><count-to :start-val="0" :end-val="count.orderCount" :duration="3000" class="card-panel-num" /></span>
              <el-divider></el-divider>
              <div class="acea-row row-between-wrapper">
                <span class="content-time">昨日订单数</span>
                <span>{{ count.proCount }} 单</span>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <div slot="header" class="acea-row row-between-wrapper">
              <span>总金额</span>
              <el-tag type="success">全平台</el-tag>
            </div>
            <div class="content" v-if="count">
              <span class="content-number spBlock mb15"><count-to :start-val="0" :end-val="count.priceCount" :duration="3200" class="card-panel-num" /></span>
              <el-divider></el-divider>
              <div class="acea-row row-between-wrapper">
                <span class="content-time">近七天订单数</span>
                <span>{{ count.lastWeekCount }} 单</span>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <div slot="header" class="acea-row row-between-wrapper">
              <span>商品总数</span>
              <el-tag type="success">全平台</el-tag>
            </div>
            <div class="content" v-if="count">
              <span class="content-number spBlock mb15"><count-to :start-val="0" :end-val="count.goodsCount" :duration="3600" class="card-panel-num" /></span>
              <el-divider></el-divider>
              <div class="acea-row row-between-wrapper">
                <span class="content-time">本月订单数</span>
                <span>{{ count.monthCount }} 单</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import CountTo from "vue-count-to";
import { gett } from "@/api/visits";
export default {
  components: {
    CountTo,
  },
  data() {
    return {
      count: {
        todayPrice: 0,
        todayCount: 0,
        proPrice: 0,
        proCount: 0,
        monthPrice: 0,
        monthCount: 0,
        lastWeekCount: 0,
        lastWeekPrice: 0,
        userCount: 0,
        orderCount: 0,
        priceCount: 0,
        goodsCount: 0,
      },

    };
  },
  mounted() {
    gett().then((res) => {
      this.count.todayPrice = res.todayPrice;
      this.count.todayCount = res.todayCount;
      this.count.proCount = res.proCount;
      this.count.proPrice = res.proPrice;

      this.count.monthPrice = res.monthPrice;
      this.count.monthCount = res.monthCount;
      this.count.lastWeekCount = res.lastWeekCount;
      this.count.lastWeekPrice = res.lastWeekPrice;

      this.count.userCount = res.userCount;
      this.count.orderCount = res.orderCount;
      this.count.priceCount = res.priceCount;
      this.count.goodsCount = res.goodsCount;
    });
  },
};
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
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

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
      color: #34bfa3;
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

@media (max-width: 550px) {
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
.baseInfo {
  ::v-deep .el-card__header {
    padding: 15px 20px !important;
  }
}

.ivu-mb {
  margin-bottom: 10px;
}
.up,
.el-icon-caret-top {
  color: #f5222d;
  font-size: 12px;
  opacity: 1 !important;
}

.down,
.el-icon-caret-bottom {
  color: #39c15b;
  font-size: 12px;
  /*opacity: 100% !important;*/
}

.content {
  &-number {
    font-size: 30px;
  }
  &-time {
    font-size: 14px;
    /*color: #8C8C8C;*/
  }
}
.spBlock {
  display: block;
}
</style>
