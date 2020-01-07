<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="字段名称">
        <el-input v-model="form.menuName" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="类型(文本框,单选按钮...)">
        <el-input v-model="form.type" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="表单类型">
        <el-input v-model="form.inputType" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="配置分类id">
        <el-input v-model="form.configTabId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="规则 单选框和多选框">
        <el-input v-model="form.parameter" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="上传文件格式1单图2多图3文件">
        <el-input v-model="form.uploadType" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="规则">
        <el-input v-model="form.required" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="多行文本框的宽度">
        <el-input v-model="form.width" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="多行文框的高度">
        <el-input v-model="form.high" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="默认值">
        <el-input v-model="form.value" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="配置名称">
        <el-input v-model="form.info" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="配置简介">
        <el-input v-model="form.desc" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.sort" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否隐藏">
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
import { add, edit } from '@/api/yxSystemConfig'
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
        menuName: '',
        type: '',
        inputType: '',
        configTabId: '',
        parameter: '',
        uploadType: '',
        required: '',
        width: '',
        high: '',
        value: '',
        info: '',
        desc: '',
        sort: '',
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
        menuName: '',
        type: '',
        inputType: '',
        configTabId: '',
        parameter: '',
        uploadType: '',
        required: '',
        width: '',
        high: '',
        value: '',
        info: '',
        desc: '',
        sort: '',
        status: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
