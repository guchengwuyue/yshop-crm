<template>
  <div class="adv-box mx20 mb10">
    <!-- 模板1-->
    <div class="x-f" v-if="componentContent.detail.style == 1">
      <img
        @tap="jump(componentContent.detail.list[0].path)"
        :src="componentContent.detail.list[0].image" />
    </div>
    <!-- 模板2-->
    <div class="type1 x-f" v-if="componentContent.detail.style == 2">
      <img
        class="type1-img"
        @tap="jump(componentContent.detail.list[0].path)"
        :src="componentContent.detail.list[0].image" />
      <img
        class="type1-img"
        @tap="jump(componentContent.detail.list[1].path)"
        :src="componentContent.detail.list[1].image" />
    </div>
    <!-- 模板3-->
    <div class="type2 x-bc" v-if="componentContent.detail.style == 3">
      <img
        class="type2-img1"
        @tap="jump(componentContent.detail.list[0].path)"
        :src="componentContent.detail.list[0].image" />
      <div class="y-f type2-box">
        <img
          class="type2-img2"
          @tap="jump(componentContent.detail.list[1].path)"
          :src="componentContent.detail.list[1].image"
          style="border-bottom: 1px solid #f6f6f6" />
        <img
          class="type2-img2"
          @tap="jump(componentContent.detail.list[2].path)"
          :src="componentContent.detail.list[2].image" />
      </div>
    </div>
    <!-- 模板4-->
    <div class="type3 x-bc" v-if="componentContent.detail.style == 4">
      <div class="type3-box y-f">
        <img
          class="type3-img1"
          @tap="jump(componentContent.detail.list[0].path)"
          :src="componentContent.detail.list[0].image" />
        <img
          class="type3-img1"
          @tap="jump(componentContent.detail.list[1].path)"
          :src="componentContent.detail.list[1].image" />
      </div>
      <img
        class="type3-img2"
        @tap="jump(componentContent.detail.list[2].path)"
        :src="componentContent.detail.list[2].image" />
    </div>
    <!-- 模板5-->
    <div class="type4 y-f" v-if="componentContent.detail.style == 5">
      <div class="type4-box x-f">
        <img
          class="type4-img1"
          @tap="jump(componentContent.detail.list[0].path)"
          :src="componentContent.detail.list[0].image" />
        <img
          class="type4-img1"
          @tap="jump(componentContent.detail.list[1].path)"
          :src="componentContent.detail.list[1].image" />
      </div>
      <img
        class="type4-img2"
        @tap="jump(componentContent.detail.list[2].path)"
        :src="componentContent.detail.list[2].image" />
    </div>
    <!-- 模板6-->
    <div class="type5 y-f" v-if="componentContent.detail.style == 6">
      <img
        class="type5-img1"
        @tap="jump(componentContent.detail.list[0].path)"
        :src="componentContent.detail.list[0].image" />
      <div class="type5-box x-bc">
        <img
          class="type5-img2"
          @tap="jump(componentContent.detail.list[1].path)"
          :src="componentContent.detail.list[1].image"
          style="border-bottom: 1px solid #f6f6f6" />
        <img
          class="type5-img2"
          @tap="jump(componentContent.detail.list[2].path)"
          :src="componentContent.detail.list[2].image" />
      </div>
    </div>
    <!-- 模板7-->
    <div class="type6 y-f" v-if="componentContent.detail.style == 7">
      <div class="x-f type6-box1">
        <img
          class="type6-img1"
          @tap="jump(componentContent.detail.list[0].path)"
          :src="componentContent.detail.list[0].image" />
        <img
          class="type6-img1"
          @tap="jump(componentContent.detail.list[1].path)"
          :src="componentContent.detail.list[1].image" />
      </div>
      <div class="x-f type6-box2">
        <img
          class="type6-img2"
          @tap="jump(componentContent.detail.list[2].path)"
          :src="componentContent.detail.list[2].image" />
        <img
          class="type6-img2"
          @tap="jump(componentContent.detail.list[3].path)"
          :src="componentContent.detail.list[3].image" />
        <img
          class="type6-img2"
          @tap="jump(componentContent.detail.list[4].path)"
          :src="componentContent.detail.list[4].image" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    componentContent: {
      type: Object
    }
  },
  methods: {
    // 路由跳转
    jump (path) {}
  }
}
</script>

<style lang="scss" scoped>
.y-f {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.x-bc {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.adv-box {
  background-color: #fff;
  border-radius: 20/750 * 500px;
  overflow: hidden;
  image {
    width: 100%;
  }
  .type1 {
    .type1-img {
      flex: 1;
      height: 220/750 * 500px;
      &:first-child {
        border-right: 1px solid #f6f6f6;
      }
    }
  }
  .type2 {
    .type2-img1 {
      width: 249px;
      height: 249px;
      border-right: 1px solid #f6f6f6;
    }
    .type2-box {
      flex: 1;
      height: 249px;
      width: 249px;
      .type2-img2 {
        width: 100%;
      }
    }
  }
  .type3 {
    .type3-box {
      width: (710/750 * 500px);
      border-right: 1px solid #f6f6f6;
      .type3-img1 {
        flex: 1;
        height: (340/750 * 500px);
        &:first-child {
          border-bottom: 1px solid #f6f6f6;
        }
      }
    }
    .type3-img2 {
      flex: 1;
      height: 340/750 * 500px;
      width: (710/750 * 500px);
    }
  }
  .type4 {
    .type4-box {
      border-bottom: 1px solid #f6f6f6;
      .type4-img1 {
        flex: 1;
        height: (340/750 * 500px);
        &:first-child {
          border-right: 1px solid #f6f6f6;
        }
      }
    }
    .type4-img2 {
      flex: 1;
      height: (340/750 * 500px);
      width: 710/750 * 500px;
    }
  }
  .type5 {
    .type5-img1 {
      width: 710/750 * 500px;
      height: (340/750 * 500px);
      border-bottom: 1px solid #f6f6f6;
    }
    .type5-box {
      flex: 1;
      height: (340/750 * 500px);
      width: 710/750 * 500px;
      .type5-img2 {
        height: (340/750 * 500px);
        &:first-child {
          border-right: 1px solid #f6f6f6;
        }
      }
    }
  }
  .type6 {
    .type6-box1 {
      .type6-img1 {
        width: (710/750 * 500px);
        height: (340/750 * 500px);
        &:first-child {
          border-right: 1px solid #f6f6f6;
        }
      }
    }
    .type6-box2 {
      border-top: 1px solid #f6f6f6;
      .type6-img2 {
        width: (710/750 * 500 / 3) px;
        height: (340/750 * 500px);
        border-right: 1px solid #f6f6f6;
        &:last-child {
          border-right: 0;
        }
      }
    }
  }
}
</style>
