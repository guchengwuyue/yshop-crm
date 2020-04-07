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
      <el-table-column prop="uid" label="用户id" />
      <el-table-column prop="nickname" label="用户昵称" />
      <el-table-column ref="table" prop="avatar" label="用户头像">
        <template slot-scope="scope">
          <a :href="scope.row.avatar" style="color: #42b983" target="_blank"><img :src="scope.row.avatar" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号码" />
      <el-table-column prop="nowMoney" label="用户余额" />
      <el-table-column prop="brokeragePrice" label="佣金金额" />
      <el-table-column :show-overflow-tooltip="true" prop="addTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ formatTime(scope.row.addTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <div @click="onStatus(scope.row.uid,scope.row.status)">
            <el-tag v-if="scope.row.status == 1" style="cursor: pointer" :type="''">正常</el-tag>
            <el-tag v-else style="cursor: pointer" :type=" 'info' ">禁用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="spreadCount" label="直推人数" />
      <el-table-column prop="payCount" label="购买次数" />
      <!---->
      <!--<el-table-column v-if="checkPermission(['admin','YXUSER_ALL','YXUSER_EDIT','YXUSER_DELETE'])" label="操作" width="150px" align="center">-->
      <!--<template slot-scope="scope">-->
      <!--<el-button v-permission="['admin','YXUSER_ALL','YXUSER_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>-->
      <!--<el-popover-->
      <!--v-permission="['admin','YXUSER_ALL','YXUSER_DELETE']"-->
      <!--:ref="scope.row.uid"-->
      <!--placement="top"-->
      <!--width="180">-->
      <!--<p>确定删除本条数据吗？</p>-->
      <!--<div style="text-align: right; margin: 0">-->
      <!--<el-button size="mini" type="text" @click="$refs[scope.row.uid].doClose()">取消</el-button>-->
      <!--<el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.uid)">确定</el-button>-->
      <!--</div>-->
      <!--<el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>-->
      <!--</el-popover>-->
      <!--</template>-->
      <!--</el-table-column>-->
      <!---->
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
import { del, onStatus } from '@/api/yxUser'
import eForm from './form'
import { formatTime } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      queryTypeOptions: [
        { key: 'nickname', display_name: '用户昵称' },
        { key: 'phone', display_name: '手机号码' }
      ]
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
    onStatus(id, status) {
      this.$confirm(`确定进行[${status ? '禁用' : '开启'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          onStatus(id, { status: status }).then(({ data }) => {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.init()
              }
            })
          })
        })
        .catch(() => { })
    },
    beforeInit() {
      this.url = 'api/yxUser'
      const sort = 'uid,desc'
      this.params = { page: this.page, size: this.size, sort: sort, isPromoter: 1 }
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) { this.params[type] = value }
      return true
    },
    subDelete(uid) {
      this.delLoading = true
      del(uid).then(res => {
        this.delLoading = false
        this.$refs[uid].doClose()
        this.dleChangePage()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[uid].doClose()
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
        uid: data.uid,
        account: data.account,
        pwd: data.pwd,
        realName: data.realName,
        birthday: data.birthday,
        cardId: data.cardId,
        mark: data.mark,
        partnerId: data.partnerId,
        groupId: data.groupId,
        nickname: data.nickname,
        avatar: data.avatar,
        phone: data.phone,
        addTime: data.addTime,
        addIp: data.addIp,
        lastTime: data.lastTime,
        lastIp: data.lastIp,
        nowMoney: data.nowMoney,
        brokeragePrice: data.brokeragePrice,
        integral: data.integral,
        signNum: data.signNum,
        status: data.status,
        level: data.level,
        spreadUid: data.spreadUid,
        spreadTime: data.spreadTime,
        userType: data.userType,
        isPromoter: data.isPromoter,
        payCount: data.payCount,
        spreadCount: data.spreadCount,
        cleanTime: data.cleanTime,
        addres: data.addres,
        adminid: data.adminid,
        loginType: data.loginType
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
