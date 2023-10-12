<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '发布优惠券'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="优惠券ID">
        <el-input v-model="form.cid" style="width: 300px;" :disabled="true" />
        <el-input v-model="form.ctype" type="hidden" />
      </el-form-item>
      <el-form-item label="优惠券名称">
        <el-input v-model="form.cname" style="width: 300px;" :disabled="true" />
      </el-form-item>
      <el-form-item label="领取开启时间">
        <template>
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="券领结束时间">
        <template>
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="发布数量">
        <el-input v-model="form.totalCount" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="是否不限量">
        <el-radio v-model="form.isPermanent" :label="1">不限量</el-radio>
        <el-radio v-model="form.isPermanent" :label="0">限量</el-radio>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio v-model="form.status" :label="1">开启</el-radio>
        <el-radio v-model="form.status" :label="0">关闭</el-radio>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreCouponIssue'
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
        cname: '',
        ctype: 0,
        startTimeDate: '',
        endTimeDate: '',
        totalCount: 0,
        remainCount: 0,
        isPermanent: 0,
        status: 1,
        isDel: 0,
        addTime: ''
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
        startTime: '',
        endTime: '',
        totalCount: '',
        remainCount: '',
        isPermanent: '',
        status: '',
        isDel: '',
        addTime: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
