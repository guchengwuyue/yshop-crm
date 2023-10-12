<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="开启">
        <el-radio v-model="form.exp_enable" :label="1">开启</el-radio>
        <el-radio v-model="form.exp_enable" :label="2">关闭</el-radio>
      </el-form-item>
      <el-form-item label="appId">
        <el-input v-model="form.exp_appId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="appKey">
        <el-input v-model="form.exp_appKey" style="width: 370px;" type="password" />
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" @click="doSubmit">提交</el-button>
      </el-form-item>
      <el-form-item label="快递鸟申请地址">
         yshopb2c系统使用的第三方快递鸟api实现了快递查询，注册地址请点击，
        <span style="color: blue; cursor: pointer;">
          <a href="http://www.kdniao.com/reg?from=cbb-yx" target="_blank" style="color: inherit; text-decoration: underline;">
            我注册！
          </a>
        </span>
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
        exp_enable: 2,
        exp_appId: '',
        exp_appKey: ''
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

      this.form.exp_enable = parseInt(this.form.exp_enable)
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
