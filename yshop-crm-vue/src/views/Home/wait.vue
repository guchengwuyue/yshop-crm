<template>
  <div style="padding:20px 20px">
    <el-row :gutter="30">
      <el-col :span="6">
        <el-card>
           <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1003.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;" @click="toPath('/check/checkcontract')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.contractCheckCount}}</div>
               <div>待审批合同</div>
             </div>
           </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1005.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;" @click="toPath('/check/checkreceivables')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.receivablesCheckCount}}</div>
               <div>待审批回款</div>
             </div>
           </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1002.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;"  @click="toPath('/check/checkinvoice')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.invoiceCheckCount}}</div>
               <div>待审批发票</div>
             </div>
           </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1001.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;" @click="toPath('/customer/crm/customer')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.followCustomerCount}}</div>
               <div>待跟进客户</div>
             </div>
           </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="30" style="margin-top:50px">
      <el-col :span="6">
        <el-card>
           <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1006.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;" @click="toPath('/customer/clues/cluesindex')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.followCluesCount}}</div>
               <div>待跟进线索</div>
             </div>
           </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display:flex;align-items: center;padding:30px 15px">
             <div><img style="width: 80px; height: 80px" src="@/assets/imgs/1000.png" /></div>
             <div style="margin-left:20px;font-size:16px;color:#4397fd;cursor:pointer;" @click="toPath('/customer/business/business')">
               <div style="font-size:28px;font-weight:bold;margin-bottom:6px">{{count.followBusinessCount}}</div>
               <div>待跟进商机</div>
             </div>
           </div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>
<script setup lang="ts">
import * as NoticeApi from '@/api/system/notice'
// import PanelGroupT from './PanelGroupT.vue'
import { formatTime } from '@/utils'
import { useUserStore } from '@/store/modules/user'
import avatarImg from '@/assets/imgs/avatar.gif'
import ContractList from './components/contract/index.vue'
import ReceivablesList from './components/receivables/index.vue'
import InvoiceList from './components/invoice/index.vue'
import { ContractApi } from '@/api/crm/crmcontract'
import router from '@/router'

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
  invoiceCheckCount: 0,
  receivablesCheckCount: 0,
  contractCheckCount: 0,
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

const toPath = (redirect) => {
  router.push({ path: redirect || '/' })
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
