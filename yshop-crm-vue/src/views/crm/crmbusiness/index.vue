<template>
  <ContentWrap>
    <el-menu
    :default-active="activeIndex"
    style="margin-bottom:10px"
    mode="horizontal"
    @select="handleSelect"
  >
      <el-menu-item index="my">我的商机</el-menu-item>
      <el-menu-item index="sub">下属商机</el-menu-item>
    </el-menu>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="商机状态" prop="isEnd">
        <el-select v-model="queryParams.isEnd" clearable placeholder="请选择状态" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CRM_BUSINESS_STATUS)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="商机名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商机名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="ownerUserName">
        <el-input
          v-model="queryParams.ownerUserName"
          placeholder="请输入负责人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['crm:business:create']"
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
      <el-table-column label="商机名称" align="center" prop="name" width="150">
        <template #default="scope">
          <div @click="openBusDetail(scope.row.id)" style="color:#409EFF;cursor: pointer">{{scope.row.name}}</div>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" width="150">
        <template #default="scope">
          <div @click="openDetail(scope.row.customerId)" style="color:#409EFF;cursor: pointer">{{scope.row.customerName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="商机预算" align="center" prop="money" />
      <el-table-column label="产品金额" align="center" prop="totalPrice" />
      <el-table-column
        label="预计成交日期"
        align="center"
        prop="dealTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="下次跟进"
        align="center"
        prop="nextTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="负责人" align="center" prop="ownUserName" />
      <el-table-column label="商机状态" align="center" prop="isEnd">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CRM_BUSINESS_STATUS" :value="scope.row.isEnd" />
        </template>
      </el-table-column>
      <el-table-column
        label="添加时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" fixed="right" width="200">
        <template #default="scope">
          <el-button
            link
            type="warning"
            @click="openBusDetail(scope.row.id)"
          >
            跟进
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['crm:business:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['crm:business:delete']"
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
  <BusinessForm ref="formRef" @success="getList" />
  <RecordForm ref="recordFormRef"  />
  <BusRecordForm ref="busRecordFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { BusinessApi, BusinessVO } from '@/api/crm/crmbusiness'
import BusinessForm from './BusinessForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'
import BusRecordForm from '@/views/crm/crmbusinessrecord/RecordForm.vue'

/** 商机 列表 */
defineOptions({ name: 'CrmBusiness' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<BusinessVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  customerId: undefined,
  status: undefined,
  isEnd: undefined,
  name: undefined,
  ownerUserName:undefined,
  relation: 'my'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const activeIndex = ref('my')

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await BusinessApi.getBusinessPage(queryParams)
    list.value = data.list
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
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const recordFormRef = ref()
const openDetail = (id?: number) => {
  recordFormRef.value.open('', id)
}

const busRecordFormRef = ref()
const openBusDetail = (id?: number) => {
  busRecordFormRef.value.open('', id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await BusinessApi.deleteBusiness(id)
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
    const data = await BusinessApi.exportBusiness(queryParams)
    download.excel(data, '商机.xls')
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