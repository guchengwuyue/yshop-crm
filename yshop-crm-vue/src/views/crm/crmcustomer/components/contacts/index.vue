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
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['crm:customer-contacts:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="客户名称" align="center" prop="customerName" width="150"  />
      <el-table-column label="姓名" align="center" prop="name" width="150"  />
      <el-table-column label="手机" align="center" prop="mobile" width="120" />
      <el-table-column label="电话" align="center" prop="telephone" width="120" />
      <el-table-column label="微信号" align="center" prop="wechat" />
      <el-table-column label="电子邮箱" align="center" prop="email" />
      <el-table-column label="职务" align="center" prop="post" />
      <el-table-column label="性别" align="center" prop="sex">
       <template #default="scope">
          <div>
            <el-tag v-if="scope.row.sex == 1" type="success">男</el-tag>
            <el-tag v-else-if="scope.row.sex == 0" type="Warning">女</el-tag>
            <el-tag v-else  type="danger">未知</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="关键决策人" align="center" prop="decision" width="120">
      <template #default="scope">
          <div>
            <el-tag v-if="scope.row.decision == 1" type="success">是</el-tag>
            <el-tag v-else-if="scope.row.decision == 0" type="Warning">不是</el-tag>
            <el-tag v-else  type="danger">未知</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="下次联系时间"
        align="center"
        prop="nextTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" fixed="right" width="150">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['crm:customer-contacts:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['crm:customer-contacts:delete']"
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
  <CustomerContactsForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CustomerContactsApi, CustomerContactsVO } from '@/api/crm/crmcustomercontacts'
import CustomerContactsForm from './CustomerContactsForm.vue'

/** 联系人 列表 */
defineOptions({ name: 'CrmCustomerContacts' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CustomerContactsVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  customerName: undefined,
  name: undefined,
  mobile: undefined,
  customerId: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const props = defineProps<{
  customerId: 0,
  customerName: ''
}>()

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    queryParams.customerId = props.customerId
    const data = await CustomerContactsApi.getCustomerContactsPage(queryParams)
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id,props.customerId,props.customerName)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CustomerContactsApi.deleteCustomerContacts(id)
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
    const data = await CustomerContactsApi.exportCustomerContacts(queryParams)
    download.excel(data, '联系人.xls')
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