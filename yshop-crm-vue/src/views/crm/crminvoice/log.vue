<template>
   <Dialog :title="dialogTitle" v-model="dialogVisible" width="55%">
  <!-- 列表 -->
    <ContentWrap>
      <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
        <el-table-column label="ID" align="center" prop="id" />
        <el-table-column label="审核人" align="center" prop="nickname" />
        <el-table-column label="审核意见" align="center" prop="remark" />
        <el-table-column label="审核状态" align="center" prop="status">
          <template #default="scope">
            <div>
              <el-tag v-if="scope.row.status === 1"  type="success">审核通过</el-tag>
              <el-tag v-else  type="danger">未通过</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="审核流程" align="center" prop="isEnd">
          <template #default="scope">
            <div>
              <el-tag v-if="scope.row.isEnd === 1"  type="success">审核已结束</el-tag>
              <el-tag v-else  type="danger">未结束</el-tag>
            </div>
          </template>
        </el-table-column>
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
  </Dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { FlowLogApi, FlowLogVO } from '@/api/crm/crmflowlog'

/** 审核日志 列表 */
defineOptions({ name: 'CrmFlowLog' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('审核日志') // 弹窗的标题
const loading = ref(true) // 列表的加载中
const list = ref<FlowLogVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  flowId: undefined,
  typesId: undefined,
  adminId: undefined,
  types: 'invoice',
  nickname: undefined,
  status: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中


const open = async (id) => {
  dialogVisible.value = true
  try {
    queryParams.typesId = id
    const data = await FlowLogApi.getFlowLogPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗


</script>