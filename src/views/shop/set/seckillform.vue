<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="开启时间(整数小时)">
        <el-input v-model="form.time" style="width: 270px;" />
      </el-form-item>
      <el-form-item label="持续时间(整数小时)">
        <el-input v-model="form.continued" style="width: 270px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <!--<el-input v-model="form.groupName" />-->
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxSystemGroupData'
import picUpload from '@/components/pic-upload'
export default {
  components: { picUpload },
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
        groupName: 'yshop_seckill_time',
        time: 5,
        continued: 2
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
        groupName: 'yshop_seckill_time',
        time: 5,
        continued: 2
      }
    }
  }
}
</script>

<style scoped>

</style>
