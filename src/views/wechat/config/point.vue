<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="积分抵用比例">
        <el-input v-model="form.integral_ratio" style="width: 370px;" />
        <p style="color: red">积分抵用比例(1积分抵多少金额)</p>
      </el-form-item>
      <el-form-item label="满多少可以抵扣">
        <el-input v-model="form.integral_full" style="width: 370px;" />
        <p style="color: red">消费必须满一定额度才可使用,0代表无限制</p>
      </el-form-item>
      <el-form-item label="单次最大抵扣积分">
        <el-input v-model="form.integral_max" style="width: 370px;" />
        <p style="color: red">限制一次只能使用多少积分,0代表无限制</p>
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
        integral_ratio: '',
        integral_full: 0,
        integral_max: 0
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
