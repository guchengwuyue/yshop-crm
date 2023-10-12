<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="form.realName" disabled style="width: 370px;" />
      </el-form-item>
      <el-form-item label="提现金额">
        <el-input-number v-model="form.extractPrice" disabled style="width: 370px;" />
      </el-form-item>
      <el-form-item label="审核状态">
        <el-radio v-model="form.status" :label="-1">无效</el-radio>
        <el-radio v-model="form.status" :label="1">通过</el-radio>
      </el-form-item>
      <el-form-item label="无效原因">
        <el-input v-model="form.failMsg" style="width: 300px;" rows="5" type="textarea" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxUserExtract'
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
        realName: '',
        extractType: '',
        bankCode: '',
        bankAddress: '',
        alipayCode: '',
        extractPrice: '',
        mark: '',
        balance: '',
        failMsg: '',
        failTime: '',
        addTime: '',
        status: '',
        wechat: ''
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
        realName: '',
        extractType: '',
        bankCode: '',
        bankAddress: '',
        alipayCode: '',
        extractPrice: '',
        mark: '',
        balance: '',
        failMsg: '',
        failTime: '',
        addTime: '',
        status: '',
        wechat: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
