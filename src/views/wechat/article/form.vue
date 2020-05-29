<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="900px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="标题">
        <el-input v-model="form.title" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="form.author" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="封面">
        <MaterialList v-model="form.imageArr" style="width: 370px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="简介">
        <el-input v-model="form.synopsis" style="width: 370px;" rows="5" type="textarea" />
      </el-form-item>
      <el-form-item label="正文">
        <editor v-model="form.content" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxArticle'
import picUpload from '@/components/pic-upload'
import editor from '../../components/Editor'
import yamedit from '@/components/YamlEdit'
import MaterialList from '@/components/material'
export default {
  components: { editor, picUpload, yamedit, MaterialList },
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
        title: '',
        author: '',
        imageInput: '',
        imageArr: [],
        synopsis: '',
        content: '',
        shareTitle: '',
        shareSynopsis: '',
        visit: '',
        sort: '',
        url: '',
        status: '',
        addTime: '',
        hide: '',
        merId: '',
        productId: '',
        isHot: '',
        isBanner: ''
      },
      rules: {
      }
    }
  },
  watch: {
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if(this.form.imageArr.length > 0){
        this.form.imageInput = this.form.imageArr.join(',')
      }else{
        this.form.imageInput = ''
      }

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
        title: '',
        author: '',
        imageInput: '',
        synopsis: '',
        shareTitle: '',
        shareSynopsis: '',
        visit: '',
        sort: '',
        url: '',
        status: '',
        addTime: '',
        hide: '',
        adminId: '',
        merId: '',
        productId: '',
        isHot: '',
        isBanner: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
