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
      <el-form-item label="微信" prop="weixin">
        <el-input
          v-model="queryParams.weixin"
          placeholder="请输入微信"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="QQ" prop="qq">
        <el-input
          v-model="queryParams.qq"
          placeholder="请输入QQ"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
  
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          plain
          @click="openSms"
          :disabled = "isDisabled"
        >
          <Icon icon="ep:notification" class="mr-5px" /> 发短信
        </el-button>
        <el-button
          plain
          @click="openMail"
          :disabled = "isDisabled"
        >
          <Icon icon="ep:message" class="mr-5px" /> 发邮件
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" />
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
        label="下次跟进"
        align="center"
        prop="nextTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="最后跟进"
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
      <el-table-column label="微信" align="center" prop="weixin" width="120" />
      <el-table-column label="QQ" align="center" prop="qq" width="120" />
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

  <SmsTemplateSendForm ref="smsTemplateSendFormRef"  />
  <MailTemplateSendForm ref="mailTemplateSendFormRef"  />

</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import SmsTemplateSendForm from '@/views/components/sms/SmsTemplateSendForm.vue'
import MailTemplateSendForm from '@/views/components/email/MailTemplateSendForm.vue'


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
  weixin: undefined,
  qq: undefined,
  type: 'open'
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const selectCustomers = ref([])
const isDisabled = ref(true)

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

const handleSelectionChange = (val) => {
  if(val.length > 0) {
    isDisabled.value = false
  }
  selectCustomers.value = val
}

const smsTemplateSendFormRef = ref()
const openSms = () => {
  smsTemplateSendFormRef.value.open(selectCustomers.value,true)
}

const mailTemplateSendFormRef = ref()
const openMail = () => {
  mailTemplateSendFormRef.value.open(selectCustomers.value,true)
}


const handleReceive = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm('确认要领取？')
    // 发起删除
    await CustomerApi.receCustomer(id)
    message.success('领取成功')
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
    const data = await CustomerApi.exportCustomer(queryParams)
    download.excel(data, '客户.xls')
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