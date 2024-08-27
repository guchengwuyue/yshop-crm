<template>
  <el-row :gutter="20">
  <!-- 左侧部门树 -->
  <el-col :span="4" :xs="24">
    <ContentWrap class="h-1/1">
      <CateTree @node-click="handleDeptNodeClick" />
    </ContentWrap>
  </el-col>
  <el-col :span="20" :xs="24">
    <ContentWrap>
      <!-- 搜索工作栏 -->
      <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="68px"
      >
        <el-form-item label="商品名称" prop="storeName">
          <el-input
            v-model="queryParams.storeName"
            placeholder="请输入商品名称"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="店铺名称" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
          <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          <!-- <router-link :to="{ path: '/product/store-product/product-crreate' }"> -->
          <el-button
            type="primary"
            plain
            @click="openForm('create')"
            v-hasPermi="['shop:store-product:create']"
          >
            <Icon icon="ep:plus" class="mr-5px" /> 新增
          </el-button>
        </el-form-item>
      </el-form>
    </ContentWrap>

    <!-- 列表 -->
    <ContentWrap>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane v-for="item in tableTabs" :label="item.title" :key="item.name" :name="item.name">
          <el-table v-loading="loading" :data="list">
            <el-table-column label="id" align="center" width="50" prop="id" />
            <el-table-column label="商品图片" align="center" prop="image" >
              <template #default="scope">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="scope.row.image"
                  :zoom-rate="1.2"
                  :preview-src-list="[scope.row.image]"
                  :initial-index="0"
                  :z-index="900"
                  :hide-on-click-modal="true"
                  :preview-teleported="true"
                  fit="cover"
                />
              </template>
            </el-table-column>
            <el-table-column label="商品名称" align="center" prop="storeName" />
            <el-table-column label="商品价格" align="center" prop="price" />
            <el-table-column label="销量" align="center"  width="80" prop="sales" />
            <el-table-column label="库存" align="center"  width="80" prop="stock" />
            <el-table-column label="状态" align="center" prop="isShow" >
              <template #default="scope">
                <div @click="onSale(scope.row.id,scope.row.isShow)">
                  <el-tag v-if="scope.row.isShow === 1" style="cursor: pointer" type="success">已上架</el-tag>
                  <el-tag v-else style="cursor: pointer" type="danger">已下架</el-tag>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="添加时间"
              align="center"
              prop="createTime"
              :formatter="dateFormatter"
            />
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button
                  link
                  type="primary"
                  @click="openForm('update', scope.row.id)"
                  v-hasPermi="['shop:store-product:update']"
                >
                  编辑
                </el-button>
                <el-button
                  link
                  type="danger"
                  @click="handleDelete(scope.row.id)"
                  v-hasPermi="['shop:store-product:delete']"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <!-- 分页 -->
      <Pagination
        :total="total"
        v-model:page="queryParams.pageNo"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </ContentWrap>
   </el-col>
  </el-row>
  <!-- 表单弹窗：添加/修改 -->
  <StoreProductForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts" name="StoreProduct">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as StoreProductApi from '@/api/mall/product/product'
import StoreProductForm from './StoreProductForm.vue'
import CateTree from './CateTree.vue'
import type { TabsPaneContext } from 'element-plus'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  storeName: null,
  isPostage: null,
  isShow: 1,
  stock: 1,
  cateId: null,
  shopName: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

const activeName = ref('1')
const tableTabs = ref([
    {
    title: '出售中产品',
    name: '1',
  },
   {
    title: '待上架产品',
    name: '2',
  },
   {
    title: '已售罄产品',
    name: '3',
  },
  
])



/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await StoreProductApi.getStoreProductPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 分类点击 */
const handleDeptNodeClick = async (row) => {
  queryParams.cateId = row.id
  await getList()
}

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab.paneName, event)
  if (tab.paneName == '1') {
    queryParams.isShow = 1
    queryParams.stock = 1
    getList()
  }
  
  if (tab.paneName == '2') {
    queryParams.isShow = 0
    queryParams.stock = 1
    getList()
  }

  if (tab.paneName == '3') {
    queryParams.isShow = 1
    queryParams.stock = 0
    getList()
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
/** 上下架按钮操作 */
const onSale = async (id,isShow) => {
  try {
    // 删除的二次确认
    await message.confirm('确定要上下架？')
    // 发起删除
    await StoreProductApi.saleStoreProduct(id,isShow)
    message.success('操作成功')
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
    await StoreProductApi.deleteStoreProduct(id)
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
    const data = await StoreProductApi.exportStoreProduct(queryParams)
    download.excel(data, '商品.xls')
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
