<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
        <el-form-item label="门店名称" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="门店简介" prop="introduction">
          <el-input v-model="form.introduction" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="门店手机" prop="phone">
          <el-input v-model="form.phone" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="门店地址" prop="address">
          <el-input v-model="form.address" style="width: 370px;" />
          <el-button :loading="loading" size="medium" type="primary" @click="getL(form.address)">获取经纬度</el-button>
        </el-form-item>
        <el-form-item label="门店logo" prop="image">
          <MaterialList v-model="form.imageArr" style="width: 370px" type="image" :num="1" :width="150" :height="150" />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="form.latitude" :disabled="true" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="form.longitude" :disabled="true" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="核销时效" prop="validTime">
          <el-date-picker
            @change="getTimeT"
            style="width: 370px;"
            v-model="form.validTimeArr"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="营业时间" prop="dayTime">
          <el-time-picker
            @change="getTime"
            style="width: 370px;"
            is-range
            v-model="form.dayTimeArr"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="">
          <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
import { get, update, getL } from '@/api/yxSystemStore'
import MaterialList from '@/components/material'
import { parseTime } from '@/utils/index'
export default {
  name: 'YxSystemStore',
  components: { MaterialList },
  data() {
    return {
      loading: false,
      form: { id:null, name: '', introduction: '', phone: '', address: '', detailedAddress: '', image: '' ,
        latitude: '', longitude: '', validTime: '', dayTime: '', validTimeStart: null, validTimeEnd: null,
        dayTimeStart: null, dayTimeEnd: null, dayTimeArr: [new Date(),new Date()], validTimeArr: [], imageArr: []},
      rules: {
        name: [
          { required: true, message: '门店名称不能为空', trigger: 'blur' }
        ],
        introduction: [
          { required: true, message: '简介不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '省市区不能为空', trigger: 'blur' }
        ],
        latitude: [
          { required: true, message: '纬度不能为空', trigger: 'blur' }
        ],
        longitude: [
          { required: true, message: '经度不能为空', trigger: 'blur' }
        ],
        validTime: [
          { required: true, message: '核销有效日期不能为空', trigger: 'blur' }
        ],
        dayTime: [
          { required: true, message: '每日营业开关时间不能为空', trigger: 'blur' }
        ]
      }

      }
  },
  watch: {
    'form.imageArr': function(val) {
      console.log(222)
      if (val) {
        console.log(val)
        this.form.image = val.join(',')
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      get().then(res => {
        if(res.content.length > 0){
          const con = res.content[0]
          this.form = con
          this.form.imageArr = [con.image]

          this.form.dayTimeArr = [con.dayTimeStart,con.dayTimeEnd]
          this.form.validTimeArr = [con.validTimeStart,con.validTimeEnd]
        }

      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.form.image = this.form.imageArr.join(',')
          update(this.form).then(res => {
            this.$notify({
              title: '保存成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    },
    getTime(t) {
      this.form.dayTimeStart = t[0]
      this.form.dayTimeEnd = t[1]
      this.form.dayTime = parseTime(t[0],'{h}:{i}:{s}') + ' - ' + parseTime(t[1],'{h}:{i}:{s}')
    },
    getTimeT(t) {
      this.form.validTimeStart = t[0]
      this.form.validTimeEnd = t[1]
      this.form.validTime = parseTime(t[0],'{y}-{m}-{d}') + ' - ' + parseTime(t[1],'{y}-{m}-{d}')
    },
    getL(addr) {
      getL({addr}).then(res => {
        this.form.latitude = res.result.location.lat
        this.form.longitude = res.result.location.lng
      })
    }
  }
}
</script>

<style scoped>

</style>
