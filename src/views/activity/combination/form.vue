<template>
  <div class="app-container">
    <el-card :bordered="false">
      <el-form ref="formValidate" :rules="ruleValidate" :model="formValidate" label-width="130px">
        <el-row :gutter="24">
          <!-- 商品信息-->
          <el-col :span="24">
            <el-form-item label="选择商品：" prop="good">
              <cgood v-model="form1.good"></cgood>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团名称" prop="title">
              <el-input v-model="formValidate.title" @input="onInput()" style="width: 500px;" placeholder="请输入拼团名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团简介" prop="info">
              <el-input v-model="formValidate.info" @input="onInput()" style="width: 500px;" placeholder="请输入拼团简介"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="formValidate.unitName" @input="onInput()" style="width: 500px;" placeholder="请输入单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商品原价">
<!--              oneFormValidate[0].cost-->
              <el-input type="text" style="width: 500px;" disabled v-model="formValidate.productPrice" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团开始时间" prop="startTime">
              <template>
                <el-date-picker
                    v-model="formValidate.startTime"
                    type="datetime"
                    placeholder="选择日期时间"
                />
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团结束时间" prop="stopTime">
              <template>
                <el-date-picker
                    v-model="formValidate.stopTime"
                    type="datetime"
                    placeholder="选择日期时间"
                />
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="活动状态" prop="isShow">
              <el-radio v-model="formValidate.isShow" :label="1">开启</el-radio>
              <el-radio v-model="formValidate.isShow" :label="0" style="width: 200px;">关闭</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产品主图片" prop="image">
              <single-pic v-model="formValidate.image" style="width: 500px" type="image" :num="1" :width="150"
                          :height="150"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产品轮播图" prop="slider_image">
              <MaterialList v-model="formValidate.slider_image" style="width: 500px" type="image" :num="4" :width="150"
                            :height="150"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团时效(小时)" prop="effectiveTime">
              <el-input-number v-model="formValidate.effectiveTime" style="width: 500px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼团人数" prop="people">
              <el-input-number :min="1" :max="99" v-model="formValidate.people"/>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="商品规格：" props="spec_type">
              <el-radio-group v-model="formValidate.spec_type" @change="changeSpec" :disabled="true">
                <el-radio :label="0" class="radio">单规格</el-radio>
                <el-radio :label="1">多规格{{ formValidate.spec_typ }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 多规格设置-->
          <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24"
                  v-if="manyFormValidate.length && formValidate.header.length!==0 && attrs.length!==0">
            <!-- 多规格表格-->
            <el-col :span="24">
              <el-form-item label="商品属性：" class="labeltop">
                <el-table :data="manyFormValidate" size="small" style="width: 90%;">
                  <el-table-column type="myindex" v-for="(item,index) in formValidate.header" :key="index"
                                   :label="item.title" :property="item.slot" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.column.property == 'pic'">
                        <single-pic v-model="scope.row[scope.column.property]" type="image" :num="1" :width="60"
                                    :height="60" align="center"/>
                      </div>
                      <div v-else-if="scope.column.property.indexOf('value') != -1">
                        {{ scope.row[scope.column.property] }}
                      </div>
                      <div v-else-if="scope.column.property == 'pink_price'||scope.column.property == 'pink_stock'">
                        <el-input v-model="scope.row[scope.column.property]" align="center"/>
                      </div>
                      <div v-else-if="scope.column.property == 'action'" align="center">
                        <a :disabled="true" align="center">不可删除</a>
                      </div>
                      <div v-else>
                        <el-input v-model="scope.row[scope.column.property]" :disabled="true" align="center"/>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </el-form-item>
            </el-col>
          </el-col>
          <!-- 单规格表格-->
          <el-col :xl="23" :lg="24" :md="24" :sm="24" :xs="24" v-if="formValidate.spec_type === 0">
            <el-form-item>
              <el-table :data="oneFormValidate" size="small" style="width: 90%;">
                <el-table-column prop="pic" label="图片" align="center">
                  <template slot-scope="scope">
                    <single-pic v-model="scope.row.pic" type="image" :num="1" :width="60" :height="60"/>
                  </template>
                </el-table-column>
                <el-table-column prop="bar_code" label="商品编号" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.bar_code" :disabled="true"/>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="售价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.price" :disabled="true"/>
                  </template>
                </el-table-column>
                <el-table-column prop="cost" label="成本价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.cost" :disabled="true"/>
                  </template>
                </el-table-column>
                <el-table-column prop="ot_price" label="原价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.ot_price" :disabled="true"/>
                  </template>
                </el-table-column>
                <el-table-column prop="stock" label="库存" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.stock" maxlength="7" :disabled="true"/>
                  </template>
                </el-table-column>

                <el-table-column prop="stock" label="拼团价" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.pink_price"/>
                  </template>
                </el-table-column>
                <el-table-column prop="stock" label="拼团库存" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.pink_stock" maxlength="7"/>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量（KG）" align="center ">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.weight" :disabled="true"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="体积(m³)" align="center">
                  <template slot-scope="scope">
                    <el-input type="text" v-model="scope.row.volume" :disabled="true"/>
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
                <el-select v-model="formValidate.temp_id" class="mr20" :disabled="true">
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
              <ueditor-wrap v-model="formValidate.description" :config="myConfig" @beforeInit="addCustomDialog"
                            style="width: 90%;"></ueditor-wrap>
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
import cgood from '@/views/components/good'
import {getInfo, isFormatAttrForActivity} from '@/api/yxStoreProduct'
import {add, edit, getCombinationInfo, onsaleCombination, delCombination} from '@/api/yxStoreCombination'
import editor from '../../components/Editor'
import picUpload from '@/components/pic-upload'
import mulpicUpload from '@/components/mul-pic-upload'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import MaterialList from '@/components/material'
import singlePic from '@/components/singlematerial'
import UeditorWrap from 'vue-ueditor-wrap';

export default {
  components: {editor, picUpload, mulpicUpload, Treeselect, MaterialList, UeditorWrap, singlePic, cgood},
  data() {
    return {
      spinShow: false,
      // 批量设置表格data
      oneFormBatch: [
        {
          pic: '',
          price: 0,
          cost: 0,
          ot_price: 0,
          stock: 0,
          bar_code: '',
          pink_stock: 0,
          pink_price: 0,
          weight: 0,
          volume: 0,
          integral: 0
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
      form1: {
        good: {
          productId: null,
          storeName: null,
          image: null,
          otPrice: null,
          price: null,

        }
      },
      formValidate: {
        slider_image: [],
        is_sub: 0,
        id: 0,
        combinationId: 0,
        productId: '',
        merId: '',
        image: '',
        images: '',
        imageArr: [],
        sliderImageArr: [],
        productPrice:0,
        title: '',
        attr: '',
        people: 1,
        info: '',
        price: '',
        sort: 0,
        integral: 0,
        sales: '',
        stock: '',
        addTime: '',
        isHost: '',
        isShow: 1,
        isDel: 0,
        merUse: '',
        isPostage: '',
        postage: '',
        description: '',
        startTime: '',
        stopTime: '',
        effectiveTime: 1,
        cost: '',
        unitName: '',
        combination: 1,
        browse: 0,
        startTimeDate: '',
        endTimeDate: '',
        spec_type: 0,
        temp_id: '',
        attrs: [],
        items: [
          {
            pic: '',
            price: 0,
            pink_stock: 0,
            pink_price: 0,
            cost: 0,
            ot_price: 0,
            stock: 0,
            bar_code: '',
            integral: 0
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
          price: 0,
          cost: 0,
          ot_price: 0,
          stock: 0,
          pink_stock: 0,
          pink_price: 0,
          seckill_stock: 0,
          seckill_price: 0,
          bar_code: '',
          weight: 0,
          volume: 0,
          brokerage: 0,
          brokerage_two: 0,
          integral: 0
        }
      ],
      images: [],
      loading: false,
      treeSelect: [],
      tableIndex: 0,
      ruleValidate: {
        title: [
          {required: true, message: '请输入拼团名称', trigger: 'blur'}
        ],
        info: [
          {required: true, message: '请输入拼团简介', trigger: 'blur'}
        ],
        unitName: [
          {required: true, message: '请输入单位', trigger: 'blur'}
        ],
        startTime: [
          {required: true, message: '请选择开团时间', trigger: 'blur'}
        ],
        stopTime: [
          {required: true, message: '请选择开团结束时间', trigger: 'blur'}
        ],
        isShow: [
          {required: true, message: '请选择活动状态', trigger: 'change'}
        ],
        temp_id: [
          {required: true, message: '请选择运费模板', trigger: 'change', type: 'number'}
        ],
        price: [
          {required: true, message: '请输入拼团价格', trigger: 'blur'}
        ],
        people: [
          {required: true, message: '请选择拼团人数', trigger: 'change', type: 'number'}
        ],
        spec_type: [
          {required: true, message: '请选择商品规格', trigger: 'change'}
        ],
        effectiveTime: [
          {required: true, message: '请选择拼团时效', trigger: 'blur'}
        ]
      },
      attrs: []
    }
  },
  watch: {
    // 'formValidate.imageArr': function(val) {
    //   if (val) {
    //     this.form.image = val.join(',')
    //   }
    // },

    'formValidate.slider_image': function (val) {
      if (val && Array.isArray(val)) {
        this.formValidate.images = val.join(',')
      }
    },
    'form1.good.productId': {
      handler(val, oldVal) {
        console.info("val:" + val)
        console.info("oldval:" + oldVal)
        if (val) {
          this.getInfoChooseGood(val)
        }
      },
    },
  },
  mounted() {
    this.getInfo();
  },
  methods: {
    onInput() {
      this.$forceUpdate();
    },
    confirm() {
      let that = this;
      that.createBnt = true;
      if (that.formValidate.selectRule.trim().length <= 0) {
        return this.$message({
          message: '请选择属性',
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
    delAttrTable(index) {
      this.manyFormValidate.splice(index, 1);
    },

    // 添加按钮
    addBtn() {
      this.clearAttr();
      this.createBnt = false;
      this.showIput = true;
    },
    // 立即生成
    generate(data) {
      isFormatAttrForActivity(data === null ? this.formValidate.productId : data, {attrs: this.attrs}).then(res => {
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
        this.$message.error(res.msg);
      })
    },
    // 取消
    offAttrName() {
      this.showIput = false;
      this.createBnt = true;
    },
    clearAttr() {
      this.formDynamic.attrsName = '';
      this.formDynamic.attrsVal = '';
    },
    // 删除规格
    handleRemoveRole(index) {
      this.attrs.splice(index, 1);
      this.manyFormValidate.splice(index, 1);
    },
    // 删除属性
    handleRemove2(item, index) {
      item.splice(index, 1);
    },
    // 添加规则名称
    createAttrName() {
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
    createAttr(num, idx) {
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
    changeSpec() {
    },

    // 详情选择商品生成规格用
    getInfoChooseGood(id) {
      let that = this;
      let cid = that.$route.params.id || 0;

      getInfo(id == null ? 0 : id).then(async res => {
        let data = res.productInfo;
        if (data) {
          that.attrs = data.items || [];
          //that.formValidate = data;
          Object.keys(that.formValidate).forEach(key => {
            if (data[key]) that.formValidate[key] = data[key];
          })
          that.formValidate.id = cid;
          //that.formValidate.id = 0;
          that.formValidate.productId = id
          that.formValidate.title = data.store_name
          that.formValidate.info = data.store_info
          that.formValidate.unitName = data.unit_name
          that.formValidate.isShow = 1
          // that.formValidate.people = 0
          // that.formValidate.effectiveTime = 0
          that.oneFormValidate = [data.attr];
          that.formValidate.productPrice =  that.oneFormValidate[0].cost
          that.formValidate.header = [];
          that.generate(null);
          that.manyFormValidate = data.attrs;
          if (data.spec_type === 0) {
            that.manyFormValidate = [];
          } else {
            that.createBnt = true;
            that.oneFormValidate = [
              {
                pic: '',
                price: 0,
                seckill_stock: 0,
                seckill_price: 0,
                pink_stock: 0,
                pink_price: 0,
                cost: 0,
                ot_price: 0,
                stock: 0,
                bar_code: '',
                weight: 0,
                volume: 0,
                brokerage: 0,
                brokerage_two: 0
              }
            ]
          }
        }
        that.treeSelect = res.cateList;
        that.ruleList = res.ruleList;
        that.templateList = res.tempList;

      }).catch(res => {
        this.$message.error(res.msg);
      })
    },

    // 详情
    getInfo() {
      let that = this;
      let id = that.$route.params.id || 0;
      that.formValidate.id = id;
      getCombinationInfo(id).then(async res => {
        let data = res.productInfo;
        if (data) {
          let cate_id = parseInt(data.cate_id) || 0;
          this.attrs = data.items || [];
          that.formValidate = data;
          that.formValidate.cate_id = cate_id;
          that.oneFormValidate = [data.attr];
          that.formValidate.productPrice = that.oneFormValidate[0].cost
          that.formValidate.header = [];
          that.generate(data.productId);
          that.manyFormValidate = data.attrs;
          that.form1.good.productId = data.productId;
          that.form1.good.image = data.image
          if (data.spec_type === 0) {
            that.manyFormValidate = [];
          } else {
            that.createBnt = true;
            that.oneFormValidate = [
              {
                pic: '',
                price: 0,
                seckill_stock: 0,
                seckill_price: 0,
                pink_stock: 0,
                pink_price: 0,
                cost: 0,
                ot_price: 0,
                stock: 0,
                bar_code: '',
                weight: 0,
                volume: 0,
                brokerage: 0,
                brokerage_two: 0
              }
            ]
          }
        }
        that.treeSelect = res.cateList;
        that.ruleList = res.ruleList;
        that.templateList = res.tempList;

      }).catch(res => {
        this.$message.error(res.msg);
      })
    },


    // 提交
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          if (this.formValidate.spec_type === 0) {
            this.formValidate.attrs = this.oneFormValidate;
            this.formValidate.header = [];
            this.formValidate.items = [];
          } else {
            this.formValidate.items = this.attrs;
            this.formValidate.attrs = this.manyFormValidate;
          }
          if (this.formValidate.spec_type === 1 && this.manyFormValidate.length === 0) {
            return this.$message.warning('请点击生成规格！');
          }
          add(this.formValidate).then(async res => {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            setTimeout(() => {
              this.$router.push({path: '/activity/combination'});
            }, 500);
          }).catch(res => {
            this.$message.error(res.msg);
          })
        } else {
          if (!this.formValidate.store_name || !this.formValidate.cate_id || !this.formValidate.keyword
              || !this.formValidate.unit_name || !this.formValidate.store_info
              || !this.formValidate.image || !this.formValidate.slider_image) {
            this.$message.warning("请填写完整商品信息！");
          }
        }
      })
    },

    // 表单验证
    validate(prop, status, error) {
      if (status === false) {
        this.$message.warning(error);
      }
    },
    addCustomDialog() {
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

.color-list .tip {
  color: #c9c9c9;
}

.color-list .color-item {
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  color: #fff;
  margin-right: 10px;
}

.color-list .color-item.blue {
  background-color: #1E9FFF;
}

.color-list .color-item.yellow {
  background-color: rgb(254, 185, 0);
}

.color-list .color-item.green {
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
