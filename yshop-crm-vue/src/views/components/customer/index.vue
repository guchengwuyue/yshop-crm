<template>
  <el-dialog
    v-model="groupVisible"
    title="选择客户"
    width="65%"
    top="50px"
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    :modal="false"
  >
    <ContentWrap>
      <el-menu
      :default-active="activeIndex"
      style="margin-bottom:10px"
      mode="horizontal"
      @select="handleSelect"
    >
        <el-menu-item index="my">我的客户</el-menu-item>
        <el-menu-item index="sub">下属客户</el-menu-item>
      </el-menu>
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
        <el-table-column label="客户名称" align="center" prop="name" width="150" />
        <el-table-column label="手机" align="center" prop="mobile" width="120" />
        <el-table-column label="电话" align="center" prop="telephone" width="120" />
        <el-table-column label="客户级别" align="center" prop="level" width="80">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_LEVEL" :value="scope.row.level" />
          </template>
        </el-table-column>
        <el-table-column label="客户行业" align="center" prop="industry" width="80">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_INDUSTRY" :value="scope.row.industry" />
          </template>
        </el-table-column>
        <el-table-column label="客户来源" align="center" prop="source" width="80">
          <template #default="scope">
            <dict-tag :type="DICT_TYPE.CRM_CUSTOMER_SOURCE" :value="scope.row.source" />
          </template>
        </el-table-column>
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
import download from '@/utils/download'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import CustomerForm from './CustomerForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'

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
  userId: undefined,
  // province: undefined,
  // city: undefined,
  // area: undefined,
  weixin: undefined,
  qq: undefined,
  relation: 'my'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const activeIndex = ref('my')
const groupVisible = ref(false)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CustomerApi.getCustomerPage(queryParams)
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

const handleSelect = (key) => {
  queryParams.relation = key
  getList()
}

const open = () => {
  groupVisible.value = true;
  getList()
}

defineExpose({ open }) 

const emit = defineEmits(['saveCustomer'])
const doSelect = async (row) => {
  emit('saveCustomer', row);
  groupVisible.value = false;
}



/** 初始化 **/
// onMounted(() => {
//   getList()
// })
</script>