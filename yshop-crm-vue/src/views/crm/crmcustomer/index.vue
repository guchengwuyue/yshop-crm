<template>
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
      <!-- <el-form-item label="城市检索" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省份id"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item> -->
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
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['crm:customer:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
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
      <el-table-column label="客户名称" align="center" prop="name" width="200">
        <template #default="scope">
          <div @click="openDetail(scope.row.id)" style="color:#409EFF;cursor: pointer">{{scope.row.name}}</div>
          <div v-if="scope.row.tags.length > 0" style="display:flex;flex-wrap:wrap;text-align:center;justify-content:center">
            <el-tag type="danger" round size="small"  v-for="(tag,idx) in scope.row.tags" :key="idx" >{{tag}} </el-tag>
          </div>
        </template>
      </el-table-column>
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
      <el-table-column label="负责人" align="center" prop="ownUserName" width="150" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" fixed="right" width="200">
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
            type="danger"
            @click="handleOpen(scope.row.id)"
          >
            公海
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['crm:customer:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['crm:customer:delete']"
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
  <CustomerForm ref="formRef" @success="getList" />
  <RecordForm ref="recordFormRef"  />
  <SmsTemplateSendForm ref="smsTemplateSendFormRef"  />
  <MailTemplateSendForm ref="mailTemplateSendFormRef"  />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CustomerApi, CustomerVO } from '@/api/crm/crmcustomer'
import CustomerForm from './CustomerForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RecordForm from '@/views/crm/crmrecord/RecordForm.vue'
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
const selectCustomers = ref([])
const isDisabled = ref(true)

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

const handleSelectionChange = (val) => {
  if(val.length > 0) {
    isDisabled.value = false
  }
  selectCustomers.value = val
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

const handleOpen  = async (id: number) => {
  try {
    // 删除的二次确认
    await message.confirm("确定把客户放入公海")
    // 发起删除
    await CustomerApi.openCustomer(id)
    message.success('放入成功')
    // 刷新列表
    await getList()
  } catch {}
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CustomerApi.deleteCustomer(id)
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
    const data = await CustomerApi.exportCustomer(queryParams)
    download.excel(data, '客户.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

const smsTemplateSendFormRef = ref()
const openSms = () => {
  smsTemplateSendFormRef.value.open(selectCustomers.value,true)
}

const mailTemplateSendFormRef = ref()
const openMail = () => {
  mailTemplateSendFormRef.value.open(selectCustomers.value,true)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>