<template>
  <div>
    <div>
      <ul v-for="(item,index) in product" :key="index" class="el-upload-list el-upload-list--picture-card">
        <li tabindex="0" class="el-upload-list__item is-ready" :style="'width: '+width+'px;height: '+height+'px'">
          <div>
            <img :src="item.coverImgeUrl" alt="" class="el-upload-list__item-thumbnail">
            <span class="el-upload-list__item-actions">
            <span class="el-upload-list__item-delete" @click="deleteGood(index)">
              <i class="el-icon-delete" />
            </span>
          </span>
          </div>
        </li>
      </ul>
      <div tabindex="0" class="el-upload el-upload--picture-card"  @click="toSelete">
        <i class="el-icon-plus" />
      </div>
    </div>

    <el-dialog :visible.sync="dialog" append-to-body width="60%" title="商品列表">
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

    </div>
    <!--表单组件-->

    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;" @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column prop="goodsId" label="直播商品id" />
      <el-table-column prop="name" label="商品名称" />
      <el-table-column prop="productId" label="关联商品id" />
      <el-table-column prop="coverImgUrl" label="商品图片" >
        <template slot-scope="scope">
          <a :href="scope.row.coverImgeUrl" style="color: #42b983" target="_blank"><img :src="scope.row.coverImgeUrl" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="url" label="商品小程序路径" />
      <el-table-column prop="priceType" label="价格类型" >
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.priceType == 1" :type="''">一口价</el-tag>
            <el-tag v-else-if="scope.row.priceType == 2" :type="''">价格区间</el-tag>
            <el-tag v-else-if="scope.row.priceType == 3" :type="''">显示折扣价</el-tag>
          </div>
        </template>
      </el-table-column>
      <!--        <el-table-column v-if="columns.visible('price')" prop="price" label="price" />-->
      <!--        <el-table-column v-if="columns.visible('price2')" prop="price2" label="price2" />-->
      <el-table-column prop="auditStatus" label="审核状态" >
        <!--          //0：未审核，1：审核中，2:审核通过，3审核失败-->
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.auditStatus === 0" :type="''">未审核</el-tag>
            <el-tag v-else-if="scope.row.auditStatus === 1" :type="''">审核中</el-tag>
            <el-tag v-else-if="scope.row.auditStatus === 2" :type="''">审核通过</el-tag>
            <el-tag v-else-if="scope.row.auditStatus === 3" :type="''">审核失败</el-tag>
          </div>
        </template>
      </el-table-column>
      <!--        1, 2：表示是为api添加商品，否则是直播控制台添加的商品-->
      <el-table-colum prop="thirdPartyTag" label="添加途径" >
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.thirdPartyTag == 0" :type="''">api添加</el-tag>
            <el-tag v-else-if="scope.row.thirdPartyTag == 2" :type="''">控制台</el-tag>
          </div>
        </template>
      </el-table-colum>
    </el-table>

      <div style="margin-top: 20px">
        <el-button type="primary" @click="doSelect()">确定选择</el-button>
      </div>
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
  components: { },
  mixins: [initData],
  props: {
    product : Array,
    // 宽度
    width: {
      type: Number,
      default() {
        return 150
      }
    },
    // 宽度
    height: {
      type: Number,
      default() {
        return 150
      }
    }
  },
  data() {
    return {
      product:this.product,
      delLoading: false,
      visible: false,
      queryTypeOptions: [
        { key: 'storeName', display_name: '商品名称' }
      ],
      isAttr: false,
      multipleSelection: []
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    deleteGood(index) {
      const that = this
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        that.product.splice(index, 1)
        that.url = []
      })
    },
    doSelect() {
      this.product = this.multipleSelection
      this.$emit("selectGoods", this.product)
      this.dialog = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    toSelete() {
      this.dialog = true
    },
    checkPermission,
    beforeInit() {
      this.url = 'api/yxWechatLiveGoods'
      const sort = 'goods_id,desc'
      this.params = { page: this.page, size: this.size, sort: sort, auditStatus: "2" }
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) { this.params[type] = value }
      return true
    }
  }
}
</script>

<style scoped>

</style>
