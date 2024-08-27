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
      <el-form-item>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['pay:merchant-details:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="id(+租户ID)" align="center" prop="detailsId" />
      <el-table-column label="支付类型(支付渠道)" align="center" prop="payType" />
      <el-table-column label="应用id" align="center" prop="appid" />
      <el-table-column label="商户id" align="center" prop="mchId" />
      <el-table-column label="异步回调地址" align="center" prop="notifyUrl" />
      <el-table-column label="同步回调地址" align="center" prop="returnUrl" />
      <el-table-column label="签名方式" align="center" prop="signType" />
      <el-table-column label="是否为测试环境" align="center" prop="isTest" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="120"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.detailsId)"
            v-hasPermi="['pay:merchant-details:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.detailsId)"
            v-hasPermi="['pay:merchant-details:delete']"
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
  <MerchantDetailsForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts" name="MerchantDetails">
import * as MerchantDetailsApi from '@/api/pay/merchantDetails'
import MerchantDetailsForm from './MerchantDetailsForm.vue'
import { dateFormatter } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  payType: null,
  appid: null,
  mchId: null,
  certStoreType: null,
  keyPrivate: null,
  keyPublic: null,
  keyCert: null,
  keyCertPwd: null,
  notifyUrl: null,
  returnUrl: null,
  signType: null,
  seller: null,
  subAppId: null,
  subMchId: null,
  inputCharset: null,
  isTest: null
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MerchantDetailsApi.getMerchantDetailsPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}


/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await MerchantDetailsApi.deleteMerchantDetails(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}



/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
