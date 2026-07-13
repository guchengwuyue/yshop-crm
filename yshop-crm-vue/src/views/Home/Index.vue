<template>
  <div>
    <div class="divBox">
      <div style="background-color:#ffffff;padding:0 0 0px 20px">
        <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            @select="handleSelect"
          >
            <el-menu-item index="my">我的简报</el-menu-item>
            <el-menu-item index="sub">下属简报</el-menu-item>
        </el-menu>
      </div>
      <div style="background-color:#ffffff;padding:0 0 0px 20px">
        <div style="padding:20px 0;display: flex;align-items: center;">
          <Icon color ="#67C23A" icon="ep:monitor" class="mr-3px" /><span style="font-weight:bold">销售简报</span>
        </div>
        <el-row :gutter="24" class="baseInfo">
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div  class="acea-row row-between-wrapper">
                  <span>新增客户(人)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count03" :duration="2600" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count003}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per003}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div class="acea-row row-between-wrapper">
                  <span>新增联系人(人)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count08" :duration="3000" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count008}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per008}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div  class="acea-row row-between-wrapper">
                  <span>新增商机(个)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count01" :duration="3200" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count001}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per001}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div class="acea-row row-between-wrapper">
                  <span>新增合同(个)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count05" :duration="3600" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count005}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per005}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="24" class="baseInfo">
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div  class="acea-row row-between-wrapper">
                  <span>合同金额(元)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count06" :duration="2600" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count006}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per006}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div class="acea-row row-between-wrapper">
                  <span>回款金额(元)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count07" :duration="3000" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count007}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per007}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div  class="acea-row row-between-wrapper">
                  <span>新增线索(个)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count02" :duration="3200" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count002}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per002}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
            <el-card :bordered="false" dis-hover :padding="12">
              <template #header>
                <div class="acea-row row-between-wrapper">
                  <span>新增跟进记录(条)</span>
                  <el-tag type="success">今日</el-tag>
                </div>
              </template>
              <div class="content">
                <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.count04" :duration="3600" class="card-panel-num" /></span>
              </div>
              <template #footer>
                <div  class="acea-row " style="align-items: center;">
                  <span>昨日<span class="ml-1">{{count.count004}}</span></span>
                  <span class="ml-5">较昨日<span class="ml-5px">{{count.per004}}%</span></span>
                  <Icon color ="#67C23A" icon="ep:top" class="ml-3px" />
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- <PanelGroupT /> -->
    <div class="divBox" style="background-color:#ffffff">
      <EchartClock />
    </div>
  </div>
</template>
<script setup lang="ts">
import * as NoticeApi from '@/api/system/notice'
import EchartClock from './components/echart/index.vue'
import { formatTime } from '@/utils'

import { useUserStore } from '@/store/modules/user'
import avatarImg from '@/assets/imgs/avatar.gif'
import { ContractApi } from '@/api/crm/crmcontract'

const userStore = useUserStore()
const avatar = userStore.getUser.avatar ? userStore.getUser.avatar : avatarImg
const shopId = userStore.getUser.shopId
const countName = ref('全门店')
const { t } = useI18n()
const count = ref({
  followCustomerCount: 0,
  followBusinessCount: 0,
  followCluesCount: 0,
  contractDueCount: 0,
  customerDueCount: 0,
  contractReturnCount: 0,
  myInvoiceCount: 0,
  productCount: 0,
})
const loading = ref(true)
const notice = ref([])
const activeIndex = ref('my')
const contractCheckCount = ref(0)
const receivablesCheckCount = ref(0)
const invoiceCheckCount = ref(0)

const handleSelect = (key) => {
  activeIndex.value = key
  getData()
}

/** 查询列表 */
const getData = async () => {
  try {
    const data = await ContractApi.getIndexCount({relation:activeIndex.value})

    count.value = data.brieCountVO
  } finally {
  }
}

/** 初始化 **/
onMounted(() => {
  getData()
  loading.value = false
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
    font-size:30px;
    font-weight:bold
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
