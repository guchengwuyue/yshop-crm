<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button
          v-permission="['admin','YXWECHATTEMPLATE_ALL','YXWECHATTEMPLATE_CREATE']"
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
      <el-table-column prop="status" label="消息类型">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.type == 'template'" :type="''">模板消息</el-tag>
            <el-tag v-else :type=" 'info' ">订阅消息</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="模板id" />
      <el-table-column prop="tempkey" label="模板编号" />
      <el-table-column prop="name" label="模板名" />
      <el-table-column prop="tempid" label="模板ID" />
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status === 1" :type="''">可用</el-tag>
            <el-tag v-else :type=" 'info' ">禁用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXWECHATTEMPLATE_ALL','YXWECHATTEMPLATE_EDIT','YXWECHATTEMPLATE_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXWECHATTEMPLATE_ALL','YXWECHATTEMPLATE_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXWECHATTEMPLATE_ALL','YXWECHATTEMPLATE_DELETE']"
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
import { del } from '@/api/yxWechatTemplate'
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
      this.url = 'api/yxWechatTemplate'
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
        tempkey: data.tempkey,
        name: data.name,
        content: data.content,
        tempid: data.tempid,
        addTime: data.addTime,
        status: data.status,
        type: data.type
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
