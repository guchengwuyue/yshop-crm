<template>
  <el-dialog
  class="afterSealsAdd"
  :close-on-click-modal="false"
  :visible.sync="visible"
  width="800px">
    <div class="checkForm">
      <span>订单详情：</span>
      <el-form ref="form" :model="checkForm" size="small" label-width="100px">
        <el-form-item label="订单号：">
          <el-input v-model="checkForm.orderCode" disabled />
        </el-form-item>
        <el-form-item label="售后类型：">
          <el-input v-if="checkForm.serviceType === 0" :value="'仅退款'" disabled />
          <el-input v-if="checkForm.serviceType === 1" :value="'退货退款'" disabled />
        </el-form-item>
        <el-form-item label="售后原因：">
          <el-input v-model="checkForm.reasons" disabled />
        </el-form-item>
        <el-form-item label="售后说明：">
          <el-input v-model="checkForm.explains" disabled />
        </el-form-item>
        <el-form-item label="原因图片：">
          <div v-if="!checkForm.explainImg">
            <b>用户未上传图片</b>
          </div>
          <div v-else>
            <el-image
              v-for="(item,index) in checkForm.explainImg.split(',')"
              :key="index"
              style="width: 100px; height: 100px"
              :src="item"
              :preview-src-list="[item]">
            </el-image>
          </div>
        </el-form-item>
        <el-form-item label="提交时间：">
          <el-input v-model="checkForm.createTime" disabled />
        </el-form-item>
        <div v-for="item in checkForm.cartInfo"
        :key="item.id">
          <el-form-item label="售后商品：">
            <el-input v-model="item.productInfo.storeName" disabled />
          </el-form-item>
          <el-form-item label="商品图片：">
            <el-image
            :src="item.productInfo.image"
            style="width: 100px; height: 100px">
              <div slot="placeholder" class="image-slot">
                加载中<b class="dot">...</b>
              </div>
            </el-image>
          </el-form-item>
          <el-form-item label="规格：">
            <el-input v-model="item.productInfo.attrInfo.sku" disabled />
          </el-form-item>
          <el-form-item label="商品单价">
            <el-input v-model="item.truePrice" disabled />
          </el-form-item>
          <el-form-item label="商品数量">
            <el-input v-model="item.cartNum" disabled />
          </el-form-item>
          <el-form-item label="邮费：">
            <el-input v-model="item.productInfo.postage" disabled />
          </el-form-item>
          <el-form-item v-if="checkForm.deliveryName" label="快递公司：">
            <el-input v-model="checkForm.deliveryName" disabled />
          </el-form-item>
          <el-form-item v-if="checkForm.deliverySn" label="快递单号：">
            <el-input v-model="checkForm.deliverySn" disabled />
          </el-form-item>
          <el-form-item v-if="checkForm.deliveryTime" label="发货时间：">
            <el-input v-model="checkForm.deliveryTime" disabled />
          </el-form-item>
          <el-form-item v-if="checkForm.deliverySn" label="订单跟踪：">
            <el-button size="mini" @click="showLogisticsDialog">订单跟踪</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
    <div v-if="serviceType === 0 && isShow">
      <span>该订单为仅退款订单，审核通过之后将直接退款，是否审核通过？</span>
    </div>
    <div v-if="serviceType === 1 && isShow">
      <span>该订单为退货退款，请输入退货地址：</span>
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="收货人" prop="consignee">
          <el-input v-model="form.consignee" />
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" />
        </el-form-item>
      </el-form>
    </div>
    <el-dialog title="订单跟踪"
               :visible.sync="kuaidiDialogVisible"
               width="40%">
      <el-steps direction="vertical"
                :active="90"
                finish-status="success"
                space="50px">
        <el-step  v-for="item in logisticsList"
                  :key="item.AcceptStation"
                  :title="item.AcceptStation"
                  :description="item.AcceptTime"></el-step>
      </el-steps>
    </el-dialog>
    <div slot="footer" class="dialog-footer">
      <el-button class="refuse" type="danger" v-if="isShow" :loading="loading" @click="submit(1)">拒绝</el-button>
      <el-button class="check" type="primary" v-if="isShow" :loading="loading" @click="submit(0)">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {salesCheck} from '@/api/yxStoreAfterSales.js'
import { express } from '@/api/yxStoreOrder'
export default {
  props: {
  },
  data () {
    return {
      visible: false,
      isShow: false,
      loading: false,
      kuaidiDialogVisible: false,
      serviceType: '',
      logisticsList:[],
      checkForm: {},
      form: {
        salesId: '', // 售后id
        orderCode: '', // 订单编号
        approvalStatus: '', // 审核状态0成功1失败
        consignee: '', // 收货人
        phoneNumber: '', // 手机号
        address: '' // 地址
      },
      rules: {
        consignee: [{ required: true, message: '请输入收货人', trigger: 'blur' }],
        phoneNumber: [{ required: true, message: '请输入收货人手机号', trigger: 'blur' }],
        address: [{ required: true, message: '请输入收货地址', trigger: 'blur' }]
      },
    }
  },
  methods: {
    cancel() {
      this.visible = false
      this.$refs['form'].resetFields()
    },
    showLogisticsDialog(){
      this.express();
    },
    express() {
      let params ={
        "orderCode": this.checkForm.id,
        "shipperCode": this.checkForm.deliverySn,
        "logisticCode": this.checkForm.deliveryId
      }

      express(params).then(res=>{
        this.expressInfo = res.Traces
        this.kuaidiDialogVisible = true;
        this.logisticsList = this.expressInfo
      }).catch(err => {
      })

    },
    async submit(type) {
      this.loading = true
      if (this.serviceType === 0) {
        this.form.consignee = ''
        this.form.phoneNumber = ''
        this.form.address = ''
      }
      this.form.approvalStatus = type // 0成功 1失败
      var res = await salesCheck(this.form)
      if (res) {
        this.$message.success('审核成功')
        this.visible = false
        this.$emit('checkSuccess')
      } else {
        this.$message.error(res.message || '审核失败！')
      }
      this.loading = false
    }
  }
}
</script>

<style lang="scss" scoped>
.afterSealsAdd{
  padding-bottom: 10vh;
  span{
    color: #F56C6C;
    line-height: 40px;
    font-size: 18px;
    font-weight: bold;
    margin: 25px 0;
  }
}
.afterSealsAdd ::v-deep.el-input.is-disabled .el-input__inner{
  color: #333333;
}
.afterSealsAdd ::v-deep .dialog-footer{
  display: flex;
  justify-content: space-around;
  .el-button{
    width: 120px;
    height: 40px;
  }
  .refuse{}
  .check{}
}
</style>
