<template>
  <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU"
             :visible.sync="crud.status.cu > 0"
             :title="crud.status.title" width="950px">
    <el-form ref="formData" :model="formData" class="attrFrom"  :rules="rules" label-width="130px" :inline="true">
      <el-row :gutter="24" type="flex">
        <el-col :xl="18" :lg="18" :md="18" :sm="24" :xs="24">
          <el-form-item label="模板名称：" prop="name">
            <el-input type="text" placeholder="请输入模板名称" v-model="formData.name"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" type="flex">
        <el-col :xl="18" :lg="18" :md="18" :sm="24" :xs="24">
          <el-form-item label="计费方式：" props="state" >
            <el-radio-group class="radio" v-model="formData.type" >
              <el-radio :label="1">按件数</el-radio>
              <el-radio :label="2">按重量</el-radio>
              <el-radio :label="3">按体积</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" type="flex">
        <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
          <el-form-item class="label" label="配送区域及运费：" props="state">
            <el-table
              ref="table"
              :data="templateList"
              empty-text="暂无数据"
              border
            >
              <el-table-column prop="regionName" label="可配送区域" width="130" />
              <el-table-column prop="first" label="首件" width="120">
                <template slot="header" slot-scope="scope">
                  <span v-if="formData.type == 1">首件</span>
                  <span v-else-if="formData.type == 2">首件重量(KG)</span>
                  <span v-else>首件体积(m³)</span>
                </template>
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row.first" /></span>
                </template>
              </el-table-column>
              <el-table-column prop="price" label="运费（元）" width="110">
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row.price"/></span>
                </template>
              </el-table-column>
              <el-table-column prop="_continue" label="续件" width="120">
                <template slot="header" slot-scope="scope">
                  <span v-if="formData.type == 1">续件</span>
                  <span v-else-if="formData.type == 2">续件重量(KG)</span>
                  <span v-else>续件体积(m³)</span>
                </template>
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row._continue"/></span>
                </template>
              </el-table-column>
              <el-table-column prop="continue_price" label="续费（元）" width="110">
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row.continue_price"/></span>
                </template>
              </el-table-column>
              <el-table-column  label="操作">
                <template slot-scope="scope">
                  <a v-if="scope.row.regionName!=='默认全国'" @click="delCity(index,1)">删除</a>
                </template>
              </el-table-column>

            </el-table>
            <el-row type="flex" class="addTop">
              <el-col>
                <el-button type="primary" icon="md-add" @click="addCity(1)">单独添加配送区域</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" type="flex">
        <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
          <el-form-item label="指定包邮：" prop="store_name">
            <el-radio-group class="radio" v-model="formData.appoint_check">
              <el-radio :label="1">开启</el-radio>
              <el-radio :label="0">关闭</el-radio>
            </el-radio-group>
            <el-table
              ref="table"
              :data="appointList"
              empty-text="暂无数据"
              border
              v-if="formData.appoint_check === 1"
            >
              <el-table-column prop="placeName" label="选择地区" />
              <el-table-column prop="a_num" label="包邮件数" width="120" >
                <template slot="header" slot-scope="scope">
                  <span v-if="formData.type == 1">包邮件数</span>
                  <span v-else-if="formData.type == 2">包邮重量(KG)</span>
                  <span v-else>包邮体积(m³)</span>
                </template>
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row.a_num"/></span>
                </template>
              </el-table-column>
              <el-table-column prop="a_price" label="包邮金额（元）" width="120">
                <template slot-scope="scope">
                  <span><el-input type="text" v-model="scope.row.a_price"/></span>
                </template>
              </el-table-column>
              <el-table-column  label="操作">
                <template slot-scope="scope">
                  <a v-if="scope.row.regionName!=='默认全国'" @click="delCity(index,2)">删除</a>
                </template>
              </el-table-column>

            </el-table>
            <el-row type="flex" v-if="formData.appoint_check === 1">
              <el-col>
                <el-button type="primary" icon="md-add" @click="addCity(2)">单独指定包邮</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" type="flex">
        <el-col :xl="18" :lg="18" :md="18" :sm="24" :xs="24">
          <el-form-item label="排序：" prop="store_name">
            <el-input-number :min="0" placeholder="输入值越大越靠前" v-model="formData.sort" ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" type="flex">
        <el-col>
          <el-form-item prop="store_name" label=" ">
            <el-button type="primary" @click="handleSubmit">{{id ? '立即修改':'立即提交'}}</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <!--<el-button type="text" @click="crud.cancelCU">取消</el-button>-->
      <!--<el-button :loading="modal_loading" type="primary" @click="handleSubmit('formData')">确认</el-button>-->
    </div>
    <city ref="city" @selectCity="selectCity" :type="type"></city>
  </el-dialog>

</template>

