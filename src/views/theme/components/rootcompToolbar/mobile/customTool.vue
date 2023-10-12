<template>
  <div class="customTool">
    <h3 class="toolTit">自定义</h3>
    <div class="toolBox">
      <div class="selectBox">
        <div class="selectLayTit">选择模板<span>{{selectTemplateName}}</span></div>
        <div class="layoutList">
          <span class="item iconfont"
            :class="{active: activeComponent.componentContent.layoutType === item.type}"
            @click="selectLayout(item, index)"
            v-for="(item, index) of layoutList"
            :key="item.id"
            v-html="item.Icon"></span>
        </div>
      </div>
      <div class="itemBox flexStyle">
        <label>图片间隙</label>
        <div class="block">
          <el-slider
            :show-input-controls=false
            input-size="mini"
            v-model="activeComponent.componentContent.imgClearance"
            show-input></el-slider>
        </div>
      </div>
      <div class="itemBox flexStyle">
        <label>页面间距</label>
        <div class="block">
          <el-slider
            :show-input-controls=false
            input-size="mini"
            v-model="activeComponent.componentContent.pageSpacing"
            show-input></el-slider>
        </div>
      </div>

      <div class="itemBox">
        <div v-if="activeComponent.componentContent.layoutType !== 'average'">
          <div class="textTit">布局</div>
          <p>选定布局区域，在下方添加图片，建议添加比例一致的图片</p>
          <div class="layoutBox"
            :class="{
              L3: activeComponent.componentContent.layoutType === 'L3',
              L4: activeComponent.componentContent.layoutType === 'L4',
              T2B2: activeComponent.componentContent.layoutType === 'T2B2',
              L1R2: activeComponent.componentContent.layoutType === 'L1R2',
              T1B2: activeComponent.componentContent.layoutType === 'T1B2',
              L1T1B2: activeComponent.componentContent.layoutType === 'L1T1B2',
              average: activeComponent.componentContent.layoutType === 'average'}" >
            <div class="item"
              :class="{active: activeComponent.componentContent.imgBoxActive === index-1}"
              @click="changeLayout(index-1)"
              v-for="index of activeComponent.componentContent.elementNum"
              :key="index">
              <!--<span>宽度375像素</span>-->
              <img class="img"
                :src="activeComponent.componentContent.imgData[index-1].src"
                v-show="activeComponent.componentContent.imgData[index-1].src" />
            </div>
          </div>
        </div>
        <div v-else>
          <dl class="densityLiist">
            <dt>密度</dt>
            <dd>
              <el-select v-model="activeComponent.componentContent.density" placeholder="" @change="densityChange">
                <el-option label="4*4" value="4"></el-option>
                <el-option label="5*5" value="5"></el-option>
                <el-option label="6*6" value="6"></el-option>
                <el-option label="7*7" value="7"></el-option>
              </el-select>
            </dd>
          </dl>
          <div class="textTit">布局</div>
          <p>移动鼠标选定布局区域大小</p>
          <div class="averageBoxWarp">
            <div class="averageBox" @mouseleave="averageBoxLeave">
              <ul
                v-for="i of parseInt(activeComponent.componentContent.density)"
                :key="'y'+i"
                :class="'col'+activeComponent.componentContent.density">
                <li
                  v-for="j of parseInt(activeComponent.componentContent.density)"
                  :key="'x'+j"
                  :class="{'on':activeComponent.componentContent.averageBoxData[i-1][j-1].hover}"
                  @click="averageBoxClick(i-1,j-1)"
                  @mouseover="averageBoxMouseover(i-1,j-1)"></li>
              </ul>
            </div>
            <div class="selectedCube">
              <ul>
                <li
                  v-for="(item,index) of activeComponent.componentContent.imgData"
                  :class="{active: activeComponent.componentContent.imgBoxActive === index}"
                  @click="changeLayout(index)"
                  :key="index"
                  :style="{
                    'width':getItemValue(item.width) + '%',
                    'height':getItemValue(item.height) + '%',
                    'left':getItemValue(item.left) + '%',
                    'top':getItemValue(item.top) + '%'}">
                  <span>
                    {{parseInt(getItemValue(item.width) * 12)}}x{{parseInt(getItemValue(item.height) * 12)}}
                  </span>
                  <img class="img"
                    :src="activeComponent.componentContent.imgData[index].src"
                    v-if="activeComponent.componentContent.imgData[index].src">
                  <a class="btn-close"
                    @click="delsSlectedCube(item,index)">
                    <i class="icon iconfont icon-close"></i></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div v-if="activeComponent.componentContent.imgData.length !== 0">
          <div class="addImgTit">请添加图片</div>
          <div class="addImgBox">
            <div class="addImgBoxInner">
              <div class="addImg">
                <el-upload
                  drag
                  action="#"
                  :show-file-list="false"
                  :on-change="imgChange"
                  :auto-upload="false">
                  <img class="avatar"
                    v-if="activeComponent.componentContent.imgData[activeComponent.componentContent.imgBoxActive].src"
                    :src="activeComponent.componentContent.imgData[activeComponent.componentContent.imgBoxActive].src" >
                  <div class="tips"
                    v-if="activeComponent.componentContent.imgData[activeComponent.componentContent.imgBoxActive].src" >更换图片</div>
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  <span>添加图片</span>
                </el-upload>
              </div>
              <div class="addLink">
                <tool-select-link
                  :linkObj.sync='activeComponent.componentContent.imgData[activeComponent.componentContent.imgBoxActive].linkObj'
                  title="链接"></tool-select-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { toolMixin } from '@/mixins/tool.js'
