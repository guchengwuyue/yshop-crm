<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="订单号" >
        <el-input v-model="form.orderId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="用户id" >
        <el-input v-model="form.uid" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="用户姓名" >
        <el-input v-model="form.realName" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="用户电话" >
        <el-input v-model="form.userPhone" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="详细地址" >
        <el-input v-model="form.userAddress" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="购物车id" >
        <el-input v-model="form.cartId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="运费金额" >
        <el-input v-model="form.freightPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="订单商品总数" >
        <el-input v-model="form.totalNum" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="订单总价" >
        <el-input v-model="form.totalPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="邮费" >
        <el-input v-model="form.totalPostage" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="实际支付金额" >
        <el-input v-model="form.payPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="支付邮费" >
        <el-input v-model="form.payPostage" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="抵扣金额" >
        <el-input v-model="form.deductionPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="优惠券id" >
        <el-input v-model="form.couponId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="优惠券金额" >
        <el-input v-model="form.couponPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="支付状态" >
        <el-input v-model="form.paid" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="支付时间" >
        <el-input v-model="form.payTime" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="支付方式" >
        <el-input v-model="form.payType" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="创建时间" >
        <el-input v-model="form.addTime" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="订单状态（-1 : 申请退款 -2 : 退货成功 0：待发货；1：待收货；2：已收货；3：待评价；-1：已退款）" >
        <el-input v-model="form.status" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="0 未退款 1 申请中 2 已退款" >
        <el-input v-model="form.refundStatus" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="退款图片" >
        <el-input v-model="form.refundReasonWapImg" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="退款用户说明" >
        <el-input v-model="form.refundReasonWapExplain" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="退款时间" >
        <el-input v-model="form.refundReasonTime" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="前台退款原因" >
        <el-input v-model="form.refundReasonWap" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="不退款的理由" >
        <el-input v-model="form.refundReason" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="退款金额" >
        <el-input v-model="form.refundPrice" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="快递名称/送货人姓名" >
        <el-input v-model="form.deliveryName" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="发货类型" >
        <el-input v-model="form.deliveryType" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="快递单号/手机号" >
        <el-input v-model="form.deliveryId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="消费赚取积分" >
        <el-input v-model="form.gainIntegral" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="使用积分" >
        <el-input v-model="form.useIntegral" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="给用户退了多少积分" >
        <el-input v-model="form.backIntegral" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="备注" >
        <el-input v-model="form.mark" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="是否删除" >
        <el-input v-model="form.isDel" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="唯一id(md5加密)类似id" prop="unique">
        <el-input v-model="form.unique" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="管理员备注" >
        <el-input v-model="form.remark" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="商户ID" >
        <el-input v-model="form.merId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="isMerCheck" >
        <el-input v-model="form.isMerCheck" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="拼团产品id0一般产品" >
        <el-input v-model="form.combinationId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="拼团id 0没有拼团" >
        <el-input v-model="form.pinkId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="成本价" >
        <el-input v-model="form.cost" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="秒杀产品ID" >
        <el-input v-model="form.seckillId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="砍价id" >
        <el-input v-model="form.bargainId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="核销码" >
        <el-input v-model="form.verifyCode" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="门店id" >
        <el-input v-model="form.storeId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="配送方式 1=快递 ，2=门店自提" >
        <el-input v-model="form.shippingType" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="支付渠道(0微信公众号1微信小程序)" >
        <el-input v-model="form.isChannel" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="isRemind" >
        <el-input v-model="form.isRemind" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="isSystemDel" >
        <el-input v-model="form.isSystemDel" style="width: 370px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreOrder'
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
