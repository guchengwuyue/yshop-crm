<template>
  <div class="recommended">
    <div class="recommended-title-card mb10">
      <div class="title-box">
        <img
          class="title-bg"
          src="data:image/png;base64,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"
        />
        <div class="title-text">为你推荐</div>
      </div>
    </div>
    <div class="recommended-body">
      <div class="goods-list">
        <div class="min-goods">
          <div class="min-goods-box">
            <div class="img-box">
              <img
                class="img"
                src="https://image.dayouqiantu.cn/3.jpg"
              />
            </div>
            <div class="price-box">
              <div class="tip one-t">黑白彩色专业高速打印复印</div>
              <div class="title more-t">黑白彩色专业高速打印复印</div>
              <div class="price-box">
                <span class="seckill-current">￥0.50</span>
                <span class="original">销量133张</span>
              </div>
            </div>
          </div>
        </div>
        <div class="min-goods">
          <div class="min-goods-box">
            <div class="img-box">
              <img
                class="img"
                src="https://image.dayouqiantu.cn/3.jpg"
              />
            </div>
            <div class="price-box">
              <div class="tip one-t">黑白彩色专业高速打印复印</div>
              <div class="title more-t">黑白彩色专业高速打印复印</div>
              <div class="price-box">
                <span class="seckill-current">￥0.50</span>
                <span class="original">销量133张</span>
              </div>
            </div>
          </div>
        </div>
        <div class="min-goods">
          <div class="min-goods-box">
            <div class="img-box">
              <img
                class="img"
                src="https://image.dayouqiantu.cn/3.jpg"
              />
            </div>
            <div class="price-box">
              <div class="tip one-t">黑白彩色专业高速打印复印</div>
              <div class="title more-t">黑白彩色专业高速打印复印</div>
              <div class="price-box">
                <span class="seckill-current">￥0.50</span>
                <span class="original">销量133张</span>
              </div>
            </div>
          </div>
        </div>
        <div class="min-goods">
          <div class="min-goods-box">
            <div class="img-box">
              <img
                class="img"
                src="https://image.dayouqiantu.cn/3.jpg"
              />
            </div>
            <div class="price-box">
              <div class="tip one-t">黑白彩色专业高速打印复印</div>
              <div class="title more-t">黑白彩色专业高速打印复印</div>
              <div class="price-box">
                <span class="seckill-current">￥0.50</span>
                <span class="original">销量133张</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style lang="scss" scoped>
.recommended-title-card {
  width: 100%;
  margin-bottom: 5/375 * 500px;
}

.title-box {
  width: 389/375 * 500px;
  height: 48/375 * 500px;
  margin: 0 auto;
  position: relative;
  border-radius: 16/375 * 500px;
  text-align: center;
  .title-bg {
    width: 100%;
  }
  .title-text {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    line-height: 48/375 * 500px;
    font-weight: 700;
    font-size: 20/375 * 500px;
  }
}
.recommended-body {
  overflow-x: hidden;
}
.min-goods {
  background: #fff;
  .img-box {
    width: 100%;
    height: auto;
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
  padding: 0 7.5/375 * 500px;
  flex-wrap: wrap;
  .min-goods {
    width: 50%;
    flex: 0 0 50%;
    padding: 0 7.5/375 * 500px;
  }
  .min-goods-box {
    margin-right: 10/375 * 500px;
    margin-bottom: 10/375 * 500px;
    width: 189/375 * 500px;
    box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.1);
    border-radius: 10/375 * 500px;
    width: 100%;
    overflow: hidden;
    padding-bottom: 10/375 * 500px;
    img {
      width: 100%;
    }
  }
}

.price-box {
  .tip {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    transition: all linear 0.2s;
    width: 100%;
    line-height: 30/375 * 500px;
    background: #f6f2ea;
    font-size: 12/375 * 500px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #a8700d;
    padding: 0 10/375 * 500px;
  }
  .title {
    overflow: hidden;
    text-overflow: ellipsis;
    transition: all linear 0.2s;
    font-size: 13/375 * 500px;
    font-family: PingFang SC;
    font-weight: 500;
    line-height: 19/375 * 500px;
    height: 39/375 * 500px;
    margin: 10/375 * 500px 10/375 * 500px 5/375 * 500px;
  }
  .price-box {
    padding: 5/375 * 500px 10/375 * 500px 0;
    width: 188/375 * 500px;
    box-sizing: border-box;
  }
  .seckill-current {
    font-size: 16/375 * 500px;
    font-weight: 500;
    color: #e1212b;
    line-height: 16/375 * 500px;
    margin-bottom: 10/375 * 500px;
  }
  .original {
    font-size: 10/375 * 500px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #999;
    line-height: 10/375 * 500px;
    margin-bottom: 10/375 * 500px;
  }
}
</style>
