<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="70%">
    <el-card> 
      <template #header>
        <el-row>
          <el-col :span="12">
            <div style="display:flex;align-items: center;">
              <el-image style="width: 40px; height: 30px" :src="customerinfoimg"  />
              <span style="margin-left:8px;font-weight:bolder">{{formData.name}}</span>
            </div>
          </el-col>
        </el-row>
      </template>
      <div>
        <el-descriptions title="基本信息">
          <el-descriptions-item label="合同编号">{{formData.number}}</el-descriptions-item>
          <el-descriptions-item label="合同名称">{{formData.name}}</el-descriptions-item>
          <el-descriptions-item label="客户">{{formData.customerName}}</el-descriptions-item>
          <el-descriptions-item label="商机">{{formData.businessName}}</el-descriptions-item>
          <el-descriptions-item label="合同金额">{{formData.money}}</el-descriptions-item>
          <el-descriptions-item label="下单时间"> 
            {{formatDate(formData.orderTime)}}   
          </el-descriptions-item>
          <el-descriptions-item label="合同开始时间">
            {{formatDate(formData.startTime)}}
          </el-descriptions-item>
          <el-descriptions-item label="合同结束时间">
            {{formatDate(formData.endTime)}}
          </el-descriptions-item>
          <el-descriptions-item label="客户签约人">
            <span v-for="item in contacts" :key="item.id">
                <span v-if="formData.contactsId == item.id">{{item.name}}</span>
            </span>
          </el-descriptions-item>
          <el-descriptions-item label="公司签约人">
            <span v-for="item in userList" :key="item.id">
                <span v-if="formData.orderAdminId == item.id">{{item.nickname}}</span>
            </span>
          </el-descriptions-item>
          <el-descriptions-item label="备注">
            {{formData.remark}}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
        disabled
      >
        <!-- 子表的表单 -->
        <el-tabs v-model="subTabsName">
          <el-tab-pane label="合同产品明细" name="contractProduct">
            <ContractProductForm ref="contractProductFormRef"  :is-check="true" @get-new-data="getNewData" :business-id="formData.businessId" :contract-id="formData.id" />
          </el-tab-pane>
        </el-tabs>
        <el-row style="margin-top:10px">
          <el-col :span="8">
            <span></span>
          </el-col>
          <el-col :span="8">
            <el-form-item label="整单折扣" prop="discountRate">
              <!-- <el-input v-model="formData.discountRate" placeholder="请输入整单折扣" /> -->
              <span>{{formData.discountRate}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品总金额" prop="totalPrice">
              <span>{{formData.totalPrice}}元</span>
              <!-- <el-input v-model="formData.totalPrice" placeholder="请输入产品总金额" /> -->
            </el-form-item>
          </el-col>
        </el-row>
        
      </el-form>
    </el-card> 
    <el-card> 
      <template #header>
        回款信息
      </template>
      <ReceivablesList :contractId="contractId" />
    </el-card> 
    <el-card> 
      <template #header>
        审核日志
      </template>
      <FlowStep :flowAdmins="flowAdmins" />
    </el-card> 

    <template #footer>
      <!-- <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button> -->
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>

</template>
<script setup lang="ts">
import { formatDate } from '@/utils/formatTime'
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import ContractProductForm from './components/ContractProductForm2.vue'
import ReceivablesList from './components/receivables/index.vue'
import LogList from './components/log/index.vue'
import { CustomerContactsApi, CustomerContactsVO } from '@/api/crm/crmcustomercontacts'
import * as UserApi from '@/api/system/user'
import { FlowApi } from '@/api/crm/crmflow'
import { useUserStore } from '@/store/modules/user'
import FlowStep from '@/views/components/flow/index.vue'
import customerinfoimg from '@/assets/imgs/customerinfo.png'

const userStore = useUserStore()

/** 合同 表单 */
defineOptions({ name: 'ContractForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('合同详情') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  customerId: undefined,
  businessId: undefined,
  contactsId: undefined,
  sourceId: undefined,
  name: undefined,
  number: undefined,
  orderTime: undefined,
  money: undefined,
  totalPrice: undefined,
  returnMoney: undefined,
  discountRate: undefined,
  checkStatus: undefined,
  flowId: undefined,
  stepId: undefined,
  checkAdminId: undefined,
  flowAdminId: undefined,
  startTime: undefined,
  endTime: undefined,
  orderAdminId: undefined,
  remark: undefined,
  ownerUserId: undefined,
  nextTime: undefined,
  expireHandle: undefined,
  invoiceMoney: undefined
})
const formRules = reactive({
  customerId: [{ required: true, message: '请选择客户', trigger: 'blur' }],
  name: [{ required: true, message: '合同名称不能为空', trigger: 'blur' }],
  number: [{ required: true, message: '合同编号不能为空', trigger: 'blur' }],
  money: [{ required: true, message: '合同金额不能为空', trigger: 'blur' }],
  flowAdminId: [{ required: true, message: '请选择审批人', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const contacts = ref([])
const userList = ref<UserApi.UserVO[]>([]) // 用户列表


/** 子表的表单 */
const subTabsName = ref('contractProduct')
const contractProductFormRef = ref()
const myMoney = ref(0)
const contractId = ref(0)
const flowAdmins = ref([])


/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  contractId.value = id
  formType.value = type
  //resetForm()
  // 修改时，设置数据
  if (id) {
    flowAdmins.value = await FlowApi.getFlowUserIds('contract',id ? id : 0)
    formLoading.value = true
    try {
      formData.value = await ContractApi.getContract(id)
      formData.value.flowAdminId = formData.value.flowAdminId2
      if(formData.value.customerId){
        const data = await CustomerContactsApi.getCustomerContactsPage({customerId:formData.value.customerId})
        contacts.value = data.list
      }
    } finally {
      formLoading.value = false
    }
  }else{
    formData.value.number = await ContractApi.getContractNo()
    formData.value.orderAdminId = userStore.getUser.id
  }
  userList.value = await UserApi.getSimpleUserList()
 
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 校验子表单
  try {
    await contractProductFormRef.value.validate()
  } catch (e) {
    subTabsName.value = 'contractProduct'
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as ContractVO
    // 拼接子表的数据
    data.contractProducts = contractProductFormRef.value.getData()
    if (formType.value === 'create') {
      await ContractApi.createContract(data)
      message.success(t('common.createSuccess'))
    } else {
      await ContractApi.updateContract(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}



</script>

<style lang="scss" scoped>
/* 使用深度选择器覆盖默认样式 */
// :deep(.el-icon svg ) {
//   height:0;
//   width:0;
// }


// :deep(.el-input.is-disabled .el-input__wrapper) {
//   box-shadow: none;
// }
// :deep(.el-cascader .el-input__inner) {
//   border:none !important;
// }
// :deep(.el-input.is-disabled .el-input__wrapper) {
//   background-color: #ffffff !important;
// }



</style>