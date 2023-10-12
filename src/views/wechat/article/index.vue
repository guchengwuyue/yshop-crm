<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button
          v-permission="['admin','YXARTICLE_ALL','YXARTICLE_CREATE']"
          class="filter-item"
          size="mini"
          type="primary"
          icon="el-icon-plus"
                 >
          <router-link :to="'/wechat/artadd'">
            新增
          </router-link>
        </el-button>
      </div>
    </div>
    <!--表单组件-->
    <!--<eForm ref="form" :is-add="isAdd" />-->
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="author" label="作者" />
      <el-table-column ref="table" prop="imageInput" label="封面">
        <template slot-scope="scope">
          <a :href="scope.row.imageInput" style="color: #42b983" target="_blank"><img :src="scope.row.imageInput" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="createTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXARTICLE_ALL','YXARTICLE_EDIT','YXARTICLE_DELETE'])" label="操作" width="220px" align="center">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXARTICLE_ALL','YXARTICLE_EDIT']" size="mini"
                     type="primary" icon="el-icon-edit"
                     >
            <router-link :to="'/wechat/artadd/'+scope.row.id">
              编辑
            </router-link>
          </el-button>
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXARTICLE_ALL','YXARTICLE_DELETE']"
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
          <el-popover
            :ref="'item'+scope.row.id"
            v-permission="['admin','YXARTICLE_ALL','YXARTICLE_DELETE']"
            placement="top"
            width="180"
          >
            <p>确定发布本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs['item'+scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subPublish(scope.row.id)">确定</el-button>
            </div>
            <el-button slot="reference" type="primary" size="mini">发布</el-button>
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
import { del, publish } from '@/api/yxArticle'
import eForm from './form'
import { formatTime } from '@/utils/index'
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
    formatTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/yxArticle'
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
    subPublish(id) {
      this.delLoading = true
      publish(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.dleChangePage()
        this.init()
        this.$notify({
          title: '发布成功',
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
        title: data.title,
        author: data.author,
        imageInput: data.imageInput,
        imageArr: data.imageInput.split(','),
        synopsis: data.synopsis,
        content: data.content,
        shareTitle: data.shareTitle,
        shareSynopsis: data.shareSynopsis,
        visit: data.visit,
        sort: data.sort,
        url: data.url,
        status: data.status,
        addTime: data.addTime,
        hide: data.hide,
        adminId: data.adminId,
        merId: data.merId,
        productId: data.productId,
        isHot: data.isHot,
        isBanner: data.isBanner
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
