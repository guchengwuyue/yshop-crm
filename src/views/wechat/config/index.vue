<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <!--<el-form-item label="公众号名称" >-->
        <!--<el-input v-model="form.wechat_name" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="微信号" >-->
        <!--<el-input v-model="form.wechat_id" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="公众号原始id" >-->
        <!--<el-input v-model="form.wechat_sourceid" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="AppID" >-->
        <!--<el-input v-model="form.wechat_appid" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="AppSecret" >-->
        <!--<el-input v-model="form.wechat_appsecret" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="微信验证TOKEN" >-->
        <!--<el-input v-model="form.wechat_token" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="消息加解密方式" >-->
        <!--<el-input v-model="form.wechat_encode" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="EncodingAESKey" >-->
        <!--<el-input v-model="form.wechat_encodingaeskey" style="width: 370px;"/>-->
      <!--</el-form-item>-->
      <el-form-item label="微信分享图片" >
        <pic-upload v-model="form.wechat_share_img" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="公众号二维码" >
        <pic-upload v-model="form.wechat_qrcode" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="公众号类型" >
        <el-input v-model="form.wechat_type" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="微信分享标题" >
        <el-input v-model="form.wechat_share_title" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="微信分享简介" >
        <el-input v-model="form.wechat_share_synopsis" style="width: 370px;" rows="5" type="textarea"/>
      </el-form-item>
      <el-form-item label="接口地址" >
        <el-input v-model="form.api" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="公众号logo" >
        <pic-upload v-model="form.wechat_avatar" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="" >
        <el-button :loading="loading" type="primary" @click="doSubmit">提交</el-button>
      </el-form-item>

    </el-form>


  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/initData'
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
        wechat_name: '',
        wechat_id: '',
        wechat_sourceid: '',
        wechat_appid: '',
        wechat_appsecret: '',
        wechat_token: '',
        wechat_encode: '',
        wechat_encodingaeskey: '',
        wechat_share_img: '',
        wechat_qrcode: '',
        wechat_type: '',
        wechat_share_title: '',
        wechat_share_synopsis: '',
        api: '',
        wechat_avatar: ''
      },
      rules: {
      }
    }
  },
  created() {
    get().then(rese=>{
      let newObj = {}
      rese.content.map(function (key,value) {
        let keyName = key.menuName;
        newObj[keyName] = key.value
      })

      this.form = newObj
    })
  },
  methods: {
    checkPermission,
    doSubmit() {
      add(this.form).then(res => {
        Message({message: '设置成功',type: 'success'})
      }).catch(err => {
        //this.loading = false
        console.log(err.response.data.message)
      })
    },

  }
}
</script>

<style scoped>

</style>
