<template>
  <ContentWrap>
  <!-- <Dialog :title="dialogTitle" v-model="dialogVisible"> -->
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="180px"
    >
      <el-form-item label="可拥有客户" prop="customerNum">
        <el-input-number v-model="formData.customerNum"  :min="0" :max="9999" placeholder="请输入可拥有客户"  class="!w-340px" />
        <el-alert title="员工可拥有的非成交客户数量，0不限制" type="warning" style="margin-top:2px" />
      </el-form-item>
      <el-form-item label="不跟进自动公海天数" prop="notRecordDay">
        <el-input-number v-model="formData.notRecordDay"  :min="0" :max="10" placeholder="请输入不跟进自动公海天数" class="!w-340px" />
        <el-alert title="多少天不跟进自动掉进公海(已经成交的客户不会丢进公海),0关闭" type="warning" style="margin-top:2px" />
      </el-form-item>
      <el-form-item label="不成交自动公海天数" prop="notSuccessDay">
        <el-input-number  v-model="formData.notSuccessDay" :min="0" :max="10" placeholder="请输入不成交自动公海天数" class="!w-340px" />
        <el-alert title="当拥有客户多少天之后不成交会自动掉进公海(已经成交的客户不会丢进公海).0关闭" type="warning" style="margin-top:2px" />
      </el-form-item>
      <el-form-item label="">
        <el-button @click="submitForm" type="primary">确 定</el-button>
        <el-button @click="resetForm">重 置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- </Dialog> -->
</template>
<script setup lang="ts">
import { CustomerApi } from '@/api/crm/crmcustomer'

/** 企业微信配置 表单 */
defineOptions({ name: 'WorkWxConfigForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  customerNum: 0,
  notRecordDay: 0,
  notSuccessDay: 0,
})
const formRules = reactive({
  customerNum: [{ required: true, message: '可拥有客户不能为空', trigger: 'blur' }],
  notRecordDay: [{ required: true, message: '不跟进自动公海天数不能为空', trigger: 'blur' }],
  notSuccessDay: [{ required: true, message: '不成交自动公海天数不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref



const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as WorkWxConfigVO
    await CustomerApi.saveRedisSet(data)
    message.success('配置成功')
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  formData.value = {
    customerNum: 0,
    notRecordDay: 0,
    notSuccessDay: 0,
  }
  formRef.value?.resetFields()
}

const getConfig = async () => {
  const data = await CustomerApi.getRedisSet()
  if(data.customerNum){
    formData.value = data
  }
}

onMounted(() => {
  getConfig()
})
</script>