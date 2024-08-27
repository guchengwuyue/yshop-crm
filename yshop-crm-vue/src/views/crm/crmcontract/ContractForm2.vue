<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="70%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      
    >
    <el-row>
      <el-col :span="12">
        <el-form-item label="合同编号" prop="number">
          <el-input v-model="formData.number" disabled placeholder="请输入合同编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="合同名称" prop="name">
          <el-input v-model="formData.name" disabled placeholder="请输入合同名称" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户" prop="customerId">
          <el-input v-model="formData.customerName" placeholder="请输入选择客户" disabled>
            <template #append><span  @click="chooseCustomer()" style="cursor:pointer;color:#F56C6C">选择客户</span></template>
          </el-input>
          <el-input v-model="formData.customerId" type="hidden" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商机" prop="businessId">
          <el-input v-model="formData.businessName" placeholder="请输入选择商机" disabled>
            <template #append><span  @click="chooseBusiness()" style="cursor:pointer;color:#F56C6C">选择商机</span></template>
          </el-input>
          <el-input v-model="formData.businessId" type="hidden" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="合同金额" prop="money">
          <el-input v-model="formData.money" placeholder="请输入合同金额" disabled />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="下单时间" prop="orderTime">
          <el-date-picker
            v-model="formData.orderTime"
            type="date"
            value-format="x"
            placeholder="选择下单时间"
            disabled
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="合同开始时间" prop="startTime">
          <el-date-picker
            v-model="formData.startTime"
            type="date"
            value-format="x"
            placeholder="选择开始时间"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="合同结束时间" prop="endTime">
          <el-date-picker
            v-model="formData.endTime"
            type="date"
            value-format="x"
            placeholder="选择结束时间"
            disabled
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户签约人" prop="contactsId">
          <el-select v-model="formData.contactsId" clearable placeholder="请选择客户签约人（联系人)" disabled>
              <el-option
                  v-for="item in contacts"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="公司签约人" prop="orderAdminId">
          <el-select v-model="formData.orderAdminId" clearable placeholder="请输入公司签约人" disabled>
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
      <el-col :span="12">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="formData.remark" type="textarea" :rows="2" placeholder="请输入备注" disabled />
        </el-form-item>
      </el-col>
    </el-row>

       <!-- 子表的表单 -->
      <el-tabs v-model="subTabsName">
        <el-tab-pane label="合同产品关系" name="contractProduct">
          <ContractProductForm ref="contractProductFormRef" :is-check="true"  @get-new-data="getNewData" :business-id="formData.businessId" :contract-id="formData.id" />
        </el-tab-pane>
      </el-tabs>
      <el-row style="margin-top:50px">
        <el-col :span="8">
          <span></span>
        </el-col>
        <el-col :span="8">
          <el-form-item label="整单折扣" prop="discountRate">
            <el-input v-model="formData.discountRate" placeholder="请输入整单折扣" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="产品总金额" prop="totalPrice">
            <el-input v-model="formData.totalPrice" placeholder="请输入产品总金额" disabled />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row style="margin-top:50px">
        <el-col :span="12">
          <el-form-item label="审核状态" prop="agreeType">
            <el-radio-group v-model="formData.agreeType">
              <el-radio :value="1">审核通过</el-radio>
              <el-radio :value="2">审核拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="拒绝理由" prop="remark" v-if="formData.agreeType == 2">
            <el-input v-model="formData.reason" type="textarea" :rows="2" placeholder="请输入拒绝理由" />
          </el-form-item>
        </el-col>
      </el-row>
      
    </el-form>

    <template #footer>
      <el-button @click="submitForm()" type="success" :disabled="formLoading">确认提交</el-button>
      <!-- <el-button @click="submitForm(2)" type="danger">驳回</el-button> -->
    </template>
  </Dialog>
  <select-customer ref="selectCustomerRef" @save-customer="saveCustomer"/>
  <select-business ref="selectBusinessRef" @save-business="saveBusiness"/>
</template>
<script setup lang="ts">
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import ContractProductForm from './components/ContractProductForm.vue'
import SelectCustomer from "@/views/components/customer/index.vue"
import SelectBusiness from "@/views/components/business/index.vue"
import { CustomerContactsApi, CustomerContactsVO } from '@/api/crm/crmcustomercontacts'
import * as UserApi from '@/api/system/user'
import { FlowApi } from '@/api/crm/crmflow'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

/** 合同 表单 */
defineOptions({ name: 'ContractForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('审核') // 弹窗的标题
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
  invoiceMoney: undefined,
  agreeType:undefined,
  reason:undefined,
})
const formRules = reactive({
})
const formRef = ref() // 表单 Ref
const contacts = ref([])
const userList = ref<UserApi.UserVO[]>([]) // 用户列表


/** 子表的表单 */
const subTabsName = ref('contractProduct')
const contractProductFormRef = ref()
const myMoney = ref(0)


/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  formType.value = type
  resetForm()
  // 修改时，设置数据
  formData.value = await ContractApi.getContract(id)
  formData.value.agreeType = 1
  userList.value = await UserApi.getSimpleUserList()
  formData.value.flowAdminId = formData.value.flowAdminId2
  if(formData.value.customerId){
    const data = await CustomerContactsApi.getCustomerContactsPage({customerId:formData.value.customerId})
    contacts.value = data.list
  }
  formData.value.orderAdminId = userStore.getUser.id
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
 
  // 提交请求

  if(formData.value.agreeType == 2 && !formData.value.reason){
    message.error('请填写拒绝理由')
    return
  }
  formLoading.value = true
  try {
    const data = formData.value as unknown as ContractVO

    await ContractApi.checkContract({id:formData.value.id,agreeType:formData.value.agreeType,remark:formData.value.reason})
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

  const data = await CustomerContactsApi.getCustomerContactsPage({customerId:customer.id})
  contacts.value = data.list
}

const selectBusinessRef = ref()
const chooseBusiness = () => {
  selectBusinessRef.value.open()
}

const saveBusiness = (value) => {
  formData.value.businessId = value.id
  formData.value.businessName = value.name
}

const getNewData = (money) => {
  myMoney.value = money
  preferentialRate()
  //formData.value.totalPrice = money
}

const preferentialRate = () => {
  formData.value.totalPrice = (myMoney.value - myMoney.value * (formData.value.discountRate / 100)).toFixed(2)
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    customerId: undefined,
    businessId: undefined,
    contactsId: undefined,
    sourceId: undefined,
    name: undefined,
    number: undefined,
    orderTime: undefined,
    money: undefined,
    totalPrice: 0,
    returnMoney: undefined,
    discountRate: 0,
    checkStatus: undefined,
    flowId: undefined,
    stepId: undefined,
    checkAdminId: undefined,
    flowAdminId: undefined,
    startTime: Date.parse(new Date()),
    endTime: undefined,
    orderAdminId: undefined,
    remark: undefined,
    ownerUserId: undefined,
    nextTime: undefined,
    expireHandle: undefined,
    invoiceMoney: undefined
  }
  formRef.value?.resetFields()
}
</script>