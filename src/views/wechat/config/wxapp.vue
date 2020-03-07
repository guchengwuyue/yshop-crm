<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="AppID">
        <el-input v-model="form.wxapp_appId" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="小程序密钥">
        <el-input v-model="form.wxapp_secret" style="width: 370px;" type="password" />
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
        wxapp_appId: '',
        wxapp_secret: ''
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
