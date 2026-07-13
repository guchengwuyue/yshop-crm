<template>
  <div>
    <div style="padding:20px">
        <el-menu
          :default-active="queryParams.type"
          style="margin-bottom:10px"
          mode="horizontal"
          @select="handleSelect"
        >
          <el-menu-item :index="2">我的业绩</el-menu-item>
          <!-- <el-menu-item :index="3">部门业绩</el-menu-item> -->
        </el-menu>
        <el-row>
          <el-col :span="14">
            <el-tag type="primary">业绩概况-完成率(%)</el-tag>
          </el-col>
          <el-col :span="10" style="text-align:right"><el-tag type="success">合同目标：{{countData.contractMoney}}  合同成交：{{countData.contractSuccessMoney}}</el-tag><el-tag type="danger">回款目标：{{countData.receivablesMoney}}  回款：{{countData.receivablesSuccessMoney}}</el-tag></el-col>
        </el-row>
      </div>
      <div style="  width: 50%; margin-left: auto;margin-right: auto;">
        <el-form :inline="true">
          <el-form-item label="年度" prop="type">
            <el-date-picker
              v-model="queryParams.year"
              type="year"
              value-format="YYYY"
            />
          </el-form-item>
          <el-form-item label="月份" prop="type">
            <el-select
              v-model="queryParams.month"
              placeholder="请选择"
              clearable
              class="!w-240px"
              @change="selectMonth()"
            >
             <el-option label="全年" :value="0" />
              <el-option label="一月" :value="1" />
              <el-option label="二月" :value="2" />
              <el-option label="三月" :value="3" />
              <el-option label="四月" :value="4" />
              <el-option label="五月" :value="5" />
              <el-option label="六月" :value="6" />
              <el-option label="七月" :value="7" />
              <el-option label="八月" :value="8" />
              <el-option label="九月" :value="9" />
              <el-option label="十月" :value="10" />
              <el-option label="十一月" :value="11" />
              <el-option label="十二月" :value="12" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <Echart :height="600" :options="lineOptionsData" />
  </div>
</template>
<script setup lang="ts">
import { EChartsOption } from 'echarts'
import { clockOptions } from '@/views/Home/echarts-data'
import { formatDate } from '@/utils/formatTime'
import { AchievementApi } from '@/api/crm/crmachievement'
const lineOptionsData = reactive<EChartsOption>(clockOptions) as EChartsOption

const { t } = useI18n()

const queryParams = reactive({
  year: formatDate(new Date(),'YYYY'),
  month:0,
  type:2
})

const countData = ref({})
//const activeIndex = ref(2)

const selectMonth = () => {
  getData()
}

const handleSelect = (key) => {
  queryParams.type = key
  getData()

}

/** 查询列表 */
const getData = async () => {
  countData.value = await AchievementApi.achievementCount(queryParams)
  lineOptionsData.series[0].data[0].value = countData.value.contractPer
  lineOptionsData.series[0].data[1].value = countData.value.receivablesPer
}

/** 初始化 **/
onMounted(() => {
  getData()
})


</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.acea-row {
    display: -webkit-box;
    display: -moz-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-lines: multiple;
    -moz-box-lines: multiple;
    -o-box-lines: multiple;
    -webkit-flex-wrap: wrap;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    /* 辅助类 */
}

/* 上下两边居中对齐 */
.acea-row.row-between-wrapper {
    -webkit-box-align: center;
    -moz-box-align: center;
    -o-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
    -webkit-box-pack: justify;
    -moz-box-pack: justify;
    -o-box-pack: justify;
    -ms-flex-pack: justify;
    -webkit-justify-content: space-between;
    justify-content: space-between;
}
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
    padding: 10px 20px !important;
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
    font-size: 24px;
  }
  &-time {
    font-size: 14px;
    /*color: #8C8C8C;*/
  }
}
.spBlock {
  display: block;
}
.el-divider--horizontal{
  margin: 10px 0;
}
</style>
