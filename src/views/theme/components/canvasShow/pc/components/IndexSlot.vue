<template>
  <div class="canvasIndexSlot">
    <nav class="title">
      <div class="bcGra">{{ obj.bcText }}</div>
      <div class="des">{{ obj.title }}</div>
      <span></span>
    </nav>
    <section class="son">
      <slot class="section"></slot>
    </section>
    <footer class="foot">
      <button class="slotBtn" @click="NavTo(obj.interface)">查看更多</button>
    </footer>
  </div>
</template>

<script>
export default {
  props: {obj: Object},
  methods: {
    NavTo (paths) {
        // if (!paths) { return }
        // window.scrollTo(0, 0)
        // this.$router.push({path: paths})
    }
  }
}
</script>

<style lang="scss">
.canvasIndexSlot{
  .title{
    text-align: center;
    .bcGra{
      margin: auto;
      font-size: 60px;
      font-weight: 400;
      font-family: UTM Bebas;
      background-image: linear-gradient(#DFDFDF, #EEE, transparent, transparent);
      color: transparent;
      -webkit-background-clip: text;
      background-clip: text;
      position: relative;
      top: 30px;
      z-index: 0;
    }
    .des{
      font-size: 40px;
      color: #333333;
      background-color: transparent;
      font-weight: 500;
      cursor: default;
    }
    span{
      display: block;
      width: 30px;
      height: 4px;
      background-color: #F26E47;
      margin: 20px auto 0;
    }
  }
  .foot{
    display: flex;
    align-items: center;
    margin-top: 30px;
    .slotBtn{
        width: 160px;
        height: 50px;
        margin: auto;
        font-size: 18px;
        color: #666666;
        background-color: #FFFFFF;
        border: 1px solid #E6E6E6;
        cursor: pointer;
        &:hover{
            color: #FFFFFF;
            background-color: #666666;
            border: 1px solid #666666;
        }
    }
  }
}
</style>
