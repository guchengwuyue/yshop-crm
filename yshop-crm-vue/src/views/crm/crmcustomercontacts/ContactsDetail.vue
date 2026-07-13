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
                  <el-button type="danger" @click="call(formData2.id,formData2.mobile)">云呼叫</el-button>
                  <el-button type="warning" @click="openSms">发送邮件</el-button>
                  <el-button type="success" @click="openMail">发送短信</el-button>
                </el-col>
              </el-row>
            </template>

            <div>
              <el-descriptions title="基本信息">
                <el-descriptions-item label="姓名">{{formData2.name}}</el-descriptions-item>
                <el-descriptions-item label="客户名称">{{formData2.customerName}}</el-descriptions-item>
                <el-descriptions-item label="生日">{{formatDate(formData2.birthday,'YYYY-MM-DD')}}</el-descriptions-item>
                <el-descriptions-item label="手机">{{formData2.mobile}}</el-descriptions-item>
                <el-descriptions-item label="电话">{{formData2.telephone}}</el-descriptions-item>
                <el-descriptions-item label="微信">{{formData2.wechat}}</el-descriptions-item>
                <el-descriptions-item label="电子邮箱">{{formData2.email}}</el-descriptions-item>
                <el-descriptions-item label="职务"> 
                  {{formData2.post}}
                </el-descriptions-item>
                <el-descriptions-item label="关键决策人">
                  <span v-if="formData2.decision == 1">是</span>
                  <span v-else-if="formData2.decision == 0">不是</span>
                  <span v-else>未知</span>
                </el-descriptions-item>
                <el-descriptions-item label="性别">
                  <span v-if="formData2.sex == 1">男</span>
                  <span v-else-if="formData2.sex == 0">女</span>
                  <span v-else>未知</span>
                </el-descriptions-item>
                <el-descriptions-item label="地址">
                  {{formData2.detailAddress}}
                </el-descriptions-item>
                <el-descriptions-item label="备注">{{formData2.remark}}</el-descriptions-item>
              </el-descriptions>
            </div>   
        </el-card> 
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
import customerinfoimg from '@/assets/imgs/customerinfo.png'
import SmsTemplateSendForm from '@/views/components/sms/SmsTemplateSendForm.vue'
import MailTemplateSendForm from '@/views/components/email/MailTemplateSendForm.vue'
import { CustomerContactsApi, CustomerContactsVO } from '@/api/crm/crmcustomercontacts'
import { formatDate } from '@/utils/formatTime'

/** 跟进记录 表单 */
defineOptions({ name: 'RecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
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
  dialogTitle.value = '联系人详情'
  formType.value = type
  formData2.value =  await CustomerContactsApi.getCustomerContacts(id)
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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