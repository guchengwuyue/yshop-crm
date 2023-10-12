<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="分销开关">
        <el-radio v-model="form.store_brokerage_open" :label="1">开启</el-radio>
        <el-radio v-model="form.store_brokerage_open" :label="2">关闭</el-radio>
      </el-form-item>
      <el-form-item label="一级返佣比例">
        <el-input v-model="form.store_brokerage_ratio" style="width: 370px;" />
        <p style="color: red">金额乘以百分比,如果商品设置了单独分销，最终金额：固定返佣 + 比例返佣 = 总返佣金额</p>
      </el-form-item>
      <el-form-item label="二级返佣比例">
        <el-input v-model="form.store_brokerage_two" style="width: 370px;" />
        <p style="color: red">金额乘以百分比,如果商品设置了单独分销，最终金额：固定返佣 + 比例返佣 = 总返佣金额<</p>
      </el-form-item>
      <el-form-item label="提现最低金额">
        <el-input v-model="form.user_extract_min_price" style="width: 370px;" />
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
        store_brokerage_open: 1,
        store_brokerage_ratio: 0,
        store_brokerage_two: 0,
        user_extract_min_price: 100
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

      this.form.store_brokerage_open = parseInt(this.form.store_brokerage_open)
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
