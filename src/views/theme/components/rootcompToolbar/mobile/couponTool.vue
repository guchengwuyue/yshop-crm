<template>
  <div class="couponTool">
    <h3 class="toolTit">优惠券</h3>
    <div class="toolBox">
      <!--<tool-coupon></tool-coupon>-->
      <div class="block topLine">
        <div class="blockTit">
          <span>排列样式</span>
          <span>{{composeData[activeComponent.componentContent.arrangeActiveIndex].name}}</span>
        </div>
        <div class="selectCompose">
          <div class="composeList">
            <span class="item iconfont" :class="{active: activeComponent.componentContent.arrangeActiveIndex === index}" @click="selectCompose(item, index)" v-for="(item, index) of composeData" :key="item.id" v-html="item.Icon"></span>
          </div>
        </div>
      </div>
      <div class="block">
        <div class="blockTit">
          <span>卡片样式</span>
          <span>{{cardType[activeComponent.componentContent.cardActiveIndex].name}}</span>
        </div>
        <div class="selectCompose">
          <div class="composeList">
            <span class="item iconfont" :class="{active: activeComponent.componentContent.cardActiveIndex === index}" @click="selectCard(item, index)" v-for="(item, index) of cardType" :key="item.id" v-html="item.Icon"></span>
          </div>
        </div>
      </div>
      <div class="block colorBox">
        <div class="blockTit">
          <span>颜色</span>
          <span>{{colorList[activeComponent.componentContent.colorActiveIndex].name}}</span>
        </div>
        <div class="selectColor">
          <div class="selectColor">
            <div class="colorList">
              <div :class="{active: activeComponent.componentContent.colorActiveIndex === index}" v-for="(item, index) of colorList" :key="item.value" @click="selectColor(item, index)"><span></span></div>
            </div>
          </div>
        </div>
      </div>
      <div class="block showInvBox">
        <div class="showInvalid">
          <div class="blockTit">
            <span>隐藏已抢完及失效的券</span>
            <span v-text="isHideCoupon ? '隐藏' : '显示'"></span>
          </div>
          <div class="showHideBtn">
            <el-checkbox v-model="isHideCoupon"></el-checkbox>
          </div>
        </div>
        <p>当前无可显示的优惠券时，优惠券区块将隐藏</p>
      </div>
    </div>
  </div>
</template>

<script>
import { toolMixin } from '@/mixins/tool.js'
import ToolCoupon from '@/views/theme/components/rootcompToolbar/toolModule/tool-coupon.vue'
export default {
  name: 'couponTool',
  mixins: [toolMixin],
  components: {
    ToolCoupon
  },
  data () {
    return {
      title: '', // 标题内容
      composeData: [
        {
          id: 1,
          type: 'L1',
          name: '一行一个',
          Icon: '&#xe603'
        },
        {
          id: 2,
          type: 'L2',
          name: '一行两个',
          Icon: '&#xe603'
        },
        {
          id: 3,
          type: 'L3',
          name: '一行三个',
          Icon: '&#xe603'
        },
        {
          id: 4,
          type: 'L4',
          name: '横向滑动',
          Icon: '&#xe603;'
        }
      ],
      // 卡片样式
      cardType: [
        {
          id: 1,
          type: 'type1',
          name: '样式一',
          Icon: '&#xe61f;'
        },
        {
          id: 2,
          type: 'type2',
          name: '样式二',
          Icon: '&#xe645;'
        },
        {
          id: 3,
          type: 'type3',
          name: '样式三',
          Icon: '&#xe66d;'
        },
        {
          id: 4,
          type: 'type4',
          name: '样式四',
          Icon: '&#xe692;'
        }
        // {
        //   id: 5,
        //   type: 'type5',
        //   name: '样式五',
        //   Icon: '&#xe6cf;'
        // },
        // {
        //   id: 6,
        //   type: 'type6',
        //   name: '样式六',
        //   Icon: '&#xe60b;'
        // },
        // {
        //   id: 7,
        //   type: 'type7',
        //   name: '样式七',
        //   Icon: '&#xe624;'
        // },
        // {
        //   id: 8,
        //   type: 'type8',
        //   name: '样式八',
        //   Icon: '&#xe625;'
        // }
      ],
      colorList: [
        {
          name: '红色',
          value: 'red'
        },
        {
          name: '蓝色',
          value: 'blue'
        },
        {
          name: '绿色',
          value: 'green'
        },
        {
          name: '粉红色',
          value: 'pink'
        },
        {
          name: '灰色',
          value: 'grey'
        }
      ],
      // arrangeActiveIndex: 0,
      // cardActiveIndex: 0,
      // colorActiveIndex: 0,
      dialogVisible: false,
      isHideCoupon: true
    }
  },
  computed: {
  },
  methods: {
    selectCompose (item, index) {
      this.activeComponent.componentContent.arrangeActiveIndex = index
    },
    // 选择卡片样式
    selectCard (item, index) {
      this.activeComponent.componentContent.cardActiveIndex = index
    },
    selectColor (item, index) {
      this.activeComponent.componentContent.colorActiveIndex = index
    }
  }
}
</script>

<style lang="scss" scoped>
.couponTool {
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
  }
  .block {
    .blockTit {
      padding-top: 20px;
      span {
        margin-right: 16px;
      }
      span:last-child {
        color: $mainColor;
      }
    }
  }
  .colorBox {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .blockTit {
      padding-top: 0;
    }
  }
  .topLine {
    border-top: 1px solid #eeeeee;
  }
  .composeList {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    margin-bottom: 30px;
    padding-top: 20px;
    span {
      width: 50px;
      height: 30px;
      display: flex;
      align-items: center;
      justify-content: center;
      border: 1px solid #E8EAEC;
      color: #999999;
      font-size: 18px;
      text-align: center;
      cursor: pointer;
    }
    span.active {
      color: $mainColor;
      border: 1px solid $mainColor;
    }
    span:hover {
      color: $mainColor;
      border: 1px solid $mainColor;
    }
  }
  .selectColor {
    display: flex;
    align-items: center;
    .colorList {
      display: flex;
      align-items: center;
      div {
        padding: 3px;
        border: 1px solid #eeeeee;
        border-radius: 50%;
        font-style: normal;
        margin-right: 10px;
        cursor: pointer;
        span {
          width: 15px;
          height: 15px;
          border-radius: 50%;
          background: #eeeeee;
          display: block;
        }
        &:nth-child(1) {
          span {
            background: #FF3737;
          }
        }
        &:nth-child(2) {
          span {
            background: #86A7FF;
          }
        }
        &:nth-child(3) {
          span {
            background: #98D4CB;
          }
        }
        &:nth-child(4) {
          span {
            background: #FF8888;
          }
        }
        &:nth-child(5) {
          span {
            background: #AFAFAF;
          }
        }
      }
      div.active {
        &:nth-child(1) {
          border-color: #FF3737;
        }
        &:nth-child(2) {
          border-color: #86A7FF;
        }
        &:nth-child(3) {
          border-color: #98D4CB;
        }
        &:nth-child(4) {
          border-color: #FF8888;
        }
        &:nth-child(5) {
          border-color: #AFAFAF;
        }
      }
    }
  }
  .showInvBox {
    p {
      color: #999999;
      font-size: 14px;
    }
  }
  .showInvalid {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 20px 0 13px 0;
    .blockTit {
      padding-top: 0;
    }
  }
}
</style>
