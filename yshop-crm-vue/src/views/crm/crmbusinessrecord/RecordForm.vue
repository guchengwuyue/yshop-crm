<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="75%">
    <el-row>
      <el-col :span="12">
        <el-card> 
            <template #header>
              商机信息
            </template>
            <el-form
              ref="formRef"
              :model="formData"
              :rules="formRules"
              label-width="120px"
              disabled
            >
              <el-form-item label="商机名称" prop="name">
                <el-input v-model="formData2.name" placeholder="请输入客户名称" />
              </el-form-item>
              <el-form-item label="商机金额" prop="totalPrice">
                <el-input v-model="formData2.totalPrice" placeholder="请输入手机" />
              </el-form-item>
              <el-form-item label="预计成交日期" prop="dealTime">
                <el-date-picker
                  v-model="formData2.dealTime"
                  type="datetime"
                  value-format="x"
                  placeholder="选择预计成交日期"
                />
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="formData2.remark" type="textarea" :rows="2" placeholder="请输入备注" />
              </el-form-item>
            </el-form>
        </el-card> 
      </el-col>
      <el-col :span="12">
        <el-card> 
          <template #header>
            跟进操作
          </template>
          <el-form
            ref="formRef"
            :model="formData"
            :rules="formRules"
            label-width="120px"
            v-loading="formLoading"
          >
            <el-form-item label="跟进内容" prop="content">
              <el-input type="textarea" :rows="2" v-model="formData.content"  />
            </el-form-item>
            <el-form-item label="下次联系时间" prop="nextTime">
              <el-date-picker
                v-model="formData.nextTime"
                type="datetime"
                value-format="x"
                placeholder="选择下次联系时间"
              />
            </el-form-item>
            <el-form-item label="跟进状态" prop="followStatusStr">
              <el-select v-model="followStatusStr" clearable placeholder="请选择跟进状态">
                <el-option
                    v-for="dict in getIntDictOptions(DICT_TYPE.CRM_FOLLOW_STATUS)"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
              </el-select>
            </el-form-item>
            <el-form-item label="跟进方式" prop="recordType">
              <el-select v-model="formData.recordType" clearable placeholder="请选择跟进方式">
                <el-option
                    v-for="dict in getIntDictOptions(DICT_TYPE.CRM_FOLLOW_TYPE)"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
              </el-select>
            </el-form-item>
            <el-form-item label=" " prop="recordType">
              <el-button @click="submitForm" type="primary">立即保存</el-button>
            </el-form-item>
          </el-form>
        </el-card> 
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="margin-top:10px">
        <el-tabs type="border-card">
          <el-tab-pane label="跟进记录">
            <RecordList :my-id="myId" :business-id="businessId" />
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
              <el-table-column prop="name" label="产品名称"  />
              <el-table-column prop="sku" label="产品规格"  />
              <el-table-column prop="price" label="产品单价" />
              <el-table-column prop="nums" label="产品数量" />
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <template #footer>
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
  dialogTitle.value = '详情'
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
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as RecordVO
    let markArr = getIntDictOptions(DICT_TYPE.CRM_FOLLOW_STATUS)
    markArr.map((mark) => {
      if(mark.value == followStatusStr.value){
        data.content = '【'+mark.label+'】' + data.content
      }
        
    })
    data.followStatus = followStatusStr.value
    myId.value = await RecordApi.createRecord(data)
    message.success(t('common.createSuccess'))
    formData.value.content = ''
    //dialogVisible.value = false
    // 发送操作成功的事件
    //emit('success')
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