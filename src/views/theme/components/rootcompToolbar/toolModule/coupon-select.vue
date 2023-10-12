<template>
  <div class="product-select">
    <el-form :inline="true" :model="formData" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="formData.keyword" placeholder="店铺名称/商品ID/商品分组"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      max-height="500"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="优惠券名称" width="180"></el-table-column>
      <el-table-column prop="productId" label="适用商品"></el-table-column>
      <el-table-column prop="price" label="优惠内容"></el-table-column>
      <el-table-column prop="originalPrice" label="领取人限制" width="100"></el-table-column>
      <el-table-column prop="stockNumber" label="限领次数"></el-table-column>
      <el-table-column prop="number" label="剩余库存"></el-table-column>
      <el-table-column prop="number" label="状态"></el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :hide-on-single-page="true"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import { getProducts } from '@/api/canvasApi.js'
export default {
  name: 'coupon-select',
  data () {
    return {
      tableRadio: '',
      formData: {
        keyword: '',
        status: '',
        categoryId: ''
      },
      currentPage: 1,
      total: 0,
      pageSize: 10,
      multipleSelection: [],
      tableData: [{
        id: 100,
        name: '测试'
      }]
    }
  },
  mounted () {
    // this.getTableData()
  },
  methods: {
    // 获取产品信息
    getTableData () {
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.formData.keyword) {
        params.search = this.formData.keyword
      }
      if (this.formData.status) {
        params.shelveState = this.formData.status
      }
      let nodesObj = this.$refs['cascader'].getCheckedNodes()
      if (nodesObj && nodesObj.length !== 0) {
        params.classifyId = nodesObj[0].value
      }
      getProducts(params).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      })
    },
    // 搜索
    onSubmit () {
      this.getTableData()
    },
    // 每页条数改变
    handleSizeChange (val) {
      this.pageSize = val
      this.getTableData()
    },
    // 当前页改变
    handleCurrentChange (val) {
      this.currentPage = val
      this.getTableData()
    },
    // 多选改变
    handleSelectionChange (val) {
      this.multipleSelection = val
    }
  }
}
</script>

<style lang="scss" scoped>
.product-select{
  .el-pagination{
    padding: 0px;
    margin-top: 30px;
  }
  .el-table{
    .img{
      width: 80px;
      height: 80px;
    }
  }
}
</style>
