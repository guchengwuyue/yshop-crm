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
      <el-form-item label="客户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客户名称"
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
      <el-form-item label="成交状态" prop="dealStatus">
        <el-select
          v-model="queryParams.dealStatus"
          placeholder="请选择成交状态"
          clearable
          class="!w-240px"
        >
          <el-option label="未成交" value="0" />
          <el-option label="成交" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="ownUserName">
        <el-input
          v-model="queryParams.ownUserName"
          placeholder="请输入负责人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createName">
        <el-input
          v-model="queryParams.createName"
          placeholder="请输入创建人"
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
          v-hasPermi="['crm:customer:create']"
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
      <el-table-column label="客户名称" align="center" prop="name" width="200" />
      <el-table-column label="手机" align="center" prop="mobile" width="120" />
      <el-table-column label="电话" align="center" prop="telephone" width="120" />
      <el-table-column label="成交状态" align="center" prop="dealStatus">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.dealStatus === 1"  type="success">成交</el-tag>
            <el-tag v-else  type="danger">未成交</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="ownUserName" width="120" />
      <el-table-column label="创建人" align="center" prop="createName" width="120" />
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

  <!-- 表单弹窗：添加/修改 -->
  <CustomerForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import CustomerForm from '@/views/crm/crmcustomer/CustomerForm.vue'

/** 客户 列表 */
defineOptions({ name: 'CrmCustomer' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CustomerVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  mobile: undefined,
  telephone: undefined,
  dealStatus: undefined,
  level: undefined,
  industry: undefined,
  tags: undefined,
  source: undefined,
  remark: undefined,
  ownUserName: undefined,
  createName: undefined,
  weixin: undefined,
  qq: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CustomerApi.getCustomerPage2(queryParams)
    list.value = data.list
    list.value.map((value) => {
        if(value.tags != ''){
          value.tags = value.tags.split(',')
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
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}


/** 初始化 **/
onMounted(() => {
  getList()
})
</script>