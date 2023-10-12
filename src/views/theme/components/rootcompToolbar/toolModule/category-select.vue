<template>
  <el-cascader style="width: 100%"
    ref="cascader"
    :options="categoryList"
    :props="{ checkStrictly: true,label: 'categoryName',value: 'id',children: 'childs' }"
    clearable></el-cascader>
</template>

<script>
import { checkEmptyChild } from '@/mixins/tool.js'
import { getClassify } from '@/api/canvasApi.js'
export default {
  name: 'category-select',
  data() {
    return {
      categoryList: []
    }
  },
  mounted() {
    this.getCategory()
  },
  methods: {
    // 获取类别
    getCategory() {
      getClassify().then(res => {
        this.categoryList = res.data
        checkEmptyChild(this.categoryList)
      })
    }
  }
}
</script>
