<template>
  <div class="app-container">
    <el-card :bordered="false">
      <el-form  ref="formValidate" :rules="ruleValidate" :model="formValidate" label-width="130px" >
        <el-row :gutter="24">
          <!-- 商品信息-->
          <el-col v-bind="grid2">
            <el-form-item label="商品名称：" prop="store_name">
              <el-input v-model="formValidate.store_name" placeholder="请输入商品名称"  />
            </el-form-item>
          </el-col>
          <el-col v-bind="grid2">
            <el-form-item label="商品分类：" prop="cate_id">
              <el-select v-model="formValidate.cate_id" filterable :filter-method="dataFilter" clearable>
                <el-option v-for="item in optionsMetaShow" :disabled="item.disabled === 0"
                :value="item.value" :key="item.id" :label="item.label" ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid2">
            <el-form-item label="商品关键字：" prop="">
              <el-input v-model="formValidate.keyword" placeholder="请输入商品关键字"  />
            </el-form-item>
          </el-col>
          <el-col v-bind="grid2">
            <el-form-item label="单位：" prop="unit_name">
              <el-input v-model="formValidate.unit_name"  placeholder="请输入单位" />
            </el-form-item>
          </el-col>
          <el-col v-bind="grid2">
            <el-form-item label="商品简介：" prop="">
              <el-input v-model="formValidate.store_info" type="textarea" :rows="3" placeholder="请输入商品简介" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商品封面图：" prop="image">
              <single-pic v-model="formValidate.image"  type="image" :num="1" :width="150" :height="150" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商品轮播图：" prop="slider_image">
              <MaterialList v-model="formValidate.slider_image"  type="image" :num="4" :width="150" :height="150" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商品规格：" props="spec_type">
              <el-radio-group v-model="formValidate.spec_type"  @change="changeSpec">
                <el-radio :label="0" class="radio">单规格</el-radio>
                <el-radio :label="1">多规格{{formValidate.spec_typ}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="开启积分兑换：" props="is_integral">
              <el-radio-group v-model="formValidate.is_integral"  @change="changeSpec">
                <el-radio :label="0" class="radio">不开启</el-radio>
                <el-radio :label="1">开启</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 多规格添加-->
          <el-col :span="24" v-if="formValidate.spec_type === 1" class="noForm">
            <el-col :span="24">
              <el-form-item label="选择规格：" prop="">
                <div  class="acea-row row-middle">
                  <el-select v-model="formValidate.selectRule" style="width: 23%;">
                    <el-option v-for="(item, index) in ruleList" :value="item.ruleName" :key="index">{{ item.ruleName }}</el-option>
                  </el-select>
                  <el-button type="primary" class="mr20" @click="confirm">确认</el-button>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item v-if="attrs.length!==0">
                <div  v-for="(item, index) in attrs" :key="index">
                  <div class="acea-row row-middle"><span class="mr5">{{item.value}}</span>
                    <i class="el-icon-circle-close"  @click="handleRemoveRole(index)"></i>
                  </div>
                  <div class="rulesBox">
                    <el-tag type="dot" closable color="primary" v-for="(j, indexn) in item.detail" :key="indexn" :name="j" class="mr20" @close="handleRemove2(item.detail,indexn)">{{j}}</el-tag>
                    <el-input placeholder="请输入属性名称" v-model="item.detail.attrsVal"
                              style="width: 150px">
                      <el-button slot="append" type="primary" @click="createAttr(item.detail.attrsVal,index)">添加</el-button>
                    </el-input>
                  </div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-if="createBnt">
              <el-form-item>
                <el-button type="primary" icon="md-add" @click="addBtn" class="mr15">添加新规格</el-button>
                <el-button type="success" @click="generate">立即生成</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="24" v-if="showIput">
              <el-col  :xl="6" :lg="9" :md="10" :sm="24" :xs="24" >
                <el-form-item label="规格：">
                  <el-input  placeholder="请输入规格" v-model="formDynamic.attrsName"  />
                </el-form-item>
              </el-col>
              <el-col  :xl="6" :lg="9" :md="10" :sm="24" :xs="24">
                <el-form-item label="规格值：">
                  <el-input v-model="formDynamic.attrsVal" placeholder="请输入规格值"  />
                </el-form-item>
              </el-col>
              <el-col :xl="6" :lg="5" :md="10" :sm="24" :xs="24" >

                  <el-button type="primary"    @click="createAttrName">确定</el-button>
                  <el-button type="danger" @click="offAttrName" >取消</el-button>

              </el-col>
            </el-col>
            <!-- 多规格设置-->
            <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24" v-if="manyFormValidate.length && formValidate.header.length!==0 && attrs.length!==0">
              <!-- 多规格表格-->
              <el-col :span="24">
                <el-form-item label="商品属性：" class="labeltop">
                  <el-table :data="manyFormValidate" size="small" style="width: 90%;">
                    <el-table-column type="myindex" v-for="(item,index) in formValidate.header" :key="index" :label="item.title" :property="item.slot" align="center">
                      <template slot-scope="scope">
                        <div v-if="scope.column.property == 'pic'" align="center">
                          <single-pic v-model="scope.row[scope.column.property]" type="image" :num="1" :width="60" :height="60" />
                        </div>
                        <div v-else-if="scope.column.property.indexOf('value') != -1" align="center">
                         {{ scope.row[scope.column.property] }}
                        </div>
                        <div v-else-if="scope.column.property == 'action'" align="center" >
                          <a @click="delAttrTable(scope.$index)" align="center">删除</a>
                        </div>
                        <div v-else align="center">
                          <el-input  v-model="scope.row[scope.column.property]" align="center" />
                        </div>
                      </template>
                    </el-table-column>

                  </el-table>
                </el-form-item>
              </el-col>
            </el-col>
          </el-col>
          <!-- 单规格表格-->
          <el-col :xl="23" :lg="24" :md="24" :sm="24" :xs="24" v-if="formValidate.spec_type === 0">
            <el-form-item >
              <el-table :data="oneFormValidate"  size="small" style="width: 90%;">
                <el-table-column prop="pic" label="图片" align="center">
                  <template slot-scope="scope">
                    <single-pic v-model="scope.row.pic" type="image" :num="1" :width="60" :height="60" />
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="售价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.price"/>
                  </template>
                </el-table-column>
                <el-table-column prop="cost" label="成本价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.cost"/>
                  </template>
                </el-table-column>
                <el-table-column prop="ot_price" label="原价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.ot_price"/>
                  </template>
                </el-table-column>
                <el-table-column prop="stock" label="库存" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.stock" maxlength="7"/>
                  </template>
                </el-table-column>
                <el-table-column prop="bar_code" label="商品编号" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.bar_code"/>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量（KG）" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.weight"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="体积(m³)" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.volume"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="所需兑换积分" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.integral"/>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="运费模板：" prop="temp_id">
              <div class="acea-row">
                <el-select v-model="formValidate.temp_id"  class="mr20">
                  <el-option v-for="(item,index) in templateList" :value="item.id" :key="index" :label="item.name">
                  </el-option>
                </el-select>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="商品详情：">
              <ueditor-wrap v-model="formValidate.description" :config="myConfig"  @beforeInit="addCustomDialog"  style="width: 90%;"></ueditor-wrap>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-bind="grid">
            <el-form-item label="虚拟销量：">
              <el-input-number  :min="0" v-model="formValidate.ficti" placeholder="请输入虚拟销量"  />
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="购买返回积分：">
              <el-input-number  v-model="formValidate.give_integral" :min="0" placeholder="请输入积分" />
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="排序：">
              <el-input-number :min="0"  v-model="formValidate.sort" placeholder="请输入排序" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="佣金设置：">
              <el-radio-group v-model="formValidate.is_sub">
                <el-radio :label="1" class="radio">单独设置</el-radio>
                <el-radio :label="0">默认设置</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="formValidate.is_sub === 1">
            <!--单规格返佣-->
            <el-form-item label="商品属性：" v-if="formValidate.spec_type === 0">
              <el-table :data="oneFormValidate"  size="small" style="width: 90%;">
                <el-table-column prop="imageArr" label="图片" align="center">
                  <template slot-scope="scope">
                    <el-image :src="scope.row.pic" class="el-avatar">
                      <div slot="error" class="image-slot">
                        <i class="el-icon-picture-outline"></i>
                      </div>
                    </el-image>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="售价" align="center" />
                <el-table-column prop="cost" label="成本价" align="center" />
                <el-table-column prop="ot_price" label="原价" align="center" />
                <el-table-column prop="stock" label="库存" align="center" />
                <el-table-column prop="bar_code" label="商品编号" align="center" />
                <el-table-column prop="weight" label="重量（KG）" align="center" />
                <el-table-column prop="volume" label="体积(m³" align="center" />
                <el-table-column prop="volume" label="一级返佣" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.brokerage"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="二级返佣" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.brokerage_two"/>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="商品属性：" v-if="formValidate.spec_type === 1 && manyFormValidate.length">
              <el-table :data="manyFormValidate" size="small" style="width: 90%;">
                <el-table-column prop="imageArr" label="图片" align="center">
                  <template slot-scope="scope">
                    <el-image :src="scope.row.pic" :width="60" :height="60" >
                      <div slot="error" class="image-slot">
                        <i class="el-icon-picture-outline"></i>
                      </div>
                    </el-image>
                  </template>
                </el-table-column>
                <el-table-column prop="sku" label="规格" align="center" />
                <el-table-column prop="price" label="售价" align="center" />
                <el-table-column prop="cost" label="成本价" align="center" />
                <el-table-column prop="ot_price" label="原价" align="center" />
                <el-table-column prop="stock" label="库存" align="center" />
                <el-table-column prop="bar_code" label="商品编号" align="center" />
                <el-table-column prop="weight" label="重量（KG）" align="center" />
                <el-table-column prop="volume" label="体积(m³)" align="center" />
                <el-table-column prop="integral" label="所需兑换积分" align="center" />
                <el-table-column prop="volume" label="一级返佣" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.brokerage"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="二级返佣" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.brokerage_two"/>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="商品状态：">
              <el-radio-group v-model="formValidate.is_show" >
                <el-radio :label="1" class="radio">上架</el-radio>
                <el-radio :label="0">下架</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="热卖单品：">
              <el-radio-group v-model="formValidate.is_hot" >
                <el-radio :label="1" class="radio">开启</el-radio>
                <el-radio :label="0">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="猜你喜欢：">
              <el-radio-group v-model="formValidate.is_benefit" >
                <el-radio :label="1" class="radio">开启</el-radio>
                <el-radio :label="0">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="精品推荐：">
              <el-radio-group v-model="formValidate.is_best" >
                <el-radio :label="1" class="radio">开启</el-radio>
                <el-radio :label="0">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-bind="grid">
            <el-form-item label="首发新品：">
              <el-radio-group v-model="formValidate.is_new" >
                <el-radio :label="1" class="radio">开启</el-radio>
                <el-radio :label="0">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" class="submission" @click="handleSubmit('formValidate')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getCates } from '@/api/yxStoreCategory'
