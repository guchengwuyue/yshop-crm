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
      <el-table-column
        label="序号"
        type="index"
        width="50"
        align="center">
      </el-table-column>
      <el-table-column prop="uid" label="参与人" width="150" >
        <template slot-scope="scope">
          头像：<a :href="scope.row.userImg" style="color: #42b983" target="_blank"><img :src="scope.row.userImg" alt="点击打开" class="el-avatar"></a><br>
          昵称：{{ scope.row.nickname }}<br>
          用户ID：{{ scope.row.uid }}<br>
          手机号：{{ scope.row.phone }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="开团时间" />
      <el-table-column label="拼团产品"width="200">
        <template slot-scope="scope">
          产品主图：<a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a><br>
          拼团名称：{{ scope.row.product }}<br>
          拼团 ID：{{ scope.row.cid }}
        </template>
      </el-table-column>
      <el-table-column prop="orderId" label="订单号" width="150" />
      <el-table-column prop="totalPrice" label="拼团价" />
      <el-table-column prop="people" label="几人团" />
      <el-table-column prop="countPeople" label="几人参加" />
      <el-table-column label="结束时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.stopTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 1">进行中</span>
          <span v-else-if="scope.row.status == 2">已完成</span>
          <span v-else>未完成</span>
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
  import { del } from '@/api/yxStorePink'
  import eForm from './form'
  import { formatTimeTwo, parseTime } from '@/utils/index'
  export default {
    components: { eForm },
    mixins: [initData],
    data() {
      return {
        delLoading: false,
        queryTypeOptions: [
          { key: 'cid', display_name: '拼团id' }
        ]
      }
    },
    created() {
      this.$nextTick(() => {
        this.init()
      })
    },
    methods: {
      parseTime,
      formatTimeTwo,
      checkPermission,
      beforeInit() {
        this.url = 'api/yxStorePink'
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
          uid: data.uid,
          orderId: data.orderId,
          orderIdKey: data.orderIdKey,
          totalNum: data.totalNum,
          totalPrice: data.totalPrice,
          cid: data.cid,
          pid: data.pid,
          people: data.people,
          price: data.price,
          addTime: data.addTime,
          stopTime: data.stopTime,
          kId: data.kId,
          isTpl: data.isTpl,
          isRefund: data.isRefund,
          status: data.status
        }
        _this.dialog = true
      }
    }
  }
</script>

<style scoped>

</style>
