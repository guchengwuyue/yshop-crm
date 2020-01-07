<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="关键字" prop="key">
        <el-input v-model="form.key" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="回复类型">
        <el-input v-model="form.type" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="回复数据">
        <el-input v-model="form.data" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="0=不可用  1 =可用">
        <el-input v-model="form.status" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否隐藏">
        <el-input v-model="form.hide" style="width: 370px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxWechatReply'
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
        key: '',
        type: '',
        data: '',
        status: '',
        hide: ''
      },
      rules: {
        key: [
          { required: true, message: 'please enter', trigger: 'blur' }
        ]
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
        key: '',
        type: '',
        data: '',
        status: '',
        hide: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
