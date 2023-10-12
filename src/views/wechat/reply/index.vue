<template>
  <div class="app-container">
    <div class="wechat-reply-wrapper">
      <div class="ibox-title"><p>{{ msg }}</p></div>
      <div class="ibox-content clearfix">
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="view-wrapper col-sm-4">
              <div class="mobile-header">公众号</div>
              <section class="view-body" style="overflow:scroll;">
                <div class="time-wrapper"><span class="time">9:36</span></div>
                <div class="view-item text-box clearfix" :class="{show:type=='text'}">
                  <div class="avatar fl"><img src="../../../assets/wechat/head.gif"></div>
                  <div class="box-content fl">
                    {{ dataGroup.text.content }}
                  </div>
                </div>

                <div v-if="dataGroup.news.length >0" class="view-item news-box" :class="{show:type=='news'}">
                  <div v-if="dataGroup.news.length ==1" class="vn-content">
                    <div class="vn-title">{{ dataGroup.news[0].title }}</div>
                    <div class="vn-time">{{ dataGroup.news[0].date }}</div>
                    <div class="vn-picture" :style="{backgroundImage: 'url('+dataGroup.news[0].image+')'}" />
                    <div class="vn-picture-info">{{ dataGroup.news[0].description }}</div>
                    <div class="vn-more">
                      <a :href="dataGroup.news[0].url">阅读原文</a>
                    </div>
                  </div>
                  <div v-else class="vn-content">
                    <div class="con-item-box">
                      <div class="vn-picture" :style="{backgroundImage: 'url('+dataGroup.news[0].image+')'}" />
                      <div class="first-title">{{ dataGroup.news[0].title }}</div>
                    </div>
                    <div v-for="(newinfos,index) in dataGroup.news" v-if="index>0" class="con-item-list clearfix">
                      <div class="list-tit-info fl">{{ newinfos.title }}</div>
                      <div class="list-pic fr" :style="{backgroundImage: 'url('+newinfos.image+')'}" />
                    </div>
                  </div>
                </div>

                <div class="view-item text-box clearfix" :class="{show:type=='image'}">
                  <div class="avatar fl"><img src="../../../assets/wechat/head.gif"></div>
                  <div class="box-content fl">
                    <img class="picbox" :src="dataGroup.image.src" alt="">
                  </div>
                </div>

              </section>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="control-wrapper col-sm-8">
              <section>
                <form action="" method="post" enctype="multipart/form-data">
                  <div class="control-title">{{ msg }}</div>
                  <div class="control-body">
                    <div class="form-group clearfix">
                      <el-row :gutter="20">
                        <el-col :span="4">
                          <label class="col-sm-2 control-label tips" for="">规则状态</label>
                        </el-col>
                        <el-col :span="16">
                          <div class="group-item">
                            <div class="radio i-checks" style="display:inline;margin-left: 5px;">
                              <label class="" style="padding-left: 0;">
                                <input v-model="status" style="position: relative;top: 1px;left: 3px;" checked="checked" type="radio" value="1" name="status">
                                启用</label>
                            </div>
                            <div class="radio i-checks" style="display:inline;margin-left: 10px;">
                              <label class="" style="padding-left: 0;">
                                <input v-model="status" style="position: relative;top: 1px;left: 3px;" type="radio" value="0" name="status">
                                禁用
                              </label>
                            </div>
                          </div>
                        </el-col>
                      </el-row>
                    </div>
                    <div class="form-group clearfix">
                      <el-row :gutter="20">
                        <el-col :span="4">
                          <label class="col-sm-2 tips" for="">消息类型</label>
                        </el-col>
                        <el-col :span="16">
                          <div class="col-sm-10 group-item">
                            <select v-model="type" class="form-control m-b" name="account">
                              <option value="text">文字消息</option>
                            </select>
                          </div>
                        </el-col>
                      </el-row>
                    </div>
                    <div class="control-main">
                      <!-- 文字 -->
                      <div class="control-item control-main-txt" :class="{show:type=='text'}">
                        <div class="form-group clearfix">
                          <label class="col-sm-2 tips" for="">规则内容</label>
                          <div class="col-sm-10 group-item">
                            <textarea id="" v-model="dataGroup.text.content" name="" cols="30" rows="10" placeholder="请输入内容" />
                          </div>
                        </div>
                      </div>
                      <!-- 图片 -->
                      <div class="control-item control-main-picture" :class="{show:type=='image'}">
                        <div class="form-group clearfix">
                          <pic-upload v-model="dataGroup.image.src" style="width: 500px;" />
                        </div>

                      </div>
                    </div>
                  </div>
                  <div style="text-align: center;"><button type="button" class="el-button el-button--primary" @click="submit">提交</button></div>
                </form>
              </section>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del, add, get } from '@/api/yxWechatReply'