<script>
  import crudYxShippingTemplates from '@/api/yxShippingTemplates'
  import CRUD, {presenter, header, form, crud} from '@crud/crud'
  import rrOperation from '@crud/RR.operation'
  import crudOperation from '@crud/CRUD.operation'
  import udOperation from '@crud/UD.operation'
  import MaterialList from "@/components/material";
  import city from './city';

  var g =  [
    {
      region: [
        {
          name: '默认全国',
          city_id: 0
        }
      ],
      regionName: '默认全国',
      first: 1,
      price: 0,
      _continue: 1,
      continue_price: 0
    }
  ]

  const defaultForm = {id: null, type: null, sort: null, appointInfo: null, appoint: null, name: null}
  export default {
    name: 'tempForm',
    components: {crudOperation, rrOperation, udOperation, MaterialList, city},
    mixins: [header(), form(defaultForm), crud()],
    data() {
      return {

        permission: {
          add: ['admin', 'storeProductRule:add'],
          edit: ['admin', 'storeProductRule:edit'],
          del: ['admin', 'storeProductRule:del']
        },
        rules: {
          ruleName: [
            {required: true, message: '规格名称不能为空', trigger: 'blur'}
          ]
        },

        templateList: g,
        appointList: [],
        type: 1,
        formData: {
          type: 1,
          sort: 0,
          name: '',
          appoint_check: 0
        },
        id: 0,

        addressView: false,
        indeterminate: true,
        checkAll: false,
        checkAllGroup: [],
        activeCity: -1,
        provinceAllGroup: [],
        index: -1,
        displayData: '',
        currentProvince: ''

      }
    },
    watch: {},
    methods: {
      // 获取数据前设置好接口地址
      [CRUD.HOOK.beforeRefresh]() {
        return true
      }, // 新增与编辑前做的操作
      [CRUD.HOOK.afterToCU](crud, form) {
        console.log('uu:'+form.name)
        this.appointList = form.appointInfo ? eval('(' + form.appointInfo + ')') : []
        if(form.id){
          this.templateList =  eval('(' + form.regionInfo + ')')
        }else{
          this.templateList = g
        }

        this.formData.type = form.type || 1
        this.formData.sort = form.sort || 0
        this.formData.name = form.name || ''
        this.formData.appoint_check = form.appoint || 0
        this.id = form.id || 0
      },


      selectCity: function (data, type) {
        let cityName = data.map(function (item) {
          return item.name;
        }).join(';');
        switch (type) {
          case 1:
            this.templateList.push({
              region: data,
              regionName: cityName,
              first: 1,
              price: 0,
              _continue: 1,
              continue_price: 0
            });
            break;
          case 2:
            this.appointList.push({
              place: data,
              placeName: cityName,
              a_num: 0,
              a_price: 0
            });
            break;
        }
      },
      // 单独添加配送区域
      addCity (type) {
        this.$refs.city.addressView = true;
        this.type = type;
        this.$refs.city.getCityList()
      },
      // 提交
      handleSubmit: function () {
        let that = this;
        if (!that.formData.name.trim().length) {
          return this.$message({
            message:'请填写模板名称',
            type: 'error'
          });
        }
        for (let i = 0; i < that.templateList.length; i++) {
          if (that.templateList[i].first <= 0) {
            return this.$message({
              message:'首件/重量/体积应大于0\'',
              type: 'error'
            });
          }
          if (that.templateList[i].price < 0) {
            return this.$message({
              message:'运费应大于等于0',
              type: 'error'
            });
          }
          if (that.templateList[i].continue <= 0) {
            return this.$message({
              message:'续件/重量/体积应大于0',
              type: 'error'
            });
          }
          if (that.templateList[i].continue_price < 0) {
            return this.$message({
              message:'续费应大于等于0',
              type: 'error'
            });
          }
        }
        if (that.formData.appoint_check === 1) {
          for (let i = 0; i < that.appointList.length; i++) {
            if (that.appointList[i].a_num <= 0) {
              return this.$message({
                message:'包邮件数应大于0',
                type: 'error'
              });
            }
            if (that.appointList[i].a_price < 0) {
              return this.$message({
                message:'包邮金额应大于等于0',
                type: 'error'
              });
            }
          }
        }
        let data = {
          appoint_info: that.appointList,
          region_info: that.templateList,
          sort: that.formData.sort,
          type: that.formData.type,
          name: that.formData.name,
          appoint: that.formData.appoint_check
        };
        crudYxShippingTemplates.add(data,that.id).then(() => {
          if(that.id){
            this.crud.status.edit = CRUD.STATUS.NORMAL
            this.crud.editSuccessNotify()
          }else{
            this.crud.status.add = CRUD.STATUS.NORMAL
            this.crud.addSuccessNotify()
          }

          this.crud.resetForm()
          this.crud.toQuery()

          this.formData = {
            type: 1,
            sort: 0,
            name: '',
            appoint_check: 0
          };
          this.appointList = [];
          this.addressView = false;
          this.templateList = [
            {
              region: [
                {
                  name: '默认全国',
                  city_id: 0
                }
              ],
              regionName: '默认全国',
              first: 1,
              price: 0,
              continue: 1,
              continue_price: 0
            }
          ];
        });
      },
      // 删除
      delCity (index,type) {
        if (type === 1) {
          this.templateList.splice(index, 1);
        } else {
          this.appointList.splice(index, 1);
        }
      },
      // 关闭
      cancel () {
        this.formData = {
          type: 1,
          sort: 0,
          name: '',
          appoint_check: 0
        };
        this.appointList = [];
        this.addressView = false;
        this.templateList = [
          {
            region: [
              {
                name: '默认全国',
                city_id: 0
              }
            ],
            regionName: '默认全国',
            first: 0,
            price: 0,
            continue: 0,
            continue_price: 0
          }
        ];
      },

      address () {
        this.addressView = true
      },
      enter (index) {
        this.activeCity = index;
      },
      leave () {
        this.activeCity = null;
      }


    }
  }


</script>

<style scoped>

</style>
