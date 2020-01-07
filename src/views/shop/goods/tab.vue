<template>
  <el-tabs v-model="activeName" style="padding-left: 8px;" @tab-click="tabClick">
    <el-tab-pane label="出售中产品" name="first">
      <onSale ref="onSale" />
    </el-tab-pane>
    <el-tab-pane label="待上架产品" name="second">
      <unonSale ref="unonSale" />
    </el-tab-pane>
    <el-tab-pane label="产品回收站" name="thrid">
      <recycle ref="recycle" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import onSale from '@/views/shop/goods/index'
import unonSale from '@/views/shop/goods/index2'
import recycle from '@/views/shop/goods/index3'
import '@/assets/styles/description.scss'
export default {
  name: 'Tab',
  components: { onSale, unonSale, recycle },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {
    tabClick(name) {
      if (this.activeName === 'first') {
        this.$refs.onSale.init()
      } else if (this.activeName === 'second') {
        this.$refs.unonSale.init()
      } else {
        this.$refs.recycle.init()
      }
    }
  }
}
</script>

<style scoped>
</style>
