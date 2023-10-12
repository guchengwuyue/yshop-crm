<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="140px">
      <el-form-item label="标题">
        <el-input v-model="form.name" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="H5跳转url">
        <el-input v-model="form.url" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="uniapp路由">
        <el-input v-model="form.uniapp_url" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="图片(750*375)">
        <MaterialList v-model="form.imageArr" style="width: 300px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="轮播背景色">
        <el-color-picker v-model="form.color"></el-color-picker>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.sort" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="是否显示">
        <el-radio v-model="form.status" :label="1">是</el-radio>
        <el-radio v-model="form.status" :label="0" style="width: 200px;">否</el-radio>
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
        groupName: 'yshop_home_banner',
        name: '',
        url: '',
        wxapp_url: '',
        uniapp_url: '',
        pic: '',
        imageArr: [],
        sort: 0,
        status: 1,
        color: ''
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
        groupName: 'yshop_home_banner',
        name: '',
        url: '',
        wxapp_url: '',
        uniapp_url: '',
        pic: '',
        imageArr: [],
        sort: 0,
        status: 1,
        color: null
      }
    }
  }
}
</script>

<style scoped>

</style>
