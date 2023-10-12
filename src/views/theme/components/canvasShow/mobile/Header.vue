<template>
  <div class="header">
    <h1>{{ componentContent.title }}</h1>
  </div>
</template>

<script>
export default {
  props: {
    componentContent: {
      type: Object
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  height: 45px;
  padding-top: 0px;
  background: rgb(196, 4, 20);
  h1 {
    font-size: 20px;
    line-height: 45px;
    font-family: PingFang SC;
    font-weight: 500;
    color: #fff;
    margin-left: 15px;
  }
}
</style>
