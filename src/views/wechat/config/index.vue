<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="AppID">
        <el-input v-model="form.wechat_appid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="AppSecret">
        <el-input v-model="form.wechat_appsecret" style="width: 370px;" type="password" />
      </el-form-item>
      <el-form-item label="微信验证TOKEN">
        <el-input v-model="form.wechat_token" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="EncodingAESKey">
        <el-input v-model="form.wechat_encodingaeskey" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="微信服务器地址">
        <el-input v-model="form.api" :disabled="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="微信分享图片">
        <MaterialList v-model="form.imageArr" style="width: 370px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="微信分享标题">
        <el-input v-model="form.wechat_share_title" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="微信分享简介">
        <el-input v-model="form.wechat_share_synopsis" style="width: 370px;" rows="5" type="textarea" />
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
import MaterialList from '@/components/material'
export default {
  components: { eForm, picUpload, MaterialList },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      form: {
        wechat_name: '',
        wechat_id: '',
        wechat_sourceid: '',
        wechat_appid: '',
        wechat_appsecret: '',
        wechat_token: '',
        wechat_encode: '',
        wechat_encodingaeskey: '',
        wechat_share_img: '',
        imageArr: [],
        wechat_qrcode: '',
        wechat_type: '',
        wechat_share_title: '',
        wechat_share_synopsis: '',
        api: 'http://你的H5端域名/api/wechat/serve',
        wechat_avatar: ''
      },
      rules: {
      }
    }
  },
  watch: {
    'form.imageArr': function(val) {
      if (val) {
        this.form.wechat_share_img = val.join(',')
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

      this.form.imageArr = this.form.wechat_share_img.split(',')
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
