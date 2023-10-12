<template>
  <div class="layout hom-layout">
    <draggable
      class="dragArea list-group"
      :list="componentsData"
      group="pageEdit"
      @change="pageChange"
    >
      <div
      class="list-group-item"
      v-for="(item,index) in componentsData"
      :key="index" :class="{'on':activeComponent == index}"
      @click="selectComponent(item,index)">
        <component
        :is="componentMap[terminal-3].get(item.type)"
        :componentContent="item.componentContent"
        :terminal="terminal"></component>
        <div class="btns">
          <span @click="delComponent(item,index)">
            <i class="iconfont icon-shanchu"></i>
            </span>
        </div>
      </div>
    </draggable>
  </div>
</template>

<script>
  import draggable from 'vuedraggable'
  import componentMap from '@/views/theme/components/canvasShow/componentMap.js'
  import { mapGetters, mapMutations } from 'vuex'
  export default {
    name: 'canvasEditPage',
    components: {
      draggable
    },
    props: {
      terminal: {
        type: Number,
        default: 4
      }
    },
    data () {
      return {
        activeComponent: -1,
        componentMap: componentMap
      }
    },
    computed: {
      ...mapGetters([
        'componentsData'
      ])
    },
    methods: {
      ...mapMutations({
        setActiveComponent: 'SET_ACTIVECOMPONENT',
        setComponentsData: 'SET_COMPONENTSDATA'
      }),
      pageChange (e) {
        if (e.added) {
          this.activeComponent = e.added.newIndex
          e.added.element.index = e.added.newIndex
          this.setActiveComponent(e.added.element)
        }
        if (e.moved) {
          this.activeComponent = e.moved.newIndex
          e.moved.element.index = e.moved.newIndex
          this.setActiveComponent(e.moved.element)
        }
      },
      selectComponent (item, index) {
        this.activeComponent = index
        item.index = index
        this.setActiveComponent(item)
      },
      delComponent (item, index) {
        this.$confirm('确定删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.activeComponent = -1
          this.componentsData.splice(index, 1)
        }).catch(() => {
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
.hom-layout{
  background-color: #fff;
  >>> .sortable-chosen{
    .contentBox {
      display: none;
    }
    .cloneText{
      display: block;
      width: 100%;
      height: 50px;
      line-height: 50px;
      font-size: 18px;
      text-align: center;
      background-color: $mainColor;
      color: #fff;
    }
  }
  .list-group{
    min-height: calc(100vh - 50px);
  }
  .list-group-item{
    position: relative;
    cursor: move;
    .btns{
      display: none;
    }
    &:hover{
      &:after{
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        left: 0;
        top: 0;
        border:1px $mainColor dashed;
        z-index: 2;
      }
    }
    &.on{
      &:after{
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        left: 0;
        top: 0;
        border:1px $mainColor solid;
        z-index: 2;
      }
      .btns{
        display: block;
        position: absolute;
        right: -13px;
        top: 50%;
        margin-top: -13px;
        z-index: 3;
        span{
          display: block;
          width: 26px;
          height: 26px;
          line-height: 26px;
          text-align: center;
          color: #666;
          background-color: #fff;
          box-shadow: 0 0 2px rgba(51,51,51,.2);
          cursor: pointer;
        }
      }
    }
  }
}
</style>

<style lang="scss">
.warp{
  width: 1252px;
  margin: 0 auto;
  max-width: 96%;
}
.flex-box{
  display: flex;
}
</style>
