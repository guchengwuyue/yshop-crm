<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
     
      <el-form-item label="审核意见" prop="remark">
        <el-input  :rows="4" type="textarea" v-model="formData.remark" placeholder="请输入审核意见" />
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
  remark: undefined,
  checkType: undefined,
})
const formRules = reactive({
  remark: [{ required: true, message: '审核意见不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type == 'agree' ? '同意审核' : '拒绝审核'
  formType.value = type
  resetForm()
  formData.value.id = id
  formData.value.checkType = type
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
    await FlowLogApi.examine(data)
    message.success('操作成功')
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