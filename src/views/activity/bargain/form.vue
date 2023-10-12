<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '开启砍价'" width="900px">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" size="small" label-width="140px">
      <el-col :span="24">
        <el-form-item label="选择商品：" prop="good">
          <cgood v-model="form1.good" :disabled="true" ></cgood>
        </el-form-item>
      </el-col>
      <el-form-item label="砍价名称">
        <el-input v-model="form.title" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="砍价简介">
        <el-input v-model="form.info" style="width: 500px;" rows="5" type="textarea" />
      </el-form-item>
      <el-form-item label="单位">
        <el-input v-model="form.unitName" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="活动开始时间">
        <template>
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="活动结束时间">
        <template>
          <el-date-picker
            v-model="form.stopTime"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="砍价产品主图片">
        <single-pic v-model="form.image" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="砍价产品轮播图">
        <MaterialList v-model="form.sliderImageArr" style="width: 500px" type="image" :num="4" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="form.stock" maxlength="7"/>
      </el-form-item>
      <el-form-item label="销量">
        <el-input-number v-model="form.sales" />
      </el-form-item>
      <el-form-item label="砍价金额">
        <el-input-number v-model="form.price" />
      </el-form-item>
      <el-form-item label="允许砍到最低价">
        <el-input-number v-model="form.minPrice" />
      </el-form-item>
      <el-form-item label="限购">
        <el-input-number v-model="form.num" />
      </el-form-item>
      <el-form-item label="单次砍最高价">
        <el-input-number v-model="form.bargainMaxPrice" />
      </el-form-item>
      <el-form-item label="单次砍最低价">
        <el-input-number v-model="form.bargainMinPrice" />
      </el-form-item>
      <el-form-item label="用户每次砍价的次数">
        <el-input-number v-model="form.bargainNum" />
      </el-form-item>
      <el-form-item label="砍价状态">
        <el-radio v-model="form.status" :label="1">开启</el-radio>
        <el-radio v-model="form.status" :label="0" style="width: 110px;">关闭</el-radio>
      </el-form-item>
      <el-form-item label="成本价">
        <el-input-number v-model="form.cost" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="form.sort" />
      </el-form-item>
      <el-col :span="24">
        <el-form-item label="运费模板：" prop="temp_id">
          <div class="acea-row">
            <el-select v-model="form.temp_id"  class="mr20" :disabled="true">
              <el-option v-for="(item,index) in templateList" :value="item.id" :key="index" :label="item.name">
              </el-option>
            </el-select>
          </div>
        </el-form-item>
      </el-col>
      <el-form-item label="砍价规则">
        <editor v-model="form.rule" />
      </el-form-item>
      <el-form-item label="砍价详情">
        <editor v-model="form.description" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import cgood from '@/views/components/good'
import { add, edit } from '@/api/yxStoreBargain'
import editor from '../../components/Editor'
import MaterialList from '@/components/material'
import singlePic from '@/components/singlematerial'
import { getInfo } from '@/api/yxStoreProduct'
export default {
  components: { editor, MaterialList,cgood,singlePic },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      templateList: [],
      form1: {
        good:{
          productId: null,
          storeName: null,
          image: null,
          otPrice: null,
          price: null,
        }
      },
      form: {
        //that.form.minPrice = 0
          //that.form.num = 1
          //that.form.bargainMaxPrice = 0
          //that.form.bargainMinPrice = 0
          //that.form.bargainNum = 1
        id: '',
        productId: '',
        title: '',
        image: '',
        unitName: '',
        stock: '',
        sales: '',
        images: '',
        imageArr: [],
        sliderImageArr: [],
        startTime: '',
        stopTime: '',
        storeName: '',
        price: '',
        minPrice: 0,
        num: 1,
        bargainMaxPrice: 0,
        bargainMinPrice: 0,
        bargainNum: 1,
        status: 1,
        description: '',
        giveIntegral: 0,
        info: '',
        cost: '',
        sort: 0,
        isHot: 0,
        isDel: 0,
        addTime: '',
        isPostage: 1,
        postage: '',
        rule: '',
        look: '',
        share: '',
        startTimeDate: '',
        endTimeDate: '',
        temp_id: ''
      },
      rules: {
      }
    }
  },
  watch: {
    'form.image': function(val) {
      this.form1.good.image = this.form.image
      this.form1.good.productId = this.form.productId
     
    },
    'form.sliderImageArr': function(val) {
      console.log("aaa:"+val)
      if (val && Array.isArray(val)) {
        this.form.images = val.join(',')
      }
    },
    'form1.good.productId': {
      handler(val,oldVal){
        if(val){
          this.getInfoChooseGood (val)
        }
      },
      deep:true//对象内部的属性监听，也叫深度监听
    },
  },
 
  methods: {
    // 详情选择商品生成规格用
    getInfoChooseGood (id) {
      let that = this;
      getInfo(id).then(async res => {
        let data = res.productInfo;
        console.info('data:'+JSON.stringify(data))
        if(data){
          let cate_id = parseInt(data.cate_id) || 0;
          //that.form = data;
          Object.keys(that.form).forEach(key=>{
                if(data[key]) that.form[key] = data[key];
          })
          that.form.productId = data.id
          that.form.cate_id = cate_id;
          that.form.title = data.store_name
          that.form.info = data.store_info
          that.form.unitName = data.unit_name
          that.form.imageArr = data.image
          that.form.sliderImageArr = data.slider_image
          that.form.status = 1
          //that.form.minPrice = 0
          //that.form.num = 1
          //that.form.bargainMaxPrice = 0
          //that.form.bargainMinPrice = 0
          //that.form.bargainNum = 1

        }
        that.templateList = res.tempList;

      }).catch(res => {
        console.log('err:'+res)
        return this.$message({
          message:res.msg,
          type: 'error'
        });
      })
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
        productId: '',
        title: '',
        image: '',
        unitName: '',
        stock: '',
        sales: '',
        images: '',
        imageArr: [],
        sliderImageArr: [],
        startTime: '',
        stopTime: '',
        storeName: '',
        price: '',
        minPrice: '',
        num: '',
        bargainMaxPrice: '',
        bargainMinPrice: '',
        bargainNum: '',
        status: 1,
        description: '',
        giveIntegral: '',
        info: '',
        cost: '',
        sort: '',
        isHot: '',
        isDel: '',
        addTime: '',
        isPostage: '',
        postage: '',
        rule: '',
        look: '',
        share: '',
        temp_id: '',
      }
    }
  }
}
</script>

<style scoped>

</style>
