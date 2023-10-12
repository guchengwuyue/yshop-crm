<template>
  <div class="toolWarp">
    <!-------------------------Mobile---------------------------------->
    <!--    店铺头部-->
    <div class="toolPanel" v-if="activeComponent.type == 'header'">
      <shop-top></shop-top>
    </div>
    <!-- 轮播图 -->
    <div class="toolPanel" v-if="activeComponent.type == 'banner'">
      <banner></banner>
    </div>
    <!-- 菜单 -->
    <div class="toolPanel" v-if="activeComponent.type == 'menu'">
      <menus></menus>
    </div>
    <!-- 公告 -->
    <div class="toolPanel" v-if="activeComponent.type == 'adv'">
      <adv></adv>
    </div>
    <!-- 菜单 -->
    <div class="toolPanel" v-if="activeComponent.type == 'noticeBar'">
      <noticeBar></noticeBar>
    </div>
    <!--    文本控件-->
    <div class="toolPanel" v-if="activeComponent.type == 'text'">
      <text-tool></text-tool>
    </div>
    <!--    视频控件-->
    <div class="toolPanel" v-if="activeComponent.type == 'videoBox'">
      <video-tool></video-tool>
    </div>
    <!--    图文控件-->
    <div class="toolPanel" v-if="activeComponent.type == 'imageText'">
      <image-text></image-text>
    </div>
    <!--    自定义控件-->
    <div class="toolPanel" v-if="activeComponent.type == 'custom'">
      <custom-tool></custom-tool>
    </div>
    <!--    品牌列表-->
    <div class="toolPanel" v-if="activeComponent.type == 'brandList'">
      <brand-list></brand-list>
    </div>
    <!--    图文列表-->
    <div class="toolPanel" v-if="activeComponent.type == 'imageTextList'">
      <image-text-list></image-text-list>
    </div>
    <!--    优惠券-->
    <div class="toolPanel" v-if="activeComponent.type == 'coupon'">
      <coupon-tool></coupon-tool>
    </div>
    <!--    类别列表-->
    <div class="toolPanel" v-if="activeComponent.type == 'categoryList'">
      <category-list></category-list>
    </div>
    <!--    商品列表-->
    <div class="toolPanel" v-if="activeComponent.type == 'productList'">
      <product-list></product-list>
    </div>
    <!-------------------------PC---------------------------------->
    <!-- 个人中心导航 -->
    <div class="toolPanel" v-if="activeComponent.type == 'NavPersonal'">
      <!-- <NavPersonal /> -->
    </div>
    <!-- 分类导航 -->
    <div class="toolPanel" v-if="activeComponent.type == 'NavType'">
      <NavType />
    </div>
    <!-- 轮播图 -->
    <div class="toolPanel" v-if="activeComponent.type == 'PcBanner'">
      <PcBanner />
    </div>
    <!-- 底部版权相关 -->
    <div class="toolPanel" v-if="activeComponent.type == 'Footer'">
      <PcFooter />
    </div>
    <!-- 新品首发 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductNew'">
      <!-- <ProductNew /> -->
    </div>
    <!-- 人气爆款 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductHot'">
      <!-- <ProductHot /> -->
    </div>
    <!-- 秒杀专区 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductSeckill'">
      <!-- <ProductSeckill /> -->
    </div>
    <!-- 拼团专区 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductComb'">
      <!-- <ProductComb /> -->
    </div>
    <!-- 折扣专区 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductDiscount'">
      <!-- <ProductDiscount /> -->
    </div>
    <!-- 砍价专区 -->
    <div class="toolPanel" v-if="activeComponent.type == 'ProductBargin'">
      <!-- <ProductBargin /> -->
    </div>
  </div>
</template>

<script>
// mobile
import shopTop from '@/views/theme/components/rootcompToolbar/mobile/shopTop.vue'
import banner from '@/views/theme/components/rootcompToolbar/mobile/banner.vue'
import menus from '@/views/theme/components/rootcompToolbar/mobile/menus.vue'
import adv from '@/views/theme/components/rootcompToolbar/mobile/adv.vue'
import noticeBar from '@/views/theme/components/rootcompToolbar/mobile/noticeBar.vue'
import textTool from '@/views/theme/components/rootcompToolbar/mobile/textTool.vue'
import videoTool from '@/views/theme/components/rootcompToolbar/mobile/videoTool.vue'
import imageText from '@/views/theme/components/rootcompToolbar/mobile/imageText.vue'
import customTool from '@/views/theme/components/rootcompToolbar/mobile/customTool.vue'
import brandList from '@/views/theme/components/rootcompToolbar/mobile/brandList.vue'
import imageTextList from '@/views/theme/components/rootcompToolbar/mobile/imageTextList.vue'
import couponTool from '@/views/theme/components/rootcompToolbar/mobile/couponTool.vue'
import categoryList from '@/views/theme/components/rootcompToolbar/mobile/categoryTool.vue'
import productList from '@/views/theme/components/rootcompToolbar/mobile/productList.vue'

// pc 21.4月单商户改版
import NavPersonal from '@/views/theme/components/rootcompToolbar/pc/NavPersonal'
import NavType from '@/views/theme/components/rootcompToolbar/pc/NavType'
import PcBanner from '@/views/theme/components/rootcompToolbar/pc/Banner'
import PcFooter from '@/views/theme/components/rootcompToolbar/pc/Footer'
import ProductNew from '@/views/theme/components/rootcompToolbar/pc/ProductNew'
import ProductHot from '@/views/theme/components/rootcompToolbar/pc/ProductHot'
import ProductSeckill from '@/views/theme/components/rootcompToolbar/pc/ProductSeckill'
import ProductComb from '@/views/theme/components/rootcompToolbar/pc/ProductComb'
import ProductDiscount from '@/views/theme/components/rootcompToolbar/pc/ProductDiscount'
import ProductBargin from '@/views/theme/components/rootcompToolbar/pc/ProductBargin'

import { mapGetters } from 'vuex'
export default {
  components: {
    shopTop,
    banner,
    menus,
    adv,
    noticeBar,
    textTool,
    videoTool,
    imageText,
    customTool,
    brandList,
    imageTextList,
    couponTool,
    categoryList,
    productList,

    NavPersonal,
    NavType,
    PcBanner,
    PcFooter,
    ProductNew,
    ProductHot,
    ProductSeckill,
    ProductComb,
    ProductDiscount,
    ProductBargin
  },
  computed: {
    ...mapGetters(['activeComponent'])
  }
}
</script>

<style lang="scss" scoped>
.toolWarp {
  width: 320px;
}
.toolPanel {
  width: 320px;
}
.toolPanel::-webkit-scrollbar {
  width: 4px;
  height: 1px;
}

.toolPanel::-webkit-scrollbar-thumb {
  border-radius: 4px;
  -webkit-box-shadow: inset 0 0 5px rgba(156, 148, 148, 0.2);
  background: #cdcdcd;
}
.toolPanel::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px rgba(158, 150, 150, 0.2);
  border-radius: 4px;
  background: #ededed;
}
</style>
