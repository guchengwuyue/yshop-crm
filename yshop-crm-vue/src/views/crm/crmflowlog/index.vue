<template>
  <ContentWrap>
    <el-menu
    :default-active="activeIndex"
    style="margin-bottom:10px"
    mode="horizontal"
    @select="handleSelect"
  >
      <el-menu-item :index="0"> 
        <el-badge :value="total" class="item">
          待我审批的
        </el-badge>
      </el-menu-item>
      <el-menu-item :index="1">我已审批的</el-menu-item>
    </el-menu>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="提交的审批人" align="center" prop="otherUser" />
      <el-table-column label="合同编号" align="center" prop="otherNo" width="150">
      <template #default="scope">
          <div @click="openForm('detail',scope.row.typesId)" style="color:#409EFF;cursor: pointer">{{scope.row.otherNo}}</div>
        </template>
      </el-table-column>
      <el-table-column label="合同名称" align="center" prop="otherName" />
      <el-table-column label="审批人" align="center" prop="adminName" />
      <el-table-column label="审批状态" align="center" prop="status">
        <template #default="scope">
          <div>
            <el-tag v-if="scope.row.status === 0"  type="primary">待审批</el-tag>
            <el-tag v-else-if="scope.row.status === 1"  type="success">审核通过</el-tag>
            <el-tag v-else  type="danger">审核拒绝</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="审批意见" align="center" prop="remark" />
      <el-table-column
        label="审批时间"
        align="center"
        prop="adminTime"
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
      <el-table-column label="操作" align="center" v-if="activeIndex == 0">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('agree', scope.row.id)"
          >
            通过
          </el-button>
          <el-button
            link
            type="danger"
            @click="openForm('refuse',scope.row.id)"
          >
            拒绝
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
  <FlowLogForm ref="formRef" @success="getList" />
  <ContractForm3 ref="formRef3"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { FlowLogApi, FlowLogVO } from '@/api/crm/crmflowlog'
import FlowLogForm from './FlowLogForm.vue'
import ContractForm3 from '@/views/crm/crmcontract/ContractForm3.vue'

/** 审核日志 列表 */
defineOptions({ name: 'CrmFlowLog' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<FlowLogVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  status: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const activeIndex = ref(0)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await FlowLogApi.getFlowLogPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleSelect = (key) => {
  queryParams.status = key
  activeIndex.value = key
  getList()
}


/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const formRef3 = ref()
const openForm = (type: string, id?: number) => {
  if(type == 'detail'){
    formRef3.value.open(type, id)
  }else{
    formRef.value.open(type, id)
  }
  
}



/** 初始化 **/
onMounted(() => {
  getList()
})
</script>