import ToolSelectLink from '@/views/theme/components/rootcompToolbar/toolModule/tool-select-link.vue'
export default {
  name: 'customTool',
  mixins: [toolMixin],
  components: {
    ToolSelectLink
  },
  data () {
    return {
      title: '', // 标题内容
      // imgClearance: 0, // 图片间隙
      // pageSpacing: 0, // 页面间距
      imgBoxActive: 0,
      imageUrl: '', // 图片地址
      linkValue: '', // 链接地址
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
      layoutList: [
        {
          id: 1,
          type: 'L2',
          name: '一行二个',
          number: 2, // 显示格子数
          Icon: '&#xe603'
        },
        {
          id: 2,
          type: 'L3',
          name: '一行三个',
          number: 3,
          Icon: '&#xe603'
        },
        {
          id: 3,
          type: 'L4',
          name: '一行四个',
          number: 4,
          Icon: '&#xe603'
        },
        {
          id: 4,
          type: 'T2B2',
          name: '二左二右',
          number: 4,
          Icon: '&#xe603;'
        },
        {
          id: 5,
          type: 'L1R2',
          name: '一左二右',
          number: 3,
          Icon: '&#xe603'
        },
        {
          id: 6,
          type: 'T1B2',
          name: '一上二下',
          number: 3,
          Icon: '&#xe603'
        },
        {
          id: 7,
          type: 'L1T1B2',
          name: '一左三右',
          number: 4,
          Icon: '&#xe603'
        },
        {
          id: 8,
          type: 'average',
          name: '自定义',
          number: 0,
          Icon: '&#xe603'
        }
      ],
      activeLay: 0,
      elementNum: 2, // 生成格子数量
      // layoutType: 'L2',
      // density: '4',
      // averageBoxData: [], // 记录格子的激活状态
      beginAverageBox: [], // 记录开始点击的格子
      endAverageBox: [], // 记录结束点击的格子
      imgData: [] // 图片数据
    }
  },
  computed: {
    selectTemplateName () {
      for (let i = 0; i < this.layoutList.length; i++) {
        if (this.layoutList[i].type === this.activeComponent.componentContent.layoutType) {
          return this.layoutList[i].name
        }
      }
    }
  },
  methods: {
    // 计算生成格子百分比
    getItemValue (val) {
      const density = parseInt(this.activeComponent.componentContent.density)
      if (val === 0 || density === 0) {
        return 0
      }
      return (val / density * 10000 / 100.00)// 小数点后两位百分比
    },
    // 选择布局
    selectLayout (item, index) {
      this.activeComponent.componentContent.imgBoxActive = 0
      this.activeLay = index
      this.activeComponent.componentContent.elementNum = item.number
      this.activeComponent.componentContent.layoutType = item.type
      if (item.type === 'average') {
        this.densityChange(this.activeComponent.componentContent.density)
      } else {
        this.activeComponent.componentContent.imgData = []
        let obj = {
          src: '',
          linkObj: {
            selsectValue: '',
            selectName: '',
            typeText: '',
            url: ''
          }
        }
        for (let i = 0; i < item.number; i++) {
          this.activeComponent.componentContent.imgData.push(JSON.parse(JSON.stringify(obj)))
        }
      }
    },
    // 选择格子
    changeLayout (index) {
      this.activeComponent.componentContent.imgBoxActive = index
    },
    // 添加图片
    imgChange (file, fileList) {
      this.activeComponent.componentContent.imgData[this.activeComponent.componentContent.imgBoxActive].src = URL.createObjectURL(file.raw)
    },
    // 切换密度
    densityChange (val) {
      this.activeComponent.componentContent.imgBoxActive = 0
      const densityVal = parseInt(val)
      this.activeComponent.componentContent.averageBoxData = []
      for (let i = 0; i < densityVal; i++) {
        this.activeComponent.componentContent.averageBoxData[i] = []
        for (let j = 0; j < densityVal; j++) {
          this.activeComponent.componentContent.averageBoxData[i].push({
            hover: false, // 判断经过激活的位置
            takeUp: false // 判断占击占用的位置
          })
        }
      }
      this.activeComponent.componentContent.imgData = []
    },
    // 自定义格子点击
    averageBoxClick (x, y) {
      if (this.beginAverageBox.length === 0) {
        this.beginAverageBox = [x, y]
        this.endAverageBox = [x, y]
        this.activeComponent.componentContent.averageBoxData[x][y].hover = true
        this.activeComponent.componentContent.averageBoxData = this.activeComponent.componentContent.averageBoxData.concat([]) // 触发视图更新
      } else {
        const bx = this.beginAverageBox[0] // 起点X
        const by = this.beginAverageBox[1] // 起点Y
        // 使用最后一次合理经过的位置
        x = this.endAverageBox[0]
        y = this.endAverageBox[1]
        // 计算占用位置
        if (x >= bx) {
          for (let i = bx; i <= x; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].takeUp = true
              }
            } else {
              for (let j = y; j <= by; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].takeUp = true
              }
            }
          }
        } else {
          for (let i = x; i <= bx; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].takeUp = true
              }
            } else {
              for (let j = y; j <= by; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].takeUp = true
              }
            }
          }
        }

        // 生成图片框
        var obj = {
          src: '',
          linkObj: {
            selsectValue: '',
            selectName: '',
            typeText: '',
            url: ''
          }
        }
        if (x >= bx) {
          obj.width = x - bx + 1
          obj.left = bx
        } else {
          obj.width = bx - x + 1
          obj.left = x
        }
        if (y >= by) {
          obj.height = y - by + 1
          obj.top = by
        } else {
          obj.height = by - y + 1
          obj.top = y
        }
        this.activeComponent.componentContent.imgData.push(JSON.parse(JSON.stringify(obj)))
        this.beginAverageBox = []
      }
    },
    // 自定义格子经过
    averageBoxMouseover (x, y) {
      if (this.beginAverageBox.length !== 0) {
        // 计算有没有经过有占用的位置
        let flag = this.isBoxTakeUp(x, y)
        if (!flag) {
          return false
        }
        // 每次经过洗白格子
        const bx = this.beginAverageBox[0] // 起点X
        const by = this.beginAverageBox[1] // 起点Y
        const densityVal = parseInt(this.activeComponent.componentContent.density)
        for (let i = 0; i < densityVal; i++) {
          for (let j = 0; j < densityVal; j++) {
            this.activeComponent.componentContent.averageBoxData[i][j].hover = false
          }
        }
        this.activeComponent.componentContent.averageBoxData[x][y].hover = true
        console.log('111')
        // 起终点中间位置激活
        if (x >= bx) {
          for (let i = bx; i <= x; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].hover = true
              }
            } else {
              for (let j = y; j <= by; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].hover = true
              }
            }
          }
        } else {
          for (let i = x; i <= bx; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].hover = true
              }
            } else {
              for (let j = y; j <= by; j++) {
                this.activeComponent.componentContent.averageBoxData[i][j].hover = true
              }
            }
          }
        }
        this.endAverageBox = [x, y] // 记录结束位置
        this.activeComponent.componentContent.averageBoxData = this.activeComponent.componentContent.averageBoxData.concat([]) // 触发视图更新
      }
    },
    // 计算有没有经过有占用的位置
    isBoxTakeUp (x, y) {
      if (this.beginAverageBox.length !== 0) {
        const bx = this.beginAverageBox[0] // 起点X
        const by = this.beginAverageBox[1] // 起点Y
        let flag = true
        if (x >= bx) {
          for (let i = bx; i <= x; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                if (this.activeComponent.componentContent.averageBoxData[i][j].takeUp) {
                  flag = false
                  break
                }
              }
            } else {
              for (let j = y; j <= by; j++) {
                if (this.activeComponent.componentContent.averageBoxData[i][j].takeUp) {
                  flag = false
                  break
                }
              }
            }
          }
        } else {
          for (let i = x; i <= bx; i++) {
            if (y >= by) {
              for (let j = by; j <= y; j++) {
                if (this.activeComponent.componentContent.averageBoxData[i][j].takeUp) {
                  flag = false
                  break
                }
              }
            } else {
              for (let j = y; j <= by; j++) {
                if (this.activeComponent.componentContent.averageBoxData[i][j].takeUp) {
                  flag = false
                  break
                }
              }
            }
          }
        }
        return flag
      }
    },
    // 移出盒子清空
    averageBoxLeave () {
      this.beginAverageBox = []
      this.endAverageBox = []
      const densityVal = parseInt(this.activeComponent.componentContent.density)
      for (let i = 0; i < densityVal; i++) {
        for (let j = 0; j < densityVal; j++) {
          this.activeComponent.componentContent.averageBoxData[i][j].hover = false
        }
      }
      console.log(this.activeComponent.componentContent.averageBoxData)
    },
    // 删除选中格子
    delsSlectedCube (item, index) {
      console.log(this.activeComponent.componentContent.imgData)
      // 清除占用位置
      let bx = item.left
      let by = item.top
      let xl = item.width
      let yl = item.height
      for (let i = bx; i < bx + xl; i++) {
        for (let j = by; j < by + yl; j++) {
          this.activeComponent.componentContent.averageBoxData[i][j].takeUp = false
        }
      }
      this.activeComponent.componentContent.imgData.splice(index, 1)
    }
  },
  watch: {
    // 'activeComponent.componentContent.elementNum': function (val) {
    //   this.activeComponent.componentContent.imgData = []
    //   if (this.activeComponent.componentContent.layoutType !== 'average') {
    //     let obj = {
    //       src: '',
    //       link: ''
    //     }
    //     for (let i = 0; i < val; i++) {
    //       this.activeComponent.componentContent.imgData.push(JSON.parse(JSON.stringify(obj)))
    //     }
    //   }
    // }
  }
}
</script>

