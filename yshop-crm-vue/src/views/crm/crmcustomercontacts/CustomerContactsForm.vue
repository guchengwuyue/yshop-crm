<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="客户" prop="customerName">
        <el-input v-model="formData.customerName" disabled placeholder="请输入客户ID" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="formData.name" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input v-model="formData.mobile" placeholder="请输入手机" />
      </el-form-item>
      <el-form-item label="电话" prop="telephone">
        <el-input v-model="formData.telephone" placeholder="请输入电话" />
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker
          v-model="formData.birthday"
          type="date"
          value-format="x"
          placeholder="选择下次联系时间"
        />
      </el-form-item>
      <el-form-item label="微信号" prop="wechat">
        <el-input v-model="formData.wechat" placeholder="请输入微信号" />
      </el-form-item>
      <el-form-item label="电子邮箱" prop="email">
        <el-input v-model="formData.email" placeholder="请输入电子邮箱" />
      </el-form-item>
      <el-form-item label="职务" prop="post">
        <el-input v-model="formData.post" placeholder="请输入职务" />
      </el-form-item>
      <el-form-item label="关键决策人" prop="decision">
        <el-radio-group v-model="formData.decision">
          <el-radio :value="-1" >未知</el-radio>
          <el-radio :value="1" >是</el-radio>
          <el-radio :value="0" >不是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="formData.sex">
          <el-radio :value="-1" >未知</el-radio>
          <el-radio :value="1" >男</el-radio>
          <el-radio :value="0" >女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="地址" prop="detailAddress">
        <el-input v-model="formData.detailAddress" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="下次联系时间" prop="nextTime">
        <el-date-picker
          v-model="formData.nextTime"
          type="datetime"
          value-format="x"
          placeholder="选择下次联系时间"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { CustomerContactsApi, CustomerContactsVO } from '@/api/crm/crmcustomercontacts'

/** 联系人 表单 */
defineOptions({ name: 'CustomerContactsForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  customerId: undefined,
  customerName:undefined,
  name: undefined,
  mobile: undefined,
  birthday: undefined,
  telephone: undefined,
  wechat: undefined,
  email: undefined,
  decision: undefined,
  post: undefined,
  sex: undefined,
  detailAddress: undefined,
  remark: undefined,
  nextTime: undefined
})
const formRules = reactive({
  customerId: [{ required: true, message: '客户ID不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await CustomerContactsApi.getCustomerContacts(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as CustomerContactsVO
    if (formType.value === 'create') {
      await CustomerContactsApi.createCustomerContacts(data)
      message.success(t('common.createSuccess'))
    } else {
      await CustomerContactsApi.updateCustomerContacts(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    customerId: undefined,
    name: undefined,
    mobile: undefined,
    birthday: undefined,
    telephone: undefined,
    wechat: undefined,
    email: undefined,
    decision: undefined,
    post: undefined,
    sex: undefined,
    detailAddress: undefined,
    remark: undefined,
    nextTime: undefined
  }
  formRef.value?.resetFields()
}
</script>