<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
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
            <el-button  size="medium" type="primary" @click="getL(form.address)">获取经纬度</el-button>
          </el-form-item>
          <el-form-item label="门店logo" prop="image">
            <MaterialList v-model="form.imageArr" style="width: 370px" type="image" :num="1" :width="150" :height="150" />
          </el-form-item>
          <el-form-item label="纬度" prop="latitude">
            <el-input v-model="form.latitude" style="width: 370px;" :disabled="true" />
          </el-form-item>
          <el-form-item label="经度" prop="longitude">
            <el-input v-model="form.longitude" style="width: 370px;" :disabled="true" />
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
          <el-form-item label="是否显示" prop="isShow">
            <el-radio-group v-model="form.isShow" style="width: 178px">
              <el-radio :label="1">显示</el-radio>
              <el-radio :label="0">隐藏</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" width="50" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="门店名称" />
        <el-table-column v-if="columns.visible('phone')" prop="phone" label="门店电话" />
        <el-table-column v-if="columns.visible('address')" prop="address" label="地址" />
        <el-table-column v-if="columns.visible('image')" prop="image" label="门店logo" >
          <template slot-scope="scope">
            <a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('validTime')" prop="validTime" label="核销有效日期" />
        <el-table-column v-if="columns.visible('dayTime')" prop="dayTime" label="营业时间" />
        <el-table-column v-if="columns.visible('isShow')" prop="isShow" label="是否显示" >
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.isShow === 1" :type="''">显示</el-tag>
              <el-tag v-else :type=" 'info' ">隐藏</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','yxSystemStore:edit','yxSystemStore:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
  import crudYxSystemStore from '@/api/yxSystemStore'
  import CRUD, { presenter, header, form, crud } from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import pagination from '@crud/Pagination'
  import MaterialList from '@/components/material'
  import { parseTime } from '@/utils/index'

  // crud交由presenter持有
  const defaultCrud = CRUD({ title: '门店', url: 'api/yxSystemStore', sort: 'id,desc', crudMethod: { ...crudYxSystemStore }})
  const defaultForm = { id: null, name: null, introduction: null, phone: null, address: null, detailedAddress: null, image: null, latitude:
  null, longitude: null, validTime: null, dayTime: null, addTime: null, isShow: 1, imageArr: [], validTimeArr: [],  dayTimeArr: [new Date(),new Date()] }
  export default {
    name: 'YxSystemStore',
    components: { pagination, crudOperation, rrOperation, udOperation, MaterialList },
    mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
    data() {
      return {
        permission: {
          add: ['admin', 'yxSystemStore:add'],
          edit: ['admin', 'yxSystemStore:edit'],
          del: ['admin', 'yxSystemStore:del']
        },
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
          isShow: [
            { required: true, message: '是否显示不能为空', trigger: 'blur' }
          ]
        }
        }
    },
    methods: {
      // 获取数据前设置好接口地址
      [CRUD.HOOK.beforeRefresh]() {
        return true
      },
      // 添加后
      [CRUD.HOOK.beforeSubmit]() {
        //console.log('hah:'+this.form.imageArr)
        this.form.image = this.form.imageArr.join(',')
      },
      // 编辑前
      [CRUD.HOOK.beforeToEdit](crud, form) {
        form.imageArr = [form.image]
        form.dayTimeArr = [form.dayTimeStart,form.dayTimeEnd]
        form.validTimeArr = [form.validTimeStart,form.validTimeEnd]
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
        crudYxSystemStore.getL({addr}).then(res => {
          this.form.latitude = res.result.location.lat
          this.form.longitude = res.result.location.lng

          //console.log(this.form)
        })
      }
    }
  }
</script>

<style scoped>

</style>
