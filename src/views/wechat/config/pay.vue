<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="微信APP支付AppID">
        <el-input v-model="form.wx_native_app_appId" style="width: 370px;" />
        <p style="color: red">微信开放平台审核通过的应用APPID（请登录open.weixin.qq.com查看，注意与公众号的APPID不同）</p>
      </el-form-item>
      <el-form-item label="商户id">
        <el-input v-model="form.wxpay_mchId" style="width: 370px;" type="password" />
      </el-form-item>
      <el-form-item label="商户密钥">
        <el-input v-model="form.wxpay_mchKey" style="width: 370px;" type="password" />
      </el-form-item>
      <el-form-item label="微信证书">
        <file-upload v-model="form.wxpay_keyPath" style="width: 500px;" />
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
import fileUpload from '@/components/file-upload'
export default {
  components: { eForm, picUpload, fileUpload },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      form: {
        wx_native_app_appId: '',
        wxpay_mchId: '',
        wxpay_mchKey: '',
        wxpay_keyPath: ''
      },
      rules: {
      }
    }
  },
  created() {
    get().then(rese => {
      const that = this
      rese.content.map(function(key, value) {
        const keyName = key.menuName
        const newValue = key.value
        if(keyName in that.form){
          that.form[keyName] = newValue
        }
      })
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
