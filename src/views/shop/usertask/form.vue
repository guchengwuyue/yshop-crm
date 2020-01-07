<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="任务名称">
        <el-input v-model="form.levalName" :disabled="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="任务类型">
        <el-input v-model="form.taskType" :disabled="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="任务名称">
        <el-input v-model="form.name" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="限定数">
        <el-input v-model="form.number" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.sort" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否显示">
        <el-radio v-model="form.isShow" :label="1">是</el-radio>
        <el-radio v-model="form.isShow" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="任务说明">
        <el-input v-model="form.illustrate" rows="3" type="textarea" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxSystemUserTask'
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
        name: '',
        realName: '',
        taskType: '',
        number: '',
        levelId: '',
        sort: '',
        isShow: '',
        isMust: '',
        illustrate: '',
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
        name: '',
        realName: '',
        taskType: '',
        number: '',
        levelId: '',
        sort: '',
        isShow: '',
        isMust: '',
        illustrate: '',
        addTime: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
