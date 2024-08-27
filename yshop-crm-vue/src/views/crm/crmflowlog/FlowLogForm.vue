<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="审批流程ID" prop="flowId">
        <el-input v-model="formData.flowId" placeholder="请输入审批流程ID" />
      </el-form-item>
      <el-form-item label="对象ID（如审批类型ID）" prop="typesId">
        <el-input v-model="formData.typesId" placeholder="请输入对象ID（如审批类型ID）" />
      </el-form-item>
      <el-form-item label="审核人" prop="adminId">
        <el-input v-model="formData.adminId" placeholder="请输入审核人" />
      </el-form-item>
      <el-form-item label="关联对象 contract-合同 receivables-回款 invoice-发票" prop="types">
        <el-input v-model="formData.types" placeholder="请输入关联对象 contract-合同 receivables-回款 invoice-发票" />
      </el-form-item>
      <el-form-item label="审核人名称" prop="nickname">
        <el-input v-model="formData.nickname" placeholder="请输入审核人名称" />
      </el-form-item>
      <el-form-item label="审核意见" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入审核意见" />
      </el-form-item>
      <el-form-item label="1审核结束" prop="isEnd">
        <el-input v-model="formData.isEnd" placeholder="请输入1审核结束" />
      </el-form-item>
      <el-form-item label="0审核未通过,1审核通过" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio label="1">请选择字典生成</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { FlowLogApi, FlowLogVO } from '@/api/crm/crmflowlog'

/** 审核日志 表单 */
defineOptions({ name: 'FlowLogForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  flowId: undefined,
  typesId: undefined,
  adminId: undefined,
  types: undefined,
  nickname: undefined,
  remark: undefined,
  isEnd: undefined,
  status: undefined
})
const formRules = reactive({
  flowId: [{ required: true, message: '审批流程ID不能为空', trigger: 'blur' }],
  typesId: [{ required: true, message: '对象ID（如审批类型ID）不能为空', trigger: 'blur' }],
  adminId: [{ required: true, message: '审核人不能为空', trigger: 'blur' }],
  types: [{ required: true, message: '关联对象 contract-合同 receivables-回款 invoice-发票不能为空', trigger: 'blur' }],
  isEnd: [{ required: true, message: '1审核结束不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '0审核未通过,1审核通过不能为空', trigger: 'blur' }]
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
      formData.value = await FlowLogApi.getFlowLog(id)
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
    const data = formData.value as unknown as FlowLogVO
    if (formType.value === 'create') {
      await FlowLogApi.createFlowLog(data)
      message.success(t('common.createSuccess'))
    } else {
      await FlowLogApi.updateFlowLog(data)
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
    flowId: undefined,
    typesId: undefined,
    adminId: undefined,
    types: undefined,
    nickname: undefined,
    remark: undefined,
    isEnd: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>