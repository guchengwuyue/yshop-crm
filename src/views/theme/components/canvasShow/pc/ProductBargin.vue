<template>
  <div class="canvanProductDiscount">
      <product-slot
      :obj="obj">
          <div>砍价专区</div>
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
        'title': '砍价专区',
        'bcText': 'BARGAINING ZONE',
        'interface': ''
      }
    }
  }
}
</script>

<style lang="scss">

</style>
