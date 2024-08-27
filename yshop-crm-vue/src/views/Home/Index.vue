<template>
  <div>
    <div class="divBox">
      <el-row :gutter="24" class="baseInfo">
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div  class="acea-row row-between-wrapper">
                <span>需要跟进的客户</span>
                <el-tag type="success">今日</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.followCustomerCount" :duration="2600" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div class="acea-row row-between-wrapper">
                <span>需要跟进的商机</span>
                <el-tag type="success">今日</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.followBusinessCount" :duration="3000" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div  class="acea-row row-between-wrapper">
                <span>需要跟进进线索</span>
                <el-tag type="success">今日</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.followCluesCount" :duration="3200" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div class="acea-row row-between-wrapper">
                <span>即将到期的合同</span>
                <el-tag type="success">全部</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.contractDueCount" :duration="3600" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="24" class="baseInfo">
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div  class="acea-row row-between-wrapper">
                <span>即将到期的客户</span>
                <el-tag type="success">全部</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.customerDueCount" :duration="2600" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div class="acea-row row-between-wrapper">
                <span>待回款的合同</span>
                <el-tag type="success">全部</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.contractReturnCount" :duration="3000" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div  class="acea-row row-between-wrapper">
                <span>我的发票</span>
                <el-tag type="success">全部</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.myInvoiceCount" :duration="3200" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="ivu-mb">
          <el-card :bordered="false" dis-hover :padding="12">
            <template #header>
              <div class="acea-row row-between-wrapper">
                <span>商品总数</span>
                <el-tag type="success">全部</el-tag>
              </div>
            </template>
            <div class="content">
              <span class="content-number spBlock"><count-to :start-val="0" :end-val="count.productCount" :duration="3600" class="card-panel-num" /></span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <!-- <PanelGroupT /> -->
    <div class="divBox" style="background-color:#ffffff">
      <el-tabs
        v-model="activeName"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
      >
        <el-tab-pane name="first">
          <template #label>
              <span><el-badge :value="contractCheckCount" :hidden="contractCheckCount == 0">合同审批</el-badge></span>
          </template>
          <ContractList @get-contract-count="getContractCount" />
        </el-tab-pane>
        <el-tab-pane name="second">
          <template #label>
              <span><el-badge :value="receivablesCheckCount" :hidden="receivablesCheckCount == 0">回款审批</el-badge></span>
          </template>
          <ReceivablesList @get-receivables-count="getReceivablesCount" />
        </el-tab-pane>
        <el-tab-pane name="third">
          <template #label>
              <span><el-badge :value="invoiceCheckCount" :hidden="invoiceCheckCount == 0">发票审批</el-badge></span>
          </template>
          <InvoiceList @get-invoice-count="getInvoiceCount" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script setup lang="ts">
import * as NoticeApi from '@/api/system/notice'
import PanelGroupT from './PanelGroupT.vue'
import { formatTime } from '@/utils'
import { useUserStore } from '@/store/modules/user'
import avatarImg from '@/assets/imgs/avatar.gif'
import ContractList from './components/contract/index.vue'
import ReceivablesList from './components/receivables/index.vue'
import InvoiceList from './components/invoice/index.vue'
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
const activeName = ref('first')
const contractCheckCount = ref(0)
const receivablesCheckCount = ref(0)
const invoiceCheckCount = ref(0)

const getContractCount = (count) => {
  contractCheckCount.value = count
}

const getReceivablesCount = (count) => {
  receivablesCheckCount.value = count
}

const getInvoiceCount = (count) => {
  invoiceCheckCount.value = count
}

/** 查询列表 */
const getData = async () => {
  try {
    const data = await ContractApi.getIndexCount()

    count.value = data
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
