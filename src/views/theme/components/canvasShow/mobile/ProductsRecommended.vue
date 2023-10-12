<template>
  <div class="hotCommodity card">
    <div class="card-header">
      <p class="title">精品推荐</p>
      <span class="more">更多</span>
    </div>
    <div class="card-body">
      <el-carousel height="170px">
        <el-carousel-item>
          <div class="goods-list">
            <div class="min-goods">
              <div class="img-box">
                <div class="tag">hot</div>
                <img class="img" src="https://image.dayouqiantu.cn/3.jpg" />
              </div>
              <div class="price-box">
                <div class="y-f">
                  <span class="seckill-current">￥0.50</span>
                  <span class="original">销量133张</span>
                </div>
              </div>
              <div class="title"></div>
            </div>
            <div class="min-goods">
              <div class="img-box">
                <div class="tag">hot</div>
                <img class="img" src="https://image.dayouqiantu.cn/3.jpg" />
              </div>
              <div class="price-box">
                <div class="y-f">
                  <span class="seckill-current">￥0.50</span>
                  <span class="original">销量133张</span>
                </div>
              </div>
              <div class="title"></div>
            </div>
            <div class="min-goods">
              <div class="img-box">
                <div class="tag">hot</div>
                <img class="img" src="https://image.dayouqiantu.cn/3.jpg" />
              </div>
              <div class="price-box">
                <div class="y-f">
                  <span class="seckill-current">￥0.50</span>
                  <span class="original">销量133张</span>
                </div>
              </div>
              <div class="title"></div>
            </div>
            <div class="min-goods">
              <div class="img-box">
                <div class="tag">hot</div>
                <img class="img" src="https://image.dayouqiantu.cn/3.jpg" />
              </div>
              <div class="price-box">
                <div class="y-f">
                  <span class="seckill-current">￥0.50</span>
                  <span class="original">销量133张</span>
                </div>
              </div>
              <div class="title"></div>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style lang="scss" scoped>
.y-f {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.card {
  margin-top: 5/375 * 500px;
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  margin-left: 10px;
  margin-right: 10px;
  padding: 10px;
  margin-bottom: 5px;
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 10/375 * 500px;
    .title {
      font-size: 17/375 * 500px;
      font-weight: 700;
    }
    .more {
      font-size: 15/375 * 500px;
      padding-left: 16/375 * 500px;
      color: #666;
    }
  }
}
.min-goods {
  width: 25%;
  background: #fff;
  .img-box {
    width: 83/375 * 500px;
    height: 83/375 * 500px;
    overflow: hidden;
    position: relative;
    .tag {
      position: absolute;
      left: 0;
      bottom: 0/375 * 500px;
      z-index: 2;
      line-height: 19/375 * 500px;
      background: linear-gradient(132deg, #f3dfb1, #f3dfb1, #ecbe60);
      border-radius: 0 9/375 * 500px 9/375 * 500px 0;
      padding: 0 5/375 * 500px;
      font-size: 13/375 * 500px;
      font-family: PingFang SC;
      font-weight: 700;
      color: #784f06;
    }
    .img {
      width: 100%;
      background-color: #ccc;
      display: block;
    }
  }
  .price-box {
    width: 100%;
    margin-top: 5/375 * 500px;
    .seckill-current {
      font-size: 16/375 * 500px;
      font-weight: 500;
      color: #e1212b;
    }
    .original {
      font-size: 10/375 * 500px;
      font-weight: 400;
      text-decoration: line-through;
      color: #999;
      margin-left: 7/375 * 500px;
    }
  }
}
.goods-list {
  display: flex;
  justify-content: space-between;
}
</style>
