<template>
  <div class="noticeBar">
    <div class="noticeBar-icon"></div>
    <div class="noticeBar-text">
      {{componentContent.roll[0].info}}
    </div>
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
.noticeBar {
  display: flex;
  width: 100%;
  box-sizing: border-box;
  flex-direction: row;
  align-items: center;
  padding: 6/750 * 500px 12/750 * 500px;
  margin-bottom: 10/750 * 500px;
  background-color: rgb(255, 251, 232);
  height: 60/750 * 500px;
  .noticeBar-icon {
    width: 14/750 * 500px;
    height: 14/750 * 500px;
    margin-right: 5/750 * 500px;
  }
  .noticeBar-text {
    flex: 1;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}
</style>
