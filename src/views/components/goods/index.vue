<template>
  <div>
    <div>
      <ul v-for="(item,index) in newValue" :key="index" class="el-upload-list el-upload-list--picture-card">
        <li tabindex="0" class="el-upload-list__item is-ready" :style="'width: '+width+'px;height: '+height+'px'">
          <div>
            <img :src="item.image" alt="" class="el-upload-list__item-thumbnail">
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
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;" @selection-change="handleSelectionChange" ref="multipleTable">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column prop="id" label="商品id" />
      <el-table-column ref="table" prop="image" label="商品图片">
        <template slot-scope="scope">
          <a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="storeName" label="商品名称" />
      <el-table-column prop="storeCategory.cateName" label="分类名称" />
      <el-table-column prop="price" label="商品价格" />
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
import { del, onsale } from '@/api/yxStoreProduct'
export default {
  components: { },
  mixins: [initData],
  props: {
    value: {
      type: Array,
      default() {
        return []
      }
    },
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
  watch: {
    page: function (val) {
      var map = this.selectGoods;
      map.set(this.lastPage, this.multipleSelection);
      this.selectGoods = map;

      if (map.get(val)) {
        this.multipleSelection = map.get(val);
      }
      this.lastPage = val;
    },
    data: function (val) {
      const _this=this;
      _this.$nextTick(()=> {
        //获取map
        var map = this.selectGoods;
        //判断当前页是否有数据
        var thePageData = map.get(this.page);
        if (thePageData) {
          thePageData.forEach(thePageData=>{
            val.forEach(tableData=>{
              if(tableData.id==thePageData.id){
                this.$refs.multipleTable.toggleRowSelection(tableData,true);
              }
            })
          })
        }
      })
    }
  },
  data() {
    return {
      lastPage: 0,
      selectGoods: new Map(),
      newValue: this.value,
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
        that.newValue.splice(index, 1)
        that.url = []
      })
    },
    doSelect() {
      this.newValue =[];
      var dataList=this.selectGoods;
      dataList.forEach(i=>{
        i.forEach(j=>{
          this.newValue.push(j)
        })
      })
      this.$emit("selectGoods", this.newValue)
      this.dialog = false
    },
    handleSelectionChange(val) {
      var map = this.selectGoods;
      map.set(this.lastPage, val);
      this.selectGoods = map;
      this.multipleSelection = val;
    },
    toSelete() {
      this.dialog = true
    },
    checkPermission,
    beforeInit() {
      this.url = 'api/yxStoreProduct'
      const sort = 'id,desc'
      this.params = {page: this.page, size: this.size, sort: sort, isShow: 1, isDel: 0,isIntegral:0}
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) {
        this.params[type] = value
      }
      return true
    }
  }
}
</script>

<style scoped>

</style>
