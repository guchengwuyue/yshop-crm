<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '开启秒杀'" width="900px">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" size="small" label-width="140px">
      <el-form-item label="秒杀名称">
        <el-input v-model="form.title" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="秒杀简介">
        <el-input v-model="form.info" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="单位">
        <el-input v-model="form.unitName" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="秒杀开始时间">
        <template>
          <el-date-picker
            v-model="form.startTimeDate"
            type="date"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="秒杀结束时间">
        <template>
          <el-date-picker
            v-model="form.endTimeDate"
            type="date"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="开始时间" prop="storeId">
        <el-select v-model="form.timeId" style="width: 178px" placeholder="请先选择开始时间">
          <el-option
            v-for="(item, index) in myTimes"
            :key="index"
            :label="item.map.time + '点开始，持续' + item.map.continued + '小时'"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品主图片">
        <MaterialList v-model="form.imageArr" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="产品轮播图">
        <MaterialList v-model="form.sliderImageArr" style="width: 500px" type="image" :num="4" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="秒杀价">
        <el-input-number v-model="form.price" />
      </el-form-item>
      <el-form-item label="成本">
        <el-input-number v-model="form.cost" />
      </el-form-item>
      <el-form-item label="原价">
        <el-input-number v-model="form.otPrice" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="form.stock" />
      </el-form-item>
      <el-form-item label="虚拟销量">
        <el-input-number v-model="form.sales" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="form.sort" />
      </el-form-item>
      <el-form-item label="限购">
        <el-input-number v-model="form.num" />
      </el-form-item>
      <el-form-item label="邮费">
        <el-input-number v-model="form.postage" />
      </el-form-item>
      <el-form-item label="是否包邮">
        <el-radio v-model="form.isPostage" :label="1">是</el-radio>
        <el-radio v-model="form.isPostage" :label="0" style="width: 110px;">否</el-radio>
      </el-form-item>
      <el-form-item label="活动状态">
        <el-radio v-model="form.status" :label="1">开启</el-radio>
        <el-radio v-model="form.status" :label="0" style="width: 200px;">关闭</el-radio>
      </el-form-item>
      <el-form-item label="详情">
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
import { add, edit } from '@/api/yxStoreSeckill'
import { initData } from '@/api/data'
import editor from '../../components/Editor'
import MaterialList from '@/components/material'
export default {
  components: { editor, MaterialList },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false, myTimes: [],
      form: {
        id: '',
        productId: '',
        image: '',
        images: '',
        imageArr: [],
        sliderImageArr: [],
        title: '',
        info: '',
        price: '',
        cost: '',
        otPrice: '',
        giveIntegral: '',
        sort: '',
        stock: '',
        sales: '',
        unitName: '',
        postage: '',
        description: '',
        startTime: '',
        stopTime: '',
        addTime: '',
        status: 1,
        isPostage: 1,
        isHot: '',
        isDel: 0,
        num: '',
        isShow: '',
        startTimeDate: '',
        endTimeDate: '',
        timeId: null
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
        this.form.images = val.join(',')
      }
    }
  },
  mounted() {
    console.log('9999')
    initData('api/yxSystemGroupData',{ groupName: 'yshop_seckill_time' }).then(res => {
      //console.log(res.content)
      this.myTimes = res.content
    })
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
        productId: '',
        image: '',
        images: '',
        imageArr: [],
        sliderImageArr: [],
        title: '',
        info: '',
        price: '',
        cost: '',
        otPrice: '',
        giveIntegral: '',
        sort: '',
        stock: '',
        sales: '',
        unitName: '',
        postage: '',
        description: '',
        startTime: '',
        stopTime: '',
        addTime: '',
        status: '',
        isPostage: '',
        isHot: '',
        isDel: '',
        num: '',
        isShow: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
