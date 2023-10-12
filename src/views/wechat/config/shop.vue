<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="移动端H5地址">
        <el-input v-model="form.site_url" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="uniapp-H5地址">
        <el-input v-model="form.uni_site_url" style="width: 370px;" />
        <span style="color: red">主要用于兼容单独h5</span>
      </el-form-item>
      <el-form-item label="移动端API地址">
        <el-input v-model="form.api_url" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="后台API地址">
        <el-input v-model="form.admin_api_url" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="文件存储方式">
        <el-radio v-model="form.file_store_mode" :label="1">本地存储</el-radio>
        <el-radio v-model="form.file_store_mode" :label="2">云存储</el-radio>
      </el-form-item>
      <el-form-item label="包邮金额">
        <el-input v-model="form.store_free_postage" style="width: 370px;" />
        <p style="color: red">如果设置满包邮0 表示全局包邮，如果设置大于0表示满这价格包邮，否则走运费模板算法</p>
      </el-form-item>
      <el-form-item label="隐藏充值按钮">
        <el-radio v-model="form.yshop_show_recharge" :label="0">显示</el-radio>
        <el-radio v-model="form.yshop_show_recharge" :label="1">隐藏</el-radio>
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" @click="doSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del, add, get } from '@/api/yxSystemConfig'
import eForm from './form'
import picUpload from '@/components/pic-upload'
import { Message } from 'element-ui'
export default {
  components: { eForm, picUpload },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      form: {
        yshop_show_recharge: 1,
        file_store_mode: 2,
        site_url: '',
        api_url: '',
        uni_site_url: '',
        admin_api_url: '',
        store_free_postage: ''
      },
      rules: {
      }
    }
  },
  created() {
    get().then(rese => {
      const that = this;
      rese.content.map(function(key, value) {
        const keyName = key.menuName
        const newValue = key.value
        if(keyName in that.form){
          that.form[keyName] = newValue
        }
      })

      this.form.file_store_mode = parseInt(this.form.file_store_mode)
      this.form.yshop_show_recharge = parseInt(this.form.yshop_show_recharge)
    })
  },
  methods: {
    checkPermission,
    doSubmit() {
      add(this.form).then(res => {
        Message({ message: '设置成功', type: 'success' })
      }).catch(err => {
        // this.loading = false
        console.log(err.response.data.message)
      })
    }

  }
}
</script>

<style scoped>

</style>
