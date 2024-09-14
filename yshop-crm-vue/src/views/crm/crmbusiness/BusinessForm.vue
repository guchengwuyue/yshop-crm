<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="70%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="110px"
      v-loading="formLoading"
    >
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户" prop="customerId">
          <el-input v-model="formData.customerName" placeholder="请输入选择客户">
            <template #append><span  @click="chooseCustomer()" style="cursor:pointer;color:#F56C6C">选择客户</span></template>
          </el-input>
          <el-input v-model="formData.customerId" type="hidden" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="商机名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入商机名称" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="预算金额" prop="money">
          <el-input v-model="formData.money" placeholder="请输入商机金额" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="预计成交日期" prop="dealTime">
          <el-date-picker
            v-model="formData.dealTime"
            type="datetime"
            value-format="x"
            placeholder="选择预计成交日期"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
      <el-form-item label="下次跟进时间" prop="nextTime">
          <el-date-picker
            v-model="formData.nextTime"
            type="datetime"
            value-format="x"
            placeholder="选择下次联系时间"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="formData.remark" type="textarea" :rows="2" placeholder="请输入备注" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 子表的表单 -->
    <el-tabs v-model="subTabsName">
      <el-tab-pane label="商机产品关联" name="businessProduct">
        <BusinessProductForm ref="businessProductFormRef" @get-new-data="getNewData" :business-id="formData.id" />
      </el-tab-pane>
    </el-tabs>

      <el-row style="margin-top:50px">
        <el-col :span="8">
          <span></span>
        </el-col>
        <el-col :span="8">
          <el-form-item label="优惠率%" prop="totalPrice">
            <el-input type="number" v-model="formData.discountRate" @input="preferentialRate" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="总金额" prop="totalPrice">
            <el-input type="number" v-model="formData.totalPrice" readonly  />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    

    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
  <select-customer ref="selectCustomerRef" @save-customer="saveCustomer"/>
</template>
<script setup lang="ts">
import { BusinessApi, BusinessVO } from '@/api/crm/crmbusiness'
import BusinessProductForm from './components/BusinessProductForm.vue'
import SelectCustomer from "@/views/components/customer/index.vue"

/** 商机 表单 */
defineOptions({ name: 'BusinessForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  customerId: undefined,
  customerName: undefined,
  status: undefined,
  statusTime: undefined,
  isEnd: undefined,
  nextTime: undefined,
  name: undefined,
  money: undefined,
  totalPrice: undefined,
  dealTime: undefined,
  discountRate: undefined,
  remark: undefined,
  ownerUserId: undefined,
})
const formRules = reactive({
  customerId: [{ required: true, message: '客户ID不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '商机名称不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 子表的表单 */
const subTabsName = ref('businessProduct')
const businessProductFormRef = ref()
const myMoney = ref(0)

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
      formData.value = await BusinessApi.getBusiness(id)
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
    await businessProductFormRef.value.validate()
  } catch (e) {
    subTabsName.value = 'businessProduct'
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as BusinessVO
    // 拼接子表的数据
    data.businessProducts = businessProductFormRef.value.getData()
    if (formType.value === 'create') {
      await BusinessApi.createBusiness(data)
      message.success(t('common.createSuccess'))
    } else {
      await BusinessApi.updateBusiness(data)
      message.success(t('common.updateSuccess'))
    }
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

const saveCustomer = (customer) => {
  console.log('customer:',customer)
  formData.value.customerId = customer.id
  formData.value.customerName = customer.name
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
    status: undefined,
    statusTime: undefined,
    isEnd: undefined,
    nextTime: Date.parse(new Date()),
    name: undefined,
    money: undefined,
    totalPrice: 0,
    dealTime: undefined,
    discountRate: 0,
    remark: undefined,
    ownerUserId: undefined,
  }
  formRef.value?.resetFields()
}
</script>