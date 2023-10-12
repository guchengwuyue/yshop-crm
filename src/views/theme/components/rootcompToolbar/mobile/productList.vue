<template>
  <div class="brandListTool">
    <h3 class="toolTit">商品列表</h3>
    <div class="toolBox">
      <div class="itemBox">
        <label>标题</label>
        <el-input v-model="activeComponent.componentContent.title" placeholder="请输入内容"></el-input>
      </div>
      <tool-select :linkValue.sync='activeComponent.componentContent.textAlign' title="文字对齐方式" :options="alignList"></tool-select>
      <div class="single-img"></div>
      <div class="textTit">商品来源</div>
      <div class="porListBox">
        <div class="addProduct">
          <div v-if="!activeComponent.componentContent.categoryName" class="addProBtn addImgBtn" @click="addProductCls"><span class="iconfont">&#xe685;</span> 添加类别</div>
          <div v-else class="categoryName">
            <span>{{activeComponent.componentContent.categoryName}}</span>
            <div class="operation">
              <span class="iconfont" @click="replaceCategory">&#xe66c;</span>
              <span class="iconfont" @click="deleteCategory">&#xe633;</span>
            </div>
          </div>
        </div>
      </div>
      <div class="productTit">
        <span>展示排数</span>
        <span>{{activeComponent.componentContent.productRowNum}}</span>
      </div>
      <div class="itemBox">
        <div class="block">
          <el-slider :max="9" :min="1" v-model="activeComponent.componentContent.productRowNum"></el-slider>
        </div>
      </div>
      <div class="productTit">
        <span>每排商品数</span>
        <span>{{activeComponent.componentContent.productNum}}</span>
      </div>
      <div class="itemBox">
        <div class="block">
          <el-slider :max="5" :min="2" v-model="activeComponent.componentContent.productNum"></el-slider>
        </div>
      </div>
    </div>

    <div class="labelLisTit titleBox">广告图配置
      <el-switch
        v-model="activeComponent.componentContent.bannerShow"
        active-color="#FF7800"
        inactive-color="#E8EAEC">
      </el-switch>
    </div>
    <div v-show="activeComponent.componentContent.bannerShow">
      <tool-single-img :imageUrl.sync='activeComponent.componentContent.bannerUrl'></tool-single-img>
      <tool-select-link :linkObj.sync='activeComponent.componentContent.bannerLinkObj' title="图片链接"></tool-select-link>
    </div>

    <div class="labelLisTit titleBox">标签配置
      <el-switch
        v-model="activeComponent.componentContent.labelShow"
        active-color="#FF7800"
        inactive-color="#E8EAEC">
      </el-switch>
    </div>
    <div class="labelListWarp" v-show="activeComponent.componentContent.labelShow">
      <div class="labelList">
        <div class="imgListBox">
          <draggable v-model="activeComponent.componentContent.labelList">
            <div v-for="(item, index) in activeComponent.componentContent.labelList" :key="index" class="item">
              <div class="listItemBox">
                <div class="addImgTit" @click="openAddLabel(item, index)">
                  <div class="titLeft">
                    <span class="iconfont">&#xe703;</span>
                    <span class="iconfont">&#xe64a;</span>
                    <span>标签</span>
                  </div>
                  <div class="titRight">
                    <span class="iconfont" @click.stop="deleteLabelItem(item, index)">&#xe633;</span>
                    <span v-html="labelCurrent === index ? '&#xe660;' : '&#xe695;'" class="iconfont"></span>
                  </div>
                </div>
                <div class="addLabelBox" v-show="labelCurrent === index">
                  <div class="itemBox">
                    <label>名称</label>
                    <el-input v-model="item.name" placeholder="请输入内容"></el-input>
                  </div>
                  <tool-select-link :linkObj.sync='item.linkObj'></tool-select-link>
                </div>
              </div>
            </div>
          </draggable>
        </div>
      </div>
      <div class="addImgBtn" @click="addLabel"><span class="iconfont">&#xe64a;</span>添加标签</div>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="deleteItem">
      <span>点击确定删除此项</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="选择类别" :visible.sync="dialogCategory" width="600px">
      <el-cascader style="width: 100%"
        ref="cascader"
        :options="categoryList"
        :props="{ checkStrictly: true,label: 'categoryName',value: 'id',children: 'childs' }"
        clearable></el-cascader>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogCategory = false">取 消</el-button>
        <el-button type="primary" @click="addCategoryData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Draggable from 'vuedraggable'
import { toolMixin, checkEmptyChild } from '@/mixins/tool.js'
import ToolSelectLink from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-link.vue'
import ToolSingleImg from '@/views/theme/components/rootcompToolbar/toolModule/tool-single-img.vue'
import ToolSelect from '@/views/theme/components/rootcompToolbar/toolModule/tool-select.vue'
import { getClassify, getProducts } from '@/api/canvasApi.js'

