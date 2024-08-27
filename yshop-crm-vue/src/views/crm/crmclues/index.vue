<template>
  <ContentWrap>
    <el-menu
    :default-active="activeIndex"
    style="margin-bottom:10px"
    mode="horizontal"
    @select="handleSelect"
  >
      <el-menu-item index="my">我的线索</el-menu-item>
      <el-menu-item index="sub">下属线索</el-menu-item>
    </el-menu>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="线索" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入线索"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="电话" prop="telephone">
        <el-input
          v-model="queryParams.telephone"
          placeholder="请输入电话"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="线索状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option label="无效线索" :value="-1" />
          <el-option label="未转客户" :value="0" />
          <el-option label="转成客户" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="客户级别" prop="level">
        <el-select v-model="queryParams.level" clearable placeholder="请选择客户级别" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_LEVEL)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="客户行业" prop="industry">
        <el-select v-model="queryParams.industry" clearable placeholder="请选择客户行业" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_INDUSTRY)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="客户来源" prop="source">
        <el-select v-model="queryParams.source" clearable placeholder="请选择客户行业" class="!w-240px">
          <el-option
              v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_SOURCE)"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="ownerUserName">
        <el-input
          v-model="queryParams.ownerUserName"
          placeholder="请输入负责人ID"
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
          v-hasPermi="['crm:clues:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="线索名称" align="center" prop="name" width="150"  />
      <el-table-column label="客户手机" align="center" prop="mobile" width="120" />
      <el-table-column label="客户电话" align="center" prop="telephone" width="120" />
      <el-table-column label="客户级别" align="center" prop="level">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_LEVEL" :value="scope.row.level" />
        </template>
      </el-table-column>
      <el-table-column label="客户行业" align="center" prop="industry">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_INDUSTRY" :value="scope.row.industry" />
        </template>
      </el-table-column>
      <el-table-column label="客户来源" align="center" prop="source">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_SOURCE" :value="scope.row.source" />
        </template>
      </el-table-column>
      <el-table-column label="线索状态" align="center" prop="status">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.status === 1"  type="success">转成客户</el-tag>
            <el-tag v-else-if="scope.row.status === 0"  type="primary">未转客户</el-tag>
            <el-tag v-else  type="danger">无效线索</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="下次跟进时间"
        align="center"
        prop="nextTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="最近跟进时间"
        align="center"
        prop="followTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="领取时间"
        align="center"
        prop="collectTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="负责人" align="center" prop="ownUserName" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" fixed="right" width="260">
        <template #default="scope">
          <el-button
            link
            type="warning"
            @click="openDetail(scope.row.id)"
          >
            跟进
          </el-button>
          <el-button
            link
            type="success"
            @click="openCustomerDetail(scope.row.customerId)"
            v-if="scope.row.status === 1"
          >
            客户
          </el-button>
          <el-button
            link
            type="success"
            @click="openCustomer(scope.row.id)"
            v-if="scope.row.status != 1"
          >
            转客
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleOpen(scope.row.id)"
          >
          线索池
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['crm:clues:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['crm:clues:delete']"
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
  <CluesForm ref="formRef" @success="getList" />
  <RecordForm ref="recordFormRef"  />
  <CustomerForm ref="customerFormRef" @success="getList"  />
  <CustomerRecordForm ref="customerRecordFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CluesApi, CluesVO } from '@/api/crm/crmclues'
import CluesForm from './CluesForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmcluesrecord/RecordForm.vue'
import CustomerForm from '@/views/crm/crmclues/components/customer/CustomerForm.vue'
import CustomerRecordForm from '@/views/crm/crmrecord/RecordForm.vue'

/** 线索 列表 */
defineOptions({ name: 'CrmClues' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CluesVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  mobile: undefined,
  telephone: undefined,
  status: undefined,
  level: undefined,
  industry: undefined,
  source: undefined,
  remark: undefined,
  ownerUserName: undefined, 
  followStatus: undefined,
  relation: 'my'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const activeIndex = ref('my')

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CluesApi.getCluesPage(queryParams)
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

const handleSelect = (key) => {
  queryParams.relation = key
  getList()
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

const customerFormRef = ref()
const openCustomer = (id?: number) => {
  customerFormRef.value.open('', id)
}

const customerRecordFormRef = ref()
const openCustomerDetail = (id?: number) => {
  customerRecordFormRef.value.open('', id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CluesApi.deleteClues(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const handleOpen  = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm("确定把线索放入线索池")
    // 发起删除
    await CluesApi.openClues(id)
    message.success('放入成功')
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
    const data = await CluesApi.exportClues(queryParams)
    download.excel(data, '线索.xls')
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