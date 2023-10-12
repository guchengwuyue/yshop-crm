<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="开启短信">
        <el-radio v-model="form.sms_enable" :label="1">开启</el-radio>
        <el-radio v-model="form.sms_enable" :label="2">关闭</el-radio>
      </el-form-item>
      <el-form-item label="签名">
        <el-input v-model="form.sms_sign" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="模板id">
        <el-input v-model="form.sms_templateId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="region">
        <el-input v-model="form.sms_region" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="accessKey">
        <el-input v-model="form.sms_access_key" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="accessKeySecret">
        <el-input v-model="form.sms_access_secret" style="width: 370px;" type="password" />
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
        sms_enable: 2,
        sms_sign: '',
        sms_templateId: '',
        sms_region: '',
        sms_access_key: '',
        sms_access_secret: ''
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

      this.form.sms_enable = parseInt(this.form.sms_enable)
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
