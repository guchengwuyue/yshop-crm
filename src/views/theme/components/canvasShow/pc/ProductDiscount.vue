<template>
  <div class="canvanProductDiscount">
      <product-slot
      :obj="obj">
          <div>折扣专区</div>
      </product-slot>
  </div>
</template>

<script>
import ProductSlot from '@/views/theme/components/canvasShow/pc/components/IndexSlot.vue'
export default {
  components: {ProductSlot},
  data () {
    return {
      obj: {
        'title': '折扣专区',
        'bcText': 'DISCOUNT ZONE',
        'interface': ''
      }
    }
  }
}
</script>

<style lang="scss">

</style>
