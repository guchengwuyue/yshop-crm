<template>
  <div class="canvasFooter">
    <div class="content">
      <div class="lab-group">
        <label class="fs24">{{ this.componentContent.publicList[0].title}}</label>
        <label class="fs50 mar-top-40">{{this.componentContent.publicList[0].des}}</label>
        <label class="col-ccc fs28 mar-top-10">{{this.componentContent.publicList[1].des}}</label>
        <div class="consult mar-top-40">咨询客服</div>
      </div>
      <div class="verticalline"></div>
      <div class="icon-group">
        <img class="gzh-icon" :src="componentContent.publicList[2].pic"/>
        <label class="des">{{this.componentContent.publicList[2].des}}</label>
      </div>
      <div class="icon-group">
        <img class="xcxm-icon" :src="componentContent.publicList[3].pic"/>
        <label class="des">{{this.componentContent.publicList[3].des}}</label>
      </div>
    </div>
    <div class="foot-box">
      <span style="font-family:arial;">&copy; </span>2016-2020CERESHOP版权所有，并保留所有权利 粤ICP备D51DD288号
    </div>
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
.canvasFooter{
  margin-top: 30px;
  height: 454px;
  background-color: #333333;
  color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  .content{
    display: flex;
    // width: 60%;
    // justify-content: space-between;
    width: 1240px;
    justify-content: space-around;
    .lab-group{
      display: flex;
      flex-direction: column;
      justify-content: center;
      label{
        font-weight: 400;
        margin: 10px;
      }
      .fs24{
        font-size: 24px;
      }
      .fs50{
        font-size: 50px;
      }
      .fs28{
        font-size: 28px;
      }
      .consult{
        width: 164px;
        height: 50px;
        margin-top: 40px;
        color: #cccccc;
        border: 1px #cccccc solid;
        line-height: 50px;
        text-align: center;
        cursor: pointer;
      }
    }
    .icon-group{
      display: flex;
      flex-direction: column;
      align-items: center;
      label{
        font-size: 20px;
        font-weight: 400;
      }
      .des{
        margin-top: 20px;
      }
    }
    .gzh-icon{
      height: 196px;
      width: 196px;
    }
    .xcxm-icon{
      height: 196px;
      width: 196px;
    }
    .verticalline{
      height: 238px;
      width: 1px;
      background-color:#666666;
    }
  }
}
.foot-box{
  margin-top: 80px;
}
</style>
