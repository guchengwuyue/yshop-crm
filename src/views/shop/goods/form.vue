<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="900px">
    <el-form ref="form" :model="form" :inline="true" :rules="rules" size="small" label-width="80px">
      <el-form-item style="margin-bottom: 0px;" label="商品分类">
        <treeselect v-model="form.storeCategory.id" :options="cates" style="width: 370px;" placeholder="选择商品分类" />
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input v-model="form.storeName" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="关键字">
        <el-input v-model="form.keyword" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="单位名">
        <el-input v-model="form.unitName" style="width: 320px;" />
      </el-form-item>
      <el-form-item label="产品条码">
        <el-input v-model="form.barCode" style="width: 320px;" />
      </el-form-item>
      <el-form-item label="商品图片">
        <MaterialList v-model="form.imageArr" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="轮播图">
        <MaterialList v-model="form.sliderImageArr" style="width: 500px" type="image" :num="4" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="商品简介">
        <el-input v-model="form.storeInfo" style="width: 500px;" rows="5" type="textarea" />
      </el-form-item>
      <el-form-item label="产品描述">
        <editor v-model="form.description" />
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input v-model="form.price" />
      </el-form-item>
      <el-form-item label="市场价">
        <el-input v-model="form.otPrice" />
      </el-form-item>
      <el-form-item label="成本价">
        <el-input v-model="form.cost" />
      </el-form-item>
      <el-form-item label="邮费">
        <el-input v-model="form.postage" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.sort" />
      </el-form-item>
      <el-form-item label="销量">
        <el-input v-model="form.sales" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input v-model="form.stock" />
      </el-form-item>
      <el-form-item label="热卖单品">
        <el-radio v-model="form.isHot" :label="1">是</el-radio>
        <el-radio v-model="form.isHot" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="促销单品">
        <el-radio v-model="form.isBenefit" :label="1">是</el-radio>
        <el-radio v-model="form.isBenefit" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="精品推荐">
        <el-radio v-model="form.isBest" :label="1">是</el-radio>
        <el-radio v-model="form.isBest" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="首发新品">
        <el-radio v-model="form.isNew" :label="1">是</el-radio>
        <el-radio v-model="form.isNew" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="是否包邮">
        <el-radio v-model="form.isPostage" :label="1">是</el-radio>
        <el-radio v-model="form.isPostage" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="优品推荐">
        <el-radio v-model="form.isGood" :label="1">是</el-radio>
        <el-radio v-model="form.isGood" :label="0" style="width: 200px;">否</el-radio>
      </el-form-item>
      <el-form-item label="获得积分">
        <el-input v-model="form.giveIntegral" />
      </el-form-item>
      <el-form-item label="虚拟销量">
        <el-input v-model="form.ficti" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getCates } from '@/api/yxStoreCategory'
import { add, edit } from '@/api/yxStoreProduct'
import editor from '../../components/Editor'
import picUpload from '@/components/pic-upload'
import mulpicUpload from '@/components/mul-pic-upload'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import MaterialList from '@/components/material'
export default {
  components: { editor, picUpload, mulpicUpload, Treeselect, MaterialList },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false, cates: [],
      form: {
        id: '',
        merId: 0,
        image: '',
        sliderImage: '',
        imageArr: [],
        sliderImageArr: [],
        storeName: '',
        storeInfo: '',
        keyword: '',
        barCode: '',
        cateId: 1,
        storeCategory: {id:null},
        price: 0,
        vipPrice: 0,
        otPrice: 0,
        postage: 0,
        unitName: '',
        sort: 0,
        sales: 0,
        stock: 0,
        isShow: 1,
        isHot: 0,
        isBenefit: 0,
        isBest: 0,
        isNew: 0,
        description: '',
        addTime: '',
        isPostage: 0,
        isDel: 0,
        merUse: 0,
        giveIntegral: 0,
        cost: 0,
        isSeckill: 0,
        isBargain: 0,
        isGood: 0,
        ficti: 0,
        browse: 0,
        codePath: '',
        soureLink: ''
      },
      rules: {
      }
    }
  },
  watch: {
    'form.imageArr': function(val) {
      if (val) {
        this.form.image = val.join(',')
      }
    },
    'form.sliderImageArr': function(val) {
      if (val) {
        this.form.sliderImage = val.join(',')
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
        merId: 0,
        image: '',
        sliderImage: '',
        imageArr: [],
        sliderImageArr: [],
        storeName: '',
        storeInfo: '',
        keyword: '',
        barCode: '',
        cateId: 1,
        storeCategory: {},
        price: 0,
        vipPrice: 0,
        otPrice: 0,
        postage: 0,
        unitName: '',
        sort: 0,
        sales: 0,
        stock: 0,
        isShow: 1,
        isHot: 0,
        isBenefit: 0,
        isBest: 0,
        isNew: 0,
        description: '',
        addTime: '',
        isPostage: 0,
        isDel: 0,
        merUse: 0,
        giveIntegral: 0,
        cost: 0,
        isSeckill: 0,
        isBargain: 0,
        isGood: 0,
        ficti: 0,
        browse: 0,
        codePath: '',
      }
    },
    getCates() {
      getCates({ enabled: true }).then(res => {
        this.cates = res.content
      })
    }
  }
}
</script>

<style scoped>

</style>
