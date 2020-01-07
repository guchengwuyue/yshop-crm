<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="600px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="等级名称">
        <el-input v-model="form.name" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="是否永久">
        <el-radio v-model="form.isForever" :label="1">是</el-radio>
        <el-radio v-model="form.isForever" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="有效时间(天)">
        <el-input v-model="form.validDate" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="会员等级">
        <el-input v-model="form.grade" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="享受折扣">
        <el-input v-model="form.discount" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="会员背景">
        <pic-upload v-model="form.image" />
      </el-form-item>
      <el-form-item label="会员图标">
        <pic-upload v-model="form.icon" />
      </el-form-item>
      <el-form-item label="是否显示">
        <el-radio v-model="form.isShow" :label="1">是</el-radio>
        <el-radio v-model="form.isShow" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="说明">
        <el-input v-model="form.explain" rows="3" type="textarea" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxSystemUserLevel'
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
        merId: '',
        name: '',
        money: '',
        validDate: '',
        isForever: '',
        isPay: '',
        isShow: '',
        grade: '',
        discount: '',
        image: '',
        icon: '',
        explain: '',
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
        merId: '',
        name: '',
        money: '',
        validDate: '',
        isForever: '',
        isPay: '',
        isShow: '',
        grade: '',
        discount: '',
        image: '',
        icon: '',
        explain: '',
        addTime: '',
        isDel: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
