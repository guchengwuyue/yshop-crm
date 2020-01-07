<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->

    </div>
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="levalName" label="等级名称" />
      <el-table-column prop="name" label="任务名称" />
      <el-table-column prop="sort" label="排序" />
      <el-table-column prop="isShow" label="是否显示">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow === 1" style="cursor: pointer" :type="''">是</el-tag>
          <el-tag v-else style="cursor: pointer" :type=" 'info' ">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXSYSTEMUSERTASK_ALL','YXSYSTEMUSERTASK_EDIT','YXSYSTEMUSERTASK_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXSYSTEMUSERTASK_ALL','YXSYSTEMUSERTASK_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
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
import { del } from '@/api/yxSystemUserTask'
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
      this.url = 'api/yxSystemUserTask'
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
        levalName: data.levalName,
        id: data.id,
        name: data.name,
        realName: data.realName,
        taskType: data.taskType,
        number: data.number,
        levelId: data.levelId,
        sort: data.sort,
        isShow: data.isShow,
        isMust: data.isMust,
        illustrate: data.illustrate,
        addTime: data.addTime
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
