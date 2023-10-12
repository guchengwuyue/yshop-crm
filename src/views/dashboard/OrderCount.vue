<template>
          <div :class="className" ref="chart" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import { getOrderCount } from "@/api/visits";
import { debounce } from "@/utils";

export default {
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
  },
  data() {
    return {
      chart: null,
      column: [],
      orderCountDatas: [],
    };
  },
  mounted() {
    getOrderCount()
      .then((res) => {
        (this.column = res.column),
          (this.orderCountDatas = res.orderCountDatas);
      })
      .then(() => {
        this.initChart();
      });

    this.__resizeHandler = debounce(() => {
      if (this.chart) {
        this.chart.resize();
      }
    }, 100);
    window.addEventListener("resize", this.__resizeHandler);
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    window.removeEventListener("resize", this.__resizeHandler);
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.chart, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          bottom: "10",
          data: this.column,
        },
        calculable: true,
        series: [
          {
            name: "商品分类销售占总销售的比例",
            type: "pie",
            roseType: "radius",
            radius: [15, 95],
            center: ["50%", "38%"],
            data: this.orderCountDatas,
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      });
    },
  },
};
</script>


<style lang="scss" scoped>
  .acea-row{
    ::v-deep.el-avatar--small {
      width: 22px;
      height: 22px;
      line-height: 22px;
    }
  }
  .checkTime{
    ::v-deep.el-radio__input{
      display: none;
    }
  }
  .ivu-pl-8{
    margin-left: 8px;
    font-size: 14px;
  }
  .divBox {
    // padding: 0 20px !important;
  }
  .dashboard-console-visit {
    ::v-deep.el-card__header{
      padding: 14px 20px !important;
    }
    ul {
      li {
        list-style-type: none;
        margin-top: 12px;
      }
    }
  }
  .ivu-mb{
    margin-bottom: 10px;
  }
</style>

