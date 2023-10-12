<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="用户ID">
        <el-input v-model="form.uid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="砍价产品id">
        <el-input v-model="form.bargainId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="砍价的最低价">
        <el-input v-model="form.bargainPriceMin" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="砍价金额">
        <el-input v-model="form.bargainPrice" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="砍掉的价格">
        <el-input v-model="form.price" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="状态 1参与中 2 活动结束参与失败 3活动结束参与成功">
        <el-input v-model="form.status" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="参与时间">
        <el-input v-model="form.addTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否取消">
        <el-input v-model="form.isDel" style="width: 370px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreBargainUser'
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
        bargainId: '',
        bargainPriceMin: '',
        bargainPrice: '',
        price: '',
        status: '',
        addTime: '',
        isDel: ''
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
        bargainId: '',
        bargainPriceMin: '',
        bargainPrice: '',
        price: '',
        status: '',
        addTime: '',
        isDel: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
