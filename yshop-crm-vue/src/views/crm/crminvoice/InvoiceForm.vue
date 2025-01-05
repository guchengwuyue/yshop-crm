<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="55%">
    <el-alert title="审核通过，不可修改数据" type="success" v-if="formData.checkStatus == 2" style="margin-bottom:20px" />
    <el-alert title="审核中，不可修改数据" type="warning" v-else-if="formData.checkStatus == 1" style="margin-bottom:20px" />
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="130px"
      v-loading="formLoading"
    >
    <el-row>
      <el-col :span="12">
        <el-form-item label="合同" prop="contractId">
          <el-input v-model="formData.contractName" placeholder="请输入选择合同">
            <template #append><span  @click="chooseContract()" style="cursor:pointer;color:#F56C6C">选择合同</span></template>
          </el-input>
          <el-input v-model="formData.contractId" type="hidden" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开票主体" prop="invoiceBody">
          <el-input v-model="formData.invoiceBody" placeholder="请输入开票主体" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="发票抬头" prop="invoiceName">
          <el-input v-model="formData.invoiceName" placeholder="请输入发票抬头" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="抬头类型" prop="invoiceIssue">
          <el-radio-group v-model="formData.invoiceIssue">
            <el-radio :value="1">企业</el-radio>
            <el-radio :value="2">个人</el-radio>
            <el-radio :value="3">事业单位</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="发票类型" prop="invoiceType">
          <el-radio-group v-model="formData.invoiceType">
            <el-radio :value="1">增值税普票</el-radio>
            <el-radio :value="2">增值税专票</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="统一社会信用代码" prop="registerNo">
          <el-input v-model="formData.registerNo" placeholder="请输入统一社会信用代码" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="开户行名称" prop="bankName">
          <el-input v-model="formData.bankName" placeholder="请输入开户行名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开户账号" prop="bankNo">
          <el-input v-model="formData.bankNo" placeholder="请输入开户账号" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="开票金额" prop="money">
          <el-input type="number" v-model="formData.money" placeholder="请输入发票税前金额" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开票税率" prop="taxRate">
          <el-input type="number" v-model="formData.taxRate" placeholder="请输入税率" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="开票内容" prop="content">
          <el-input v-model="formData.content" placeholder="请输入开票内容" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="接收邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入接收发票邮箱" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="收件人" prop="userName">
          <el-input v-model="formData.userName" placeholder="请输入收件人" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="收件手机" prop="userPhone">
          <el-input v-model="formData.userPhone" placeholder="请输入收件手机" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="邮寄地址" prop="userAddress">
          <el-input v-model="formData.userAddress" placeholder="请输入邮寄地址" />
        </el-form-item>
      </el-col>
      <!-- <el-form-item label="开票附件" prop="files">
        <el-input v-model="formData.files" placeholder="请输入开票附件" />
      </el-form-item> -->
      <el-col :span="12">
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="formData.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="审批人" prop="flowAdminId">
          <el-select v-model="formData.flowAdminId"  multiple disabled placeholder="请去审批流程里配置合同审批">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.nickname"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading2">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
  <select-contract ref="selectContractRef" @save-contract="saveContract"/>
</template>
<script setup lang="ts">
import { InvoiceApi, InvoiceVO } from '@/api/crm/crminvoice'
import SelectContract from "@/views/components/contract/index.vue"
import * as UserApi from '@/api/system/user'
import { FlowApi } from '@/api/crm/crmflow'

/** 发票 表单 */
defineOptions({ name: 'InvoiceForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  contractId: undefined,
  customerId: undefined,
  invoiceBody: undefined,
  content: undefined,
  invoiceName: undefined,
  invoiceIssue: undefined,
  invoiceType: undefined,
  registerNo: undefined,
  bankName: undefined,
  bankNo: undefined,
  money: undefined,
  taxRate: undefined,
  remarks: undefined,
  email: undefined,
  userName: undefined,
  userPhone: undefined,
  userAddress: undefined,
  files: undefined,
  invoiceAdminId: undefined,
  checkStatus: undefined,
  flowId: undefined,
  stepId: undefined,
  checkAdminId: undefined,
  flowAdminId: undefined,
  status: undefined
})
const formRules = reactive({
  contractId: [{ required: true, message: '合同不能为空', trigger: 'blur' }],
  invoiceBody: [{ required: true, message: '开票主体不能为空', trigger: 'blur' }],
  invoiceName: [{ required: true, message: '发票抬头不能为空', trigger: 'blur' }],
  money: [{ required: true, message: '开票金额不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref<UserApi.UserVO[]>([]) // 用户列表
const formLoading2 = ref(false)

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
      formData.value = await InvoiceApi.getInvoice(id)
      formData.value.flowAdminId = formData.value.flowAdminId2
    } finally {
      formLoading.value = false
    }
    if(formData.value.checkStatus == 2 || formData.value.checkStatus == 1){
      formLoading2.value = true
    }
  }else{
    formLoading2.value = false
    formData.value.flowAdminId = await FlowApi.getFlowUserIds('invoice')
  }
  userList.value = await UserApi.getSimpleUserList()
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
    const data = formData.value as unknown as InvoiceVO
    if (formType.value === 'create') {
      await InvoiceApi.createInvoice(data)
      message.success(t('common.createSuccess'))
    } else {
      await InvoiceApi.updateInvoice(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const selectContractRef = ref()
const chooseContract = () => {
  selectContractRef.value.open()
}
const saveContract = async(contract) => {
  formData.value.contractId = contract.id
  formData.value.contractName = contract.name
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    contractId: undefined,
    customerId: undefined,
    invoiceBody: undefined,
    content: undefined,
    invoiceName: undefined,
    invoiceIssue: 1,
    invoiceType: 1,
    registerNo: undefined,
    bankName: undefined,
    bankNo: undefined,
    money: undefined,
    taxRate: undefined,
    remarks: undefined,
    email: undefined,
    userName: undefined,
    userPhone: undefined,
    userAddress: undefined,
    files: undefined,
    invoiceAdminId: undefined,
    checkStatus: undefined,
    flowId: undefined,
    stepId: undefined,
    checkAdminId: undefined,
    flowAdminId: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>