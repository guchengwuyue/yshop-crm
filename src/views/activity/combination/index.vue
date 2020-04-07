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
      <el-table-column prop="productId" label="商品id" />
      <el-table-column prop="image" label="产品主图">
        <template slot-scope="scope">
          <a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="拼团名称" />
      <el-table-column prop="people" label="参团人数" />
      <el-table-column prop="price" label="拼团价" />
      <el-table-column prop="cost" label="原价" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column prop="browse" label="浏览量" />
      <el-table-column prop="countPeopleAll" label="参与人数" />
      <el-table-column prop="countPeoplePink" label="成团数量" />
      <el-table-column prop="countPeopleBrowse" label="访客人数" />
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <div @click="onSale(scope.row.id,scope.row.isShow)">
            <el-tag v-if="scope.row.isShow === 1" style="cursor: pointer" :type="''">已开启</el-tag>
            <el-tag v-else style="cursor: pointer" :type=" 'info' ">已关闭</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="stopTime" label="结束时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.stopTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_EDIT','YXSTORECOMBINATION_DELETE'])" label="操作" width="150px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_DELETE']"
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
import { del, onsale } from '@/api/yxStoreCombination'
import eForm from './form'
import { formatTimeTwo, parseTime } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      queryTypeOptions: [
        { key: 'title', display_name: '活动标题' }
      ]
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    parseTime,
    formatTimeTwo,
    checkPermission,
    beforeInit() {
      this.url = 'api/yxStoreCombination'
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
    onSale(id, status) {
      this.$confirm(`确定进行[${status ? '下架' : '上架'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          onsale(id, { status: status }).then(({ data }) => {
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
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = {
        id: data.id,
        productId: data.productId,
        merId: data.merId,
        image: data.image,
        images: data.images,
        imageArr: data.image.split(','),
        sliderImageArr: data.images.split(','),
        title: data.title,
        attr: data.attr,
        people: data.people,
        info: data.info,
        price: data.price,
        sort: data.sort,
        sales: data.sales,
        stock: data.stock,
        addTime: data.addTime,
        isHost: data.isHost,
        isShow: data.isShow,
        isDel: data.isDel,
        combination: data.combination,
        merUse: data.merUse,
        isPostage: data.isPostage,
        postage: data.postage,
        description: data.description,
        startTime: data.startTime,
        stopTime: data.stopTime,
        startTimeDate: new Date(data.startTimeDate),
        endTimeDate: new Date(data.endTimeDate),
        effectiveTime: data.effectiveTime,
        cost: data.cost,
        browse: data.browse,
        unitName: data.unitName
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
