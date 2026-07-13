<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="75%">
    <el-row v-loading="loading">
      <el-col :span="24">
        <el-card> 
            <template #header>
              <el-row>
                <el-col :span="12">
                  <div style="display:flex;align-items: center;">
                    <el-image style="width: 40px; height: 30px" :src="customerinfoimg"  />
                    <span style="margin-left:8px;font-weight:bolder">{{formData2.name}}</span>
                  </div>
                </el-col>
                <el-col :span="12" style="text-align:right" >
                  <el-button type="primary" :disabled="formData2.dealStatus === 1" @click="handleOpen(formData2.id)">放入公海</el-button>
                  <el-button type="warning" @click="openSms">发送邮件</el-button>
                  <el-button type="success" @click="openMail">发送短信</el-button>
                </el-col>
              </el-row>
            </template>

            <div>
              <el-descriptions title="基本信息">
                <el-descriptions-item label="客户名称">{{formData2.name}}</el-descriptions-item>
                <el-descriptions-item label="手机">{{formData2.mobile}}</el-descriptions-item>
                <el-descriptions-item label="电话">{{formData2.telephone}}</el-descriptions-item>
                <el-descriptions-item label="微信">{{formData2.weixin}}</el-descriptions-item>
                <el-descriptions-item label="QQ">{{formData2.qq}}</el-descriptions-item>
                <el-descriptions-item label="客户级别"> 
                  <span v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_LEVEL)" :key="dict.value">
                      <span v-if="formData2.level == dict.value">{{dict.label}}</span>
                  </span>  
                </el-descriptions-item>
                <el-descriptions-item label="客户行业">
                  <span v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_INDUSTRY)" :key="dict.value">
                      <span v-if="formData2.industry == dict.value">{{dict.label}}</span>
                  </span>
                </el-descriptions-item>
                <el-descriptions-item label="客户来源">
                  <span v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_SOURCE)" :key="dict.value">
                      <span v-if="formData2.source == dict.value">{{dict.label}}</span>
                  </span>
                </el-descriptions-item>
                <el-descriptions-item label="地址">
                  {{formData2.addressStr ? formData2.addressStr : '未填写'}}
                </el-descriptions-item>
                <el-descriptions-item label="详细地址">{{formData2.detailAddress}}</el-descriptions-item>
                <el-descriptions-item label="跟进状态">
                  <span v-for="dict in getIntDictOptions(DICT_TYPE.CRM_FOLLOW_STATUS)" :key="dict.value">
                      <span v-if="formData2.followStatus == dict.value">{{dict.label}}</span>
                  </span>
                </el-descriptions-item>
              </el-descriptions>
            </div>   
        </el-card> 
      </el-col>
    </el-row>
    <el-row v-loading="loading">
      <el-col :span="24" style="margin-top:10px">
        <el-tabs type="border-card">
          <el-tab-pane label="跟进记录">
            <RecordList :my-id="myId" :customer-id="customerId" :is-detail="true" />
          </el-tab-pane>
          <el-tab-pane label="联系人">
            <ContactsList  :customer-id="customerId" :customer-name="formData2.name"/>
          </el-tab-pane>
          <el-tab-pane label="商机">
            <BusinessList  :customer-id="customerId" />
          </el-tab-pane>
          <el-tab-pane label="合同">
            <ContractList  :customer-id="customerId" />
          </el-tab-pane>
          <el-tab-pane label="回款">
            <ReceivablesList  :customer-id="customerId" />
          </el-tab-pane>
          <el-tab-pane label="日志">
            <LogList  :customer-id="customerId" />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
    <SmsTemplateSendForm ref="smsTemplateSendFormRef"  />
    <MailTemplateSendForm ref="mailTemplateSendFormRef"  />
  </Dialog>
</template>
<script setup lang="ts">
import { RecordApi, RecordVO } from '@/api/crm/crmrecord'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
// import * as AreaApi from '@/api/system/area'
import RecordList from '@/views/crm/crmcustomer/components/record/index.vue'
import ContactsList from '@/views/crm/crmcustomer/components/contacts/index.vue'
import BusinessList from '@/views/crm/crmcustomer/components/business/index.vue'
import ContractList from '@/views/crm/crmcustomer/components/contract/index.vue'
import ReceivablesList from '@/views/crm/crmcustomer/components/receivables/index.vue'
import LogList from '@/views/crm/crmcustomer/components/log/index.vue'
import customerinfoimg from '@/assets/imgs/customerinfo.png'
import SmsTemplateSendForm from '@/views/components/sms/SmsTemplateSendForm.vue'
import MailTemplateSendForm from '@/views/components/email/MailTemplateSendForm.vue'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()


/** 跟进记录 表单 */
defineOptions({ name: 'RecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  types: undefined,
  typesId: undefined,
  content: undefined,
  recordType: undefined,
  nextTime: undefined,
  lng: undefined,
  lat: undefined
})
const formRules = reactive({
  content: [{ required: true, message: '跟进内容不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const moreShow = ref(false)
const areaList = ref([])
const formData2 = ref({})
const cityArr = ref([])
const followStatusStr = ref(1)
const myId = ref(0)
const customerId = ref(0)
const customerData = ref({})
const loading = ref(false)

const showMore = () => {
  moreShow.value = !moreShow.value
}

const props = {
  value: 'id',
  label: 'name'
}

/** 打开弹窗 */
const open = async (type: string, id?: number,data) => {
  customerData.value = data
  customerId.value = id
  dialogVisible.value = true
  dialogTitle.value = '客户详情'
  formType.value = type
  loading.value = true
  try {
   // areaList.value = await AreaApi.getAreaTree()
    
  }finally {
    loading.value = false
  }
  //areaList.value = await AreaApi.getAreaTree()
  formData2.value = await CustomerApi.getCustomer(id)
  formData.value.typesId = id
  formData.value.types = 'customer'
  cityArr.value = []
  // cityArr.value.push(formData2.value.province)
  // cityArr.value.push(formData2.value.city)
  // cityArr.value.push(formData2.value.area)

  console.log('cityArr:',cityArr.value)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const getList = () => {
  dialogVisible.value = false
  emit('success')
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调



const smsTemplateSendFormRef = ref()
const openSms = () => {
  smsTemplateSendFormRef.value.open([formData2.value],true)
}

const mailTemplateSendFormRef = ref()
const openMail = () => {
  mailTemplateSendFormRef.value.open([formData2.value],true)
}

const handleOpen  = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm("确定把客户放入公海")
    // 发起删除
    await CustomerApi.openCustomer(id)
    message.success('放入成功')
    // 刷新列表
    getList()
  } catch {}
}


</script>
<style lang="scss" scoped>
/* 使用深度选择器覆盖默认样式 */
:deep(.el-icon svg ) {
  height:0;
  width:0;
}


:deep(.el-input.is-disabled .el-input__wrapper) {
  box-shadow: none;
}
:deep(.el-cascader .el-input__inner) {
  border:none !important;
}
:deep(.el-input.is-disabled .el-input__wrapper) {
  background-color: #ffffff !important;
}



</style>