<template>
  <div class="categoryTool">
    <h3 class="toolTit">类别列表</h3>
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
      <div class="textTit">添加类别</div>
      <div class="categoryListBox">
        <draggable v-model="activeComponent.componentContent.categoryData">
          <div v-for="(item, index) in activeComponent.componentContent.categoryData" :key="index" class="item">
            <div class="listItemBox">
              <div class="addImgTit" @click="openAddCategory(item, index)">
                <div class="titLeft">
                  <span class="iconfont">&#xe703;</span>
                  <span class="iconfont">&#xe64a;</span>
                  <span>类别</span>
                </div>
                <div class="titRight">
                  <span class="iconfont" @click.stop="deleteItem(item, index)">&#xe633;</span>
                  <span v-html="categoryCurrent === index ? '&#xe660;' : '&#xe695;'" class="iconfont"></span>
                </div>
              </div>
              <div class="addBox" v-show="categoryCurrent === index">
                <div class="addContent">
                  <div v-if="item.name === ''" class="addCategoryBox" @click="addItemCategory(item, index)"><span class="iconfont">&#xe685;</span>添加类别</div>
                  <div v-else class="categoryName">
                    <span>{{item.name}}</span>
                    <div class="operation">
                      <span class="iconfont" @click="replaceCategory">&#xe66c;</span>
                      <span class="iconfont" @click="deleteCategory(index)">&#xe633;</span>
                    </div>
                  </div>
                </div>
                <div @click="deleteItem(item, index)" class="deleteItem"><span class="iconfont">&#xe633;</span>删除内容</div>
              </div>
            </div>
          </div>
        </draggable>
      </div>
    </div>
    <div class="addImgBtn" v-show="activeComponent.componentContent.categoryData.length < 6" @click="addCategory"><span class="iconfont">&#xe64a;</span>添加类别</div>
    <div class="addImgBtn" v-show="activeComponent.componentContent.categoryData.length === 6"><span class="iconfont">&#xe608;</span>最多只能添加6个</div>
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
    <el-dialog title="选择类别" :visible.sync="dialogCategory" width="480px">
      <div class="classList">
        <div class="classTit">
          <span>类别名称</span>
          <span>数量</span>
        </div>
        <div class="classListBox">
          <div class="classItemBox" v-for="(item, index) of categoryList" :key="item.id" @click="selectCategory(item, index)">
            <span class="iconfont" v-html="currentCategory === index ? '&#xe661;' : '&#xe618;'"></span>
            <div><span>{{item.name}}</span><span>{{item.num}}</span></div>
          </div>
        </div>
      </div>
      <div class="addSelectItem" @click="addCategoryData"><span>添加类别</span></div>
    </el-dialog>
  </div>
</template>

<script>
import Draggable from 'vuedraggable'
import { toolMixin } from '@/mixins/tool.js'
export default {
  name: 'categoryTool',
  mixins: [toolMixin],
  components: {
    Draggable
  },
  data () {
    return {
      title: '', // 标题内容
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
      categoryList: [
        {
          id: '1',
          name: '标签1',
          num: '6'
        },
        {
          id: '2',
          name: '标签2',
          num: '5'
        },
        {
          id: '3',
          name: '标签3',
          num: '6'
        },
        {
          id: '4',
          name: '标签4',
          num: '3'
        },
        {
          id: '5',
          name: '标签5',
          num: '2'
        },
        {
          id: '6',
          name: '标签6',
          num: '4'
        }
      ],
      textAlign: 'left',
      categoryCurrent: null,
      dialogVisible: false,
      dialogCategory: false,
      currentCategory: null,
      selClassName: ''
    }
  },
  methods: {
    openAddCategory (item, index) {
      if (this.categoryCurrent === index) {
        this.categoryCurrent = null
        return false
      }
      this.categoryCurrent = index
    },
    // 添加类别
    addCategory () {
      this.activeComponent.componentContent.categoryData.push({
        name: '',
        value: '',
        img: ''
      })
    },
    // 删除内容
    deleteItem (item, index) {
      this.$confirm('确定删除此项？')
        .then(_ => {
          this.activeComponent.componentContent.categoryData.splice(index, 1)
        })
        .catch(_ => {})
    },
    addItemCategory (item, index) {
      this.dialogCategory = true
    },
    // 替换类别
    replaceCategory (index) {
      this.dialogCategory = true
    },
    // 删除已选类别
    deleteCategory (index) {
      this.activeComponent.componentContent.categoryData[index].name = ''
    },
    // 选择类别
    selectCategory (item, index) {
      if (this.currentCategory === index) {
        this.currentCategory = null
        this.selClassName = ''
      } else {
        this.selClassName = item.name
        this.currentCategory = index
      }
    },
    // 添加类别
    addCategoryData () {
      if (this.selClassName !== '') {
        this.activeComponent.componentContent.categoryData[this.categoryCurrent].name = this.selClassName
        this.dialogCategory = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.categoryTool {
  padding: 20px 20px 0 20px;
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
    .textTit {
      height: 35px;
      line-height: 35px;
      font-size: 16px;
      color: #333333;
      font-weight: bold;
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
  }
  >>> .ql-container {
    height: 200px;
  }
  .categoryListBox {
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
        padding: 13px;
        .addCategoryBox {
          width: 100%;
          height: 35px;
          display: flex;
          align-items: center;
          justify-content: center;
          color: #ffffff;
          background: $mainColor;
          border-radius: 4px;
          margin: 15px 0;
          cursor: pointer;
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
        .categoryName {
          height: 35px;
          display: flex;
          align-items: center;
          background: #e9e9e9;
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
  .classList {
    .classTit {
      display: flex;
      justify-content: space-between;
      height: 50px;
      align-items: center;
      padding:0 20px;
      background: #eeeeee;
      span {
        display: block;
        width: 100px;
        text-align: center;
      }
    }
    .classListBox {
      max-height: 300px;
      overflow-y: auto;
      .classItemBox {
        display: flex;
        padding: 0 20px;
        align-items: center;
        border-bottom: 1px solid #eeeeee;
        div {
          display: flex;
          flex: 1;
          justify-content: space-between;
          height: 50px;
          align-items: center;
          span:nth-child(1) {
            padding-left: 5px;
          }
          span {
            display: block;
            width: 100px;
            text-align: left;
          }
          span:nth-child(2) {
            text-align: center;
          }
        }
      }
    }
  }
  >>> .el-dialog__body {
    padding: 0;
    .addSelectItem {
      width: 100%;
      background: #f3f4f5;
      text-align: right;
      padding: 10px;
      span {
        display: inline-block;
        width: 100px;
        text-align: center;
        color: #ffffff;
        padding: 16px;
        border-radius: 4px;
        background: $mainColor;
        cursor: pointer;
      }
    }
  }
}
>>> .el-dialog {
  border-radius: 4px;
}
>>> .el-select {
  width: 100%;
}
</style>
