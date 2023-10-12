<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="800px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="140px">
      <el-form-item label="优惠券类型">
        <el-radio-group v-model="form.type" @change="couponsType">
          <el-radio :label=0>通用券</el-radio>
          <el-radio :label=1>商品券</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="选择商品" v-if="form.type == 1">
        <cgood v-model="form.product" @selectGoods="getGoods"></cgood>
      </el-form-item>
      <el-form-item label="优惠券名称">
        <el-input v-model="form.title" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="优惠券面值">
        <el-input-number v-model="form.couponPrice" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="优惠券最低消费">
        <el-input-number v-model="form.useMinPrice" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="优惠券有效期限(天)">
        <el-input-number v-model="form.couponTime" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="form.sort" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="状态">
        <el-radio v-model="form.status" :label="1">开启</el-radio>
        <el-radio v-model="form.status" :label="0">关闭</el-radio>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/yxStoreCoupon'
import cgood from '@/views/components/goods'
export default {
  components: { cgood },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  watch: {
    "form.product":function(val){
      if(val){
        this.getGoods(val)
      }
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
        id: '',
        title: '',
        integral: 0,
        couponPrice: 0,
        useMinPrice: 0,
        couponTime: 1,
        sort: 0,
        status: 1,
        type: 0,
        productId: '',
        product: []
      },
      rules: {
      }
    }
  },
  methods: {
    getGoods(p) {
      var ids = []
      p.forEach((item,index) => {
        ids.push(item.id)
      })
      this.form.productId = ids.join(",")
    },
    couponsType() {
      //alert(this.form.type)
    },
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
        title: '',
        integral: 0,
        couponPrice: 0,
        useMinPrice: 0,
        couponTime: 1,
        sort: 0,
        status: 1,
        type: 0,
        productId: '',
        product: []
      }
    }
  }
}
</script>

<style scoped>

</style>
