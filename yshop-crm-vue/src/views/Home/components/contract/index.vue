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
      <el-table-column label="合同编号" align="center" prop="number" width="150">
        <template #default="scope">
          <div @click="openForm('detail',scope.row.id)" style="color:#409EFF;cursor: pointer">{{scope.row.number}}</div>
        </template>
      </el-table-column>
      <el-table-column label="合同名称" align="center" prop="name" width="150">
        <template #default="scope">
          <span v-if="scope.row.isExpire != ''"><el-tag type="danger" size="small">{{scope.row.isExpire}}</el-tag></span><span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" width="180">
        <template #default="scope">
          <div @click="openDetail(scope.row.customerId)" style="color:#409EFF;cursor: pointer">{{scope.row.customerName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="合同金额" align="center" prop="money" />
      <el-table-column label="已收到款项" align="center" prop="returnMoney" width="100" />   
      <el-table-column
        label="下单时间"
        align="center"
        prop="orderTime"
        :formatter="dateFormatter"
        width="180px"
      />
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
  <ContractForm2 ref="formRef2" @success="getList" />
  <ContractForm3 ref="formRef3"  />
  <RecordForm ref="recordFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import ContractForm2 from '@/views/crm/crmcontract/ContractForm2.vue'
import ContractForm3 from '@/views/crm/crmcontract/ContractForm3.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'


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
  isCheck: true,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const emit = defineEmits(['getContractCount']) 

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ContractApi.getContractPage(queryParams)
    list.value = data.list
    emit('getContractCount',data.total)
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
const formRef3 = ref()
const openForm = (type: string, id?: number) => {
  if(type == 'check'){
    formRef2.value.open(type, id)
  }else if(type == 'detail'){
    formRef3.value.open(type, id)
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