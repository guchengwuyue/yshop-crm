<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="75%">
    <el-row>
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
                <el-col :span="12" style="text-align:right">
                  <el-button type="warning" @click="openSms">发送邮件</el-button>
                  <el-button type="success" @click="openMail">发送短信</el-button>
                </el-col>
              </el-row>
            </template>
            <el-descriptions title="基本信息">
                <el-descriptions-item label="商机名称">{{formData2.name}}</el-descriptions-item>
                <el-descriptions-item label="商机金额">{{formData2.totalPrice}}</el-descriptions-item>
                <el-descriptions-item label="预计成交日期">{{formData2.dealTime}}</el-descriptions-item>
                <el-descriptions-item label="备注">{{formData2.remark}}</el-descriptions-item>
              </el-descriptions>
        </el-card> 
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="margin-top:10px">
        <el-tabs type="border-card">
          <el-tab-pane label="跟进记录">
            <RecordList :my-id="myId" :business-id="businessId" :is-detail="true" />
          </el-tab-pane>
          <el-tab-pane label="客户信息">
            <el-descriptions title="信息明细">
              <el-descriptions-item label="客户名称">{{customerData.name}}</el-descriptions-item>
              <el-descriptions-item label="客户电话">{{customerData.telephone}}</el-descriptions-item>
              <el-descriptions-item label="客户手机">{{customerData.mobile}}</el-descriptions-item>
              <el-descriptions-item label="客户行业"><dict-tag :type="DICT_TYPE.CRM_CUSTOMER_INDUSTRY" :value="customerData.industry" /></el-descriptions-item>
              <el-descriptions-item label="客户来源"><dict-tag :type="DICT_TYPE.CRM_CUSTOMER_SOURCE" :value="customerData.source" /></el-descriptions-item>
              <el-descriptions-item label="客户等级"><dict-tag :type="DICT_TYPE.CRM_CUSTOMER_LEVEL" :value="customerData.level" /></el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>
          <el-tab-pane label="意向产品">
            <el-table :data="productList" style="width: 100%">
              <el-table-column prop="productName" label="产品名称"  />
              <el-table-column prop="sku" label="产品规格"  />
              <el-table-column prop="price" label="产品单价" />
              <el-table-column prop="nums" label="产品数量" />
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <template #footer>
      <el-button @click="submitForm" type="danger">无效商机</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { RecordApi, RecordVO } from '@/api/crm/crmrecord'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import * as AreaApi from '@/api/system/area'
import RecordList from '@/views/crm/crmbusiness/components/record/index.vue'
import ContactsList from '@/views/crm/crmcustomer/components/contacts/index.vue'
import { BusinessApi, BusinessVO } from '@/api/crm/crmbusiness'
import customerinfoimg from '@/assets/imgs/customerinfo.png'

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
const businessId = ref(0)
const customerData = ref({})
const productList = ref([])

const showMore = () => {
  moreShow.value = !moreShow.value
}

const props = {
  value: 'id',
  label: 'name'
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  businessId.value = id
  dialogVisible.value = true
  dialogTitle.value = '商机详情'
  formType.value = type
  resetForm()
  formData2.value = await BusinessApi.getBusiness(id)
  customerData.value = await CustomerApi.getCustomer(formData2.value.customerId)
  formData.value.typesId = id
  formData.value.types = 'business'

  productList.value = await BusinessApi.getBusinessProductListByBusinessId(id)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  //await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    formData2.value.isEnd = 3
    const data = formData2.value as unknown as RecordVO
    await BusinessApi.updateBusiness(data)
    message.success(t('common.updateSuccess'))
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
    types: undefined,
    typesId: undefined,
    content: undefined,
    recordType: 1,
    nextTime: undefined,
    lng: undefined,
    lat: undefined
  }
  formRef.value?.resetFields()
}
</script>