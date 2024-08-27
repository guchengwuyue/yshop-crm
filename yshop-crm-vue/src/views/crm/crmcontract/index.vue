<template>
  <ContentWrap>
    <el-menu
    :default-active="activeIndex"
    style="margin-bottom:10px"
    mode="horizontal"
    @select="handleSelect"
  >
      <el-menu-item index="my">我的合同</el-menu-item>
      <el-menu-item index="sub">下属合同</el-menu-item>
    </el-menu>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="85px"
    >
      <el-form-item label="客户" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
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
      <el-form-item label="审核状态" prop="checkStatus">
        <el-select v-model="queryParams.checkStatus" clearable placeholder="请选择审核状态" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CONTRACT_CHECK_STATUS)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
    
      <el-form-item label="公司签约人" prop="orderAdminName">
        <el-input
          v-model="queryParams.orderAdminName"
          placeholder="请输入公司签约人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="creatorName">
        <el-input
          v-model="queryParams.creatorName"
          placeholder="请输入创建人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="处理状态" prop="expireHandle">
        <el-select v-model="queryParams.expireHandle" clearable placeholder="请选择审核状态" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CONTRACT_HANDLE_STATUS)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['crm:contract:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
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
      <el-table-column label="操作" align="center" fixed="right" width="200">
        <template #default="scope">
          <el-button
            link
            type="success"
            @click="openRece(scope.row.id,scope.row.name)"
          >
            收款
          </el-button>
          <el-button
            link
            type="warning"
            @click="openForm('check', scope.row.id)"
            v-if="scope.row.isCheck"
          >
            审核
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['crm:contract:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['crm:contract:delete']"
          >
            删除
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

  <!-- 表单弹窗：添加/修改 -->
  <ContractForm ref="formRef" @success="getList" />
  <ContractForm2 ref="formRef2" @success="getList" />
  <ContractForm3 ref="formRef3"  />
  <RecordForm ref="recordFormRef"  />
  <ContractReceivablesForm ref="contractReceivablesFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ContractApi, ContractVO } from '@/api/crm/crmcontract'
import ContractForm from './ContractForm.vue'
import ContractForm2 from './ContractForm2.vue'
import ContractForm3 from './ContractForm3.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'
import ContractReceivablesForm from '@/views/crm/crmcontractreceivables/ContractReceivablesForm.vue'


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
  relation: 'my'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const activeIndex = ref('my')

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

const handleSelect = (key) => {
  queryParams.relation = key
  getList()
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

const contractReceivablesFormRef = ref()
const openRece = (id,name) => {
  contractReceivablesFormRef.value.open2(name, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await ContractApi.deleteContract(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ContractApi.exportContract(queryParams)
    download.excel(data, '合同.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>