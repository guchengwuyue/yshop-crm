<template>
  <div class="afterSealsContainer">
    <!-- 搜索栏 -->
    <div class="titleSearch">
      <el-input v-model="query.orderCode" clearable placeholder="输入搜索订单号" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery" />
      <el-date-picker
        v-model="searchTime"
        :default-time="['00:00:00','23:59:59']"
        type="daterange"
        range-separator=":"
        size="small"
        class="date-item"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      />
      <br>
      <el-select v-model="query.type"
        clearable placeholder="售后类型"
        class="filter-item"
        style="width: 130px">
        <el-option
        v-for="item in serviceTypeOptions"
        :key="item.key"
        :label="item.name"
        :value="item.key" />
      </el-select>
      <el-select v-model="query.salesState"
        clearable placeholder="售后状态"
        class="filter-item"
        style="width: 130px">
        <el-option
        v-for="item in salesOptions"
        :key="item.key"
        :label="item.name"
        :value="item.key" />
      </el-select>
      <el-select v-model="query.state"
        clearable placeholder="订单状态"
        class="filter-item"
        style="width: 130px">
        <el-option
        v-for="item in statusOptions"
        :key="item.key"
        :label="item.name"
        :value="item.key" />
      </el-select>
      <el-button size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
      <el-button
      size="mini"
      type="warning"
      icon="el-icon-refresh-left"
      @click="resetSearch">重置</el-button>
    </div>
    <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
    <!-- <crudOperation :permission="permission" /> -->
    
    <!--表格渲染-->
    <el-table
    ref="table"
    v-loading="loading"
    :data="data"
    size="small"
    style="width: 100%;"
    @selection-change="val => {selections = val}">
    <!-- @selection-change="selectionChangeHandler" -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="orderCode" label="订单号" />
      <el-table-column prop="refundAmount" label="退款金额" width="100px"/>
      <el-table-column prop="serviceType" label="服务类型" >
        <template slot-scope="scope">
          <span v-if="scope.row.serviceType === 0">仅退款</span>
          <span v-if="scope.row.serviceType === 1">退货退款</span>
        </template>
      </el-table-column>
      <el-table-column prop="reasons" label="申请原因" />
      <el-table-column prop="explains" label="说明" />
      <el-table-column prop="createTime" label="申请时间" align="center">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态" width="100px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.state === 0" :style="'color: #E6A23C'">等待审核</span>
          <span v-if="scope.row.state === 1" :style="'color: #409EFF'">等待用户发货</span>
          <span v-if="scope.row.state === 2" :style="'color: #F56C6C'">用户已发货</span>
          <span v-if="scope.row.state === 3" :style="'color: #909399'">退款成功</span>
        </template>
      </el-table-column>
      <el-table-column prop="salesState" label="售后状态" width="100px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.salesState === 0" :style="'color: #42b983'">正常</span>
          <span v-if="scope.row.salesState === 1" :style="'color: #409EFF'">用户已取消</span>
          <span v-if="scope.row.salesState === 2" :style="'color: #F56C6C'">已拒绝用户</span>
        </template>
      </el-table-column>
      <el-table-column
      v-permission="['admin','yxStoreAfterSales:edit','yxStoreAfterSales:del']" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="checkItem(scope.row, 0)">订单详情</el-button>
          <el-button
          size="mini"
          type="success"
          v-if="scope.row.state === 0 && scope.row.salesState === 0"
          @click="checkItem(scope.row, 1)">审核</el-button>
          <el-button
            size="mini"
            type="danger"
            v-if="scope.row.state === 2"
            @click="rebackVisible = true;
            rebackQuery.salesId = scope.row.id;
            rebackQuery.orderCode = scope.row.orderCode">
            退款</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 20px; float: right"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange" />

      <!-- 审核、订单详情 -->
      <CheckDialog ref="addForm" :visible="addVisible" @checkSuccess="toQuery"/>
      <!-- 退款 -->
      <el-dialog
        title="退款"
        :visible.sync="rebackVisible"
        width="400px">
        <span>
          是否给订单号：<b :style="'color: #409EFF'">{{ this.rebackQuery.orderCode }}</b> 确认退款？
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="rebackVisible = false">取 消</el-button>
          <el-button type="primary" @click="rebackItem">确 定</el-button>
        </span>
      </el-dialog>
  </div>
</template>

<script>
import initData from '@/mixins/crud'
import {rebackMoney} from '@/api/yxStoreAfterSales'
import CheckDialog from './checkDialog.vue'
export default {
  name: 'YxStoreAfterSales',
  components: { CheckDialog },
  mixins: [initData],
  data() {
    return {
      addVisible: false,
      rebackVisible: false,
      searchTime: [],
      permission: {
        add: ['admin', 'yxStoreAfterSales:add'],
        edit: ['admin', 'yxStoreAfterSales:edit'],
        del: ['admin', 'yxStoreAfterSales:del']
      },
      salesOptions: [
        { key: 0, name: '正常'},
        { key: 1, name: '用户已取消' },
        { key: 2, name: '已拒绝用户'}
      ],
      serviceTypeOptions: [
        { key: null, name: '全部' },
        { key: 0, name: '仅退款' },
        { key: 1, name: '退货退款' }
      ],
      statusOptions: [
        { key: 0, name: '待审核'},
        { key: 1, name: '等待用户发货'},
        { key: 2, name: '用户已发货'},
        { key: 3, name: '已完成'},
      ],
      // 退款参数
      rebackQuery: {
        orderCode: '', // 订单号
        salesId: 0 // 数据的id
      }
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    beforeInit() {
      this.url = 'api/yxStoreAfterSales/sales/List'
      this.params = {
        page: this.page,
        size: this.size,
        
        serviceType: this.query.type || '', // 查询类型
        salesState: this.query.salesState || 0, // 售后状态
        state: this.query.state,
        orderCode: this.query.orderCode || '',
        time: this.searchTime
        // startingTime: `${this.searchTime[0]}` || '',
        // endTime: `${this.searchTime[1]}` || ''
      }
      if (this.query.state === 0) this.params.state = 0;
      return true
    },
    resetSearch() {
      this.query.orderCode = this.query.type = this.query.salesState = this.query.state = '';
      this.searchTime = [];
      this.toQuery()
    },
    // 审核
    checkItem(row, type) {
      this.$refs.addForm.checkForm = row
      this.$refs.addForm.serviceType = row.serviceType

      this.$refs.addForm.form.salesId = row.id
      this.$refs.addForm.form.orderCode = row.orderCode

      if (type === 1) {
        this.$refs.addForm.isShow = true
      } else {
        this.$refs.addForm.isShow = false
      }
      this.$refs.addForm.visible = true
    },
    // 退款
    async rebackItem() {
      var res = await rebackMoney(this.rebackQuery)
      if (res) {
        this.$message.success('提交退款成功！')
        this.rebackVisible = false
        this.toQuery()
      } else {
        this.$message.error(res.message || '提交退款失败！')
      }
    }
  }
}


</script>

<style lang="scss" scoped>
.afterSealsContainer{
  padding: 12px 8px;
  .titleSearch {
    line-height: 40px;
    margin: 20px 0;
  }
  .table-img {
    display: inline-block;
    text-align: center;
    background: #ccc;
    color: #fff;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    vertical-align: middle;
    width: 32px;
    height: 32px;
    line-height: 32px;
  }
}
</style>
