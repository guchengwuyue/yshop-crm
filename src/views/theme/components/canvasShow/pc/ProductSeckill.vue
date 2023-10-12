<template>
  <div class="canvanProductSeckill">
    <product-slot
      :obj="obj">
      <ul class="Container">
        <!-- <li class="seckillItem">倒计时</li> -->
        <li class="seckillItem"
          v-for="(item, index) in componentContent.product"
          :key="index" >
            <ProductItem :obj="item"/>
        </li>
      </ul>
    </product-slot>
  </div>
</template>

<script>
import ProductSlot from '@/views/theme/components/canvasShow/pc/components/IndexSlot.vue'
import ProductItem from '@/views/theme/components/canvasShow/pc/components/ProductItem.vue'
export default {
  components: {ProductSlot, ProductItem},
  props: {
    componentContent: Object
  },
  data () {
    return {
      obj: {
        'title': '秒杀专区',
        'bcText': 'SECONDS KILL ZONE',
        'interface': '/indexpage/productlist'
      }
    }
  }
}
</script>

<style lang="scss">
.canvanProductSeckill{
  .Container{
    display: flex;
    flex: wrap;
    margin: 30px 0;
    justify-content: space-around;
  }
}
</style>
