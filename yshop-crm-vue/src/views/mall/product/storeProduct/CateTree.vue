<template>
  <div class="head-container">
    <el-input v-model="deptName" class="mb-20px" clearable placeholder="请输入分类名称">
      <template #prefix>
        <Icon icon="ep:search" />
      </template>
    </el-input>
  </div>
  <div class="head-container">
    <el-tree
      ref="treeRef"
      :data="deptList"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="defaultProps"
      default-expand-all
      highlight-current
      node-key="id"
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script lang="ts" name="StoreProductCateTree" setup>
import { ElTree } from 'element-plus'
import * as ProductCategoryApi from '@/api/mall/product/category'
import { defaultProps, handleTree } from '@/utils/tree'

const deptName = ref('')
const deptList = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()

/** 获得分类树 */
const getTree = async () => {
  const res = await ProductCategoryApi.getCategoryList('')
  deptList.value = []
  deptList.value.push(...handleTree(res))
}

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  return data.name.includes(name)
}

/** 处理分类被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>
