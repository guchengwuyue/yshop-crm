<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery" />
      <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
        <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <!-- 新增 -->
      <el-button
        type="danger"
        class="filter-item"
        size="mini"
        icon="el-icon-refresh"
        @click="toQuery"
      >刷新</el-button>
    </div>
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="couponTitle" label="优惠券名称" />
      <el-table-column prop="nickname" label="所属用户" />
      <el-table-column prop="couponPrice" label="优惠券的面值" />
      <el-table-column prop="useMinPrice" label="优惠券最低消费" />
      <el-table-column label="优惠券开始时间">
        <template slot-scope="scope">
          {{ formatTimeTwo(scope.row.addTime) }}
        </template>
      </el-table-column>
      <el-table-column label="优惠券结束时间">
        <template slot-scope="scope">
          {{ formatTimeTwo(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="获取方式">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.type == 'get'" style="cursor: pointer" :type="''">手动领取</el-tag>
            <el-tag v-else :type=" 'info' ">后台发放</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="是否可用">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status == 0 && scope.row.isFail == 0" style="cursor: pointer" :type="''">可用</el-tag>
            <el-tag v-else :type=" 'info' ">不可用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status == 2">已过期</el-tag>
            <el-tag v-if="scope.row.status == 1">已使用</el-tag>
            <el-tag v-else>未使用</el-tag>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"
    />
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del } from '@/api/yxStoreCouponUser'
import eForm from './form'
import { formatTimeTwo } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      queryTypeOptions: [
        { key: 'couponTitle', display_name: '优惠券名称' }
      ]
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    formatTimeTwo,
    checkPermission,
    beforeInit() {
      this.url = 'api/yxStoreCouponUser'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) { this.params[type] = value }
      return true
    },
    subDelete(id) {
      this.delLoading = true
      del(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.dleChangePage()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[id].doClose()
        console.log(err.response.data.message)
      })
    },
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = {
        id: data.id,
        cid: data.cid,
        uid: data.uid,
        couponTitle: data.couponTitle,
        couponPrice: data.couponPrice,
        useMinPrice: data.useMinPrice,
        addTime: data.addTime,
        endTime: data.endTime,
        useTime: data.useTime,
        type: data.type,
        status: data.status,
        isFail: data.isFail
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