import { add, edit, getInfo, isFormatAttr } from '@/api/yxStoreProduct'
import editor from '../../components/Editor'
import picUpload from '@/components/pic-upload'
import mulpicUpload from '@/components/mul-pic-upload'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import MaterialList from '@/components/material'
import singlePic from '@/components/singlematerial'
import UeditorWrap from 'vue-ueditor-wrap';
export default {
  components: { editor, picUpload, mulpicUpload, Treeselect, MaterialList, UeditorWrap, singlePic },
  data() {
    return {
      spinShow: false,
      grid2: {
        xl: 10,
        lg: 12,
        md: 12,
        sm: 24,
        xs: 24
      },
      grid3: {
        xl: 18,
        lg: 18,
        md: 20,
        sm: 24,
        xs: 24
      },
      // 批量设置表格data
      oneFormBatch: [
        {
          pic: '',
          price: 0,
          cost: 0,
          ot_price: 0,
          stock: 0,
          bar_code: '',
          seckill_stock: 0,
          seckill_price: 0,
          pink_stock: 0,
          pink_price: 0,
          weight: 0,
          volume: 0,
          integral:0
        }
      ],
      // 规格数据
      formDynamic: {
        attrsName: '',
        attrsVal: ''
      },
      formDynamicNameData: [],
      isBtn: false,
      myConfig: {
        autoHeightEnabled: false, // 编辑器不自动被内容撑高
        initialFrameHeight: 500, // 初始容器高度
        initialFrameWidth: '100%', // 初始容器宽度
        UEDITOR_HOME_URL: '/UEditor/',
        serverUrl: ''
      },
      columns: [],
      formValidate: {
        imageArr:[],
        sliderImageArr: [],
        store_name: '',
        cate_id: '',
        keyword: '',
        unit_name: '',
        store_info: '',
        image: '',
        slider_image: [],
        description: '',
        ficti: 0,
        give_integral: 0,
        sort: 0,
        is_show: 1,
        is_hot: 0,
        is_benefit: 0,
        is_best: 0,
        is_new: 0,
        is_good: 0,
        is_postage: 0,
        is_sub: 0,
        is_integral: 0,
        id: 0,
        spec_type: 0,
        temp_id: '',
        attrs: [],
        items: [
          {
            pic: '',
            price: 0,
            cost: 0,
            ot_price: 0,
            stock: 0,
            bar_code: '',
            integral:0
          }
        ],
        header: [],
        selectRule: ''
      },
      ruleList: [],
      templateList: [],
      createBnt: false,
      showIput: false,
      manyFormValidate: [],
      // 单规格表格data
      oneFormValidate: [
        {
          imageArr: [],
          pic: '',
          price: 2,
          cost: 0,
          ot_price: 0,
          stock: 0,
          seckill_stock: 0,
          seckill_price: 0,
          pink_stock: 0,
          pink_price: 0,
          bar_code: '',
          weight: 0,
          volume: 0,
          brokerage: 0,
          brokerage_two: 0,
          integral: 0
        }
      ],
      images: [],
      grid: {
        xl: 8,
        lg: 8,
        md: 12,
        sm: 24,
        xs: 24
      },
      loading: false,
      treeSelect: [],
      optionsMetaShow: [],
      tableIndex: 0,
      ruleValidate: {
        store_name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        cate_id: [
          { required: true, message: '请选择商品分类', trigger: 'change' }
        ],
        keyword: [
          { required: true, message: '请输入商品关键字', trigger: 'blur' }
        ],
        unit_name: [
          { required: true, message: '请输入单位', trigger: 'blur' }
        ],
        store_info: [
          { required: true, message: '请输入商品简介', trigger: 'blur' }
        ],
        spec_type: [
          { required: true, message: '请选择商品规格', trigger: 'change' }
        ],
        selectRule: [
          { required: true, message: '请选择商品规格属性', trigger: 'change' }
        ],
        temp_id: [
          { required: true, message: '请选择运费模板', trigger: 'change', type: 'number' }
        ]
      },
      attrs: []
    }
  },
  watch: {
    'formValidate.image': function(val) {
      console.log('aaaa:'+val)
      if (val) {
        this.oneFormValidate[0].pic = val
        console.log('bbbbbb:'+this.oneFormValidate.pic)
      }
    },
    'form.sliderImageArr': function(val) {
      if (val) {
        this.form.slider_image = val.join(',')
      }
    }
  },
  mounted () {
    this.getInfo();
  },
  methods: {
    dataFilter(val){
      this.value=val
      if(val){
        this.optionsMetaShow=this.treeSelect.filter((item=>{
          if (!!~item.label.indexOf(val) || !!~item.label.toUpperCase().indexOf(val.toUpperCase())) {
            return true
          }
        }))
      }else{
        this.optionsMetaShow=this.treeSelect
      }
    },
    confirm () {
      let that = this;
      that.createBnt = true;
      if (that.formValidate.selectRule.trim().length <= 0) {
        return this.$message({
          message:'请选择属性',
          type: 'error'
        });
      }
      that.ruleList.forEach(function (item, index) {
        if (item.ruleName === that.formValidate.selectRule) {
          that.attrs = item.ruleValue;
        }
      });
    },
    // 删除表格中的属性
    delAttrTable (index) {
      this.manyFormValidate.splice(index, 1);
    },

    // 添加按钮
    addBtn () {
      this.clearAttr();
      this.createBnt = false;
      this.showIput = true;
    },
    // 立即生成
    generate () {
      isFormatAttr(this.formValidate.id, { attrs: this.attrs }).then(res => {
        this.manyFormValidate = res.value;
        let headerdel = {
          title: '操作',
          slot: 'action',
          fixed: 'right',
          width: 220
        };
        res.header.push(headerdel);
        this.formValidate.header = res.header;
        //this.formValidate.attrs = res.attr;
        let header = res.header;
        header.pop();
        if (!this.$route.params.id && this.formValidate.spec_type === 1) {
          this.manyFormValidate.map((item) => {
            item.pic = this.formValidate.image
          });
          this.oneFormBatch[0].pic = this.formValidate.image;
        }
      }).catch(res => {
        // this.$message({
        //   message:res.msg,
        //   type: 'error'
        // });
      })
    },
    // 取消
    offAttrName () {
      this.showIput = false;
      this.createBnt = true;
    },
    clearAttr () {
      this.formDynamic.attrsName = '';
      this.formDynamic.attrsVal = '';
    },
    // 删除规格
    handleRemoveRole (index) {
      this.attrs.splice(index, 1);
      this.manyFormValidate.splice(index, 1);
    },
    // 删除属性
    handleRemove2 (item, index) {
      item.splice(index, 1);
    },
    // 添加规则名称
    createAttrName () {
      if (this.formDynamic.attrsName && this.formDynamic.attrsVal) {
        let data = {
          value: this.formDynamic.attrsName,
          detail: [
            this.formDynamic.attrsVal
          ]
        };
        this.attrs.push(data);
        var hash = {};
        this.attrs = this.attrs.reduce(function (item, next) {
          hash[next.value] ? '' : hash[next.value] = true && item.push(next);
          return item
        }, [])
        this.clearAttr();
        this.showIput = false;
        this.createBnt = true;
      } else {
        this.$message.warning('请添加完整的规格！');
      }
    },
    // 添加属性
    createAttr (num, idx) {
      if (num) {
        this.attrs[idx].detail.push(num);
        var hash = {};
        this.attrs[idx].detail = this.attrs[idx].detail.reduce(function (item, next) {
          hash[next] ? '' : hash[next] = true && item.push(next);
          return item
        }, [])
      } else {
        this.$message.warning('请添加属性！');
      }
    },

    // 改变规格
    changeSpec () {
    },
    // 详情
    getInfo () {
      let that = this;
      let id = that.$route.params.id || 0;
      getInfo(id).then(async res => {
        let data = res.productInfo;
        console.log('data:'+data)
        if(data){
          let cate_id = parseInt(data.cate_id) || 0;
          this.attrs = data.items || [];
          that.formValidate = data;
          that.formValidate.cate_id = cate_id;
          that.oneFormValidate = [data.attr];
          that.formValidate.header = [];
          that.generate();
          that.manyFormValidate = data.attrs;
          if(data.spec_type === 0){
            that.manyFormValidate = [];
          }else {
            that.createBnt = true;
            that.oneFormValidate = [
              {
                pic: '',
                price: 0,
                cost: 0,
                ot_price: 0,
                stock: 0,
                seckill_stock: 0,
                seckill_price: 0,
                pink_stock: 0,
                pink_price: 0,
                bar_code: '',
                weight:0,
                volume:0,
                brokerage:0,
                brokerage_two:0,
                integral:0
              }
            ]
          }
        }

        that.treeSelect = res.cateList;
        that.ruleList = res.ruleList;
        that.templateList = res.tempList;
        that.optionsMetaShow  = that.treeSelect

      }).catch(res => {
        console.log('err:'+res)
        return this.$message({
          message:res.msg,
          type: 'error'
        });
      })
    },


    // 提交
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          if(this.formValidate.spec_type ===0 ){
            this.formValidate.attrs = this.oneFormValidate;
            this.formValidate.header = [];
            this.formValidate.items = [];
          }else{
            this.formValidate.items = this.attrs;
            this.formValidate.attrs = this.manyFormValidate;
          }
          if(this.formValidate.spec_type === 1 && this.manyFormValidate.length===0){
            return this.$message.warning('请点击生成规格！');
          }
          add(this.formValidate).then(async res => {
            this.$message({
              message:'操作成功',
              type: 'success'
            });
            setTimeout(() => {
              this.$router.push({ path: '/shop/goods' });
            }, 500);
          }).catch(res => {
            // this.$message({
            //   message:res.message,
            //   type: 'error'
            // });
          })
        } else {
          if(!this.formValidate.store_name || !this.formValidate.cate_id || !this.formValidate.keyword
            || !this.formValidate.unit_name || !this.formValidate.store_info
            || !this.formValidate.image || !this.formValidate.slider_image){
            this.$message.warning("请填写完整商品信息！");
          }
        }
      })
    },

    // 表单验证
    validate (prop, status, error) {
      if (status === false) {
        this.$message.warning(error);
      }
    },
    addCustomDialog () {
      window.UE.registerUI('yshop', function (editor, uiName) {
        let dialog = new window.UE.ui.Dialog({
          iframeUrl: '/yshop/materia/index',
          editor: editor,
          name: uiName,
          title: '上传图片',
          cssRules: 'width:1200px;height:500px;padding:20px;'
        });
        this.dialog = dialog;

        var btn = new window.UE.ui.Button({
          name: 'dialog-button',
          title: '上传图片',
          cssRules: `background-image: url(../../../assets/images/icons.png);background-position: -726px -77px;`,
          onclick: function () {
            dialog.render();
            dialog.open();
          }
        });

        return btn;
      }, 37);
    },


    cancel() {
      this.resetForm()
    }
  }
}
</script>

<style scoped lang="stylus">
  .submission
    margin-left 10px;
  .color-list .tip{
    color: #c9c9c9;
  }
  .color-list .color-item{
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    color:#fff;
    margin-right :10px;
  }
  .color-list .color-item.blue{
    background-color: #1E9FFF;
  }
  .color-list .color-item.yellow{
    background-color: rgb(254, 185, 0);
  }
  .color-list .color-item.green{
    background-color: #009688;
  }
  .columnsBox
    margin-right 10px
  .priceBox
    width 100%
  .rulesBox
    display flex
    flex-wrap: wrap;
  .curs
    cursor pointer
</style>
