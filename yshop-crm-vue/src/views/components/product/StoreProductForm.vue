<template>
  <Dialog :title="dialogTitle" append-to-body="true" v-model="dialogVisible" width="60%">
    <el-form
      ref="formRef"
      :model="formValidate"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
      disabled
    >
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="商品规格" name="two">
           <!-- 多规格添加-->
          <el-col :span="24" v-if="formValidate.spec_type === 1" class="noForm">
            <!-- 多规格设置-->
            <el-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24" v-if="manyFormValidate.length">
              <!-- 多规格表格-->
              <el-col :span="24">
                <el-form-item label="商品属性：" class="labeltop">
                  <el-table :data="manyFormValidate" size="small" style="width: 90%;">
                    <el-table-column type="myindex" v-for="(item,index) in formValidate.header" :key="index" :label="item.title" :property="item.slot" align="center">
                      <template #default="scope">
                        <div v-if="item.slot == 'pic'" align="center">
                           <Materials v-model="scope.row[item.slot]" num="1" type="image" :width="60" :height="60" />
                        </div>
                        <div v-else-if="item.slot.indexOf('value') != -1" align="center">
                         {{ scope.row[item.slot] }}
                        </div>
                        <div v-else-if="item.slot == 'action'" align="center" >
                          <a @click="doSelect(scope.row)" style="cursor:pointer;color:#409EFF">选择</a>
                        </div>
                        <div v-else align="center">
                          <el-input  v-model="scope.row[item.slot]" align="center" />
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
            <el-form-item label="默认属性：">
              <el-table :data="oneFormValidate"  size="small" style="width: 90%;">
                <el-table-column prop="pic" label="图片" align="center">
                  <template #default="scope">
                    <Materials v-model="scope.row.pic" num="1" type="image" :width="60" :height="60" />
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="售价" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.price"/>
                  </template>
                </el-table-column>
                <el-table-column prop="cost" label="成本价" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.cost"/>
                  </template>
                </el-table-column>
                <el-table-column prop="ot_price" label="原价" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.ot_price"/>
                  </template>
                </el-table-column>
                <el-table-column prop="stock" label="库存" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.stock" maxlength="7"/>
                  </template>
                </el-table-column>
                <el-table-column prop="bar_code" label="商品编号" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.bar_code"/>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量（KG）" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.weight"/>
                  </template>
                </el-table-column>
                <el-table-column prop="volume" label="体积(m³)" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.volume"/>
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                  <template #default="scope">
                    <a @click="doSelect(scope.row)" style="cursor:pointer;color:#409EFF">选择</a>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
      </el-tab-pane>
    </el-tabs>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false" type="primary">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as StoreProductApi from '@/api/mall/product/product'
import { handleTree3 } from '@/utils/tree'
import type { TabsPaneContext } from 'element-plus'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  image: undefined,
  sliderImage: undefined,
  storeName: undefined,
  storeInfo: undefined,
  keyword: undefined,
  barCode: undefined,
  cateId: undefined,
  price: undefined,
  vipPrice: undefined,
  otPrice: undefined,
  postage: undefined,
  unitName: undefined,
  sort: undefined,
  sales: undefined,
  stock: undefined,
  isShow: 1,
  isHot: undefined,
  isBenefit: undefined,
  isBest: undefined,
  isNew: 0,
  description: undefined,
  isPostage: undefined,
  merUse: undefined,
  giveIntegral: undefined,
  cost: undefined,
  isSeckill: undefined,
  isBargain: undefined,
  isGood: undefined,
  ficti: undefined,
  browse: undefined,
  codePath: undefined,
  isSub: undefined,
  tempId: undefined,
  specType: 0,
  isIntegral: undefined,
  integral: undefined
})
const formValidate = ref({
    shopId: null,
    imageArr:[],
    sliderImageArr: [],
    store_name: '',
    cate_id: 0,
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
    price: 0,
    otPrice: 0,
    stock: 0,
    is_new: 0,
    postage: 0,
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
})

const manyFormValidate = ref([])
const oneFormValidate = ref([
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
])

const formRules = reactive({
  // shopId: [{ required: true, message: '请选择店铺', trigger: 'blur' }],
  image: [{ required: true, message: '商品图片不能为空', trigger: 'blur' }],
  slider_image: [{ required: true, message: '轮播图不能为空', trigger: 'blur' }],
  store_name: [{ required: true, message: '商品名称不能为空', trigger: 'blur' }],
  cate_id: [{ required: true, message: '分类id不能为空', trigger: 'blur' }],
  price: [{ required: true, message: '商品价格不能为空', trigger: 'blur' }]
})
const ruleList = ref([])
const attrs = ref([])
// 规格数据
const formDynamic = reactive({
  attrsName: '',
  attrsVal: ''
})
const createBnt = ref(false)
const showIput = ref(false)
// 批量设置表格data
const oneFormBatch = ref([
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
])


