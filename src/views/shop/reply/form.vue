<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="用户ID" >
        <el-input v-model="form.uid" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="订单ID" >
        <el-input v-model="form.oid" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="唯一id" >
        <el-input v-model="form.unique" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="产品id" >
        <el-input v-model="form.productId" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="某种商品类型(普通商品、秒杀商品）" >
        <el-input v-model="form.replyType" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="商品分数" >
        <el-input v-model="form.productScore" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="服务分数" >
        <el-input v-model="form.serviceScore" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="评论内容" >
        <el-input v-model="form.comment" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="评论图片" >
        <el-input v-model="form.pics" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="评论时间" >
        <el-input v-model="form.addTime" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="管理员回复内容" >
        <el-input v-model="form.merchantReplyContent" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="管理员回复时间" >
        <el-input v-model="form.merchantReplyTime" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="0未删除1已删除" >
        <el-input v-model="form.isDel" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="0未回复1已回复" >
        <el-input v-model="form.isReply" style="width: 370px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreProductReply'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
        id: '',
        uid: '',
        oid: '',
        unique: '',
        productId: '',
        replyType: '',
        productScore: '',
        serviceScore: '',
        comment: '',
        pics: '',
        addTime: '',
        merchantReplyContent: '',
        merchantReplyTime: '',
        isDel: '',
        isReply: ''
      },
      rules: {
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAdd) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    doEdit() {
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        uid: '',
        oid: '',
        unique: '',
        productId: '',
        replyType: '',
        productScore: '',
        serviceScore: '',
        comment: '',
        pics: '',
        addTime: '',
        merchantReplyContent: '',
        merchantReplyTime: '',
        isDel: '',
        isReply: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
