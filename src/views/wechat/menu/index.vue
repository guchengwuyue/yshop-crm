<template>
  <div class="app-container">
    <div class="wechat-reply-wrapper wechat-menu">
      <div class="ibox-content clearfix">
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="view-wrapper col-sm-4">
              <div class="mobile-header">公众号</div>
              <section class="view-body">
                <div class="time-wrapper"><span class="time">9:36</span></div>
              </section>
              <div class="menu-footer">
                <ul class="flex">
                  <li v-for="(menu, index) in menus" :class="{active:menu === checkedMenu}">
                    <span @click="activeMenu(menu,index,null)"><i class="icon-sub" />{{ menu.name || '一级菜单' }}</span>
                    <div class="sub-menu">
                      <ul>
                        <li v-for="(child, cindex) in menu.subButtons" :class="{active:child === checkedMenu}">
                          <span @click="activeMenu(child,cindex,index)">{{ child.name || '二级菜单' }}</span>
                        </li>
                        <li v-if="menu.subButtons.length < 5" @click="addChild(menu,index)"><i class="icon-add" /></li>
                      </ul>
                    </div>
                  </li>
                  <li v-if="menus.length < 3" @click="addMenu()"><i class="icon-add" /></li>
                </ul>
              </div>
            </div>
          </el-col>
          <el-col :span="12">
            <div v-show="checkedMenuId !== null" class="control-wrapper menu-control col-sm-8">
              <section>
                <div class="control-main">
                  <h3 class="popover-title">菜单名称 <a class="fr" href="javascript:void(0);" @click="delMenu">删除</a></h3>
                  <p class="tips-txt">已添加子菜单，仅可设置菜单名称。</p>
                  <div class="menu-content control-body">
                    <form action="">
                      <div class="form-group clearfix">
                        <label for="" class="el-form-item__label" style="width: 80px;">菜单名称</label>
                        <div class="col-sm-9 group-item">
                          <input v-model="checkedMenu.name" type="text" placeholder="菜单名称" style="width: 370px;" class="el-input__inner">
                        </div>
                      </div>
                      <div class="form-group clearfix">
                        <label class="el-form-item__label" style="width: 80px;" for="">规则状态</label>
                        <div class="group-item col-sm-9">
                          <select id="" v-model="checkedMenu.type" style="width: 370px;" class="el-input__inner" name="">
                            <option value="click">关键字</option>
                            <option value="view">跳转网页</option>
                            <option value="miniprogram">小程序</option>
                          </select>
                        </div>
                      </div>
                      <div class="menu-control-box">
                        <!-- 关键字 -->
                        <div class="keywords item" :class="{show:checkedMenu.type=='click'}">
                          <span class="el-form-item__label">关键字</span>
                          <input v-model="checkedMenu.key" type="text" placeholder="请输入关键字" class="form-control">

                        </div>
                        <!-- 跳转地址 -->
                        <div class="url item" :class="{show:checkedMenu.type=='view'}">
                          <span class="el-form-item__label">跳转地址</span>
                          <input v-model="checkedMenu.url" type="text" placeholder="请输入跳转地址" class="form-control">
                          <p class="text-left" />
                        </div>
                        <!-- 小程序 -->
                        <div class="wrchat-app item" :class="{show:checkedMenu.type=='miniprogram'}">
                          <div class="list">
                            <span class="el-form-item__label">appId</span>
                            <input v-model="checkedMenu.appId" class="form-control" type="text">
                          </div>
                          <div class="list">
                            <span class="el-form-item__label">备用网页url</span>
                            <input v-model="checkedMenu.url" class="form-control" type="text">
                          </div>
                          <div class="list">
                            <span class="el-form-item__label">小程序路径</span>
                            <input v-model="checkedMenu.pagePath" class="form-control" type="text">
                          </div>
                        </div>
                        <!-- 多客服 -->
                        <div class="service item">
                          <p>回复内容</p>
                          <textarea cols="60" rows="10" />
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </section>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="ibox-content submit">
        <button class="el-button el-button--primary" @click="submit">保存发布</button>
      </div>
    </div>
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del, add, get } from '@/api/YxWechatMenu'
import eForm from './form'
import { Message } from 'element-ui'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      menus: [],
      checkedMenu: {
        type: 'click',
        name: ''
      },
      checkedMenuId: null,
      parentMenuId: null
    }
  },
  created() {
    get().then(res => {
      this.menus = JSON.parse(res.result)
    })
  },
  methods: {
    checkPermission,
    beforeInit() {
      this.url = 'api/YxWechatMenu'
      const sort = 'key,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    },
    subDelete(key) {
      this.delLoading = true
      del(key).then(res => {
        this.delLoading = false
        this.$refs[key].doClose()
        this.dleChangePage()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[key].doClose()
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
        key: data.key,
        result: data.result,
        addTime: data.addTime
      }
      _this.dialog = true
    },
    defaultMenusData: function() {
      return {
        type: 'click',
        name: '',
        subButtons: []
      }
    },
    defaultChildData: function() {
      return {
        type: 'click',
        name: ''
      }
    },
    addMenu: function() {
      if (!this.check()) return false
      var data = this.defaultMenusData(); var id = this.menus.length
      this.menus.push(data)
      this.checkedMenu = data
      this.checkedMenuId = id
      this.parentMenuId = null
    },
    addChild: function(menu, index) {
      if (!this.check()) return false
      var data = this.defaultChildData(); var id = menu.subButtons.length
      menu.subButtons.push(data)
      this.checkedMenu = data
      this.checkedMenuId = id
      this.parentMenuId = index
    },
    delMenu: function() {
      console.log(this.parentMenuId)
      this.parentMenuId === null
        ? this.menus.splice(this.checkedMenuId, 1) : this.menus[this.parentMenuId].subButtons.splice(this.checkedMenuId, 1)
      this.parentMenuId = null
      this.checkedMenu = {}
      this.checkedMenuId = null
    },
    activeMenu: function(menu, index, pid) {
      if (!this.check()) return false
      pid === null
        ? (this.checkedMenu = menu) : (this.checkedMenu = this.menus[pid].subButtons[index], this.parentMenuId = pid)
      this.checkedMenuId = index
    },
    check: function() {
      if (this.checkedMenuId === null) return true
      if (!this.checkedMenu.name) {
        // $eb.message('请输入按钮名称!');
        this.$message({
          message: '请输入按钮名称',
          type: 'error',
          duration: 1000,
          onClose: () => {
            // this.init()
          }
        })
        return false
      }
      if (this.checkedMenu.type == 'click' && !this.checkedMenu.key) {
        // $eb.message('请输入关键字!');
        this.$message({
          message: '请输入关键字',
          type: 'error',
          duration: 1000,
          onClose: () => {
            // this.init()
          }
        })
        return false
      }
      if (this.checkedMenu.type == 'view' && !this.checkedMenu.url) {
        this.$message({
          message: '请输入跳转地址',
          type: 'error',
          duration: 1000,
          onClose: () => {
            // this.init()
          }
        })

        return false
      }
      if (this.checkedMenu.type == 'miniprogram' &&
        (!this.checkedMenu.appId ||
          !this.checkedMenu.pagePath ||
          !this.checkedMenu.url)) {
        // $eb.message('请填写完整小程序配置!');
        this.$message({
          message: '请填写完整小程序配置',
          type: 'error',
          duration: 1000,
          onClose: () => {
            // this.init()
          }
        })
        return false
      }
      return true
    },
    submit: function() {
      // this.$message.error('错了哦，这是一条错误消息')
      // return false;
      if (!this.check()) return false
      // console.log(this.menus.length)
      if (!this.menus.length) {
        this.$message({
          message: '请添加菜单',
          type: 'error',
          duration: 1000
        })
        return false
      }
      add({ buttons: this.menus }).then(function(res) {
        console.log(555)
        Message({ message: '添加成功', type: 'success' })
      }).catch(function(err) {
        // $eb.message('error',err);
        // this.$message.error('错了哦，这是一条错误消息');
        // Message({message: "2222",type: 'error'})
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" scoped>

  body {
    font-family: "open sans","Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 13px;
    color: #676a6c;
    overflow-x: hidden;
  }

  .form-group{font-size: 12px}

  .form-control {
    background-color: #FFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    -webkit-transition: border-color .15s ease-in-out 0s,box-shadow .15s ease-in-out 0s;
    transition: border-color .15s ease-in-out 0s,box-shadow .15s ease-in-out 0s;
    width: 100%;
    font-size: 14px;}

  .flex{ display: -webkit-box; display: -moz-box; display: -webkit-flex; display: -moz-flex; display: -ms-flexbox; display: flex; }

  #app .layout-ceiling-main a{ color:#9ba7b5}


  #table-list .mp-header-wrapper h1{ padding-left:15px; float: left; font-size: 18px; line-height: 24px; padding-bottom: 20px; font-weight: 400; color: #464c5b; }


  .mp-form .ivu-tree li{ margin: 0; }


  .mp-form .demo-upload-list img{ width: 100%; height: 100%; }


  .mp-form .demo-upload-list-cover i{ color: #fff; font-size: 20px; cursor: pointer; margin: 0 2px; }




  .wechat-reply-wrapper .fr{ float: right; }
  .wechat-reply-wrapper .clearfix:after{ content:"."; display:block; height:0; visibility:hidden; clear:both; }

  .wechat-reply-wrapper .ibox-title p{ border-left: 2px solid #2494f2; text-indent: 8px; }
  .wechat-reply-wrapper .ibox-content{ padding: 15px; font-size: 12ox}
  .wechat-reply-wrapper .ibox-content .view-wrapper{ position: relative; width: 317px; background-image:url(../../../assets/wechat/mobile_head.png); background-repeat: no-repeat; background-position: left top; background-color: #f5f5f5; }
  .wechat-reply-wrapper .ibox-content .view-wrapper .mobile-header{ position: relative; left: 0; top: 36px; width: 100%; text-align: center; color: #fff; font-size: 16px; }
  .wechat-reply-wrapper .ibox-content .view-wrapper .view-body{ margin-top: 65px; background-color: #f5f5f5; height: 500px; }
  .wechat-reply-wrapper .view-wrapper .view-body .time-wrapper{ margin-bottom: 10px; text-align: center; }
  .wechat-reply-wrapper .view-wrapper .view-body .time-wrapper .time{ display: inline-block; color: #f5f5f5; display: inline-block; color: #f5f5f5; background: rgba(0,0,0,.3); padding: 3px 8px; border-radius: 3px; font-size: 12px; }


  .wechat-reply-wrapper .view-wrapper .view-body .view-item .avatar img{ max-width: 100%; height: auto; }


  .wechat-reply-wrapper .submit{text-align: center;}
  /* 图文 */

  /* 音乐 */

  .view-wrapper .view-body .view-item.music-box .box-content p{ width: 75%; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }



  /* 右侧控制器 */
  .wechat-reply-wrapper .ibox-content .control-wrapper{ position: relative; width: 535px; height: 565px; padding: 0; margin-left: 20px; border: 1px solid #e2e2e2; }

  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body{ margin-top: 40px; padding: 0 10px; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group label{ font-weight: normal; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .tips:after{ content: '*'; color: red; position: absolute; margin-left: 4px; font-weight: bold; line-height: 1.8em; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item{ position: relative; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item .file-btn{ position: absolute; right: 15px; top: 0; display: block; width: 66px; border-radius: 6px; cursor: pointer; padding: .5rem; background-color: #18a689; color: #fff; text-align: center; height: 100%; line-height: 23px; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .form-group .group-item textarea{ resize: none; width: 100%; height: 100px; padding: 10px; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .tips-info{ padding-left: 100px; font-size: 12px; color: #737373; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .control-main .control-item{ display: none; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body .control-main .control-item.show{ display: block; }

  /* 微信菜单定制 */
  .wechat-menu{ position: relative; }
  .wechat-menu ul{ padding: 0; }
  .wechat-menu .menu-footer{ position: absolute; left:0; bottom: -10px; width: 100%; padding-left: 43px; background: url("../../../assets/wechat/mobile_foot.png") no-repeat 0px 20px; border-top: 1px solid #e7e7eb; }
  .wechat-menu .menu-footer span{display: block;font-size: 12px}
  .wechat-menu .menu-footer .icon-add{ background: url(../../../assets/wechat/index.png) 0 0 no-repeat; width: 14px; height: 14px; vertical-align: middle; display: inline-block; margin-top: -2px; border-bottom: none!important; }
  .wechat-menu .menu-footer li{ position: relative; -webkit-flex: 1; -moz-flex:1; -ms-flex: 1; flex: 1; height: 50px; line-height: 50px; text-align: center; cursor: pointer; list-style: none; border: 1px solid transparent; border-right: 1px solid #e7e7eb; }
  .wechat-menu .menu-footer .icon-sub{ background: url(../../../assets/wechat/index.png) 0 -48px no-repeat; width: 7px; height: 7px; vertical-align: middle; display: inline-block; margin-right: 2px; margin-top: -2px; }
  .wechat-menu .menu-footer .sub-menu{ position: absolute; border-radius: 3px; border: 1px solid #d0d0d0; display: block; bottom: 60px; width: 100%; background-color: #fafafa; }
  .wechat-menu .menu-footer .sub-menu:after{ content: ''; position: absolute; width: 10px; height: 10px; background: #fafafa;-webkit-transform: rotate(45deg); -moz-transform: rotate(45deg); -ms-transform: rotate(45deg); transform: rotate(45deg); bottom: -5px; border-bottom: 1px solid #d0d0d0; border-right: 1px solid #d0d0d0; left: 50%; margin-left: -5px; }
  .wechat-menu .menu-footer .sub-menu li{border-right: 0; border-bottom: 1px solid #d0d0d0;}
  .wechat-menu .menu-footer .sub-menu li:last-child{border-bottom: 0;}
  .wechat-menu .menu-footer .active{border:1px solid #44b549;}
  .wechat-menu .menu-footer .sub-menu li.active{border:1px solid #44b549 !important;}
  /* 右侧 */
  .wechat-menu .menu-control .popover-title{ padding: 8px 14px; margin: 0; font-size: 14px; background-color: #f7f7f7; border-bottom: 1px solid #ebebeb; border-radius: 5px 5px 0 0; font-weight: 400; }
  .wechat-menu .menu-control .popover-title a{ color: #06C; font-size: 12px; }
  .wechat-menu .menu-control .tips-txt{line-height: 40px; padding: 0 20px; font-size: 12px}
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content{ padding: 0 20px; margin-top: 0; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .radio{ display: inline-block !important; width: 45%; }
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .menu-control-box {padding: 0 20px;}
  .wechat-reply-wrapper .ibox-content .control-wrapper .control-body.menu-content .menu-control-box .radio{ display: block!important; width: 100%; }
  .menu-control-box .item{ display: none; }
  .menu-control-box .show{ display: block; }

</style>
