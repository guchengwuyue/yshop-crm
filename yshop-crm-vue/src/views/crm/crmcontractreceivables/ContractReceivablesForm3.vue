<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="50%">
    <el-card> 
      <template #header>
        <el-row>
          <el-col :span="12">
            <div style="display:flex;align-items: center;">
              <el-image style="width: 40px; height: 30px" :src="customerinfoimg"  />
              <span style="margin-left:8px;font-weight:bolder">{{formData.number}}</span>
            </div>
          </el-col>
        </el-row>
      </template>

      <div>
        <el-descriptions title="基本信息">
          <el-descriptions-item label="回款编号">{{formData.number}}</el-descriptions-item>
          <el-descriptions-item label="客户">{{formData.customerName}}</el-descriptions-item>
          <el-descriptions-item label="合同">{{formData.contractName}}</el-descriptions-item>
          <el-descriptions-item label="回款金额">{{formData.money}}</el-descriptions-item>
          <el-descriptions-item label="回款账户"> 
            <span v-for="dict in getIntDictOptions(DICT_TYPE.CRM_PAYEE_TYPE)" :key="dict.value">
                <span v-if="formData.accountId == dict.value">{{dict.label}}</span>
            </span>  
          </el-descriptions-item>
          <el-descriptions-item label="回款日期">{{formData.returnTime}}</el-descriptions-item>
          <el-descriptions-item label="备注">{{formData.remark}}</el-descriptions-item>
        </el-descriptions>
      </div> 
    </el-card>
    <el-card> 
      <template #header>
        审核日志
      </template>
      <FlowStep :flowAdmins="flowAdmins" />
    </el-card> 
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { ContractReceivablesApi, ContractReceivablesVO } from '@/api/crm/crmcontractreceivables'
import FlowStep from '@/views/components/flow/index.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { FlowApi } from '@/api/crm/crmflow'
import * as UserApi from '@/api/system/user'
import LogList from './components/log/index.vue'
import customerinfoimg from '@/assets/imgs/customerinfo.png'

/** 回款 表单 */
defineOptions({ name: 'ContractReceivablesForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('回款详情') // 弹窗的标题
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
const receivablesId = ref(0)
const flowAdmins = ref([])

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  receivablesId.value = id
  //resetForm()
  // 修改时，设置数据
  if (id) {
    flowAdmins.value = await FlowApi.getFlowUserIds('receivables',id ? id : 0)
    formLoading.value = true
    try {
      formData.value = await ContractReceivablesApi.getContractReceivables(id)
      formData.value.agreeType = 1
      //formData.value.flowAdminId = formData.value.flowAdminId2
    } finally {
      formLoading.value = false
    }
  }
  userList.value = await UserApi.getSimpleUserList()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调


/** 重置表单 */

</script>