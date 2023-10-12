<template>
  <div class="banner" :class="[{ on: componentContent.categoryShow }]">
    <el-carousel>
      <el-carousel-item
        v-for="(item, index) in bannerList"
        :key="index"
        :style="{ backgroundImage: 'url(' + item.pic + ')' }" >
        <img class="img" :src="item.pic" v-show="item.pic" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: 'cereBanner',
  props: {
    componentContent: {
      type: Object
    }
  },
  computed: {
    bannerList() {
      return this.componentContent.bannerData.filter(function (item) {
        return item.pic
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.banner {
  width: 100%;
  .el-carousel {
    height: auto;
    &.el-carousel--horizontal {
      height: 234px;
    }
    >>> .el-carousel__container {
      height: 234px;
      .el-carousel__item {
        height: auto;
        background-color: #fff;
        background-repeat: no-repeat;
        background-position: center;
        .img {
          display: block;
          width: 100%;
          height: auto;
        }
      }
    }
  }
  .el-carousel__indicators {
    bottom: 0;
  }
}
</style>
