<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
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
      <el-table-column prop="id" label="id" />
      <el-table-column prop="ctype" label="优惠券类型">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.ctype === 1" style="cursor: pointer" :type="''">商品券</el-tag>
            <el-tag v-else :type=" 'info' ">普通券</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="cname" label="优惠券名称" />
      <el-table-column label="领取日期" prop="startTime">
        <template slot-scope="scope">
          <p>{{ formatTimeTwo(scope.row.startTime) }}</p>
          <p>{{ formatTimeTwo(scope.row.endTime) }}</p>
        </template>
      </el-table-column>
      <el-table-column label="发布数量" prop="totalCount">
        <template slot-scope="scope">
          <p>发布:{{ scope.row.totalCount }}</p>
          <p>剩余:{{ scope.row.remainCount }}</p>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status === 1" style="cursor: pointer" :type="''">开启</el-tag>
            <el-tag v-else :type=" 'info' ">关闭</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXSTORECOUPONISSUE_ALL','YXSTORECOUPONISSUE_EDIT','YXSTORECOUPONISSUE_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXSTORECOUPONISSUE_ALL','YXSTORECOUPONISSUE_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXSTORECOUPONISSUE_ALL','YXSTORECOUPONISSUE_DELETE']"
            placement="top"
            width="180"
          >
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
            </div>
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
          </el-popover>
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
import { del } from '@/api/yxStoreCouponIssue'
import eForm from './formt'
import { formatTimeTwo } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false
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
      this.url = 'api/yxStoreCouponIssue'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
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
        startTime: data.startTime,
        endTime: data.endTime,
        startTimeDate: data.startTimeDate,
        endTimeDate: data.endTimeDate,
        totalCount: data.totalCount,
        remainCount: data.remainCount,
        isPermanent: data.isPermanent,
        status: data.status,
        isDel: data.isDel,
        addTime: data.addTime
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