import eForm from './form'
import picUpload from '@/components/pic-upload'
import { Message } from 'element-ui'
export default {
  components: { eForm, picUpload },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      status: 1,
      msg: '',
      type: 'text',
      textBox: '',
      pic: '',
      key: '',
      dataGroup: {
        text: {
          content: ''
        },
        image: {
          src: ''
        },
        voice: {
          src: ''
        },
        news: []
      },
      uploadColl: function() {
      },
      uploadLink: '',
      result: null
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  mounted: function() {
    this.key = 'subscribe'
    this.msg = '编辑关注回复'
    get().then(rese => {
      this.result = rese
      this.type = rese.type
      this.status = rese.status
      const newData = JSON.parse(rese.data)
      if (rese.type == 'image') {
        this.dataGroup.image.src = newDatasrc
      } else if (rese.type == 'text') {
        this.dataGroup.text.content = newData.content
      }
      if (this.used_key) {
        this.keyword = this.result.key
      }
    })
  },
  methods: {
    checkPermission,
    beforeInit() {
      this.url = 'api/yxWechatReply'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    },
    subDelete(id) {
      this.delLoading = true
      del(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.dleChangePage()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[id].doClose()
        console.log(err.response.data.message)
      })
    },
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = {
        id: data.id,
        key: data.key,
        type: data.type,
        data: data.data,
        status: data.status,
        hide: data.hide
      }
      _this.dialog = true
    },
    submit: function() {
      if (!this.check()) return false

      add({ key: this.key, status: this.status, data: this.dataGroup[this.type], type: this.type }).then(function(res) {
        Message({ message: '设置成功', type: 'success' })
      }).catch(function(err) {
        // Message({message: err,type: 'error'})
      })
    },
    check: function() {
      var dataGroup = this.dataGroup
      switch (this.type) {
        case 'text':
          if (dataGroup.text.content == '') { return this.returnError('请输入文字消息内容') }
          break
        case 'image':
          if (dataGroup.image.src == '') { return this.returnError('请上传图片') }
          break
      }
      return true
    }
  }
}
</script>

