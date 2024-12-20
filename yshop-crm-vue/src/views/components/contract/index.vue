<template>
  <el-dialog
    v-model="groupVisible"
    title="选择合同"
    width="65%"
    top="50px"
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    :modal="false"
  >
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="85px"
    >
      <el-form-item label="合同名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入合同名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="合同编号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>     
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="合同编号" align="center" prop="number" width="150"/>
      <el-table-column label="合同名称" align="center" prop="name" width="150">
        <template #default="scope">
          <span v-if="scope.row.isExpire != ''"><el-tag type="danger" size="small">{{scope.row.isExpire}}</el-tag></span><span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" width="180" />
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
      <el-table-column label="操作" align="center" fixed="right" width="200">
        <template #default="scope">
          <el-button
              link
              type="danger"
              @click="doSelect(scope.row)"
            >
            选择
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

  </el-dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
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
  customerName: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const groupVisible = ref(false)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
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
    console.log('new Date().getTime():',new Date().getTime())
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

const open = (customerId) => {
  groupVisible.value = true;
  queryParams.customerId = customerId
  getList()
}
defineExpose({ open }) 

const emit = defineEmits(['saveContract'])
const doSelect = async (row) => {
  if(row.checkStatus !== 2){
    message.error('请选择已经审核过的合同')
    return
  }
  emit('saveContract', row);
  groupVisible.value = false;
}


/** 初始化 **/
//onMounted(() => {
//   getList()
// })
</script>