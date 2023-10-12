<template>
  <div class="layout hom-layout">
    <div
      class="list-group-item"
      v-for="(item, index) in componentsData"
      :key="index"
    >
      <component
        :is="componentMap[terminal - 1].get(item.type)"
        :componentContent="item.componentContent"
        :terminal="terminal"
      ></component>
    </div>
  </div>
</template>

<script>
import componentMap from './componentMap'
import { mapMutations } from 'vuex'
import { getCanvasData, getProducts } from '@/api/canvasApi.js'
export default {
  name: 'canvasPage',
  data () {
    return {
      terminal: 3,
      componentsData: [],
      activeComponent: -1,
      componentMap: componentMap
    }
  },
  mounted () {
    this.canvasGet()
  },
  methods: {
    ...mapMutations({
      setComponentsData: 'SET_COMPONENTSDATA'
    }),
    // 读取画布
    async canvasGet () {
      const res = await getCanvasData({'terminal': this.terminal})
      if (JSON.stringify(res.data) !== '{}') {
        this.canvasId = res.data.canvasId
        this.componentsData = JSON.parse(res.data.json)
        var componentsData = this.componentsData
        for (let i = 0; i < componentsData.length; i++) {
          if (componentsData[i].type === 'productList') {
            if (componentsData[i].componentContent.categoryId) {
              let params = {
                page: 1,
                pageSize: 20,
                classifyId: componentsData[i].componentContent.categoryId
              }
              getProducts(params).then(rres => {
                componentsData[i].componentContent.imgTextData = rres.data.list
              })
            }
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.hom-layout {
  background-color: #f5f5f5;
}
</style>

<style lang="scss">
.warp {
  width: 1252px;
  margin: 0 auto;
  max-width: 96%;
}
.flex-box {
  display: flex;
}
</style>
