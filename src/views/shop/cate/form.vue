<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item v-if="form.pid !== 0" style="margin-bottom: 0px;" label="上级分类">
        <treeselect v-model="form.pid" :options="cates" style="width: 370px;" placeholder="选择上级类目" />
      </el-form-item>
      <el-form-item label="分类名称" >
        <el-input v-model="form.cateName" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="分类图片" >
        <pic-upload v-model="form.pic" style="width: 500px;"/>
      </el-form-item>
      <el-form-item label="排序" >
        <el-input v-model="form.sort" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="状态" prop="enabled">
        <el-radio v-model="form.isShow" :label="1">显示</el-radio>
        <el-radio v-model="form.isShow" :label="0">隐藏</el-radio>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit, getCates } from '@/api/yxStoreCategory'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import picUpload from '@/components/pic-upload'
export default {
  components: { Treeselect, picUpload },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false, cates: [],
      form: {
        id: '',
        pid: 1,
        cateName: '',
        sort: '',
        pic: '',
        isShow: '',
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
        pid: '',
        cateName: '',
        sort: '',
        pic: '',
        isShow: '',
        addTime: ''
      }
    },
    getCates() {
      getCates({ enabled: true }).then(res => {
        this.cates = res.content
      })
    }
  }
}
</script>

<style scoped>

</style>
