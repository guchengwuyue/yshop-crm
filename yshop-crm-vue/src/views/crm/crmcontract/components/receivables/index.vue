<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="回款编号" align="center" prop="number" width="150" />
      <el-table-column label="客户名称" align="center" prop="customerName" width="150" />
      <el-table-column label="合同名称" align="center" prop="contractName" width="150" />
      <el-table-column label="回款金额" align="center" prop="money" />
      <el-table-column label="审核状态" align="center" prop="checkStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CONTRACT_CHECK_STATUS" :value="scope.row.checkStatus" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ContractReceivablesApi, ContractReceivablesVO } from '@/api/crm/crmcontractreceivables'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

/** 回款 列表 */
defineOptions({ name: 'CrmContractReceivables' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ContractReceivablesVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  number: undefined,
  customerName: undefined,
  contractId: undefined,
  contractName: undefined,
  creatorName: undefined,
  checkStatus: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const props = defineProps<{
  contractId: 0
}>()

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    queryParams.contractId = props.contractId
    console.log('props.contractId:',props.contractId)
    const data = await ContractReceivablesApi.getContractReceivablesPage(queryParams)
    list.value = data.list
    total.value = data.total
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


/** 初始化 **/
onMounted(() => {
  getList()
})
</script>