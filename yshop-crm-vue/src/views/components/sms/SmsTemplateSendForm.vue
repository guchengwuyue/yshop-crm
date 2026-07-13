<template>
  <Dialog v-model="dialogVisible" title="发送短信">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="140px"
    >
    <el-form-item label="发送客户" prop="customerNames">
        <el-input v-model="formData.customerNames" disabled placeholder="请输入客户名称" />
    </el-form-item>
     <el-form-item label="选择模板" prop="smsTemplate">
        <el-select v-model="formData.smsTemplate" value-key="id" @change="changeTemplate" placeholder="选择模板" >
          <el-option
            v-for="item in templateList"
            :key="item.id"
            :label="item.name"
            :value="item"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模板内容" prop="content">
        <el-input
          v-model="formData.content"
          placeholder="请输入模板内容"
          readonly
          type="textarea"
        />
      </el-form-item>
      <el-form-item
        v-for="param in formData.params"
        :key="param"
        :label="'参数 {' + param + '}'"
        :prop="'templateParams.' + param"
      >
        <el-input
          v-model="formData.templateParams[param]"
          :placeholder="'请输入 ' + param + ' 参数'"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as SmsTemplateApi from '@/api/system/sms/smsTemplate'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'

defineOptions({ name: 'SystemSmsTemplateSendForm' })

const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const templateList = ref([])

// 发送短信表单相关
const formData = ref({
  customerNames: undefined,
  customerIds: undefined,
  content: '',
  params: {},
  smsTemplate: '',
  templateCode: '',
  templateParams: new Map(),
  isCustomer: true
})
const formRules = reactive({
  smsTemplate: [{ required: true, message: '清选择模板', trigger: 'blur' }],
  templateCode: [{ required: true, message: '模版编码不能为空', trigger: 'blur' }],
  templateParams: {}
})
const formRef = ref() // 表单 Ref

const open = async (customers,type) => {
  dialogVisible.value = true
  resetForm()
  // 设置数据
  formLoading.value = true
  formData.value.isCustomer = type

  let namesArr = []
  let idsArr = []
  customers.forEach(value =>  {
    namesArr.push(value.name)
    idsArr.push(value.id)
  })
  formData.value.customerNames = namesArr.toString()
  formData.value.customerIds = idsArr
  try {
    const data2 = await SmsTemplateApi.getSmsTemplatePage({status:0})
    templateList.value = data2.list

  
  } finally {
    formLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    await CustomerApi.sendSms(data)
    
    message.success('提交发送成功！发送结果，见发送日志')
 
    dialogVisible.value = false
  } finally {
    formLoading.value = false
  }
}

const changeTemplate = (data) => {
  console.log('val:',data)
     // 设置动态表单
    formData.value.content = data.content
    formData.value.params = data.params
    formData.value.templateCode = data.code
    formData.value.templateParams = data.params.reduce((obj, item) => {
      obj[item] = '' // 给每个动态属性赋值，避免无法读取
      return obj
    }, {})
    formRules.templateParams = data.params.reduce((obj, item) => {
      obj[item] = { required: true, message: '参数 ' + item + ' 不能为空', trigger: 'blur' }
      return obj
    }, {})
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    customerNames: undefined,
    customerIds: undefined,
    content: '',
    params: {},
    mobile: '',
    templateCode: '',
    templateParams: new Map()
  }
  formRef.value?.resetFields()
}
</script>
