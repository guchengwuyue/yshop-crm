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
      <el-table-column prop="id" label="id" />
      <el-table-column prop="extractPrice" label="提现金额" />
      <el-table-column prop="extractType" label="提现方式">
        <template slot-scope="scope">
          <div v-if="scope.row.extractType=='weixin'">
            姓名:{{ scope.row.realName }}<br>
            微信号:{{ scope.row.wechat }}
          </div>
          <div v-else>
            姓名:{{ scope.row.realName }}<br>
            支付宝号:{{ scope.row.alipayCode }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="addTime" label="添加时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="审核状态">
        <template slot-scope="scope">
          <div v-if="scope.row.status==1">
            提现通过
          </div>
          <div v-else-if="scope.row.status==-1">
            提现未通过<br>
            未通过原因：{{ scope.row.failMsg }}
            <br>
            未通过时间：{{ formatTimeTwo(scope.row.failTime) }}
          </div>
          <div v-else>
            未提现
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXUSEREXTRACT_ALL','YXUSEREXTRACT_EDIT','YXUSEREXTRACT_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin','YXUSEREXTRACT_ALL','YXUSEREXTRACT_EDIT']"
            size="mini"
            type="primary"
            @click="edit(scope.row)"
          >操作</el-button>
          <!--<el-popover-->
          <!--v-permission="['admin','YXUSEREXTRACT_ALL','YXUSEREXTRACT_DELETE']"-->
          <!--:ref="scope.row.id"-->
          <!--placement="top"-->
          <!--width="180">-->
          <!--<p>确定删除本条数据吗？</p>-->
          <!--<div style="text-align: right; margin: 0">-->
          <!--<el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>-->
          <!--<el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>-->
          <!--</div>-->
          <!--<el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>-->
          <!--</el-popover>-->
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
import { del } from '@/api/yxUserExtract'
import eForm from './form'
import { formatTimeTwo } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      queryTypeOptions: [
        { key: 'realName', display_name: '名称' }
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
      this.url = 'api/yxUserExtract'
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
        realName: data.realName,
        extractType: data.extractType,
        bankCode: data.bankCode,
        bankAddress: data.bankAddress,
        alipayCode: data.alipayCode,
        extractPrice: data.extractPrice,
        mark: data.mark,
        balance: data.balance,
        failMsg: data.failMsg,
        failTime: data.failTime,
        addTime: data.addTime,
        status: data.status,
        wechat: data.wechat
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
