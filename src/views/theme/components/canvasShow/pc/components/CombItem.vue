/**
    拼团专区组件
 */
<template>
  <div class="combItem">
    <div class="leftCon">
      <img
        class="leftImg"
        :src="obj.pic"
        alt="拼团商品" />
    </div>
    <div class="rightCon">
      <div class="name">{{ obj.storeName }}</div>
      <div class="price">￥{{ obj.price}}</div>
      <div class="footer">
        <div class="left">
          <span>{{ obj.people }}人团</span>
          <br>
          {{ obj.sales }}购买
        </div>
        <button class="getComb">去开团</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    obj: Object
  }
}
</script>

<style lang="scss">
.combItem{
  display: flex;
  .leftCon{
    width: 274px;
    height: 290px;
    padding: 20px 15px;
    background-color: #F3F3F3;
    .leftImg{
      width: 100%;
      height: 100%;
      cursor: pointer;
    }
  }
  .rightCon{
    width: 255px;
    margin: 30px 0;
    position: relative;
    .name{
        color: #666666;
        font-size: 24px;
        overflow: hidden;
        white-space:nowrap;
        text-overflow: ellipsis;
    }
    .price{
        font-size: 22px;
        color: #333333;
        font-family: PingFang SC;
        margin-top: 20px;
    }
    .footer{
      width: 100%;
      position: absolute;
      bottom: 0;
      font-size: 16px;
      .left{
        display: inline-block;
        color: #999999;
        span{
            color: #666666;
            font-size: 20px;
        }
      }
      .getComb{
        float: right;
        width: 100px;
        height: 40px;
        color: #FFF;
        background-color: #000;
        cursor: pointer;
        &:hover{
            color: #FFF;
            background-color: #F26E47;
            border: 1px solid #FFF;
        }
      }
    }
  }
}
</style>
