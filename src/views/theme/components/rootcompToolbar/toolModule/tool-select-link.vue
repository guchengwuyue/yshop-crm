<template>
  <div class="module-box link-select">
    <div class="module-box__title">
      <label class="module-box__label">{{title}}</label>
    </div>
    <el-select class="link-select__select" :popper-append-to-body="false" v-model="selsectValue" placeholder="请选择跳转到的页面" @change="selectChanged">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <div class="link-select__confirm" v-show="confirmBtnVisible">
      <div class="btn" v-if="!selectName" @click="openDialog">
        <span class="iconfont">&#xe685;</span> 添加{{typeText}}
      </div>
      <div class="info" v-else>
        <span class="text">{{typeText}}</span>
        <span class="name">{{selectName}}</span>
        <span class="iconfont" @click="openDialog">&#xe66c;</span>
        <span class="iconfont" @click="delSelect">&#xe651;</span>
      </div>
    </div>
    <el-dialog width="600px" title="选择类别" :visible.sync="categoryVisible">
      <category-select ref="categorySelect"></category-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="categoryVisible = false">取 消</el-button>
        <el-button type="primary" @click="categoryChanged">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="选择商品" :visible.sync="productVisible">
      <product-select ref="productSelect"></product-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="productVisible = false">取 消</el-button>
        <el-button type="primary" @click="productChanged">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="选择店辅" :visible.sync="shopVisible">
      <shop-select ref="shopSelect"></shop-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shopVisible = false">取 消</el-button>
        <el-button type="primary" @click="shopChanged">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ProductSelect from './product-select'
import ShopSelect from './shop-select'
import CategorySelect from './category-select'
export default {
  name: 'tool-select-link',
  components: { CategorySelect, ShopSelect, ProductSelect },
  data () {
    return {
      selsectValue: '',
      confirmBtnVisible: false,
      selectName: '',
      typeText: '',
      productVisible: false,
      shopVisible: false,
      categoryVisible: false
    }
  },
  props: {
    title: {
      type: String,
      default: '链接'
    },
    linkObj: {
      type: Object,
      default: () => ({
        selsectValue: '',
        selectName: '',
        typeText: '',
        url: ''
      })
    },
    options: {
      type: Array,
      default: () => [
        {
          value: '/index',
          label: '首页'
        },
        {
          value: '/category',
          label: '分类页面'
        },
        {
          value: '/shop',
          label: '店辅主页'
        },
        {
          value: '/detail',
          label: '商品详情'
        }
      ]
    }
  },
  mounted () {
    this.selsectValue = this.linkObj.selsectValue
    this.selectName = this.linkObj.selectName
    this.typeText = this.linkObj.typeText
    this.confirmBtnVisible = this.selsectValue !== '/index'
  },
  methods: {
    // 链接选择
    selectChanged (value) {
      this.categoryVisible = false
      this.shopVisible = false
      this.productVisible = false
      this.confirmBtnVisible = true
      this.selectName = ''
      this.typeText = ''
      switch (value) {
        case '/category':
          this.typeText = '类别'
          break
        case '/shop':
          this.typeText = '店辅'
          break
        case '/detail':
          this.typeText = '商品'
          break
        default:
          this.confirmBtnVisible = false
          let linkObj = {
            selsectValue: this.selsectValue,
            selectName: '',
            typeText: '',
            url: '/'
          }
          this.$emit('update:linkObj', linkObj)
      }
    },
    // 打开添加弹窗
    openDialog () {
      switch (this.typeText) {
        case '类别':
          this.categoryVisible = true
          break
        case '店辅':
          this.shopVisible = true
          break
        case '商品':
          this.productVisible = true
          break
      }
    },
    // 类别选择
    categoryChanged () {
      let nodesObj = this.$refs.categorySelect.$refs['cascader'].getCheckedNodes()
      if (nodesObj) {
        var data = nodesObj[0].data
        this.selectName = nodesObj[0].label
        this.categoryVisible = false
        let linkObj = {
          selsectValue: this.selsectValue,
          selectName: this.selectName,
          typeText: this.typeText,
          data: data
        }
        this.$emit('update:linkObj', linkObj)
      }
    },
    // 商品选择
    productChanged () {
      var data = this.$refs.productSelect.tableRadio
      this.productVisible = false
      this.selectName = this.$refs.productSelect.tableRadio.productName
      let linkObj = {
        selsectValue: this.selsectValue,
        selectName: this.selectName,
        typeText: this.typeText,
        data: data
      }
      this.$emit('update:linkObj', linkObj)
    },
    // 店辅选择
    shopChanged () {
      var data = this.$refs.shopSelect.tableRadio
      this.shopVisible = false
      this.selectName = this.$refs.shopSelect.tableRadio.shopName
      let linkObj = {
        selsectValue: this.selsectValue,
        selectName: this.selectName,
        typeText: this.typeText,
        data: data
      }
      this.$emit('update:linkObj', linkObj)
    },
    // 删除选择
    delSelect () {
      let linkObj = {
        selsectValue: '',
        selectName: '',
        typeText: '',
        data: {}
      }
      this.$emit('update:linkObj', linkObj)
    }
  },
  watch: {
    linkObj: {
      handler (newVal, oldVal) {
        this.selsectValue = newVal.selsectValue
        this.selectName = newVal.selectName
        this.typeText = newVal.typeText
        this.confirmBtnVisible = this.selsectValue !== '/index'
      },
      deep: true
    }
  }
}
</script>

<style lang="scss" scoped>
.link-select{
  &__select{
    width: 100%;
  }
  &__confirm{
    margin-top: 10px;
    .btn{
      text-align: center;
      background-color: $mainColor;
      color: #fff;
      height: 36px;
      line-height: 36px;
      border-radius: 4px;
      cursor: pointer;
    }
    .info{
      height: 36px;
      line-height: 36px;
      border-radius: 4px;
      padding: 0 10px;
      border:1px solid $mainColor;
      display: flex;
      .text{
        color: $mainColor;
      }
      .name{
        flex: 1;
        margin-left: 10px;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
      }
      .iconfont{
        margin-left: 10px;
        cursor: pointer;
        color: #666;
      }
    }
  }
}
</style>
