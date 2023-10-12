<template>
    <div class="textTool">
      <h3 class="toolTit">文本设置</h3>
      <div class="toolBox">
        <div class="itemBox">
          <label>标题内容</label>
          <el-input v-model="activeComponent.componentContent.title" placeholder="请输入内容"></el-input>
        </div>
        <div class="itemBox">
          <label>描述内容</label>
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            resize="none"
            v-model="activeComponent.componentContent.describe">
          </el-input>
        </div>
      </div>
      <div class="operationBox">
        <div class="itemBox">
          <div class="Tit">显示位置</div>
          <div class="Info">居{{positionText}}显示</div>
          <div class="modifyBox">
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.textPos === 'left'}" @click="textPosition(type = 'left')">&#xec86;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.textPos === 'center'}" @click="textPosition(type = 'center')">&#xe619;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.textPos === 'right'}" @click="textPosition(type = 'right')">&#xec82;</span>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">标题大小</div>
          <div class="Info">{{fontSize}}（{{activeComponent.componentContent.fontSizeNum}}号）</div>
          <div class="modifyBox fontSize">
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.fontSizeNum === 16}" @click="changeSize(sizeNum = 16)">&#xe600;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.fontSizeNum === 14}" @click="changeSize(sizeNum = 14)">&#xe600;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.fontSizeNum === 12}" @click="changeSize(sizeNum = 12)">&#xe600;</span>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">描述大小</div>
          <div class="Info">{{describeSize}}（{{activeComponent.componentContent.describeSizeNum}}号）</div>
          <div class="modifyBox fontSize">
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.describeSizeNum === 16}" @click="changeDescribe(sizeNum = 16)">&#xe600;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.describeSizeNum === 14}" @click="changeDescribe(sizeNum = 14)">&#xe600;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.describeSizeNum === 12}" @click="changeDescribe(sizeNum = 12)">&#xe600;</span>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">标题粗细</div>
          <div class="Info" v-text="activeComponent.componentContent.textFontW === 'bold' ? '加粗体' : '常规体'"></div>
          <div class="modifyBox fontSize">
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.textFontW === 'bold'}" @click="changeFontW(type = 'bold')">&#xe649;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.textFontW === 'normal'}" @click="changeFontW(type = 'normal')">&#xe8c2;</span>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">描述粗细</div>
          <div class="Info" v-text="activeComponent.componentContent.describeFontW === 'bold' ? '加粗体' : '常规体'"></div>
          <div class="modifyBox">
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.describeFontW === 'bold'}" @click="changeInfoFontW(type = 'bold')">&#xe649;</span>
            <span class="iconfont" :class="{textActive: activeComponent.componentContent.describeFontW === 'normal'}" @click="changeInfoFontW(type = 'normal')">&#xe8c2;</span>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">标题颜色</div>
          <div class="Info">{{activeComponent.componentContent.titColor}}</div>
          <div class="modifyBox">
            <div class="colorBox">
              <span @click="resetColor">重置</span>
              <div class="block">
                <el-color-picker v-model="activeComponent.componentContent.titColor"></el-color-picker>
              </div>
            </div>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">描述颜色</div>
          <div class="Info">{{activeComponent.componentContent.describeColor}}</div>
          <div class="modifyBox">
            <div class="colorBox">
              <span @click="resetDescribeColor">重置</span>
              <div class="block">
                <el-color-picker v-model="activeComponent.componentContent.describeColor"></el-color-picker>
              </div>
            </div>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">背景颜色</div>
          <div class="Info">{{activeComponent.componentContent.bgColor}}</div>
          <div class="modifyBox">
            <div class="colorBox">
              <span @click="resetBgColor">重置</span>
              <div class="block">
                <el-color-picker v-model="activeComponent.componentContent.bgColor"></el-color-picker>
              </div>
            </div>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">底部分割线</div>
          <div class="Info" v-text="activeComponent.componentContent.showLine ? '显示' : '隐藏'"></div>
          <div class="modifyBox">
            <el-checkbox v-model="activeComponent.componentContent.showLine"></el-checkbox>
          </div>
        </div>
        <div class="itemBox">
          <div class="Tit">查看更多</div>
          <div class="Info" v-text="activeComponent.componentContent.showMore ? '显示' : '隐藏'"></div>
          <div class="modifyBox">
            <el-checkbox v-model="activeComponent.componentContent.showMore"></el-checkbox>
          </div>
        </div>
        <div class="moreBox" v-show="activeComponent.componentContent.showMore">
          <div class="radio">
            <el-radio v-model="activeComponent.componentContent.styleValue" label="1">样式一</el-radio>
            <el-radio v-model="activeComponent.componentContent.styleValue" label="2">样式二</el-radio>
            <el-radio v-model="activeComponent.componentContent.styleValue" label="3">样式三</el-radio>
          </div>
          <div class="link">
            <label>链接</label>
            <el-select :popper-append-to-body="false" v-model="activeComponent.componentContent.link" placeholder="请选择跳转到的页面">
              <el-option
                v-for="item in linkOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { toolMixin } from '@/mixins/tool.js'
