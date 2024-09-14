<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="75%">
    <el-row>
      <el-col :span="12">
        <el-card> 
            <template #header>
              客户信息
            </template>
            <el-form
              ref="formRef"
              :model="formData"
              :rules="formRules"
              label-width="120px"
              disabled
            >
              <el-form-item label="客户名称" prop="name">
                <el-input v-model="formData2.name" placeholder="请输入客户名称" />
              </el-form-item>
              <el-form-item label="手机" prop="mobile">
                <el-input v-model="formData2.mobile" placeholder="请输入手机" />
              </el-form-item>
              <el-form-item label="电话" prop="telephone">
                <el-input v-model="formData2.telephone" placeholder="请输入电话" />
              </el-form-item>
              <el-form-item label="微信" prop="weixin">
                <el-input v-model="formData2.weixin" placeholder="请输入微信" />
              </el-form-item>
              <el-form-item label="QQ" prop="qq">
                <el-input v-model="formData2.qq" placeholder="请输入QQ" />
              </el-form-item>
              <div v-if="moreShow">
                <el-form-item label="客户级别" prop="level">
                  <el-select v-model="formData2.level" clearable placeholder="请选择客户级别">
                    <el-option
                        v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_LEVEL)"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                  </el-select>
                </el-form-item>
                <el-form-item label="客户行业" prop="industry">
                  <el-select v-model="formData2.industry" clearable placeholder="请选择客户行业">
                    <el-option
                        v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_INDUSTRY)"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                  </el-select>
                </el-form-item>
                <el-form-item label="客户来源" prop="source">
                  <el-select v-model="formData2.source" clearable placeholder="请选择客户行业">
                    <el-option
                        v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_SOURCE)"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                  </el-select>
                </el-form-item>
                <el-form-item label="地址" prop="city"  @change="handleChange">
                  <el-cascader
                    v-model="cityArr"
                    :options="areaList"
                    :props="props"
                    @change="handleChange"
                  />
                </el-form-item>
                <el-form-item label="详细地址" prop="detailAddress">
                  <el-input v-model="formData2.detailAddress" placeholder="请输入详细地址" />
                </el-form-item>
                <el-form-item label="跟进状态" prop="followStatus">
                  <el-select v-model="formData2.followStatus" clearable placeholder="请选择跟进状态业">
                    <el-option
                        v-for="dict in getIntDictOptions(DICT_TYPE.CRM_FOLLOW_STATUS)"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                  </el-select>
                </el-form-item>
              </div>
              <div style="color:#409EFF;text-align:center;cursor: pointer" @click="showMore">
                 <span v-if="!moreShow">显示更多</span>
                 <span v-else>隐藏信息</span>
              </div>
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
            <RecordList :my-id="myId" :customer-id="customerId" />
          </el-tab-pane>
          <el-tab-pane label="联系人">
            <ContactsList  :customer-id="customerId" :customer-name="formData2.name" />
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
  </Dialog>
</template>
<script setup lang="ts">
import { RecordApi, RecordVO } from '@/api/crm/crmrecord'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import * as AreaApi from '@/api/system/area'
import RecordList from '@/views/crm/crmcustomer/components/record/index.vue'
import ContactsList from '@/views/crm/crmcustomer/components/contacts/index.vue'
import BusinessList from '@/views/crm/crmcustomer/components/business/index.vue'
import ContractList from '@/views/crm/crmcustomer/components/contract/index.vue'
import ReceivablesList from '@/views/crm/crmcustomer/components/receivables/index.vue'
import LogList from '@/views/crm/crmcustomer/components/log/index.vue'

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

const showMore = () => {
  moreShow.value = !moreShow.value
}

const props = {
  value: 'id',
  label: 'name'
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  customerId.value = id
  dialogVisible.value = true
  dialogTitle.value = '详情'
  formType.value = type
  resetForm()
  areaList.value = await AreaApi.getAreaTree()
  formData2.value = await CustomerApi.getCustomer(id)
  formData.value.typesId = id
  formData.value.types = 'customer'
  cityArr.value.push(formData2.value.province)
  cityArr.value.push(formData2.value.city)
  cityArr.value.push(formData2.value.area)
  console.log('cityArr:',cityArr.value)
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