<style scoped>
  .form-control {
    background-color: #FFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    -webkit-transition: border-color .15s ease-in-out 0s, box-shadow .15s ease-in-out 0s;
    transition: border-color .15s ease-in-out 0s, box-shadow .15s ease-in-out 0s;
    width: 100%;
    font-size: 14px;
  }

  #app .layout-ceiling-main a {
    color: #9ba7b5
  }

  #table-list .mp-header-wrapper h1 {
    padding-left: 15px;
    float: left;
    font-size: 18px;
    line-height: 24px;
    padding-bottom: 20px;
    font-weight: 400;
    color: #464c5b;
  }

  .mp-form .ivu-tree li {
    margin: 0;
  }

  .mp-form .demo-upload-list img {
    width: 100%;
    height: 100%;
  }

  .mp-form .demo-upload-list-cover i {
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }

  .wechat-reply-wrapper .fl {
    float: left;
    margin-left: 10px;
  }

  .wechat-reply-wrapper .fr {
    float: right;
  }

  .wechat-reply-wrapper .clearfix:after {
    content: ".";
    display: block;
    height: 10px;
    visibility: hidden;
    clear: both;
  }

  .wechat-reply-wrapper .ibox-title {
    padding: 0px;
    font-size: 16px;
    border-bottom: 1px solid #e7eaec;
  }

  .wechat-reply-wrapper .ibox-title p {
    border-left: 2px solid #2494f2;
    text-indent: 8px;
  }

  .wechat-reply-wrapper .ibox-content {
    padding: 15px;
    font-size: 12px
  }

  .wechat-reply-wrapper .ibox-content .view-wrapper {
    position: relative;
    width: 317px;
    background-image: url(../../../assets/wechat/mobile_head.png);
    background-repeat: no-repeat;
    background-position: left top;
    background-color: #f5f5f5;
  }

  .wechat-reply-wrapper .ibox-content .view-wrapper .mobile-header {
    position: relative;
    left: 0;
    top: 36px;
    width: 100%;
    text-align: center;
    color: #fff;
    font-size: 16px;
  }

  .wechat-reply-wrapper .ibox-content .view-wrapper .view-body {
    margin-top: 65px;
    background-color: #f5f5f5;
    height: 500px;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .time-wrapper {
    margin-bottom: 10px;
    text-align: center;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .time-wrapper .time {
    display: inline-block;
    color: #f5f5f5;
    display: inline-block;
    color: #f5f5f5;
    background: rgba(0, 0, 0, .3);
    padding: 3px 8px;
    border-radius: 3px;
    font-size: 12px;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item {
    display: none;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item.show {
    display: block;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .avatar {
    width: 40px;
    height: 40px;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .avatar img {
    max-width: 100%;
    height: auto;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .box-content {
    position: relative;
    max-width: 60%;
    min-height: 40px;
    margin-left: 15px;
    padding: 10px;
    border: 1px solid #ccc;
    word-break: break-all;
    word-wrap: break-word;
    line-height: 1.5;
    border-radius: 5px;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .box-content .picbox {
    max-width: 100%;
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .box-content:before {
    content: '';
    position: absolute;
    left: -13px;
    top: 11px;
    display: block;
    width: 0;
    height: 0;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
    border-top: 10px solid #ccc;
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
  }

  .wechat-reply-wrapper .view-wrapper .view-body .view-item .box-content:after {
    content: '';
    content: '';
    position: absolute;
    left: -12px;
    top: 11px;
    display: block;
    width: 0;
    height: 0;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
    border-top: 10px solid #f5f5f5;
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
  }

  .wechat-reply-wrapper .submit {
    text-align: center;
  }

  /* 图文 */
  .view-wrapper .view-body .view-item.news-box {
    width: 100%;
    background-color: #fff;
    border-radius: 5px;
  }

  .view-wrapper .view-body .view-item .vn-content {
    padding: 0;
  }

  .view-wrapper .view-body .view-item .vn-content .vn-title {
    line-height: 1.5;
    font-size: 16px;
  }

  .view-wrapper .view-body .view-item .vn-content .vn-time {
    padding: 5px 0;
    font-size: 12px;
    color: #999;
  }

  .view-wrapper .view-body .view-item .vn-content .vn-picture {
    width: 100%;
    height: 150px;
    background-size: cover;
    background-position: center center;
    border-radius: 5px 5px 0 0;
  }

  .view-wrapper .view-body .view-item .vn-content .vn-picture-info {
    line-height: 22px;
    color: #7b7b7b;
    padding: 0;
    display: block;
    overflow: hidden;
    word-break: break-all;
    text-overflow: ellipsis;
    font-size: 12px;
    white-space: nowrap;
  }

  .view-wrapper .view-body .view-item .vn-more {
    display: block;
    padding: 10px 0 0;
    border-top: 1px solid #dddddd;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .view-wrapper .view-body .view-item .vn-content .con-item-box {
    position: relative;
  }

  .view-wrapper .view-body .view-item .vn-content .con-item-box .first-title {
    width: 100%;
    height: 44px;
    line-height: 44px;
    font-size: 14px;
    position: absolute;
    left: 0;
    bottom: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap; /*background: rgba(0,0,0,.8);*/
    color: #fff;
    text-indent: 1em;
  }

  .view-wrapper .view-body .view-item .vn-content .con-item-list {
    margin-top: 10px;
    margin: 6px 10px 0 10px;
    border-top: 1px solid #FBFBFB;
  }

  .view-wrapper .view-body .view-item .vn-content .con-item-list .list-tit-info {
    width: 70%;
    line-height: 1.5;
    word-wrap: break-word;
  }

  .view-wrapper .view-body .view-item .vn-content .con-item-list .list-pic {
    width: 20%;
    min-height: 50px;
    background-size: cover;
    background-position: center center;
  }

  /* 音乐 */
  .view-wrapper .view-body .view-item.music-box .box-content {
    position: relative;
    width: 100%;
    background: #080;
    color: #fff;
    border-color: #080;
  }

  .view-wrapper .view-body .view-item.music-box .box-content p {
    width: 75%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .view-wrapper .view-body .view-item.music-box .box-content .music-icon {
    position: absolute;
    right: 11px;
    top: 50%;
    width: 30px;
    height: 30px;
    background: #0a0;
    text-align: center;
    line-height: 30px;
    margin-top: -15px;
    font-size: 16px;
  }

  .view-wrapper .view-body .view-item.music-box .box-content:after {
    display: none;
  }

  .view-wrapper .view-body .view-item.music-box .box-content:before {
    border-top: 10px solid #080;
  }

  /* 视频 */
  .view-wrapper .view-body .view-item.video-box {
    width: 100%;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
  }

  .view-wrapper .view-body .view-item.video-box .vn-title {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  /* 右侧控制器 */
  .wechat-reply-wrapper .ibox-content .control-wrapper {
    position: relative;
    width: 535px;
    height: 565px;
    padding: 0;
    margin-left: 20px;
    border: 1px solid #e2e2e2;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-title {
    position: absolute;
    left: 71px;
    top: -12px;
    width: auto;
    padding: 0 10px;
    font-size: 20px;
    background-color: #fff;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body {
    margin-top: 40px;
    padding: 0 10px;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group label {
    font-weight: normal;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .tips:after {
    content: '*';
    color: red;
    position: absolute;
    margin-left: 4px;
    font-weight: bold;
    line-height: 1.8em;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item {
    position: relative;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item .file-btn {
    position: absolute;
    right: 15px;
    top: 0;
    display: block;
    width: 66px;
    border-radius: 6px;
    cursor: pointer;
    padding: .5rem;
    background-color: #18a689;
    color: #fff;
    text-align: center;
    height: 100%;
    line-height: 23px;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item textarea {
    resize: none;
    width: 100%;
    height: 100px;
    padding: 10px;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .tips-info {
    padding-left: 100px;
    font-size: 12px;
    color: #737373;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .control-main .control-item {
    display: none;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .control-main .control-item.show {
    display: block;
  }

  /* 微信菜单定制 */
  .wechat-menu ul {
    padding: 0;
  }

  .wechat-menu .menu-footer span {
    display: block;
  }

  .wechat-menu .menu-footer li {
    position: relative;
    -webkit-flex: 1;
    -moz-flex: 1;
    -ms-flex: 1;
    flex: 1;
    height: 50px;
    line-height: 50px;
    text-align: center;
    cursor: pointer;
    list-style: none;
    border: 1px solid transparent;
    border-right: 1px solid #e7e7eb;
  }

  .wechat-menu .menu-footer .icon-sub {
    background: url(../../../assets/wechat/index.png) 0 -48px no-repeat;
    width: 7px;
    height: 7px;
    vertical-align: middle;
    display: inline-block;
    margin-right: 2px;
    margin-top: -2px;
  }

  .wechat-menu .menu-footer .sub-menu {
    position: absolute;
    border-radius: 3px;
    border: 1px solid #d0d0d0;
    display: block;
    bottom: 60px;
    width: 100%;
    background-color: #fafafa;
  }

  .wechat-menu .menu-footer .sub-menu:after {
    content: '';
    position: absolute;
    width: 10px;
    height: 10px;
    background: #fafafa;
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    transform: rotate(45deg);
    bottom: -5px;
    border-bottom: 1px solid #d0d0d0;
    border-right: 1px solid #d0d0d0;
    left: 50%;
    margin-left: -5px;
  }

  .wechat-menu .menu-footer .sub-menu li {
    border-right: 0;
    border-bottom: 1px solid #d0d0d0;
  }

  .wechat-menu .menu-footer .sub-menu li:last-child {
    border-bottom: 0;
  }

  .wechat-menu .menu-footer .active {
    border: 1px solid #44b549;
  }

  .wechat-menu .menu-footer .sub-menu li.active {
    border: 1px solid #44b549 !important;
  }

  /* 右侧 */
  .wechat-menu .menu-control .popover-title {
    padding: 8px 14px;
    margin: 0;
    font-size: 14px;
    background-color: #f7f7f7;
    border-bottom: 1px solid #ebebeb;
    border-radius: 5px 5px 0 0;
    font-weight: 400;
  }

  .wechat-menu .menu-control .popover-title a {
    color: #06C;
    font-size: 12px;
  }

  .wechat-menu .menu-control .tips-txt {
    line-height: 40px;
    padding: 0 20px;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content {
    padding: 0 20px;
    margin-top: 0;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .radio {
    display: inline-block !important;
    width: 45%;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .menu-control-box {
    padding: 0 20px;
  }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .menu-control-box .radio {
    display: block !important;
    width: 100%;
  }

  .menu-control-box .item {
    display: none;
  }

  .menu-control-box .show {
    display: block;
  }

</style>
