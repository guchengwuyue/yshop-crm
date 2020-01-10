<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button
          v-permission="['admin','YXSYSTEMUSERLEVEL_ALL','YXSYSTEMUSERLEVEL_CREATE']"
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
      <el-table-column prop="id" label="id" />
      <el-table-column prop="icon" label="等级图标">
        <template slot-scope="scope">
          <a :href="scope.row.icon" style="color: #42b983" target="_blank"><img :src="scope.row.icon" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="等级名称" />
      <el-table-column prop="grade" label="会员等级" />
      <el-table-column prop="discount" label="享受折扣" />
      <el-table-column prop="validDate" label="有效时间" />
      <el-table-column prop="isForever" label="是否永久">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isForever === 1" style="cursor: pointer" :type="''">是</el-tag>
          <el-tag v-else style="cursor: pointer" :type=" 'info' ">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isShow" label="是否显示">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow === 1" style="cursor: pointer" :type="''">是</el-tag>
          <el-tag v-else style="cursor: pointer" :type=" 'info' ">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXSYSTEMUSERLEVEL_ALL','YXSYSTEMUSERLEVEL_EDIT','YXSYSTEMUSERLEVEL_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXSYSTEMUSERLEVEL_ALL','YXSYSTEMUSERLEVEL_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXSYSTEMUSERLEVEL_ALL','YXSYSTEMUSERLEVEL_DELETE']"
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
import { del } from '@/api/yxSystemUserLevel'
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
      this.url = 'api/yxSystemUserLevel'
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
        merId: data.merId,
        name: data.name,
        money: data.money,
        validDate: data.validDate,
        isForever: data.isForever,
        isPay: data.isPay,
        isShow: data.isShow,
        grade: data.grade,
        discount: data.discount,
        image: data.image,
        icon: data.icon,
        imageArr: data.image ? data.image.split(',') : [],
        iconArr: data.icon ? data.icon.split(',') : [],
        explain: data.explain,
        addTime: data.addTime,
        isDel: data.isDel
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