export default {
  name: 'textTool',
  mixins: [toolMixin],
  data () {
    return {
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
      ]
    }
  },
  computed: {
    fontSize () {
      if (this.activeComponent.componentContent.fontSizeNum === 16) {
        return '大'
      } else if (this.activeComponent.componentContent.fontSizeNum === 14) {
        return '中'
      } else {
        return '小'
      }
    },
    describeSize () {
      if (this.activeComponent.componentContent.describeSizeNum === 16) {
        return '大'
      } else if (this.activeComponent.componentContent.describeSizeNum === 14) {
        return '中'
      } else {
        return '小'
      }
    },
    positionText () {
      if (this.activeComponent.componentContent.textPos === 'left') {
        return '左'
      } else if (this.activeComponent.componentContent.textPos === 'center') {
        return '中'
      } else {
        return '右'
      }
    }
  },
  methods: {
    // 修改文字对齐方向
    textPosition (type) {
      this.activeComponent.componentContent.textPos = type
    },
    // 修改文本大小
    changeSize (sizeNum) {
      this.activeComponent.componentContent.fontSizeNum = sizeNum
    },
    // 修改描述大小
    changeDescribe (sizeNum) {
      this.activeComponent.componentContent.describeSizeNum = sizeNum
    },
    // 修改文本字体粗细
    changeFontW (type) {
      this.activeComponent.componentContent.textFontW = type
    },
    // 修改描述文字粗细
    changeInfoFontW (type) {
      this.activeComponent.componentContent.describeFontW = type
    },
    // 重置文本颜色
    resetColor () {
      this.activeComponent.componentContent.titColor = '#333333'
    },
    // 重置描述颜色
    resetDescribeColor () {
      this.activeComponent.componentContent.describeColor = '#333333'
    },
    // 重置背景颜色
    resetBgColor () {
      this.activeComponent.componentContent.bgColor = '#FFFFFF'
    }
  }
}
</script>

<style lang="scss" scoped>
.textTool {
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
    .itemBox {
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
    }
    border-bottom: 1px solid #eeeeee;
  }
  .operationBox {
    margin-top: 30px;
    .itemBox {
      font-size: 14px;
      display: flex;
      margin-bottom: 20px;
      align-items: center;
      .Tit {
        color: #888888;
        margin-right: 10px;
        width: 70px;
      }
      .Info {
        color: #222222;
      }
      .modifyBox {
        text-align: right;
        margin-left: auto;
        span {
          height: 26px;
          line-height: 26px;
          float: left;
          display: block;
          text-align: center;
          cursor: pointer;
          width: 30px;
          border: 1px solid #E8EAEC;
        }
        /*span:last-child {*/
        /*  border-right: 1px solid #E8EAEC;*/
        /*}*/
        .textActive {
          border: 1px solid $mainColor;
          color: $mainColor;
        }
        .colorBox {
          display: flex;
          align-items: center;
          justify-content: flex-end;
          span {
            margin-right: 10px;
            cursor: pointer;
            border: none;
            color: $mainColor;
          }
        }
      }
      .fontSize {
        span:nth-child(1) {
          font-size: 16px;
        }
        span:nth-child(2) {
          font-size: 14px;
        }
        span:nth-child(3) {
          font-size: 12px;
        }
      }
    }
    .moreBox{
      border: 1px solid #E8EAEC;
      border-radius: 4px;
      padding:20px 10px;
      .radio{
        margin-bottom: 20px;
      }
      .el-radio{
        margin-right: 10px;
      }
      .link{
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
    }
  }
  .block {
    height: 30px;
  }
  >>> .el-color-picker__trigger {
    width: 45px;
    height: 26px;
  }
  >>> .el-icon-arrow-down:before {
    display: none;
  }
}
</style>
