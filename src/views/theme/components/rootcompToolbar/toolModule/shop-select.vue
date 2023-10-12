<template>
  <div class="product-select">
    <el-form :inline="true" :model="formData" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="formData.keyword" placeholder="店铺名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      max-height="500"
      border
      style="width: 100%">
      <el-table-column label="" width="35" align="center">
        <template slot-scope="scope">
          <el-radio v-model="tableRadio" :label="scope.row"><i></i></el-radio>
        </template>
      </el-table-column>
      <el-table-column
        prop="shopName"
        label="店铺名称">
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import { getShops } from '@/api/canvasApi.js'
export default {
  name: 'shop-select',
  props: {
    categoryList: {
      type: Array,
      default: () => [
        {
          value: '/index',
          label: '首页'
        },
        {
          value: '/category',
          label: '分类页面'
        },
        {
          value: '/shop',
          label: '店辅主页'
        },
        {
          value: '/detail',
          label: '商品详情'
        }
      ]
    }
  },
  data () {
    return {
      tableRadio: '',
      currentPage: 1,
      total: 0,
      pageSize: 10,
      formData: {
        keyword: ''
      },
      tableData: []
    }
  },
  mounted () {
    this.getTableData()
  },
  methods: {
    // 获取店辅信息
    getTableData () {
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.formData.keyword) {
        params.search = this.formData.keyword
      }
      getShops(params).then(res => {
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
}
</style>
