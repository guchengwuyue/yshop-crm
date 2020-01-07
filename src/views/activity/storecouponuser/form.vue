<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="兑换的项目id">
        <el-input v-model="form.cid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="优惠券所属用户">
        <el-input v-model="form.uid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="优惠券名称">
        <el-input v-model="form.couponTitle" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="优惠券的面值">
        <el-input v-model="form.couponPrice" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="最低消费多少金额可用优惠券">
        <el-input v-model="form.useMinPrice" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="优惠券创建时间">
        <el-input v-model="form.addTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="优惠券结束时间">
        <el-input v-model="form.endTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="使用时间">
        <el-input v-model="form.useTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="获取方式">
        <el-input v-model="form.type" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="状态（0：未使用，1：已使用, 2:已过期）">
        <el-input v-model="form.status" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否有效">
        <el-input v-model="form.isFail" style="width: 370px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreCouponUser'
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
        cid: '',
        uid: '',
        couponTitle: '',
        couponPrice: '',
        useMinPrice: '',
        addTime: '',
        endTime: '',
        useTime: '',
        type: '',
        status: '',
        isFail: ''
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
        cid: '',
        uid: '',
        couponTitle: '',
        couponPrice: '',
        useMinPrice: '',
        addTime: '',
        endTime: '',
        useTime: '',
        type: '',
        status: '',
        isFail: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
