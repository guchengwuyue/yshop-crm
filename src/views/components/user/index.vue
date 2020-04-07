<template>
  <div>
    <div v-if="value.uid">
      <ul  class="el-upload-list el-upload-list--picture-card">
        <li tabindex="0" class="el-upload-list__item is-ready">
          <div>
            <img :src="value.avatar" alt="" class="el-upload-list__item-thumbnail">
            <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-delete" @click="deleteUser">
                <i class="el-icon-delete" />
              </span>
            </span>
          </div>
        </li>
      </ul>
    </div>
    <div v-else tabindex="0" class="el-upload el-upload--picture-card"  @click="toSelete">
      <i class="el-icon-plus" />
    </div>
    <el-dialog :visible.sync="dialog" append-to-body width="60%" title="商城会员">
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

      <!--表格渲染-->
      <el-table  :data="data" size="small" style="width: 100%;">
        <el-table-column prop="uid" label="用户id" />
        <el-table-column prop="nickname" label="用户昵称" />
        <el-table-column ref="table" prop="avatar" label="用户头像">
          <template slot-scope="scope">
            <a :href="scope.row.avatar" style="color: #42b983" target="_blank"><img :src="scope.row.avatar" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号码" />
        <el-table-column label="用户来源" align="center">
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.userType == 'wechat'">公众号</el-tag>
              <el-tag v-else-if="scope.row.userType == 'routine'">小程序</el-tag>
              <el-tag v-else>H5</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="checkPermission(['admin','YXUSER_ALL','YXUSER_EDIT','YXUSER_DELETE'])" label="操作" width="185" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button
              v-permission="['admin','YXUSER_ALL','YXUSER_EDIT']"
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="doSelect(scope.row)"
            >选择</el-button>
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
    </el-dialog>
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
export default {
  components: {},
  mixins: [initData],
  props: {
    value: {
      type: Object
    }
  },
  data() {
    return {
      myValue: this.value,
      delLoading: false, dialog: false,
      userType: '',
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
    checkPermission,
    beforeInit() {
      this.url = 'api/yxUser'
      const sort = 'uid,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) { this.params[type] = value }
      return true
    },
    deleteUser() {
      const that = this
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        //that.myValue = {uid: null,nickname: null,avatar: null}
        that.$set(that.value,"uid", null)
        that.$set(that.value,"nickname", null)
        that.$set(that.value,"avatar", null)
      })
    },
    toSelete() {
      this.dialog = true
    },
    doSelect(data) {
      this.$set(this.value,"uid", data.uid)
      this.$set(this.value,"nickname", data.nickname)
      this.$set(this.value,"avatar", data.avatar)

      this.dialog = false
    }

  }
}
</script>

<style scoped>
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
</style>
