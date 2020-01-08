<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">

      <!-- 搜索 -->
      <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
      <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
        <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
      <el-select v-model="status" clearable placeholder="订单状态" class="filter-item" style="width: 130px">
        <el-option
          v-for="item in statusOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <!-- 新增 -->
    </div>
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd"/>
    <eDetail ref="form1" :is-add="isAdd"/>
    <eRefund ref="form2" :is-add="isAdd"/>
    <editOrder ref="form3" :is-add="isAdd"/>
    <eRemark ref="form4" :is-add="isAdd"/>
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="orderId" width="140" label="订单号">
        <template slot-scope="scope">
          <span>{{ scope.row.orderId }}</span>
          <p>{{ scope.row.pinkName }}</p>
        </template>
      </el-table-column>
      <el-table-column prop="realName" label="用户姓名"/>
      <el-table-column prop="cartInfoList" width="300" label="商品信息">
        <template slot-scope="scope" >
          <div v-for="(item,index) in scope.row.cartInfoList">
            <span><img style="width: 30px;height: 30px;margin:0;cursor: pointer;"
            :src="item.cartInfoMap.productInfo.image"/></span>
            <span>{{item.cartInfoMap.productInfo.storeName}}</span>
            <span> | ￥{{item.cartInfoMap.truePrice}}×{{item.cartInfoMap.cartNum}}</span>
          </div>
          <div v-if="item.cartInfoMap.productInfo.attrInfo" v-for="(item,index) in scope.row.cartInfoList">
            <span>
                <img style="width: 30px;height: 30px;margin:0;cursor: pointer;"
                :src="item.cartInfoMap.productInfo.attrInfo.image">
            </span>
            <span>{{item.cartInfoMap.productInfo.storeName}}&nbsp;{{item.cartInfoMap.productInfo.attrInfo.suk}}</span>
            <span> | ￥{{item.cartInfoMap.truePrice}}×{{item.cartInfoMap.cartNum}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="payPrice" label="实际支付"/>
      <el-table-column prop="payTypeName" label="支付状态"/>
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
      <el-table-column v-if="checkPermission(['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT','YXSTOREORDER_DELETE'])" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button  v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']" size="mini" type="primary"
                     @click="detail(scope.row)">
            订单详情</el-button>
          <el-dropdown size="mini" split-button type="primary" trigger="click">
            操作
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']" size="mini" type="success"
                           @click="remark(scope.row)">
                  订单备注</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row._status == 2" v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']" size="mini" type="primary"
                           @click="edit(scope.row)">
                  去发货</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row._status == 3" v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']" size="mini" type="primary"
                           @click="refund(scope.row)">
                  立刻退款</el-button>
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row._status == 1">
                <el-button v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_EDIT']" size="mini" type="primary"
                           @click="editOrder(scope.row)">
                  修改订单</el-button>
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row._status == 1">
                <el-popover
                  v-permission="['admin','YXSTOREORDER_ALL','YXSTOREORDER_DELETE']"
                  :ref="scope.row.id"
                  placement="top"
                  width="180">
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
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
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
import { formatTime } from '@/utils/index'
export default {
  components: { eForm, eDetail, eRefund, editOrder, eRemark },
  mixins: [initData],
  data() {
    return {
      delLoading: false, status,
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
      ]
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
    beforeInit() {
      this.url = 'api/yxStoreOrder'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort, orderStatus: this.status }
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
    }
  }
}
</script>

<style scoped>

</style>
