<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container" >
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
      <el-table-column prop="id" label="砍价产品ID" />
      <el-table-column prop="title" label="砍价名称" />
      <el-table-column prop="image" label="砍价图片">
        <template slot-scope="scope">
          <a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="砍价价格" />
      <el-table-column prop="price" label="砍价区间">
        <template slot-scope="scope">
          <span>{{ scope.row.bargainMinPrice }}~{{ scope.row.bargainMaxPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="minPrice" label="最低价" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column prop="statusStr" label="砍价状态" />
      <el-table-column prop="startTime" label="开始时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="stopTime" label="结束时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.stopTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sales" label="销量" />
      <el-table-column prop="look" label="浏览量" />
      <el-table-column prop="share" label="分享量" />
      <el-table-column v-if="checkPermission(['admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_EDIT','YXSTOREBARGAIN_DELETE'])" label="操作" width="150px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button v-permission="['admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
          <el-popover
            :ref="scope.row.id"
            v-permission="['admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_DELETE']"
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
import { del } from '@/api/yxStoreBargain'
import eForm from './form'
import { formatTimeTwo, parseTime } from '@/utils/index'
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
    parseTime,
    formatTimeTwo,
    checkPermission,
    beforeInit() {
      this.url = 'api/yxStoreBargain'
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
        productId: data.productId,
        title: data.title,
        image: data.image,
        unitName: data.unitName,
        stock: data.stock,
        sales: data.sales,
        images: data.images,
        imageArr: data.image.split(','),
        sliderImageArr: data.images.split(','),
        startTime: data.startTime,
        stopTime: data.stopTime,
        storeName: data.storeName,
        price: data.price,
        minPrice: data.minPrice,
        num: data.num,
        bargainMaxPrice: data.bargainMaxPrice,
        bargainMinPrice: data.bargainMinPrice,
        bargainNum: data.bargainNum,
        status: data.status,
        description: data.description,
        giveIntegral: data.giveIntegral,
        info: data.info,
        cost: data.cost,
        sort: data.sort,
        isHot: data.isHot,
        isDel: data.isDel,
        addTime: data.addTime,
        isPostage: data.isPostage,
        postage: data.postage,
        rule: data.rule,
        look: data.look,
        share: data.share,
        startTimeDate: new Date(data.startTimeDate),
        endTimeDate: new Date(data.endTimeDate)
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
