<template>
  <div class="canvasBannerBox">
      <el-carousel
      height="755px"
      arrow="never">
        <el-carousel-item v-for="(item, index) in this.componentContent.bannerList" :key="index">
          <div carContant>
            <img class="bcimg" :src="item.pic" alt="轮播图">
            <span>{{ item.pic }}</span>
          </div>
        </el-carousel-item>
      </el-carousel>
  </div>
</template>

<script>
export default {
  props: {
    componentContent: Object
  }
}
</script>

<style lang="scss" scoped>
.canvasBannerBox{
  .el-carousel {
    // height: 755px;
    img{
      width: 100%;
      height: 755px;
    }
    .el-carousel__button{
        height: 2px;
        opacity: 1;
        background-color: #CCCCCC;
    }
    .is-active{
        .el-carousel__button{
        background-color: #333333;
        }
    }
  }
}
</style>
