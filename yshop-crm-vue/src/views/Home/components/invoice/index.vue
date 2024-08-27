<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 刷新</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="合同编号" align="center" prop="contractNumber" width="150">
        <template #default="scope">
          <div @click="openForm('contractDetail',scope.row.contractId)" style="color:#409EFF;cursor: pointer">{{scope.row.contractNumber}}</div>
        </template>
      </el-table-column>
      <el-table-column label="合同名称" align="center" prop="contractName"  width="150" />
      <el-table-column label="客户名称" align="center" prop="customerName" width="180">
        <template #default="scope">
          <div @click="openDetail(scope.row.customerId)" style="color:#409EFF;cursor: pointer">{{scope.row.customerName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="发票金额" align="center" prop="money" />
      <el-table-column label="开票主体" align="center" prop="invoiceBody" width="150"  />
      <el-table-column label="发票抬头" align="center" prop="invoiceName" width="150" />
      <el-table-column label="抬头类型" align="center" prop="invoiceIssue">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.status === 1"  type="success">企业</el-tag>
            <el-tag v-else-if="scope.row.status === 2"  type="primary">个人</el-tag>
            <el-tag v-else  type="danger">事业单位</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="发票类型" align="center" prop="invoiceType">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.status === 1"  type="success">增普</el-tag>
            <el-tag v-else  type="danger">增专</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="统一社会信用代码" align="center" prop="registerNo" width="150" />
      <el-table-column label="接收邮箱" align="center" prop="email" />
      <el-table-column label="收件人" align="center" prop="userName" />
      <el-table-column label="收件手机" align="center" prop="userPhone" />
      <el-table-column label="邮寄地址" align="center" prop="userAddress" />
      <el-table-column label="审核状态" align="center" prop="checkStatus">
      <template #default="scope">
          <dict-tag :type="DICT_TYPE.CONTRACT_CHECK_STATUS" :value="scope.row.checkStatus" />
        </template>
      </el-table-column>
      <el-table-column label="开票状态" align="center" prop="status">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.status === 2"  type="success">已开票</el-tag>
            <el-tag v-else-if="scope.row.status === 1"  type="primary">未转客户</el-tag>
            <el-tag v-else-if="scope.row.status === 0"  type="warning">未开票</el-tag>
            <el-tag v-else  type="danger">开票失败</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" fixed="right" width="100">
        <template #default="scope">
          <el-button
            link
            type="warning"
            @click="openForm('check', scope.row.id)"
            v-if="scope.row.isCheck"
          >
            审核
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <InvoiceForm2 ref="formRef2" @success="getList" />
  <ContractDetail ref="contractDetailRef"  />
  <RecordForm ref="recordFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { InvoiceApi, InvoiceVO } from '@/api/crm/crminvoice'
import InvoiceForm2 from '@/views/crm/crminvoice/InvoiceForm2.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import ContractDetail from '@/views/crm/crmcontract/ContractForm3.vue'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'

/** 发票 列表 */
defineOptions({ name: 'CrmInvoice' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<InvoiceVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  isCheck: true,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const emit = defineEmits(['getInvoiceCount']) 
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await InvoiceApi.getInvoicePage(queryParams)
    list.value = data.list
    total.value = data.total
    emit('getInvoiceCount',data.total)
  } finally {
    loading.value = false
  }
}


/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const formRef2 = ref()
const contractDetailRef = ref()
const logRef = ref()
const openForm = (type: string, id?: number) => {
  if(type == 'check'){
    formRef2.value.open(type, id)
  }else if(type == 'contractDetail'){
    contractDetailRef.value.open(type, id)
  }else if(type == 'log'){
    logRef.value.open(id)
  }else{
    formRef.value.open(type, id)
  }
 
}

const recordFormRef = ref()
const openDetail = (id?: number) => {
  recordFormRef.value.open('', id)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>