<style lang="scss" scoped>
.customTool {
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
    .selectLayTit {
      margin-bottom: 20px;
      font-size: 14px;
      color: #333333;
      span {
        font-size: 14px;
        color: $mainColor;
        margin-left: 20px;
      }
    }
    .layoutList {
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      margin-bottom: 30px;
      span {
        margin: -1px 0 0 -1px;
        flex: 0 0 15.9%;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #E8EAEC;
        color: #999999;
        font-size: 18px;
        text-align: center;
        height: 28px;
        cursor: pointer;
        position: relative;
        &.active,&:hover {
          color: $mainColor;
          &:after{
            content: '';
            width: 100%;
            height: 100%;
            border: 1px solid $mainColor;
            position: absolute;
            left: 0;
            top: 0;
            z-index: 2;
          }
        }
      }
    }
    padding-bottom: 10px;
    .flexStyle {
      display: flex;
      label {
        margin-right: 20px;
      }
      .block {
        flex: 1;
      }
      >>> .el-slider__input {
        width: 50px;
      }
      >>> .el-slider__runway {
        height: 4px;
        margin: 18px 65px 18px 0;
      }
      >>> .el-slider__bar {
        height: 4px;
      }
      >>> .el-slider__button-wrapper {
        top: -17px;
      }
      >>> .el-slider__button {
        width: 12px;
        height: 12px;
      }
      >>> .el-input-number.is-without-controls .el-input__inner {
        padding: 10px;
      }
    }
    .itemBox {
      width: 100%;
      label {
        font-size: 14px;
        color: #666666;
        height: 40px;
        line-height: 40px;
      }
      margin-bottom: 15px;
      p {
        font-size: 12px;
        color: #666666;
      }
      .layoutBox {
        margin-top: 20px;
        display: flex;
        .item {
          margin: -1px 0 0 -1px;
          flex: 0 0 50%;
          height: 142px;
          border: 1px solid #E8EAEC;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          position: relative;
          &:before {
            content: '600像素';
            font-size: 12px;
            color: #666666;
            padding: 0 10px;
            text-align: center;
          }
          .img{
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
          }
          &.active {
            background: #FBF9F8;
            &:before {
              color: $mainColor;
            }
            &:after{
              content: '';
              width: 100%;
              height: 100%;
              border: 1px solid $mainColor;
              position: absolute;
              left: 0;
              top: 0;
              z-index: 2;
            }
          }
        }
      }
      .L3 {
        .item {
          flex: 0 0 33.3%;
          height: 93px;
          &:before {
            content: '400像素';
          }
        }
      }
      .L4 {
        .item {
          flex:0 0 25%;
          height: 71px;
          &:before {
            content: '300像素';
          }
        }
      }
      .T2B2 {
        flex-wrap: wrap;
        .item {
          flex: 0 0 50%;
          height: 142px;
          &:before {
            content: '600x600像素';
          }
        }
      }
      .L1R2 {
        flex-wrap: wrap;
        .item{
          &:before {
            content: '600x600像素';
          }
        }
        .item:nth-child(1) {
          height: 284px;
          &:before {
            content: '600x1200像素';
          }
        }
        .item:nth-child(3) {
          margin-left: 138px;
          margin-top: -143px;
        }
      }
      .T1B2 {
        flex-wrap: wrap;
        .item{
          &:before {
            content: '600x600像素';
          }
        }
        .item:nth-child(1) {
          flex: 0 0 100%;
          &:before {
            content: '1200x600像素';
          }
        }
      }
      .L1T1B2 {
        flex-wrap: wrap;
        .item{
          &:before {
            content: '300x300像素';
          }
        }
        .item:nth-child(1) {
          &:before {
            content: '600x600像素';
          }
        }
        .item:nth-child(2) {
          height: 72px;
          &:before {
            content: '600x300像素';
          }
        }
        .item:nth-child(3) {
          flex: 0 0 25%;
          height: 72px;
          margin: -71px 0 0 138px;
        }
        .item:nth-child(4) {
          margin-top: -71px;
          flex: 0 0 25%;
          height: 72px;
        }
      }
      .average {
        flex-wrap: wrap;
        .item {
          flex: 0 0 33.33%;
          height: 93px;
        }
      }
      .addImgTit {
        margin-top: 20px;
        color: #FF3737;
        font-size: 12px;
      }
      .addImgBox {
        width: 100%;
        margin-top: 10px;
        padding: 10px;
        box-sizing: border-box;
        position: relative;
        padding-bottom: 50px;
        .addImgBoxInner{
          background: #F6F7F9;
          display: flex;
        }
        .addImg {
          width: 60px;
          height: 60px;
          margin-right: 10px;
          >>> .el-upload-dragger {
            width: 60px;
            height: 60px;
            text-align: center;
            .avatar{
              width: 100%;
              height: 100%;
            }
            i {
              margin-top: 10px;
            }
            .el-icon-plus:before {
              color: $mainColor;
            }
            span {
              display: block;
              font-size: 12px;
              color: $mainColor;
            }
            .tips{
            }
          }
        }
        .addLink {
          >>> .link-select{
            display: flex;
            margin-bottom: 0;
            line-height: 60px;
            .module-box__title{
              font-size: 12px;
              color: #333333;
              margin-bottom: 0;
              .module-box__label{
                line-height: 60px;
              }
            }
            &__select{
              flex: 1;
              .el-input__inner {
                border: none;
                background: none;
                font-size: 12px;
                color: $mainColor;
              }
              .el-input__inner::placeholder {
                color: $mainColor;
              }
              .el-icon-arrow-up:before {
                color: $mainColor;
              }
            }
            &__confirm{
              position: absolute;
              bottom: 0;
              left: 0;
              width: 100%;
            }
          }
        }
      }
      .averageBoxWarp{
        position: relative;
      }
      .averageBox{
        margin-top: 20px;
        display: flex;
        flex-wrap: wrap;
        ul{
          li{
            margin: -1px 0 0 -1px;
            border: 1px solid #E8EAEC;
            background-color: #f8f8f8;
            &.on{
              background-color: #fbbe73;
            }
          }
          &.col4{
            flex: 0 0 25%;
            li{
              height: 70px;
            }
          }
          &.col5{
            flex: 0 0 20%;
            li{
              height: 56px;
            }
          }
          &.col6{
            flex: 0 0 16.6%;
            li{
              height: 46px;
            }
          }
          &.col7{
            flex: 0 0 14.2%;
            li{
              height: 40px;
            }
          }
        }
      }
      .selectedCube{
        li{
          position: absolute;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 12px;
          border: 1px solid #E8EAEC;
          background-color: #fff;
          z-index: 2;
          margin: -1px 0 0 -1px;
          box-sizing: content-box;
          span{
            /*transform: scale(0.9);*/
            /*white-space: nowrap;*/
            /*text-align: center;*/
          }
          .img{
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
          }
          .btn-close{
            position: absolute;
            top: -10px;
            right: -10px;
            z-index: 4;
            cursor: pointer;
            opacity: 0.6;
            font-size: 20px;
            display: none;
          }
          &.active {
            z-index: 3;
            background: #FBF9F8;
            &:hover{
              .btn-close{
                display: block;
              }
            }
            span {
              color: $mainColor;
            }
            &:after{
              content: '';
              width: 100%;
              height: 100%;
              border: 1px solid $mainColor;
              position: absolute;
              left: 0;
              top: 0;
              z-index: 2;
            }
          }
        }
      }
    }
    .textTit {
      height: 35px;
      line-height: 35px;
      font-size: 16px;
      color: #333333;
      margin-top: 30px;
      font-weight: bold;
    }
  }
  .densityLiist{
    display: flex;
    justify-content: space-between;
    dt{
      line-height: 40px;
    }
  }
}
</style>
