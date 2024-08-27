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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
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
      <el-table-column label="创建人" align="center" prop="createName" />
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
            type="success"
            @click="handleReceive(scope.row.id)"
          >
            领取
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
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CluesApi, CluesVO } from '@/api/crm/crmclues'
import CluesForm from './CluesForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

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
  type: 'open'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

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

const handleReceive = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm('确认要领取？')
    // 发起删除
    await CluesApi.receClues(id)
    message.success('领取成功')
    // 刷新列表
    await getList()
  } catch {}
}


/** 初始化 **/
onMounted(() => {
  getList()
})
</script>