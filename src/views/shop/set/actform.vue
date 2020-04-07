<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="标题">
        <el-input v-model="form.title" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="简介">
        <el-input v-model="form.info" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="跳转url">
        <el-input v-model="form.url" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="图片(260*260/416*214)">
        <pic-upload v-model="form.pic" style="width: 500px;" />
        <MaterialList v-model="form.imageArr" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
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
import MaterialList from '@/components/material'
export default {
  components: { picUpload, MaterialList },
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
        groupName: 'yshop_home_activity',
        title: '',
        info: '',
        url: '',
        pic: '',
        imageArr: [],
        sort: '',
        status: ''
      },
      rules: {
      }
    }
  },
  watch: {
    'form.imageArr': function(val) {
      if (val) {
        this.form.pic = val.join(',')
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
        groupName: 'yshop_home_activity',
        value: '',
        addTime: '',
        sort: '',
        status: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
