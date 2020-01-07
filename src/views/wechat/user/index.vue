<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button
          v-permission="['admin','YXWECHATUSER_ALL','YXWECHATUSER_CREATE']"
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
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="uid" label="微信用户id" />
      <el-table-column prop="unionid" label="只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段" />
      <el-table-column prop="openid" label="用户的标识，对当前公众号唯一" />
      <el-table-column prop="routineOpenid" label="小程序唯一身份ID" />
      <el-table-column prop="nickname" label="用户的昵称" />
      <el-table-column prop="headimgurl" label="用户头像" />
      <el-table-column prop="sex" label="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知" />
      <el-table-column prop="city" label="用户所在城市" />
      <el-table-column prop="language" label="用户的语言，简体中文为zh_CN" />
      <el-table-column prop="province" label="用户所在省份" />
      <el-table-column prop="country" label="用户所在国家" />
      <el-table-column prop="remark" label="公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注" />
      <el-table-column prop="groupid" label="用户所在的分组ID（兼容旧的用户分组接口）" />
      <el-table-column prop="tagidList" label="用户被打上的标签ID列表" />
      <el-table-column prop="subscribe" label="用户是否订阅该公众号标识" />
      <el-table-column prop="subscribeTime" label="关注公众号时间" />
      <el-table-column prop="addTime" label="添加时间" />
      <el-table-column prop="stair" label="一级推荐人" />
      <el-table-column prop="second" label="二级推荐人" />
      <el-table-column prop="orderStair" label="一级推荐人订单" />
      <el-table-column prop="orderSecond" label="二级推荐人订单" />
      <el-table-column prop="nowMoney" label="佣金" />
      <el-table-column prop="sessionKey" label="小程序用户会话密匙" />
      <el-table-column prop="userType" label="用户类型" />
      <el-table-column v-if="checkPermission(['admin','YXWECHATUSER_ALL','YXWECHATUSER_EDIT','YXWECHATUSER_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXWECHATUSER_ALL','YXWECHATUSER_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.uid"
            v-permission="['admin','YXWECHATUSER_ALL','YXWECHATUSER_DELETE']"
            placement="top"
            width="180"
          >
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.uid].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.uid)">确定</el-button>
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
import { del } from '@/api/yxWechatUser'
import eForm from './form'
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
    checkPermission,
    beforeInit() {
      this.url = 'api/yxWechatUser'
      const sort = 'uid,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
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
        unionid: data.unionid,
        openid: data.openid,
        routineOpenid: data.routineOpenid,
        nickname: data.nickname,
        headimgurl: data.headimgurl,
        sex: data.sex,
        city: data.city,
        language: data.language,
        province: data.province,
        country: data.country,
        remark: data.remark,
        groupid: data.groupid,
        tagidList: data.tagidList,
        subscribe: data.subscribe,
        subscribeTime: data.subscribeTime,
        addTime: data.addTime,
        stair: data.stair,
        second: data.second,
        orderStair: data.orderStair,
        orderSecond: data.orderSecond,
        nowMoney: data.nowMoney,
        sessionKey: data.sessionKey,
        userType: data.userType
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
