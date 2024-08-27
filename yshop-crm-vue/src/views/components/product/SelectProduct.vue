<template>
    <el-dialog
    v-model="groupVisible"
    title="选择商品"
    width="70%"
    top="50px"
   
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    :modal="false"
  >
   <el-row :gutter="20">
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
          <el-form-item label="商品名称" prop="goodsName">
            <el-input
              v-model="queryParams.goodsName"
              placeholder="请输入商品名称"
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
          <el-table-column label="商品图片" align="center" prop="image" >
            <template #default="scope">
              <el-image
                style="width: 40px; height: 40px"
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
          <el-table-column label="商品规格" align="center" prop="storeName" width="280">
            <template #default="{ row, $index }">
              <el-input v-model="row.sku" placeholder="请输入商品规格">
                <template #append><span  @click="chooseSku(row.id,$index)" style="cursor:pointer;">选择商品规格</span></template>
              </el-input>
              <el-input v-model="row.skuNo" type="hidden" />
            </template>
          </el-table-column>
          <el-table-column label="商品价格" align="center" prop="price" />
          <el-table-column label="库存" align="center"  width="80" prop="stock" />
          <el-table-column label="操作" align="center" fixed="right" width="100">
            <template #default="scope">
              <el-button
                link
                type="success"
                @click="doSelect(scope.row)"
              >
              确认选择
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
    </el-col>
  </el-row>
</el-dialog>
<StoreProductForm ref="StoreProductFormRef" @success="getSku" />
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as GoodsApi from '@/api/mall/product/product'
import CateTree from './CateTree.vue'
import StoreProductForm from './StoreProductForm.vue'


/** 商品 列表 */
defineOptions({ name: 'PsiGoods' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<GoodsVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 6,
  storeName: null,
  isPostage: null,
  isShow: 1,
  stock: 1,
  cateId: null,
  shopName: null
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const groupVisible = ref(false)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await GoodsApi.getStoreProductPage(queryParams)
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


const open = () => {
  groupVisible.value = true;
  getList()
}

/** 处理点击 */
const handleDeptNodeClick = async (row) => {
  queryParams.goodsCateId = row.id
  await getList()
}
const emit = defineEmits(['saveProduct'])
/** 删除按钮操作 */
const doSelect = async (row) => {
  if(!row.skuNo) {
    message.error('请先选择商品规格！')
    return
  }
  emit('saveProduct', row);
  groupVisible.value = false;
}
defineExpose({ open }) 




const StoreProductFormRef = ref()
const currentIndex = ref(0)
const chooseSku = (goodsId,index) => {
  //console.log(goodsId,index)
  currentIndex.value = index
  StoreProductFormRef.value.open('',goodsId)
}

const getSku = (sku) => {
  console.log('sku:',sku)
  list.value[currentIndex.value].sku = sku.sku
  list.value[currentIndex.value].skuNo = sku.unique
  list.value[currentIndex.value].price = sku.price
}


</script>