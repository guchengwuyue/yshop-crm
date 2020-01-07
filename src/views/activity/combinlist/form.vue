<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="用户id">
        <el-input v-model="form.uid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="订单id 生成">
        <el-input v-model="form.orderId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="订单id  数据库">
        <el-input v-model="form.orderIdKey" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="购买商品个数">
        <el-input v-model="form.totalNum" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="购买总金额">
        <el-input v-model="form.totalPrice" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="拼团产品id">
        <el-input v-model="form.cid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="产品id">
        <el-input v-model="form.pid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="拼图总人数">
        <el-input v-model="form.people" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="拼团产品单价">
        <el-input v-model="form.price" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-input v-model="form.addTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="stopTime">
        <el-input v-model="form.stopTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="团长id 0为团长">
        <el-input v-model="form.kId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否发送模板消息0未发送1已发送">
        <el-input v-model="form.isTpl" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否退款 0未退款 1已退款">
        <el-input v-model="form.isRefund" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="状态1进行中2已完成3未完成">
        <el-input v-model="form.status" style="width: 370px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStorePink'
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
        uid: '',
        orderId: '',
        orderIdKey: '',
        totalNum: '',
        totalPrice: '',
        cid: '',
        pid: '',
        people: '',
        price: '',
        addTime: '',
        stopTime: '',
        kId: '',
        isTpl: '',
        isRefund: '',
        status: ''
      },
      rules: {
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
        uid: '',
        orderId: '',
        orderIdKey: '',
        totalNum: '',
        totalPrice: '',
        cid: '',
        pid: '',
        people: '',
        price: '',
        addTime: '',
        stopTime: '',
        kId: '',
        isTpl: '',
        isRefund: '',
        status: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
