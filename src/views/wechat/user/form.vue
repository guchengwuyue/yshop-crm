<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段">
        <el-input v-model="form.unionid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户的标识，对当前公众号唯一">
        <el-input v-model="form.openid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="小程序唯一身份ID">
        <el-input v-model="form.routineOpenid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户的昵称">
        <el-input v-model="form.nickname" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户头像">
        <el-input v-model="form.headimgurl" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知">
        <el-input v-model="form.sex" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户所在城市">
        <el-input v-model="form.city" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户的语言，简体中文为zh_CN">
        <el-input v-model="form.language" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户所在省份">
        <el-input v-model="form.province" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户所在国家">
        <el-input v-model="form.country" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注">
        <el-input v-model="form.remark" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户所在的分组ID（兼容旧的用户分组接口）">
        <el-input v-model="form.groupid" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户被打上的标签ID列表">
        <el-input v-model="form.tagidList" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户是否订阅该公众号标识">
        <el-input v-model="form.subscribe" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="关注公众号时间">
        <el-input v-model="form.subscribeTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="添加时间">
        <el-input v-model="form.addTime" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="一级推荐人">
        <el-input v-model="form.stair" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="二级推荐人">
        <el-input v-model="form.second" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="一级推荐人订单">
        <el-input v-model="form.orderStair" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="二级推荐人订单">
        <el-input v-model="form.orderSecond" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="佣金">
        <el-input v-model="form.nowMoney" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="小程序用户会话密匙">
        <el-input v-model="form.sessionKey" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="用户类型">
        <el-input v-model="form.userType" style="width: 370px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxWechatUser'
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
        uid: '',
        unionid: '',
        openid: '',
        routineOpenid: '',
        nickname: '',
        headimgurl: '',
        sex: '',
        city: '',
        language: '',
        province: '',
        country: '',
        remark: '',
        groupid: '',
        tagidList: '',
        subscribe: '',
        subscribeTime: '',
        addTime: '',
        stair: '',
        second: '',
        orderStair: '',
        orderSecond: '',
        nowMoney: '',
        sessionKey: '',
        userType: ''
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
        uid: '',
        unionid: '',
        openid: '',
        routineOpenid: '',
        nickname: '',
        headimgurl: '',
        sex: '',
        city: '',
        language: '',
        province: '',
        country: '',
        remark: '',
        groupid: '',
        tagidList: '',
        subscribe: '',
        subscribeTime: '',
        addTime: '',
        stair: '',
        second: '',
        orderStair: '',
        orderSecond: '',
        nowMoney: '',
        sessionKey: '',
        userType: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
