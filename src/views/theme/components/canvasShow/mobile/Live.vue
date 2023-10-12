<template>
  <div class="live-el mx20 mb10">
    <div class="head">
      <span class="head-title">热门直播</span>
      <div class="head-more">
        <span>更多</span>
        <span class="cuIcon-right"></span>
      </div>
    </div>
    <div class="content-one">
      <div
        class="content-one__item"
        v-for="live in liveList"
        :key="live.roomId" >
        <img class="item-cover" :src="live.shareImge" mode="widthFix" />
        <div class="item-status">
          <img class="status-img" :src="liveStatus[live.liveStatus].img" />
          <span class="status-span">{{
            liveStatus[live.liveStatus].title
          }}</span>
        </div>
        <div class="item-title">{{ live.name }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'shopLiveCard',
  data () {
    return {
      liveList: [
        {
          roomId: '28',
          id: null,
          name: '打印资料',
          coverImge: 'https://image.dayouqiantu.cn/WechatIMG229.jpeg',
          shareImge: 'https://image.dayouqiantu.cn/WechatIMG229.jpeg',
          liveStatus: 107,
          startTime: 1600358400,
          endTime: 1600441200,
          anchorName: 'yshop',
          anchorWechat: 'zimengzhiqiu',
          anchorImge: 'https://image.dayouqiantu.cn/zj.png',
          type: 0,
          screenType: 0,
          closeLike: 0,
          closeComment: 0,
          closeGoods: 0,
          closeReplay: 0,
          closeShare: 0,
          closeKf: 0,
          productId: '9,8,7',
          product: null
        },
        {
          roomId: '29',
          id: null,
          name: '我们能印什么',
          coverImge: 'https://image.dayouqiantu.cn/WechatIMG230.jpeg',
          shareImge: 'https://image.dayouqiantu.cn/WechatIMG230.jpeg',
          liveStatus: 107,
          startTime: 1600358400,
          endTime: 1600441200,
          anchorName: 'yshop',
          anchorWechat: 'zimengzhiqiu',
          anchorImge: 'https://image.dayouqiantu.cn/zj.png',
          type: 0,
          screenType: 0,
          closeLike: 0,
          closeComment: 0,
          closeGoods: 0,
          closeReplay: 0,
          closeShare: 0,
          closeKf: 0,
          productId: '9,8,7',
          product: null
        },
        {
          roomId: '27',
          id: null,
          name: '广告布定制',
          coverImge: 'https://image.dayouqiantu.cn/WechatIMG232.jpeg',
          shareImge: 'https://image.dayouqiantu.cn/WechatIMG232.jpeg',
          liveStatus: 107,
          startTime: 1600272000,
          endTime: 1600354800,
          anchorName: 'yshop',
          anchorWechat: 'zimengzhiqiu',
          anchorImge: 'https://image.dayouqiantu.cn/zj.png',
          type: 0,
          screenType: 0,
          closeLike: 0,
          closeComment: 0,
          closeGoods: 0,
          closeReplay: 0,
          closeShare: 0,
          closeKf: 0,
          productId: '9,8,7',
          product: null
        },
        {
          roomId: '30',
          id: null,
          name: '专业打印',
          coverImge: 'https://image.dayouqiantu.cn/3.jpg',
          shareImge: 'https://image.dayouqiantu.cn/3.jpg',
          liveStatus: 103,
          startTime: 1600077780,
          endTime: 1600077820,
          anchorName: 'yshop',
          anchorWechat: 'zimengzhiqiu',
          anchorImge: 'https://image.dayouqiantu.cn/zj.png',
          type: 0,
          screenType: 0,
          closeLike: 0,
          closeComment: 0,
          closeGoods: 0,
          closeReplay: 0,
          closeShare: 0,
          closeKf: 0,
          productId: '9,8,7',
          product: null
        }
      ],
      liveStatus: {
        101: {
          img: 'https://wx.yixiang.co/static/images/live.png',
          title: '直播中'
        },
        102: {
          img: 'https://wx.yixiang.co/static/images/prevue.png',
          title: '未开始'
        },
        103: {
          img: 'https://wx.yixiang.co/static/images/playback.png',
          title: '已结束'
        },
        104: {
          img: 'https://wx.yixiang.co/static/images/104.png',
          title: '禁播'
        },
        105: {
          img: 'https://wx.yixiang.co/static/images/105.png',
          title: '暂停中'
        },
        106: {
          img: 'https://wx.yixiang.co/static/images/106.png',
          title: '异常'
        },
        107: {
          img: 'https://wx.yixiang.co/static/images/past.png',
          title: '已过期'
        }
      }
    }
  },
  props: {
    componentContent: Object
  }
}
</script>

<style lang="scss" scoped>
.live-el {
  background: #fff;
  border-radius: 20/750 * 500px;
  padding: 30/750 * 500px 20/750 * 500px 25/750 * 500px;
  .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    &-title {
      font-size: 32/750 * 500px;
      font-weight: bold;
      font-family: PingFang SC;
      color: rgba(51, 51, 51, 1);
    }
    &-more {
      font-size: 26/750 * 500px;
      font-family: PingFang SC;
      font-weight: 500;
      color: rgba(51, 51, 51, 1);
    }
  }

  // 单个大图直播
  .content-one {
    .content-one__item {
      position: relative;
      height: 280/750 * 500px;
      border-radius: 20/750 * 500px;
      margin-top: 25/750 * 500px;
      overflow: hidden;
      .item-cover {
        background-color: #eee;
        width: 100%;
        height: 100%;
      }
      .item-status {
        position: absolute;
        top: 20/750 * 500px;
        left: 10/750 * 500px;
        height: 40/750 * 500px;
        background: rgba(0, 0, 0, 0.4);
        border-radius: 20/750 * 500px;
        display: flex;
        justify-content: center;
        align-items: center;
        .status-img {
          width: 38/750 * 500px;
          height: 38/750 * 500px;
        }
        .status-span {
          font-size: 22/750 * 500px;
          font-family: PingFang SC;
          font-weight: 500;
          color: rgba(255, 255, 255, 1);
          padding: 0 10/750 * 500px;
        }
      }
      .item-title {
        width: 680/750 * 500px;
        position: absolute;
        bottom: 0;
        line-height: 60/750 * 500px;
        padding: 0 20/750 * 500px;
        font-size: 26/750 * 500px;
        font-family: PingFang SC;
        font-weight: 500;
        color: rgba(255, 255, 255, 1);
        background: linear-gradient(transparent, rgba(#000, 0.5));
      }
      .like-img {
        position: absolute;
        bottom: 20/750 * 500px;
        right: 10/750 * 500px;
        width: 60/750 * 500px;
        height: 130/750 * 500px;
      }
    }
  }
  // 双图直播
  .content-two {
    width: 100%;
    // -moz-column-count: 2;
    // -webkit-column-count: 2;
    // column-count: 2;
    // padding-top: 20/750*500px;
    display: flex;
    flex-wrap: wrap;
    &__item {
      margin-right: 30/750 * 500px;
      margin-top: 20/750 * 500px;
      &:nth-child(2n) {
        margin-right: 0;
      }
    }
  }
}
</style>
