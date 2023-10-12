<template>
  <div class="shopTopTool">
    <h3 class="toolTit">店铺头部</h3>
    <div class="tabBox">
      <div class="toolBox">
        <div class="modelTit">标题设置</div>
        <div
          class="labelListWarp"
        >
          <div class="labelList">
            <div class="imgListBox">
              <draggable >
                <div
                  class="item"
                >
                  <div class="listItemBox">
                    <div class="addLabelBox" >
                      <div class="itemBox">
                        <label>名称</label>
                        <el-input
                          v-model="activeComponent.componentContent.title"
                          placeholder="请输入内容"
                        ></el-input>
                      </div>
                    </div>
                  </div>
                </div>
              </draggable>
            </div>
          </div>
          <div class="addImgBtn" @click="addLabel">
            <span class="iconfont">&#xe64a;</span>添加标签
          </div>
        </div>
      </div>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="deleteItem"
    >
      <span>点击确定删除此项</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog :visible.sync="dialogImageVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import Draggable from 'vuedraggable'
import { toolMixin } from '@/mixins/tool.js'
import ToolSelectLink from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-link.vue'
import ToolSelectCategory from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-category.vue'
import ToolSingleImg from '@/views/theme/components/rootcompToolbar/toolModule/tool-single-img.vue'
export default {
  name: 'shopTopTool',
  mixins: [toolMixin],
  components: {
    ToolSingleImg,
    ToolSelectCategory,
    ToolSelectLink,
    Draggable
  },
  data () {
    return {
      dialogImageVisible: false,
      dialogImageUrl: '',
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
        },
        {
          id: 3,
          label: '居右',
          value: 'right'
        }
      ],
      textAlign: 'left',
      imgCurrent: null,
      labelCurrent: null,
      dialogVisible: false
    }
  },
  methods: {
    // 添加类别
    addCategory () {},
    openAddImg (item, index) {
      if (this.imgCurrent === index) {
        this.imgCurrent = null
        return false
      }
      this.imgCurrent = index
    },
    openAddLabel (item, index) {
      if (this.labelCurrent === index) {
        this.labelCurrent = null
        return false
      }
      this.labelCurrent = index
    },
    // 添加图文
    addImgText () {
      this.activeComponent.componentContent.bannerData.push({
        title: '',
        imgData: '',
        url: ''
      })
    },
    // 删除内容
    deleteItem (item, index) {
      this.$confirm('确定删除此项？')
        .then((_) => {
          this.activeComponent.componentContent.bannerData.splice(index, 1)
        })
        .catch((_) => {})
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
        .then((_) => {
          this.activeComponent.componentContent.labelList.splice(index, 1)
        })
        .catch((_) => {})
    },
    imgChange (file, index, key) {
      this.activeComponent.componentContent.bannerData[index][key] = URL.createObjectURL(file.raw)
    },
    showImage (imgData) {
      this.dialogImageUrl = imgData
      this.dialogImageVisible = true
    },
    delImage (index, key) {
      this.activeComponent.componentContent.bannerData[index][key] = ''
    }
  }
}
</script>

<style lang="scss" scoped>
.shopTopTool {
  padding: 20px 20px 0px 20px;
  .topTit {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #eeeeee;
    margin-bottom: 20px;
    span {
      height: 35px;
      line-height: 35px;
      font-size: 14px;
      color: #333333;
    }
    span:last-child {
      font-weight: bold;
      width: 100px;
      text-align: center;
      cursor: pointer;
      &:hover {
        color: $mainColor;
      }
    }
  }
  h3 {
    font-size: 18px;
    font-weight: 500;
    height: 35px;
    line-height: 35px;
    color: #333333;
    margin-bottom: 20px;
  }
  .titleBox {
    display: flex;
    justify-content: space-between;
  }
  .btnSelect {
    margin-top: 30px;
  }
  .toolBox {
    padding-bottom: 10px;
    .modelTit {
      font-size: 14px;
      color: #333333;
      margin-top: 10px;
    }
    .itemBox {
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
    }
    .imgListBox {
      margin-top: 30px;
      .item {
        border: 1px solid #e8eaec;
        border-radius: 4px;
        margin-bottom: 10px;
      }
      .listItemBox {
        .addImgTit {
          padding: 10px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          background: #f6f7f9;
          cursor: pointer;
          .titLeft {
            display: flex;
            align-items: center;
            span {
              color: #7d7e80;
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
        .addContent {
          padding: 5px 13px;
          .imgIsShow {
            display: flex;
            justify-content: space-between;
            margin: 18px 0 22px 0;
            span {
              font-size: 14px;
              color: #666666;
            }
          }
          .deleteItem {
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
            margin-bottom: 10px;
            span {
              font-size: 18px;
              color: #ffffff;
              margin-right: 5px;
            }
          }
        }
        .deleteItem {
          padding: 10px;
          display: flex;
          align-items: center;
          justify-content: center;
          background: #f6f7f9;
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
    .textTit {
      height: 35px;
      line-height: 35px;
      font-size: 16px;
      color: #333333;
      font-weight: bold;
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
    margin-bottom: 30px;
    span {
      font-size: 20px;
      margin-right: 5px;
    }
  }
  .labelList {
    .addLabelBox {
      padding: 10px;
    }
  }
}
</style>
