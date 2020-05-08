<template>
  <el-dialog id="printDialog" :append-to-body="false" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" title="订单打印" width="220mm">

    <el-button
      type="warning"
      class="filter-item"
      size="mini"
      icon="el-icon-printer"
      onclick="doPrint()"
    >打印</el-button>

    <div id="printList">
      <div class="order-list" v-for="(list, index) in printList">
      <el-header class="order-title">订货单</el-header>

      <div class="order-info">
        <span class="info">{{list.orderId}}</span>
        <span class="info">下单日期 : {{formatTimeTwo(list.addTime)}}</span>
        <span>客户名称 : {{list.realName}}</span>
      </div>
      <div class="order-info">
        <span class="info">联系人 : {{list.userDTO.account}}</span>
        <span class="info">联系电话 : {{list.userPhone}}</span>
        <span>收货地址 : {{list.userAddress}}</span>
      </div>

      <el-table border show-summary :data="list.cartInfoList"  :summary-method="getSummaries" size="small" style="width: 100%;">
        <el-table-column type="index" label="行号" :index="indexMethod" width="60mm"/>

        <el-table-column prop="cartInfoMap.productInfo.productId" width="80mm" label="商品编号" />

        <el-table-column prop="cartInfoMap.productInfo.storeName" width="172mm" label="商品名称" />

        <el-table-column prop="cartInfoMap.productInfo.unitName" width="80mm" label="商品规格" />

        <el-table-column prop="cartInfoMap.productInfo.unitName" width="80mm" label="单位" />

        <el-table-column prop="cartInfoMap.productInfo.price" width="80mm" label="单价" />

        <el-table-column prop="cartInfoMap.cartNum" width="80mm" label="数量" />

        <el-table-column prop="cartInfoMap" width="80mm" label="小计(元)" >
          <template slot-scope="scope">
            <span>{{ scope.row.cartInfoMap.productInfo.price*scope.row.cartInfoMap.cartNum }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="cartInfoMap.mark" width="80mm" label="备注" />

      </el-table>
    </div>
    </div>

  </el-dialog>
</template>

<script>
import { formatTime, parseTime, formatTimeTwo } from '@/utils/index'
export default {
 props: {
    printList: {
      type: Array,
      required: true
    },
    toQuery: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false
    }
  },
  mounted() {
    window.doPrint = this.doPrint;
  },
  methods: {
    formatTime,
    parseTime,
    formatTimeTwo,
    cancel() {
      this.dialog = false
    },
    indexMethod(index){
      return index+1;
    },
    doPrint(){
      let printbox = document.querySelector("#printList").innerHTML;
      document.querySelector("body").innerHTML = printbox;
      window.print();
      this.cancel();
      // this.toQuery();
      window.location.reload();
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }else if(index === 7){
          const values = data.map(item => Number(item.cartInfoMap.productInfo.price*item.cartInfoMap.cartNum));
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' 元';
        }
      });
      return sums;
    }
  }
}
</script>
<style scoped lang="scss">
  .container {
    height: calc(100% - 80px);
    position: absolute;
    overflow: auto;
    width: calc(100% - 20px);

    .order-caculate {
      font-size: 14px;
      color: #909399;
      border-top: 1px solid #f0f0f0;
      padding: 20px 10px;
      .caculate-title {
        display: inline-block;
        margin-right: 50px;
      }
      .caculate-num {
        font-size: 20px;
        color: #ff4949;
      }
    }

    .el-table th {
      background-color: #fafafa;
    }
  }

  .footer-contains {
    position: absolute;
    display: -ms-flexbox;
    display: flex;
    background-color: #f6f6f6;
    bottom: 0;
    flex-align: center;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    z-index: 999;
    padding: 0 20px 0 13px;
  }

  /*打印单样式编辑*/
  .order-list {
    /*  height: 297mm;*/
    margin: 0 auto;
    width: 210mm;

    .order-title {
      height: 16mm;
      line-height: 16mm;
      font-size: 8mm;
      font-weight: bolder;
      text-align: center;
    }
    .order-info {
      span {
        display: inline-block;
        padding: 0 0 10px 0;
        font-size: 3mm;
      }
      span.info {
        width: 60mm;
      }
    }
    .el-table--small th, .el-table--small td {
      padding: 4px 0;
    }
  }
</style>
