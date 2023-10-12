<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button
          v-permission="['admin','YXSTORECOUPON_ALL','YXSTORECOUPON_CREATE']"
          class="filter-item"
          size="mini"
          type="primary"
          icon="el-icon-plus"
          @click="add"
        >新增</el-button>
      </div>
    </div>
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <eIForm ref="form2" :is-add="isAdd" />
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <!--<el-table-column prop="id" label="ID"/>-->
      <el-table-column prop="title" label="优惠券名称" />
      <el-table-column prop="type" label="优惠券类型">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.type === 1" style="cursor: pointer" :type="''">商品券</el-tag>
            <el-tag v-else :type=" 'info' ">普通券</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="couponPrice" label="优惠券面值" />
      <el-table-column prop="useMinPrice" label="优惠券最低消费" />
      <el-table-column label="优惠券有效期限">
        <template slot-scope="scope">
          <span>{{ scope.row.couponTime }}天</span>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="100" />
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status === 1" style="cursor: pointer" :type="''">开启</el-tag>
            <el-tag v-else :type=" 'info' ">关闭</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="140">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXSTORECOUPON_ALL','YXSTORECOUPON_EDIT','YXSTORECOUPON_DELETE'])" width="200" label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin','YXSTORECOUPON_ALL','YXSTORECOUPON_EDIT']"
            size="mini"
            type="primary"
            @click="edit2(scope.row)"
          >
            发布
          </el-button>
          <el-dropdown size="mini" split-button type="primary" trigger="click">
            操作
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button
                  v-permission="['admin','YXSTORECOUPON_ALL','YXSTORECOUPON_EDIT']"
                  size="mini"
                  type="primary"
                  icon="el-icon-edit"
                  @click="edit(scope.row)"
                >编辑</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-popover
                  :ref="scope.row.id"
                  v-permission="['admin','YXSTORECOUPON_ALL','YXSTORECOUPON_DELETE']"
                  placement="top"
                  width="180"
                >
                  <p>确定删除本条数据吗？</p>
                  <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                    <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                  </div>
                  <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini">删除</el-button>
                </el-popover>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
import { del } from '@/api/yxStoreCoupon'
import eForm from './form'
import eIForm from '../couponissue/form'
import { formatTime } from '@/utils/index'
export default {
  components: { eForm, eIForm },
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
    formatTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/yxStoreCoupon'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort, isDel: 0 }
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
        title: data.title,
        integral: data.integral,
        couponPrice: data.couponPrice,
        useMinPrice: data.useMinPrice,
        couponTime: data.couponTime,
        sort: data.sort,
        status: data.status,
        type: data.type,
        productId: data.productId,
        product: data.product
      }
      _this.dialog = true
    },
    edit2(data) {
      this.isAdd = true
      const _this = this.$refs.form2
      _this.form = {
        cid: data.id,
        cname: data.title,
        ctype: data.type,
        isPermanent: 0,
        status: 1,
        totalCount: 0,
        remainCount: 0,
        isDel: 0
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
