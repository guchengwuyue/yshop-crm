<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="跟进内容" align="center" prop="content" />
      <el-table-column label="跟进方式" align="center" prop="recordType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.CRM_FOLLOW_TYPE" :value="scope.row.recordType" />
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
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { RecordApi, RecordVO } from '@/api/crm/crmrecord'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'


/** 跟进记录 列表 */
defineOptions({ name: 'CrmRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<RecordVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  types: 'customer',
  typesId: 0
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const props = defineProps<{
  myId: 0,
  customerId: 0
}>()


/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    queryParams.typesId = props.customerId
    const data = await RecordApi.getRecordPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}


watch(
  () => props.myId,
  (val) => {
    getList()
   
  },
  { immediate: true }
)

// watch(
//   () => props.customerId,
//   (val) => {
//     getList()
   
//   },
//   { immediate: true }
// )




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

// /** 添加/修改操作 */
// const formRef = ref()
// const openForm = (type: string, id?: number) => {
//   formRef.value.open(type, id)
//}

// /** 删除按钮操作 */
// const handleDelete = async (id: number) => {
//   try {
//     // 删除的二次确认
//     await message.delConfirm()
//     // 发起删除
//     await RecordApi.deleteRecord(id)
//     message.success(t('common.delSuccess'))
//     // 刷新列表
//     await getList()
//   } catch {}
// }

/** 导出按钮操作 */
// const handleExport = async () => {
//   try {
//     // 导出的二次确认
//     await message.exportConfirm()
//     // 发起导出
//     exportLoading.value = true
//     const data = await RecordApi.exportRecord(queryParams)
//     download.excel(data, '跟进记录.xls')
//   } catch {
//   } finally {
//     exportLoading.value = false
//   }
// }

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>