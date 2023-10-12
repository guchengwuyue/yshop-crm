<template>
  <div class="canvanProductComb">
      <product-slot
      :obj="obj">
        <ul class="container">
            <li class="combItem"
            v-for="(item, index) in componentContent.product"
            :key="index"
            >
                <CombItem :obj="item"/>
            </li>
        </ul>
      </product-slot>
  </div>
</template>

<script>
import ProductSlot from '@/views/theme/components/canvasShow/pc/components/IndexSlot.vue'
import CombItem from '@/views/theme/components/canvasShow/pc/components/CombItem.vue'
export default {
  components: {ProductSlot, CombItem},
  props: {
      componentContent: Object
  },
  data () {
    return {
      obj: {
        'title': '拼团专区',
        'bcText': 'SPELL GROUP ZONE',
        'interface': ''
      }
    }
  }
}
</script>

<style lang="scss">
.canvanProductComb{
  .container{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin: 30px 0;
    .combItem{
        margin: 5px 0;
    }
  }
}
</style>
