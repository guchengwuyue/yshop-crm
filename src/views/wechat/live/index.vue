<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission">
        <el-tooltip slot="right" class="item" effect="dark" content="同步小程序控制台直播间数据" placement="top-start">
          <el-button
            class="filter-item"
            size="mini"
            type="success"
            icon="el-icon-refresh"
            :loading="syncLoading"
            :disabled="crud.selections.length === 0"
            @click="sync"
          >同步</el-button>
        </el-tooltip>
      </crudOperation>
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="800px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="140px">
          <el-form-item label="直播间标题" prop="name">
            <el-input v-model="form.name" style="width: 370px;" :disabled="isDisabled" />
          </el-form-item>
          <el-form-item label="直播间背景图" prop="coverImg" >
            <MaterialList v-model="form.coverImgArr" style="width: 370px" type="image" :num="1" :width="150" :height="150":disabled="isDisabled" />
          </el-form-item>
          <el-form-item label="直播间分享图片" prop="shareImg" >
            <MaterialList v-model="form.shareImgArr" style="width: 370px" type="image" :num="1" :width="150" :height="150":disabled="isDisabled" />
          </el-form-item>
          <el-form-item label="封面图" prop="feedsImg" >
            <MaterialList v-model="form.feedsImgArr" style="width: 370px" type="image" :num="1" :width="150" :height="150":disabled="isDisabled" />
            <p style="color: #cf0f0f">购物直播封面图 ，建议尺寸800*800</p>
          </el-form-item>
          <el-form-item label="计划直播开始时间" prop="startDate" >
            <el-date-picker v-model="form.startDate" type="datetime" style="width: 370px;" :disabled="isDisabled"/>
            <p style="color: #cf0f0f">开播时间需要在当前时间的10分钟后,并且开始时间不能在6个月后</p>
          </el-form-item>
          <el-form-item label="计划直播结束时间" prop="endDate" >
            <el-date-picker v-model="form.endDate" type="datetime" style="width: 370px;" :disabled="isDisabled"/>
            <p style="color: #cf0f0f">开播时间和结束时间间隔不得短于30分钟,不得超过24小时</p>
          </el-form-item>
          <el-form-item label="主播昵称" prop="anchorName" :disabled="isDisabled">
            <el-input v-model="form.anchorName" style="width: 370px;" :disabled="isDisabled"/>
          </el-form-item>
          <el-form-item label="主播微信号" prop="anchorWechat" :disabled="isDisabled">
            <el-input v-model="form.anchorWechat" style="width: 370px;" :disabled="isDisabled"/>
            <p style="color: #cf0f0f">主播微信号需要实名，搜索微信官方小程序【小程序直播】进行认证</p>
          </el-form-item>
          <el-form-item label="主播头像" prop="anchorImg" >
            <MaterialList v-model="form.anchorImgArr" style="width: 370px" type="image" :num="1" :width="150" :height="150" :disabled="isDisabled"/>
          </el-form-item>

          <el-form-item label="直播间类型" prop="type" >
            <el-radio-group v-model="form.type":disabled="isDisabled" >
              <el-radio :label="1" class="radio">推流</el-radio>
              <el-radio :label="0">手机直播</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="横屏、竖屏" prop="screenType" >
            <el-radio-group v-model="form.screenType" :disabled="isDisabled">
              <el-radio :label="1" class="radio">横屏</el-radio>
              <el-radio :label="0">竖屏</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否关闭点赞" prop="closeLike" >
            <el-radio-group v-model="form.closeLike" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否关闭货架" prop="closeGoods" >
            <el-radio-group v-model="form.closeGoods" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否关闭评论" prop="closeComment" >
            <el-radio-group v-model="form.closeComment" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="是否关闭回放" prop="closeLike" >
            <el-radio-group v-model="form.closeReplay" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否关闭分享" prop="closeGoods" >
            <el-radio-group v-model="form.closeShare" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否关闭客服" prop="closeComment" >
            <el-radio-group v-model="form.closeKf" :disabled="isDisabled">
              <el-radio :label="1" class="radio">关闭</el-radio>
              <el-radio :label="0">开启</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU" :disabled="isDisabled">确认</el-button>
        </div>
      </el-dialog>
      <el-dialog title="修改直播间商品信息"
                 :visible.sync="closeDialogVisible"
                 width="40%">
        <el-form :model="form"
                 label-width="150px">
              <el-form-item label="选择入库商品" >
                <LiveGoods :product="form.product"  @selectGoods="getGoods" > </LiveGoods>
              </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" @click="handleCloseOrder">确 定</el-button>
      </span>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('roomId')" prop="roomId" label="直播间id" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="直播间标题" />
        <el-table-column v-if="columns.visible('coverImge')" prop="coverImge" label="背景图">
          <template slot-scope="scope">
            <a :href="scope.row.coverImge" style="color: #42b983" target="_blank"><img :src="scope.row.coverImge" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('shareImge')" prop="shareImge" label="分享图片" >
          <template slot-scope="scope">
            <a :href="scope.row.shareImge" style="color: #42b983" target="_blank"><img :src="scope.row.shareImge" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
