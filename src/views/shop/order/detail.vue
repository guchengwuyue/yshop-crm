<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '订单详情'" width="700px">
    <el-card>
      <div slot="header">
        <span>收货信息</span>
        <!--<el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
      </div>
      <div>用户昵称:{{ form.nickname }}</div>
      <div>收货人: {{ form.realName }}</div>
      <div>联系电话: {{ form.userPhone }}</div>
      <div>收货地址: {{ form.userAddress }}</div>
    </el-card>
    <el-card>
      <div slot="header">
        <span>订单信息</span>
      </div>
      <el-row :gutter="24">
        <el-col :span="12">
          <div>订单编号: {{ form.orderId }}</div>
          <div>商品总数: {{ form.totalNum }}</div>
          <div>支付邮费: {{ form.totalPostage }}</div>
          <div>实际支付: {{ form.payPrice }}</div>
          <div>支付方式: {{ form.payTypeName }}</div>
        </el-col>
        <el-col :span="12">
          <div>订单状态: {{ form.statusName }}</div>
          <div>商品总价: {{ form.totalPrice }}</div>
          <div>优惠券金额: {{ form.couponPrice }}</div>
          <div>创建时间: {{ formatTime(form.addTime) }}</div>
          <div>支付时间: {{ formatTime(form.payTime) }}</div>
        </el-col>
      </el-row>
    </el-card>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreOrder'
import { formatTime } from '@/utils/index'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
        id: '',
        orderId: '',
        uid: '',
        realName: '',
        userPhone: '',
        userAddress: '',
        cartId: '',
        freightPrice: '',
        totalNum: '',
        totalPrice: '',
        totalPostage: '',
        payPrice: '',
        payPostage: '',
        deductionPrice: '',
        couponId: '',
        couponPrice: '',
        paid: '',
        payTime: '',
        payType: '',
        addTime: '',
        status: '',
        refundStatus: '',
        refundReasonWapImg: '',
        refundReasonWapExplain: '',
        refundReasonTime: '',
        refundReasonWap: '',
        refundReason: '',
        refundPrice: '',
        deliveryName: '',
        deliveryType: '',
        deliveryId: '',
        gainIntegral: '',
        useIntegral: '',
        backIntegral: '',
        mark: '',
        isDel: '',
        unique: '',
        remark: '',
        merId: '',
        isMerCheck: '',
        combinationId: '',
        pinkId: '',
        cost: '',
        seckillId: '',
        bargainId: '',
        verifyCode: '',
        storeId: '',
        shippingType: '',
        isChannel: '',
        isRemind: '',
        isSystemDel: ''
      },
      rules: {
        unique: [
          { required: true, message: 'please enter', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    formatTime,
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAdd) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    doEdit() {
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        orderId: '',
        uid: '',
        realName: '',
        userPhone: '',
        userAddress: '',
        cartId: '',
        freightPrice: '',
        totalNum: '',
        totalPrice: '',
        totalPostage: '',
        payPrice: '',
        payPostage: '',
        deductionPrice: '',
        couponId: '',
        couponPrice: '',
        paid: '',
        payTime: '',
        payType: '',
        addTime: '',
        status: '',
        refundStatus: '',
        refundReasonWapImg: '',
        refundReasonWapExplain: '',
        refundReasonTime: '',
        refundReasonWap: '',
        refundReason: '',
        refundPrice: '',
        deliveryName: '',
        deliveryType: '',
        deliveryId: '',
        gainIntegral: '',
        useIntegral: '',
        backIntegral: '',
        mark: '',
        isDel: '',
        unique: '',
        remark: '',
        merId: '',
        isMerCheck: '',
        combinationId: '',
        pinkId: '',
        cost: '',
        seckillId: '',
        bargainId: '',
        verifyCode: '',
        storeId: '',
        shippingType: '',
        isChannel: '',
        isRemind: '',
        isSystemDel: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
