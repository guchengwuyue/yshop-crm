<template>
  <div class="brandListTool">
    <h3 class="toolTit">品牌列表</h3>
    <div class="toolBox">
      <div class="itemBox">
        <label>标题</label>
        <el-input v-model="activeComponent.componentContent.title" placeholder="请输入内容"></el-input>
      </div>
      <div class="itemBox">
        <label>文字对齐方式</label>
        <el-select :popper-append-to-body="false" v-model="activeComponent.componentContent.textAlign" placeholder="请选文字对齐方式">
          <el-option
            v-for="item in alignList"
            :key="item.id"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="textTit">内容</div>
      <div class="imgListBox">
        <draggable v-model="activeComponent.componentContent.imgList">
          <div v-for="(item, index) in activeComponent.componentContent.imgList" :key="index" class="item">
            <div class="listItemBox">
              <div class="addImgTit" @click="openAddImg(item, index)">
                <div class="titLeft">
                  <span class="iconfont">&#xe703;</span>
                  <span class="iconfont">&#xe64a;</span>
                  <span>图片</span>
                </div>
                <div class="titRight">
                  <span class="iconfont" @click.stop="deleteItem(item, index)">&#xe633;</span>
                  <span v-html="activeComponent.componentContent.imgCurrent === index ? '&#xe660;' : '&#xe695;'" class="iconfont"></span>
                </div>
              </div>
              <div class="addBox" v-show="activeComponent.componentContent.imgCurrent === index">
                <div class="addContent">
                  <tool-single-img :imageUrl.sync='item.imgData'></tool-single-img>
                  <tool-select-link :linkObj.sync='item.linkObj'></tool-select-link>
                  <div class="itemImgTit itemBox">
                    <label>标题</label>
                    <el-input v-model="item.title" placeholder="请输入内容"></el-input>
                  </div>
                </div>
                <div @click="deleteItem(item, index)" class="deleteItem"><span class="iconfont">&#xe633;</span>删除内容</div>
              </div>
            </div>
          </div>
        </draggable>
      </div>
    </div>
    <div class="addImgBtn" @click="addImgText"><span class="iconfont">&#xe64a;</span>添加品牌图</div>
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
  </div>
</template>

<script>
import Draggable from 'vuedraggable'
import { toolMixin } from '@/mixins/tool.js'
import ToolSelectLink from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-link.vue'
import ToolSingleImg from '@/views/theme/components/rootcompToolbar/toolModule/tool-single-img.vue'
export default {
  mixins: [toolMixin],
  name: 'brandListTool',
  components: {
    ToolSingleImg,
    ToolSelectLink,
    Draggable
  },
  data () {
    return {
      title: '', // 标题内容
      imgTextData: [
        {
          title: '',
          imgData: '',
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
        },
        {
          id: 3,
          label: '居右',
          value: 'right'
        }
      ],
      linkOptions: [
        {
          value: '/index',
          label: '首页'
        },
        {
          value: '/list',
          label: '列表页'
        },
        {
          value: '/detail',
          label: '详情页'
        },
        {
          value: '/about',
          label: '关于我们'
        }
      ],
      textAlign: 'left',
      imgCurrent: null,
      dialogVisible: false
    }
  },
  methods: {
    openAddImg (item, index) {
      if (this.activeComponent.componentContent.imgCurrent === index) {
        this.activeComponent.componentContent.imgCurrent = null
        return false
      }
      this.activeComponent.componentContent.imgCurrent = index
    },
    // 添加图文
    addImgText () {
      this.activeComponent.componentContent.imgList.push({
        title: '标题',
        imgData: '',
        url: ''
      })
    },
    // 删除内容
    deleteItem (item, index) {
      this.$confirm('确定删除此项？')
        .then(_ => {
          this.activeComponent.componentContent.imgList.splice(index, 1)
        })
        .catch(_ => {})
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
    },
    onEditorBlur () { // 失去焦点事件
    },
    onEditorFocus () { // 获得焦点事件
    },
    onEditorChange () { // 内容改变事件
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
    .imgListBox {
      margin-top: 30px;
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
          .addImgBox {
            margin-bottom: 10px;
            >>> .el-upload {
              width: 100%;
              .el-upload-dragger {
                width: 100%;
              }
            }
            .uploadTip {
              text-align: center;
              font-size: 14px;
              color: #999999;
              line-height: 25px;
              margin-bottom: 10px;
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
    span {
      font-size: 20px;
      margin-right: 5px;
    }
  }
}
</style>