<!--        101：直播中，102：未开始，103 已结束，104 禁播，105：暂停，106：异常，107：已过期-->
        <el-table-column v-if="columns.visible('liveStatus')" prop="liveStatus" label="直播间状态" >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.liveStatus === 101" :type="''">直播中</el-tag>
              <el-tag v-if="scope.row.liveStatus === 102" :type="''">未开始</el-tag>
              <el-tag v-if="scope.row.liveStatus === 103" :type="''">已结束</el-tag>
              <el-tag v-if="scope.row.liveStatus === 104" :type="''">禁播</el-tag>
              <el-tag v-if="scope.row.liveStatus === 105" :type="''">暂停</el-tag>
              <el-tag v-if="scope.row.liveStatus === 106" :type="''">异常</el-tag>
              <el-tag v-if="scope.row.liveStatus === 107" :type="''">已过期</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="startTime" label="开始时间"  width="200" >
          <template slot-scope="scope" width="200">
            <span>{{ formatTimeThree(scope.row.startTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="endTime" label="预计结束时间"  width="200">
          <template slot-scope="scope"  width="200">
            <span>{{ formatTimeThree(scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('anchorName')" prop="anchorName" label="主播昵称" />
        <el-table-column v-if="columns.visible('anchorWechat')" prop="anchorWechat" label="主播微信号" />
        <el-table-column v-if="columns.visible('anchorImge')" prop="anchorImge" label="主播头像" >
          <template slot-scope="scope">
            <a :href="scope.row.anchorImge" style="color: #42b983" target="_blank"><img :src="scope.row.anchorImge" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('type')" prop="type" label="直播类型" >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.type === 1" :type="''">推流</el-tag>
              <el-tag v-else :type="''">手机直播</el-tag>
            </div>
          </template>
        </el-table-column>
          <el-table-column v-if="columns.visible('closeReplay')" prop="closeReplay" label="回放" >
            <template slot-scope="scope">
              <div>
                <el-tag v-if="scope.row.closeReplay === 1" :type="''">关闭</el-tag>
                <el-tag v-else :type=" '' ">开启</el-tag>
              </div>
            </template>
        </el-table-column>
        <el-table-column v-permission="['admin','yxWechatLive:edit','yxWechatLive:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">

            <udOperation
              :data="scope.row"
              :permission="permission"
            >
            </udOperation>
            <el-button
              size="mini"
              type="success"
              @click="showCloseOrderDialog(scope.row)"
            >
              添加商品
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import {  sync } from '@/api/yxWechatLive'
import crudYxWechatLive from '@/api/yxWechatLive'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'
import MaterialList from '@/components/material'
import LiveGoods from '@/views/components/livegoods'
import udOperation from '@crud/UD.operation'
import {formatTimeThree} from '@/utils/index'
import { addGoods } from '@/api/yxWechatLive'
// crud交由presenter持有
const defaultCrud = CRUD({ optShow: {
    add: true,
    edit: false,
    del: false,
    download: true
  },title: '直播房间', url: 'api/yxWechatLive', sort: 'room_id,desc', crudMethod: { ...crudYxWechatLive }})
const defaultForm = { product: [],roomId: null,productId: null,feedsImg: null, name: null, coverImge: null, startDate: null, endDate : null,shareImge: null, liveStatus: null,  coverImgArr: [],shareImgArr: [],anchorImgArr: [],feedsImgArr:[],startTime: null, endTime: null, anchorName: null, anchorWechat: null, anchorImge: null, type: 0, screenType: 0, closeLike: 0,closeGoods: 0, closeComment: 0,closeReplay: 0,closeShare:0,closeKf:0 }
export default {
  name: 'YxWechatLive',
  components: { pagination, crudOperation, rrOperation ,MaterialList,udOperation,LiveGoods},
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      closeDialogVisible:false,
      disabled: false,
      syncLoading: false,
      permission: {
        add: ['admin', 'yxWechatLive:add'],
        edit: ['admin', 'yxWechatLive:edit'],
        del: ['admin', 'yxWechatLive:del']
      },
      rules: {
        name: [
          { required: true, message: '直播间标题不能为空', trigger: 'blur' }
        ],
        coverImge: [
          { required: true, message: '背景图不能为空', trigger: 'blur' }
        ],
        shareImge: [
          { required: true, message: '分享图片不能为空', trigger: 'blur' }
        ],
        startDate: [
          { required: true, message: '开始时间不能为空', trigger: 'blur' }
        ],
        endDate: [
          { required: true, message: '预计结束时间不能为空', trigger: 'blur' }
        ],
        anchorName: [
          { required: true, message: '主播昵称不能为空', trigger: 'blur' }
        ],
        anchorWechat: [
          { required: true, message: '主播微信号不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '直播间类型 1：推流 0：手机直播不能为空', trigger: 'blur' }
        ],
        screenType: [
          { required: true, message: '横屏、竖屏 【1：横屏，0：竖屏】不能为空', trigger: 'blur' }
        ],
        closeLike: [
          { required: true, message: '是否关闭货架 【0：开启，1：关闭】不能为空', trigger: 'blur' }
        ],
        closeComment: [
          { required: true, message: '是否关闭评论 【0：开启，1：关闭】不能为空', trigger: 'blur' }
        ]
      }    }
  },
  watch: {
  },
  computed:{
    isDisabled(){
      if(this.disabled){
        return this.isdisabled=true;
      }else{
        return this.isdisabled=false;
      }
    }
  },
  methods: {
    closeDialog(){
      this.form.product = []
      this.closeDialogVisible=false;
    },
    showCloseOrderDialog(row){
      this.closeDialogVisible=true;
      this.form.roomId = row.id;
      this.form.product = row.product
    },
    handleCloseOrder(){
        this.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            let params ={
              "roomId": this.form.roomId,
              "productId": this.form.productId
            }
          addGoods(params).then(res=>{
              this.closeDialogVisible=false;
            this.$message.success("添加成功");
            }).catch(err => {
            this.$message.error(res.msg);
          })
        });
      },
    formatTimeThree,
    getGoods(p) {
      var ids = []
      p.forEach((item,index) => {
        ids.push(item.id)
      })
      this.form.productId = ids.join(",")
    },
    sync() {
      this.crud.selections.forEach(val => {
      })
      this.syncLoading = true
      sync().then(() => {
        this.crud.refresh()
        this.crud.notify('同步成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
        this.syncLoading = false
      }).then(() => {
        this.syncLoading = false
      })
    },
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }, // 新增与编辑前做的操作
    // 添加后  coverImgArr: [],shareImgArr: [],anchorImgArr: []
    [CRUD.HOOK.beforeSubmit]() {
      //console.log('hah:'+this.form.imageArr)
      this.form.coverImge = this.form.coverImgArr.join(',')
      this.form.shareImge = this.form.shareImgArr.join(',')
      this.form.anchorImge = this.form.anchorImgArr.join(',')
      this.form.feedsImg = this.form.feedsImgArr.join(',')
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.beforeToAdd](crud, form) {
      this.disabled = false;
    },
    // 编辑前
    [CRUD.HOOK.beforeToEdit](crud, form) {
      form.coverImgArr = [form.coverImge]
      form.shareImgArr = [form.shareImge]
      form.anchorImgArr = [form.anchorImge]
      form.product = form.product
      this.disabled = true;
    }
  }
}



</script>

<style scoped>
  .table-img {
    display: inline-block;
    text-align: center;
    background: #ccc;
    color: #fff;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    vertical-align: middle;
    width: 32px;
    height: 32px;
    line-height: 32px;
  }
</style>