const formRef = ref() // 表单 Ref
const categoryTree = ref<any[]>([]) // 分类树

const activeName = ref('two')


/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '商品规格'
  formType.value = type
  activeName.value = 'two'
  
  resetForm()

  // 修改时，设置数据
  if (id) {
    formLoading.value = false
  } else {
    id = 0
  }
  getInfo(id)

 
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const doSelect = (row) => {
  emit('success', row);
  dialogVisible.value = false
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    image: undefined,
    sliderImage: undefined,
    storeName: undefined,
    storeInfo: undefined,
    keyword: undefined,
    barCode: undefined,
    cateId: undefined,
    price: undefined,
    vipPrice: undefined,
    otPrice: undefined,
    postage: undefined,
    unitName: undefined,
    sort: undefined,
    sales: undefined,
    stock: undefined,
    isShow: undefined,
    isHot: undefined,
    isBenefit: undefined,
    isBest: undefined,
    isNew: undefined,
    description: undefined,
    isPostage: undefined,
    merUse: undefined,
    giveIntegral: undefined,
    cost: undefined,
    isSeckill: undefined,
    isBargain: undefined,
    isGood: undefined,
    ficti: undefined,
    browse: undefined,
    codePath: undefined,
    isSub: undefined,
    tempId: undefined,
    specType: undefined,
    isIntegral: undefined,
    integral: undefined
  }
  formValidate.value = {
    imageArr: [],
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
    price: 0,
    otPrice: 0,
    stock: 0,
    is_new: 0,
    postage: 0,
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
        integral: 0
      }
    ],
    header: [],
    selectRule: ''
  }
  attrs.value = []

  formRef.value?.resetFields()
}

 // 详情
const  getInfo  = (id) => {
     // let that = this;
      StoreProductApi.getStoreProductInfo(id).then(async res => {
      let data = res.productInfo;
      if(data){
        attrs.value = data.items || [];
        formValidate.value = data;
        formValidate.value.cate_id = Number(data.cate_id);
  
        oneFormValidate.value = [data.attr];
        formValidate.value.header = [];
        generate();
        manyFormValidate.value = data.attrs;
        console.log('spec_type2:',data.attrs)
        console.log('data2:',formValidate.value.spec_type)
        if(data.spec_type === 0){
          manyFormValidate.value = [];
        }else {
          createBnt.value = true;
          oneFormValidate.value = [
            {
              imageArr: [],
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

    }).catch(res => {
      console.log('err:'+res)
    })
}

const route = useRoute()
 // 立即生成
const generate = () => {
  StoreProductApi.isFormatAttr(formValidate.value.id, { attrs: attrs.value }).then(res => {
    manyFormValidate.value = res.value;
    let headerdel = {
      title: '操作',
      slot: 'action',
      fixed: 'right',
      width: 220
    };
    res.header.push(headerdel);
    formValidate.value.header = res.header;
    //this.formValidate.attrs = res.attr;
    let header = res.header;
    header.pop();
    if (!route.params.id && formValidate.value.spec_type === 1) {
      manyFormValidate.value.map((item) => {
        item.pic = formValidate.value.image
      });
      oneFormBatch.value[0].pic = formValidate.value.image;
    }
  }).catch(res => {
   console.log('err:'+res)
  })
}



</script>

<style scoped>
  .input-width {
    width: 40%;
  }

  .mb15 {
    margin-bottom: 15px !important;
}
.mb5 {
    margin-bottom: 5px !important;
}
.mr20 {
    margin-right: 20px !important;
}

.mr5 {
    margin-right: 5px !important;
}
.mr15 {
    margin-right: 15px !important;
}
.ml95 {
    margin-left: 95px !important;
}
.mt10{
    margin-top: 10px;
}

.acea-row {
    display: -webkit-box;
    display: -moz-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-lines: multiple;
    -moz-box-lines: multiple;
    -o-box-lines: multiple;
    -webkit-flex-wrap: wrap;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    /* 辅助类 */
}
.acea-row.row-middle {
    -webkit-box-align: center;
    -moz-box-align: center;
    -o-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
}

</style>
