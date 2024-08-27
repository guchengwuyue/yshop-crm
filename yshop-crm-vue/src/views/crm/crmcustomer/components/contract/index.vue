<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="85px"
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
      <el-table-column label="合同编号" align="center" prop="number" width="150" />
      <el-table-column label="合同名称" align="center" prop="name" width="150">
        <template #default="scope">
          <span v-if="scope.row.isExpire != ''"><el-tag type="danger" size="small">{{scope.row.isExpire}}</el-tag></span><span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同金额" align="center" prop="money" />
      <el-table-column label="已收到款项" align="center" prop="returnMoney" width="100" />   
      <el-table-column
        label="开始时间"
        align="center"
        prop="startTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="结束时间"
        align="center"
        prop="endTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="公司签约人" align="center" prop="orderAdminName" width="100" />
      <el-table-column label="创建人" align="center" prop="creatorName" />
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
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'



/** 合同 列表 */
defineOptions({ name: 'CrmContract' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ContractVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  checkStatus: undefined,
  startTime: [],
  endTime: [],
  orderAdminName: undefined,
  creatorName: undefined,
  customerName: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const props = defineProps<{
  customerId: 0,
}>()

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    queryParams.customerId = props.customerId
    const data = await ContractApi.getContractPage(queryParams)
    list.value = data.list
    let now = new Date().getTime()
    list.value.map((value) => {
      if(value.endTime < now){
        value.isExpire = '过期'
      }else{
        value.isExpire = ''
      }
        
    })
    total.value = data.total
  } finally {
    loading.value = false
  }
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