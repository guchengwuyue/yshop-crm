<template>
  <div class="containerMall">
    <div class="topBox">
      <!-- <a class="btn-black">返回店铺</a> -->
      <ul>
        <li
          v-for="(item, index) in deviceList"
          :key="index"
          @click="toggleDevice(item.id)"
          :class="{ on: terminal == item.id }"
        >
          <!-- <i class="iconfont" :class="'icon-' + item.name"></i> -->
          <i class="iconfont" :class="'icon-' + item.name"></i>
        </li>
      </ul>
      <el-button class="btn-save"
      type="primary" @click="canvasSave">保存画布</el-button>
    </div>
    <div class="bottomWarp">
      <div class="leftBox">
        <LeftBar />
      </div>
      <div class="mainContentWarp">
        <div class="mainContent" :class="'view-' + terminal">
          <CereshopLayout :terminal="terminal"></CereshopLayout>
        </div>
      </div>
      <div class="RightBox">
        <ToolPanel />
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import LeftBar from '../components/leftBar/panel.vue'
import ToolPanel from '@/views/theme/components/rootcompToolbar/toolPanel.vue'
import CereshopLayout from '../components/canvasEditPage'
import {
  getCanvasData,
  getProducts,
  saveCanvasData
} from '@/api/canvasApi.js'
export default {
  components: {
    CereshopLayout,
    LeftBar,
    ToolPanel
  },
  data() {
    return {
      deviceList: [
        // {
        //   id: 1,
        //   name: "xiaochengxu",
        // },
        // {
        //   id: 2,
        //   name: "h5",
        // },
        {
          id: 3,
          name: 'phone'
        },
        {
          id: 4,
          name: 'pc'
        }
        // {
        //   id: 1,
        //   name: 'pc'
        // }
      ],
      canvasId: null
    }
  },
  computed: {
    ...mapGetters(['terminal', 'componentsData'])
  },
  mounted () {
    this.canvasGet()
  },
  methods: {
    ...mapMutations({
      setTerminal: 'SET_TERMINAL',
      setComponentsData: 'SET_COMPONENTSDATA'
    }),
    toggleDevice (id) {
      this.setTerminal(id)
      this.canvasGet()
    },
    // 保存画布
    canvasSave () {
      // 删除非必要的字符
      let cloneComponentsData = JSON.parse(JSON.stringify(this.componentsData))
      for (let i = 0; i < cloneComponentsData.length; i++) {
        delete cloneComponentsData[i].icon
        delete cloneComponentsData[i].iconH
        if (cloneComponentsData[i].type === 'productList') {
          cloneComponentsData[i].componentContent.imgTextData = []
        }
      }
      saveCanvasData({
        terminal: this.terminal,
        json: JSON.stringify(cloneComponentsData),
        name: '1',
        canvasId: this.canvasId || ''
      }).then(res => {
        if (res) {
          this.$message.success('保存成功！')
        }
      })
    },
    // 读取画布
    async canvasGet () {
      // var res = await getCanvasData({'terminal': 3})
      var res = await getCanvasData({'terminal': this.terminal})
      if (JSON.stringify(res) !== '{}') {
        this.canvasId = res.canvasId
        var componentsData = JSON.parse(res.json)
        for (let i = 0; i < componentsData.length; i++) {
          if (componentsData[i].type === 'productList') {
            if (componentsData[i].componentContent.categoryId) {
                getProducts({
                  'pageSize': 20,
                  'page': 1,
                  'classifyId': componentsData[i].componentContent.categoryId
                }).then(res => {
                  componentsData[i].componentContent.imgTextData = res.list
                })
            }
          }
        }
        this.setComponentsData(componentsData)
      } else {
        this.setComponentsData([])
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.containerMall {
  position: relative;
  display: flex;
  flex-direction: column;
  height:calc(100vh - 84px);
  overflow: hidden;

  .topBox {
    height: 52px;
    line-height: 52px;
    border-bottom: 1px solid #f0f3f4;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    // .btn-black {
    //   position: absolute;
    //   left: 20px;
    //   top: 0;
    // }
    ul{
      margin: 0;
      padding: 0;
      height: 52px;
      display: flex;
      align-items: center;
    }
    li {
      width: 56px;
      height: 52px;
      cursor: pointer;
      text-align: center;
      display: inline-block;
      vertical-align: text-top;
      .iconfont {
        font-size: 24px;
        // line-height: 52px;
      }
      &:hover,
      &.on {
        background-color: $mainColor;
        color: #fff;
      }
    }
    .btn-save {
      position: absolute;
      right: 20px;
      top: 5px;
          font-weight: 500;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;
    }
  }
  .bottomWarp {
    flex: 1;
    display: flex;
    overflow:hidden
  }
  .leftBox {
    height: 100%;
  }
  .mainContentWarp {
    background-color: #f0f3f4;
    overflow: auto;
    height: 100%;
    flex: 1;
    .mainContent {
      margin: 0 auto;
      max-width: 100%;
      width: 500px;
      &.view-4 {
        width: 1200px;
      }
    }
  }
  .RightBox {
    height: 100%;
    overflow: auto;
  }
}
</style>
