<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="邮费基础价">
        <el-input v-model="form.store_postage" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="满额包邮">
        <el-input v-model="form.store_free_postage" style="width: 370px;" />
        <p style="color: red">0表示包邮,大于0表示满多少包邮</p>
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
        integral_ratio: ''
      },
      rules: {
      }
    }
  },
  created() {
    get().then(rese => {
      const newObj = {}
      rese.content.map(function(key, value) {
        const keyName = key.menuName
        newObj[keyName] = key.value
      })

      this.form = newObj
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
