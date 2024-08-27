<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="50%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="审批流名称" prop="name">
        <el-input v-model="formData.name"  class="!w-240px" placeholder="请输入审批流名称" />
      </el-form-item>
      <el-form-item label="审批类型" prop="types">
        <el-select
          v-model="formData.types"
          placeholder="请选择审批类型"
          clearable
          class="!w-240px"
        >
          <el-option label="合同" value="contract" />
          <el-option label="回款" value="receivables" />
          <el-option label="发票" value="invoice" />
        </el-select>
      </el-form-item>
      <el-form-item label="流程说明" prop="remark">
        <el-input v-model="formData.remark"  class="!w-240px" type="textarea" :rows="2" placeholder="请输入流程说明" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <!-- 子表的表单 -->
    <el-tabs v-model="subTabsName">
      <el-tab-pane label="审批步骤" name="flowStep">
        <FlowStepForm ref="flowStepFormRef" :flow-id="formData.id" />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { FlowApi, FlowVO } from '@/api/crm/crmflow'
import FlowStepForm from './components/FlowStepForm.vue'

/** 审批流程 表单 */
defineOptions({ name: 'FlowForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  types: undefined,
  groupIds: undefined,
  adminIds: undefined,
  remark: undefined,
  status: undefined
})
const formRules = reactive({
  name: [{ required: true, message: '审批流名称不能为空', trigger: 'blur' }],
  types: [{ required: true, message: '请选择审批类型', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 子表的表单 */
const subTabsName = ref('flowStep')
const flowStepFormRef = ref()

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
      formData.value = await FlowApi.getFlow(id)
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
  // 校验子表单
  try {
    await flowStepFormRef.value.validate()
  } catch (e) {
    subTabsName.value = 'flowStep'
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as FlowVO
    // 拼接子表的数据
    data.flowSteps = flowStepFormRef.value.getData()
    if (formType.value === 'create') {
      await FlowApi.createFlow(data)
      message.success(t('common.createSuccess'))
    } else {
      await FlowApi.updateFlow(data)
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
    name: undefined,
    types: 'contract',
    groupIds: undefined,
    adminIds: undefined,
    remark: undefined,
    status: 1
  }
  formRef.value?.resetFields()
}
</script>