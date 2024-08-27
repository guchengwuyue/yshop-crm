<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
     <el-form-item label="消息类型" prop="type">
        <el-select v-model="formData.type" placeholder="请选择类型">
          <el-option label="请选消息类型" value="" />
          <el-option label="模板消息" value="template" />
          <el-option label="订阅消息" value="subscribe" />
        </el-select>
      </el-form-item>
      <el-form-item label="模板编号" prop="tempkey">
        <el-input v-model="formData.tempkey" placeholder="请输入模板编号" />
      </el-form-item>
      <el-form-item label="模板名" prop="name">
        <el-input v-model="formData.name" placeholder="请输入模板名" />
      </el-form-item>
      <el-form-item label="模板ID" prop="tempid">
        <el-input v-model="formData.tempid" placeholder="请输入模板ID" />
      </el-form-item>
    
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :label="1">开启</el-radio>
          <el-radio :label="0">关闭</el-radio>
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
import * as WechatTemplateApi from '@/api/message/wechatTemplate'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  tempkey: undefined,
  name: undefined,
  content: undefined,
  tempid: undefined,
  status: undefined,
  type: undefined
})
const formRules = reactive({
  tempkey: [{ required: true, message: '模板编号不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '模板名不能为空', trigger: 'blur' }],
  tempid: [{ required: true, message: '模板ID不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '消息类型不能为空', trigger: 'blur' }]
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
      formData.value = await WechatTemplateApi.getWechatTemplate(id)
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
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as WechatTemplateApi.WechatTemplateVO
    if (formType.value === 'create') {
      await WechatTemplateApi.createWechatTemplate(data)
      message.success(t('common.createSuccess'))
    } else {
      await WechatTemplateApi.updateWechatTemplate(data)
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
    tempkey: undefined,
    name: undefined,
    content: undefined,
    tempid: undefined,
    status: 1,
    type: undefined
  }
  formRef.value?.resetFields()
}
</script>
