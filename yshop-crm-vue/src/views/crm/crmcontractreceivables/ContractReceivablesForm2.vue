<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="回款编号" prop="number">
        <el-input v-model="formData.number" placeholder="请输入回款编号" disabled />
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-input v-model="formData.customerName" placeholder="请输入选择客户" disabled>
          <template #append><span  @click="chooseCustomer()" style="cursor:pointer;color:#F56C6C">选择客户</span></template>
        </el-input>
        <el-input v-model="formData.customerId" type="hidden" />
      </el-form-item>
      <el-form-item label="合同" prop="contractId">
        <el-input v-model="formData.contractName" placeholder="请输入选择合同" disabled>
          <template #append><span  @click="chooseContract()" style="cursor:pointer;color:#F56C6C">选择合同</span></template>
        </el-input>
        <el-input v-model="formData.contractId" type="hidden" />
      </el-form-item>
      <el-form-item label="回款金额" prop="money">
        <el-input v-model="formData.money" placeholder="请输入回款金额" disabled />
      </el-form-item>
      <el-form-item label="回款账户" prop="accountId">
        <el-select v-model="formData.accountId" clearable placeholder="请选择收款账户" disabled>
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CRM_PAYEE_TYPE)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="回款日期" prop="returnTime">
        <el-date-picker
          v-model="formData.returnTime"
          type="date"
          value-format="x"
          placeholder="选择回款日期"
          disabled
        />
      </el-form-item>
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
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" :rows="2" placeholder="请输入备注" disabled />
      </el-form-item>
      <el-form-item label="审核状态" prop="agreeType">
        <el-radio-group v-model="formData.agreeType">
          <el-radio :value="1">审核通过</el-radio>
          <el-radio :value="2">审核拒绝</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="拒绝理由" prop="remark" v-if="formData.agreeType == 2">
        <el-input v-model="formData.reason" type="textarea" :rows="2" placeholder="请输入拒绝理由" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
  <select-customer ref="selectCustomerRef" @save-customer="saveCustomer"/>
  <select-contract ref="selectContractRef" @save-contract="saveContract"/>
</template>
<script setup lang="ts">
import { ContractReceivablesApi, ContractReceivablesVO } from '@/api/crm/crmcontractreceivables'
import SelectCustomer from "@/views/components/customer/index.vue"
import SelectContract from "@/views/components/contract/index.vue"
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { FlowApi } from '@/api/crm/crmflow'
import * as UserApi from '@/api/system/user'

/** 回款 表单 */
defineOptions({ name: 'ContractReceivablesForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  number: undefined,
  customerId: undefined,
  contractId: undefined,
  returnTime: undefined,
  accountId: undefined,
  money: undefined,
  checkStatus: undefined,
  flowId: undefined,
  stepId: undefined,
  checkAdminId: undefined,
  flowAdminId: undefined,
  remark: undefined,
  ownerUserId: undefined,
  orderAdminId: undefined,
  payStatus: undefined,
  payType: undefined,
  agreeType:undefined,
  reason:undefined,
})
const formRules = reactive({
  number: [{ required: true, message: '回款编号不能为空', trigger: 'blur' }],
  customerId: [{ required: true, message: '客户不能为空', trigger: 'blur' }],
  contractId: [{ required: true, message: '合同不能为空', trigger: 'blur' }],
  accountId: [{ required: true, message: '收款账户不能为空', trigger: 'blur' }],
  money: [{ required: true, message: '回款金额不能为空', trigger: 'blur' }],

})
const formRef = ref() // 表单 Ref
const userList = ref<UserApi.UserVO[]>([]) // 用户列表

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
      formData.value = await ContractReceivablesApi.getContractReceivables(id)
      formData.value.agreeType = 1
      formData.value.flowAdminId = formData.value.flowAdminId2
    } finally {
      formLoading.value = false
    }
  }
  userList.value = await UserApi.getSimpleUserList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  if(formData.value.agreeType == 2 && !formData.value.reason){
    message.error('请填写拒绝理由')
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as ContractReceivablesVO
    await ContractReceivablesApi.checkContract({id:formData.value.id,agreeType:formData.value.agreeType,remark:formData.value.reason})
    message.success('操作成功')
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const selectCustomerRef = ref()
const chooseCustomer = () => {
  selectCustomerRef.value.open()
}

const saveCustomer = async(customer) => {
  formData.value.customerId = customer.id
  formData.value.customerName = customer.name
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
    number: undefined,
    customerId: undefined,
    contractId: undefined,
    returnTime: Date.parse(new Date()),
    accountId: 1,
    money: undefined,
    checkStatus: undefined,
    flowId: undefined,
    stepId: undefined,
    checkAdminId: undefined,
    flowAdminId: undefined,
    remark: undefined,
    ownerUserId: undefined,
    orderAdminId: undefined,
    payStatus: undefined,
    payType: undefined
  }
  formRef.value?.resetFields()
}
</script>