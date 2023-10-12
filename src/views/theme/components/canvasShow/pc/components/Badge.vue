/**
    右上角角标组件
 */
<template>
  <div class="canvasBadge">
    <sup class="badge red" :class="bgwidth" v-if="sum != 0">{{ sum }}</sup>
  </div>
</template>

<script>
export default {
  name: 'zxBadge',
  props: {
    num: Number,
    size: String
  },
  computed: {
    sum: function () {
      if (this.num > 99) {
        return '99+'
      } else {
          return this.num
      }
    },
    bgwidth: function () {
      if (!this.size) {
          return 'small'
      } else {
          return this.size
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.canvasBadge{
    text-align: center;
    .badge{
        border-radius: 48px;
        padding: 0 3px;
    }
    .red{
        color: #FFFFFF;
        background-color: #FF0000;
    }
    .big{
        width: 48px;
        height: 48px;
        line-height: 32px;
    }
    .regular{
        font-size: 14px;
        width: 32px;
        height: 32px;
        line-height: 32px;
    }
    .small{
        font-size: 12px;
        width: 18px;
        height: 18px;
        line-height: 18px;
    }
}
</style>
