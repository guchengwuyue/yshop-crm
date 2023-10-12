/**
    画布_新品首发模块
 */
<template>
  <div class="canvasNewProduct">
    <product-slot
      class="NewGroup"
      :obj="obj">
      <ul class="Container">
        <li class="newItem"
          v-for="(item, index) in componentContent.product"
          :key="index" >
          <NewItem :obj="item"/>
        </li>
      </ul>
    </product-slot>
  </div>
</template>

<script>
import ProductSlot from '@/views/theme/components/canvasShow/pc/components/IndexSlot.vue'
import NewItem from '@/views/theme/components/canvasShow/pc/components/ProductItem.vue'
export default {
  components: {ProductSlot, NewItem},
  props: {
    componentContent: Object
  },
  data() {
    return {
      obj: {
        'title': '新品首发',
        'bcText': 'THE NEW START',
        'interface': ''
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.canvasNewProduct{
  .Container{
    display: flex;
    flex: wrap;
    margin: 30px 0;
    justify-content: space-around;
  }
}
</style>