export default {
  name: 'productListTool',
  mixins: [toolMixin, checkEmptyChild],
  components: {
    ToolSelectLink,
    ToolSingleImg,
    ToolSelect,
    Draggable
  },
  data () {
    return {
      title: '', // 标题内容
      textInfo: '', // 文本
      imgTextData: [
        {
          title: '',
          isShow: true,
          imgData: '',
          describe: '',
          url: ''
        }
      ],
      alignList: [
        {
          id: 1,
          label: '居左',
          value: 'left'
        },
        {
          id: 2,
          label: '居中',
          value: 'center'
        }
      ],
      categoryList: [],
      textAlign: 'left',
      imgCurrent: null,
      dialogVisible: false,
      dialogCategory: false,
      currentCategory: null,
      // categoryName: '', // 类别名称
      productList: [], // 产品列表
      productNum: 2, // 商品展示数量
      labelCurrent: null
    }
  },
  methods: {
    // 获取类别
    getCategory () {
      getClassify().then(res => {
        this.categoryList = res.data
        checkEmptyChild(this.categoryList)
        this.dialogCategory = true
      })
    },
    openAddImg (item, index) {
      if (this.imgCurrent === index) {
        this.imgCurrent = null
        return false
      }
      this.imgCurrent = index
    },
    // 添加图文
    addImgText () {
      this.activeComponent.componentContent.imgTextData.push({
        title: '',
        isShow: true,
        imgData: '',
        url: ''
      })
    },
    // 删除内容
    deleteItem (item, index) {
      this.$confirm('确定删除此项？')
        .then(_ => {
          this.activeComponent.componentContent.imgTextData.splice(index, 1)
        })
        .catch(_ => {})
    },
    // 添加类别
    addCategoryData () {
      let nodesObj = this.$refs['cascader'].getCheckedNodes()
      if (nodesObj) {
        var categoryId = nodesObj[0].value
        var categoryName = nodesObj[0].label
        this.activeComponent.componentContent.categoryId = categoryId
        this.activeComponent.componentContent.categoryName = categoryName
        this.dialogCategory = false
        let params = {
          page: 1,
          pageSize: 20,
          classifyId: this.activeComponent.componentContent.categoryId
        }
        getProducts(params).then(res => {
          this.activeComponent.componentContent.imgTextData = res.data.list
        })
      }
    },
    // 替换类别
    replaceCategory () {
      this.getCategory()
    },
    // 删除已选类别
    deleteCategory () {
      this.activeComponent.componentContent.categoryName = ''
      this.activeComponent.componentContent.imgTextData = []
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    addProductCls () {
      this.getCategory()
    },
    // 标签手风琴
    openAddLabel (item, index) {
      if (this.labelCurrent === index) {
        this.labelCurrent = null
        return false
      }
      this.labelCurrent = index
    },
    // 添加标签
    addLabel () {
      this.activeComponent.componentContent.labelList.push({
        name: '',
        url: ''
      })
    },
    // 删除标签
    deleteLabelItem (item, index) {
      this.$confirm('确定删除此项？')
        .then(_ => {
          this.activeComponent.componentContent.labelList.splice(index, 1)
        })
        .catch(_ => {})
    }
  }
}
</script>

<style lang="scss" scoped>
.brandListTool {
  padding: 20px 20px 0px 20px;
  h3 {
    font-size: 18px;
    font-weight: 500;
    height: 35px;
    line-height: 35px;
    color: #333333;
    margin-bottom: 20px;
  }
  .toolBox {
    padding-bottom: 10px;
    .itemBox {
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
    }
    .textTit {
      height: 35px;
      line-height: 35px;
      font-size: 14px;
      color: #333333;
      display: flex;
      justify-content: space-between;
      span {
        font-weight: normal;
        font-size: 14px;
        color: #666666;
      }
    }
    .productTit {
      margin-top: 20px;
      color: #666666;
      height: 35px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      span {
        font-size: 14px;
        color: #666666;
      }
    }
    .porListBox {
      padding: 10px;
      background: #F0F3F4;
      .addProduct {
        .categoryName {
          height: 35px;
          display: flex;
          align-items: center;
          background: #e9e9e9;
          border-radius: 4px;
          padding: 0 10px;
          justify-content: space-between;
          span {
            color: #333333;
          }
          span {
            color: #333333;
          }
          .operation {
            display: flex;
            span {
              width: 35px;
              display: block;
              height: 35px;
              line-height: 35px;
              text-align: center;
              cursor: pointer;
            }
          }
        }
        .addProBtn {}
      }
    }
  }
  >>> .el-select {
    width: 100%;
  }
  .addImgBtn {
    border-radius: 4px;
    background: $mainColor;
    text-align: center;
    height: 36px;
    color: #ffffff;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    span {
      font-size: 20px;
      margin-right: 5px;
    }
  }
  .labelLisTit{
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
  .labelListWarp{
    padding-bottom: 20px;
    .imgListBox {
      margin-top: 20px;
      .item {
        border: 1px solid #E8EAEC;
        border-radius: 4px;
        margin-bottom: 10px;
      }
      .listItemBox {
        .addImgTit {
          padding: 10px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          background: #F6F7F9;
          cursor: pointer;
          .titLeft {
            display: flex;
            align-items: center;
            span {
              color: #7D7E80;
            }
            span:nth-child(1) {
              font-size: 28px;
            }
            span:nth-child(2) {
              font-size: 25px;
              margin: 0 6px;
            }
            span:nth-child(3) {
              font-size: 14px;
            }
          }
          .titRight {
            display: flex;
            align-items: center;
            span:nth-child(1) {
              width: 40px;
              text-align: center;
              display: block;
              height: 30px;
              line-height: 30px;
            }
          }
        }
        .addLabelBox{
          padding:0 10px 10px;
          .itemBox{
            margin-bottom: 20px;
            label{
              font-size: 14px;
              color: #666666;
              height: 40px;
              line-height: 40px;
            }
          }
          >>> .module-box{
            margin-bottom: 10px;
          }
        }
        .deleteItem {
          padding: 10px;
          display: flex;
          align-items: center;
          justify-content: center;
          background: #F6F7F9;
          cursor: pointer;
          color: $mainColor;
          font-size: 14px;
          span {
            font-size: 16px;
            margin-right: 5px;
          }
        }
      }
    }
  }
}
</style>
