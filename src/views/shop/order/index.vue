<template>
  <div class="app-container" style="position: relative; height: calc(100vh - 117px);">
    <div class="container">

      <el-tabs v-model="status" type="card" @tab-click="handleOrder">
        <el-tab-pane name="-9">
          <span slot="label"><i class="el-icon-s-order"></i> 全部订单</span>
        </el-tab-pane>
        <el-tab-pane name="0">
          <span slot="label"><i class="el-icon-bank-card"></i> 未支付</span>
        </el-tab-pane>
        <el-tab-pane name="1">
          <span slot="label"><i class="el-icon-refrigerator"></i> 未发货</span>
        </el-tab-pane>
        <el-tab-pane name="2">
          <span slot="label"><i class="el-icon-truck"></i> 待收货</span>
        </el-tab-pane>
        <el-tab-pane name="3">
          <span slot="label"><i class="el-icon-document"></i> 待评价</span>
        </el-tab-pane>
        <el-tab-pane name="4">
          <span slot="label"><i class="el-icon-circle-check"></i> 交易完成</span>
        </el-tab-pane>
        <el-tab-pane name="-1">
          <span slot="label"><i class="el-icon-back"></i> 退款中</span>
        </el-tab-pane>
        <el-tab-pane name="-2">
          <span slot="label"><i class="el-icon-finished"></i> 已退款</span>
        </el-tab-pane>
        <el-tab-pane name="-4">
          <span slot="label"><i class="el-icon-circle-close"></i> 已删除</span>
        </el-tab-pane>
      </el-tabs>
      <!--工具栏-->
      <div class="head-container">

        <!-- 搜索 -->
        <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery" />
        <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
          <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
        <el-select v-model="orderType" clearable placeholder="订单类型" class="filter-item" style="width: 130px">
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-date-picker
          v-model="createTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
        <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
        <!-- 新增 -->
        <el-button
          type="danger"
          class="filter-item"
          size="mini"
          icon="el-icon-refresh"
          @click="toQuery"
        >刷新</el-button>
      </div>
      <!--表单组件-->
      <eForm ref="form" :is-add="isAdd" />
      <eDetail ref="form1" :is-add="isAdd" />
      <eRefund ref="form2" :is-add="isAdd" />
      <editOrder ref="form3" :is-add="isAdd" />
      <eRemark ref="form4" :is-add="isAdd" />
      <ePrint ref="form5" :is-add="isAdd" :print-list="checkList" :to-query="toQuery"/>

      <!--订单数据统计-->
      <div class="order-caculate">
        <a class="caculate-title">订单数 : <span class="caculate-num">{{caculateInfo.orderNum}}</span></a>
        <a class="caculate-title">商品数 : <span class="caculate-num">{{caculateInfo.storeNum}}</span></a>
        <a class="caculate-title">订单金额 : <span class="caculate-num">{{caculateInfo.orderPrice}}</span></a>
        <a class="caculate-title">客户数 : <span class="caculate-num">{{caculateInfo.userNum}}</span></a>
      </div>

      <!--表格渲染-->
      <el-table ref="multipleTable" v-loading="loading" :data="data" size="small" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column :selectable="checkboxT" type="selection" width="50" />
        <el-table-column prop="orderId" width="140" label="订单号">
          <template slot-scope="scope">
            <span>{{ scope.row.orderId }}</span>
            <p>{{ scope.row.pinkName }}</p>
          </template>
        </el-table-column>
        <el-table-column prop="realName" label="用户昵称" >
          <template slot-scope="scope">
            <span>{{ scope.row.userDTO.nickname }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="cartInfoList" width="300" label="商品信息">
          <template slot-scope="scope">
            <div v-for="(item,index) in scope.row.cartInfoList" v-if="item.cartInfoMap.productInfo.attrInfo">
            <span>
              <img
                style="width: 30px;height: 30px;margin:0;cursor: pointer;"
                :src="item.cartInfoMap.productInfo.attrInfo.image"
              >
            </span>
              <span>{{ item.cartInfoMap.productInfo.storeName }}&nbsp;{{ item.cartInfoMap.productInfo.attrInfo.suk }}</span>
              <span> | ￥{{ item.cartInfoMap.truePrice }}×{{ item.cartInfoMap.cartNum }}</span>
            </div>
            <div v-else>
            <span><img
              style="width: 30px;height: 30px;margin:0;cursor: pointer;"
              :src="item.cartInfoMap.productInfo.image"
            ></span>
              <span>{{ item.cartInfoMap.productInfo.storeName }}</span>
              <span> | ￥{{ item.cartInfoMap.truePrice }}×{{ item.cartInfoMap.cartNum }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="payPrice" label="实际支付" />
        <el-table-column prop="payTypeName" label="支付状态" />
        <el-table-column prop="statusName" label="订单状态">
          <template slot-scope="scope">
            <span v-html="scope.row.statusName">{{ scope.row.addTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="addTime" width="160" label="创建时间">
          <template slot-scope="scope">
            <span>{{ formatTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="checkPermission(['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT','YXSTOREORDER_DELETE'])" label="操作" width="200" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button
              v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']"
              size="mini"
              type="primary"
              @click="detail(scope.row)"
            >
              订单详情</el-button>
            <el-dropdown size="mini" split-button type="primary" trigger="click">
              操作
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button
                    v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']"
                    size="mini"
                    type="success"
                    @click="remark(scope.row)"
                  >
                    订单备注</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button
                    v-if="scope.row._status == 2"
                    v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']"
                    size="mini"
                    type="primary"
                    @click="edit(scope.row)"
                  >
                    去发货</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button
                    v-if="scope.row._status == 3"
                    v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']"
                    size="mini"
                    type="primary"
                    @click="refund(scope.row)"
                  >
                    立刻退款</el-button>
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row._status == 1">
                  <el-button
                    v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']"
                    size="mini"
                    type="primary"
                    @click="editOrder(scope.row)"
                  >
                    修改订单</el-button>
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row._status == 1">
                  <el-popover
                    :ref="scope.row.id"
                    v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_DELETE']"
                    placement="top"
                    width="180"
                  >
                    <p>确定删除本条数据吗？</p>
                    <div style="text-align: right; margin: 0">
                      <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                      <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                    </div>
                    <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini">删除</el-button>
                  </el-popover>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--添加订单打印及订单导出功能 2020.04.13 changxh-->
    <el-footer class="footer-contains">
      <div class="footer-search">
        <el-checkbox v-model="printChecked" @change="batchSelection" style="margin-right: 20px;"></el-checkbox>
        <el-select v-model="batchHandle" @change="handlePrintOption" clearable placeholder="批量操作" class="filter-item" style="width: 130px; margin-right: 8px;">
          <el-option
            v-for="item in handleOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-select v-model="batchExport" @change="handleExportOption" clearable placeholder="批量导出" class="filter-item" style="width: 130px">
          <el-option
            v-for="item in exportOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <!--分页组件-->
      <el-pagination
        :total="total"
        :current-page="page + 1"
        style="margin-top: 8px;"
        layout="total, prev, pager, next, sizes"
        @size-change="sizeChange"
        @current-change="pageChange"
      />
    </el-footer>
  </div>
</template>

<script>
  import checkPermission from '@/utils/permission'
  import initData from '@/mixins/crud'
  import { del } from '@/api/yxStoreOrder'
  import eForm from './form'
  import eDetail from './detail'
  import eRefund from './refund'
  import editOrder from './edit'
  import eRemark from './remark'
  import ePrint from './print'
  import { formatTime } from '@/utils/index'
  import { gett } from '@/api/visits'

  export default {
    components: { eForm, eDetail, eRefund, editOrder, eRemark, ePrint },
    mixins: [initData],
    data() {
      return {
        delLoading: false, status: '-9', orderType: '0',createTime: '', checkList: [], printChecked: false, batchHandle: '', batchExport: '', listContent: [],
        queryTypeOptions: [
          { key: 'orderId', display_name: '订单号' },
          { key: 'realName', display_name: '用户姓名' },
          { key: 'userPhone', display_name: '用户电话' }
        ],
        statusOptions: [
          { value: '0', label: '未支付' },
          { value: '1', label: '未发货' },
          { value: '2', label: '待收货' },
          { value: '3', label: '待评价' },
          { value: '4', label: '交易完成' },
          // { value: '5', label: '待核销' },
          { value: '-1', label: '退款中' },
          { value: '-2', label: '已退款' },
          { value: '-4', label: '已删除' }
        ],
        typeOptions: [
          { value: '0', label: '所有订单' },
          { value: '1', label: '普通订单' },
          { value: '2', label: '拼团订单' },
          { value: '3', label: '秒杀订单' },
          { value: '4', label: '砍价订单' }
        ],
        handleOptions: [
          {value: '', label: '批量操作'},
          {value: '0', label: '批量打印'},
        ],
        exportOptions: [
          {value: '', label: '批量导出'},
          {value: '0', label: '导出全部'},
          {value: '1', label: '导出选中'},
        ],
        caculateInfo: {
          orderNum : 0,
          storeNum : 0,
          orderPrice : 0,
          userNum : 0,
        },
      }
    },
    created() {
      this.$nextTick(() => {
        this.init()
      })
    },
    methods: {
      formatTime,
      checkPermission,
      handleOrder(tab, event) {
        this.status = tab.name
        this.toQuery()
      },
      beforeInit() {
        this.url = 'api/yxStoreOrder'
        const sort = 'id,desc'
        this.params = { page: this.page, size: this.size, sort: sort, orderStatus: this.status, orderType: this.orderType, addTime: this.createTime, listContent: this.listContent  }
        const query = this.query
        const type = query.type
        const value = query.value
        if (type && value) { this.params[type] = value }
        return true
      },
      subDelete(id) {
        this.delLoading = true
        del(id).then(res => {
          this.delLoading = false
          this.$refs[id].doClose()
          this.dleChangePage()
          this.init()
          this.$notify({
            title: '删除成功',
            type: 'success',
            duration: 2500
          })
        }).catch(err => {
          this.delLoading = false
          this.$refs[id].doClose()
          console.log(err.response.data.message)
        })
      },
      add() {
        this.isAdd = true
        this.$refs.form.dialog = true
      },
      edit(data) {
        this.isAdd = false
        const _this = this.$refs.form
        _this.form = {
          id: data.id,
          orderId: data.orderId,
          uid: data.uid,
          realName: data.realName,
          userPhone: data.userPhone,
          userAddress: data.userAddress,
          cartId: data.cartId,
          freightPrice: data.freightPrice,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          totalPostage: data.totalPostage,
          payPrice: data.payPrice,
          payPostage: data.payPostage,
          deductionPrice: data.deductionPrice,
          couponId: data.couponId,
          couponPrice: data.couponPrice,
          paid: data.paid,
          payTime: data.payTime,
          payType: data.payType,
          addTime: data.addTime,
          status: data.status,
          refundStatus: data.refundStatus,
          refundReasonWapImg: data.refundReasonWapImg,
          refundReasonWapExplain: data.refundReasonWapExplain,
          refundReasonTime: data.refundReasonTime,
          refundReasonWap: data.refundReasonWap,
          refundReason: data.refundReason,
          refundPrice: data.refundPrice,
          deliveryName: data.deliveryName,
          deliverySn: data.deliverySn,
          deliveryType: data.deliveryType,
          deliveryId: data.deliveryId,
          gainIntegral: data.gainIntegral,
          useIntegral: data.useIntegral,
          backIntegral: data.backIntegral,
          mark: data.mark,
          isDel: data.isDel,
          unique: data.unique,
          remark: data.remark,
          merId: data.merId,
          isMerCheck: data.isMerCheck,
          combinationId: data.combinationId,
          pinkId: data.pinkId,
          cost: data.cost,
          seckillId: data.seckillId,
          bargainId: data.bargainId,
          verifyCode: data.verifyCode,
          storeId: data.storeId,
          shippingType: data.shippingType,
          isChannel: data.isChannel,
          isRemind: data.isRemind,
          isSystemDel: data.isSystemDel
        }
        _this.dialog = true
      },
      editOrder(data) {
        this.isAdd = false
        const _this = this.$refs.form3
        _this.form = {
          id: data.id,
          orderId: data.orderId,
          uid: data.uid,
          realName: data.realName,
          userPhone: data.userPhone,
          userAddress: data.userAddress,
          cartId: data.cartId,
          freightPrice: data.freightPrice,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          totalPostage: data.totalPostage,
          payPrice: data.payPrice,
          payPostage: data.payPostage,
          deductionPrice: data.deductionPrice,
          couponId: data.couponId,
          couponPrice: data.couponPrice,
          paid: data.paid,
          payTime: data.payTime,
          payType: data.payType,
          addTime: data.addTime,
          status: data.status,
          refundStatus: data.refundStatus,
          refundReasonWapImg: data.refundReasonWapImg,
          refundReasonWapExplain: data.refundReasonWapExplain,
          refundReasonTime: data.refundReasonTime,
          refundReasonWap: data.refundReasonWap,
          refundReason: data.refundReason,
          refundPrice: data.refundPrice,
          deliveryName: data.deliveryName,
          deliveryType: data.deliveryType,
          deliveryId: data.deliveryId,
          gainIntegral: data.gainIntegral,
          useIntegral: data.useIntegral,
          backIntegral: data.backIntegral,
          mark: data.mark,
          isDel: data.isDel,
          unique: data.unique,
          remark: data.remark,
          merId: data.merId,
          isMerCheck: data.isMerCheck,
          combinationId: data.combinationId,
          pinkId: data.pinkId,
          cost: data.cost,
          seckillId: data.seckillId,
          bargainId: data.bargainId,
          verifyCode: data.verifyCode,
          storeId: data.storeId,
          shippingType: data.shippingType,
          isChannel: data.isChannel,
          isRemind: data.isRemind,
          isSystemDel: data.isSystemDel
        }
        _this.dialog = true
      },
      remark(data) {
        this.isAdd = false
        const _this = this.$refs.form4
        _this.form = {
          id: data.id,
          orderId: data.orderId,
          uid: data.uid,
          realName: data.realName,
          userPhone: data.userPhone,
          userAddress: data.userAddress,
          cartId: data.cartId,
          freightPrice: data.freightPrice,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          totalPostage: data.totalPostage,
          payPrice: data.payPrice,
          payPostage: data.payPostage,
          deductionPrice: data.deductionPrice,
          couponId: data.couponId,
          couponPrice: data.couponPrice,
          paid: data.paid,
          payTime: data.payTime,
          payType: data.payType,
          addTime: data.addTime,
          status: data.status,
          refundStatus: data.refundStatus,
          refundReasonWapImg: data.refundReasonWapImg,
          refundReasonWapExplain: data.refundReasonWapExplain,
          refundReasonTime: data.refundReasonTime,
          refundReasonWap: data.refundReasonWap,
          refundReason: data.refundReason,
          refundPrice: data.refundPrice,
          deliveryName: data.deliveryName,
          deliveryType: data.deliveryType,
          deliveryId: data.deliveryId,
          gainIntegral: data.gainIntegral,
          useIntegral: data.useIntegral,
          backIntegral: data.backIntegral,
          mark: data.mark,
          isDel: data.isDel,
          unique: data.unique,
          remark: data.remark,
          merId: data.merId,
          isMerCheck: data.isMerCheck,
          combinationId: data.combinationId,
          pinkId: data.pinkId,
          cost: data.cost,
          seckillId: data.seckillId,
          bargainId: data.bargainId,
          verifyCode: data.verifyCode,
          storeId: data.storeId,
          shippingType: data.shippingType,
          isChannel: data.isChannel,
          isRemind: data.isRemind,
          isSystemDel: data.isSystemDel
        }
        _this.dialog = true
      },
      refund(data) {
        this.isAdd = false
        const _this = this.$refs.form2
        _this.form = {
          id: data.id,
          orderId: data.orderId,
          uid: data.uid,
          realName: data.realName,
          userPhone: data.userPhone,
          userAddress: data.userAddress,
          cartId: data.cartId,
          freightPrice: data.freightPrice,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          totalPostage: data.totalPostage,
          payPrice: data.payPrice,
          payPostage: data.payPostage,
          deductionPrice: data.deductionPrice,
          couponId: data.couponId,
          couponPrice: data.couponPrice,
          paid: data.paid,
          payTime: data.payTime,
          payType: data.payType,
          addTime: data.addTime,
          status: data.status,
          refundStatus: data.refundStatus,
          refundReasonWapImg: data.refundReasonWapImg,
          refundReasonWapExplain: data.refundReasonWapExplain,
          refundReasonTime: data.refundReasonTime,
          refundReasonWap: data.refundReasonWap,
          refundReason: data.refundReason,
          refundPrice: data.refundPrice,
          deliveryName: data.deliveryName,
          deliveryType: data.deliveryType,
          deliveryId: data.deliveryId,
          gainIntegral: data.gainIntegral,
          useIntegral: data.useIntegral,
          backIntegral: data.backIntegral,
          mark: data.mark,
          isDel: data.isDel,
          unique: data.unique,
          remark: data.remark,
          merId: data.merId,
          isMerCheck: data.isMerCheck,
          combinationId: data.combinationId,
          pinkId: data.pinkId,
          cost: data.cost,
          seckillId: data.seckillId,
          bargainId: data.bargainId,
          verifyCode: data.verifyCode,
          storeId: data.storeId,
          shippingType: data.shippingType,
          isChannel: data.isChannel,
          isRemind: data.isRemind,
          isSystemDel: data.isSystemDel
        }
        _this.dialog = true
      },
      detail(data) {
        this.isAdd = false
        const _this = this.$refs.form1
        _this.form = {
          id: data.id,
          orderId: data.orderId,
          payTypeName: data.payTypeName,
          statusName: data.statusName,
          uid: data.uid,
          realName: data.realName,
          userPhone: data.userPhone,
          userAddress: data.userAddress,
          cartId: data.cartId,
          freightPrice: data.freightPrice,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          totalPostage: data.totalPostage,
          payPrice: data.payPrice,
          payPostage: data.payPostage,
          deductionPrice: data.deductionPrice,
          couponId: data.couponId,
          couponPrice: data.couponPrice,
          paid: data.paid,
          payTime: data.payTime,
          payType: data.payType,
          addTime: data.addTime,
          status: data.status,
          refundStatus: data.refundStatus,
          refundReasonWapImg: data.refundReasonWapImg,
          refundReasonWapExplain: data.refundReasonWapExplain,
          refundReasonTime: data.refundReasonTime,
          refundReasonWap: data.refundReasonWap,
          refundReason: data.refundReason,
          refundPrice: data.refundPrice,
          deliveryName: data.deliveryName,
          deliverySn: data.deliverySn,
          deliveryType: data.deliveryType,
          deliveryId: data.deliveryId,
          gainIntegral: data.gainIntegral,
          useIntegral: data.useIntegral,
          backIntegral: data.backIntegral,
          mark: data.mark,
          isDel: data.isDel,
          unique: data.unique,
          remark: data.remark,
          merId: data.merId,
          isMerCheck: data.isMerCheck,
          combinationId: data.combinationId,
          pinkId: data.pinkId,
          cost: data.cost,
          seckillId: data.seckillId,
          bargainId: data.bargainId,
          verifyCode: data.verifyCode,
          storeId: data.storeId,
          shippingType: data.shippingType,
          isChannel: data.isChannel,
          isRemind: data.isRemind,
          isSystemDel: data.isSystemDel,
          nickname: data.userDTO.nickname
        }
        _this.dialog = true
      },
      getCaculateInfo(){
        gett().then(res => {
          this.caculateInfo = {
            orderPrice : res.priceCount,
            orderNum : res.orderCount,
            storeNum : res.goodsCount,
            userNum : res.userCount,
          };
        })
      },
      clearCaculateInfo(){
        this.caculateInfo = {
          orderPrice : 0,
          storeNum : 0,
          orderNum : 0,
          userNum : 0,
        };
      },
      handleSelectionChange(val) {
        this.checkList = val;
        let orderPrice = 0, storeNum = 0, orderNum = 0, userNum = 0;
        if(val.length !=0 ){
          this.printChecked = true;
          let user = [];
          val.forEach((item,index)=>{
            orderNum += 1;
            orderPrice += item.totalPrice;
            storeNum += item.totalNum;
            user.push(item.userDTO.account);
          })
          user = Array.from(new Set(user));
          this.caculateInfo = {
            orderPrice : orderPrice.toFixed(2),
            storeNum : storeNum,
            orderNum : orderNum,
            userNum : user.length,
          };
        }else {
          this.printChecked = false;
          this.clearCaculateInfo();
          // this.getCaculateInfo();
        }
      },
      batchSelection(val){
        let rows = this.data;
        if (val) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handlePrintOption(val){
        switch (val) {
          case '0':
            this.getPrintList();
            this.batchHandle = '';
            break;
          default:
            break;
        }
      },
      handleExportOption(val){
        let list = this.checkList;
        this.page = 0;
        this.size = 10000;
        switch (val) {
          case "0":
            this.listContent = "";
            this.beforeInit();
            this.downloadMethod();
            break;
          case "1":
            if(list.length == 0){
              this.$message({
                message: '请选择订单',
                type: 'warning'
              });
            }else {
              this.listContent = [];
              list.forEach((item) => {
                this.listContent.push(item.orderId);
              })
              this.listContent = JSON.stringify(this.listContent);
              this.beforeInit();
              this.downloadMethod();
            }
            break;
          default:
            break;
        }
        this.batchExport = "";
      },
      getPrintList(){
        let list = this.checkList;
        if(list.length == 0){
          this.$message({
            message: '请选择订单',
            type: 'warning'
          });
        }else {
          const _this = this.$refs.form5;
          _this.dialog = true
        }
      },
      checkboxT(row, rowIndex) {
        return row.id !== 1
      },
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
