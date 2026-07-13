<template>
  <Dialog :title="dialogTitle" append-to-body="true" v-model="dialogVisible" width="70%">
    <el-form
      ref="formRef"
      :model="formValidate"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="基本信息" name="one">
        <el-form-item label="商品名称" prop="store_name">
          <el-input v-model="formValidate.store_name" class="input-width" placeholder="请输入商品名称" />
        </el-form-item>
         <el-form-item label="商品分类" prop="cate_id">
          <!-- <el-select
            v-model="formValidate.cate_id"
            placeholder="选择商品分类"
          >
            <el-option
              v-for="item in categoryTree"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select> -->
          <el-tree-select
            v-model="formValidate.cate_id"
            :data="categoryTree"
            :props="{ label: 'name', value: 'id' }"
            :render-after-expand="false"
            placeholder="请选择商品分类"
            check-strictly
            default-expand-all
          />
        </el-form-item>
         <el-form-item label="关键字" prop="keyword">
          <el-input v-model="formValidate.keyword" class="input-width" placeholder="请输入关键字" />
        </el-form-item>
         <el-form-item label="单位名" prop="unit_name">
          <el-input v-model="formValidate.unit_name" class="input-width" placeholder="请输入单位名" />
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="formValidate.price" class="input-width" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="市场价" prop="otPrice">
          <el-input v-model="formValidate.otPrice" class="input-width" placeholder="请输入市场价" />
        </el-form-item>
         <el-form-item label="库存" prop="stock">
          <el-input v-model="formValidate.stock" class="input-width" placeholder="请输入库存" />
        </el-form-item>
        <el-form-item label="封面图" prop="image">
          <Materials v-model="formValidate.image" num="1" type="image" />
        </el-form-item>
       <el-form-item label="轮播图" prop="slider_image">
        <Materials v-model="formValidate.slider_image" num="5" type="image" />
        </el-form-item>
        <el-form-item label="商品状态" prop="is_show">
          <el-radio-group v-model="formValidate.is_show">
            <el-radio :label="1">上架</el-radio>
            <el-radio :label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="商品简介" prop="store_info">
          <el-input type="textarea" rows="5"  v-model="formValidate.store_info" placeholder="请输入商品简介" />
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane label="商品规格" name="two">
        <el-form-item label="商品规格" prop="spec_type">
          <el-radio-group v-model="formValidate.spec_type" @change="changeSpec">
            <el-radio :label="0">单规格</el-radio>
            <el-radio :label="1">多规格</el-radio>
          </el-radio-group>
        </el-form-item>
           <!-- 多规格添加-->
          <el-col :span="24" v-if="formValidate.spec_type === 1" class="noForm">
            <el-col :span="24">
              <el-form-item label="选择规格：" prop="">
                <div  class="acea-row row-middle">
                  <el-select v-model="formValidate.selectRule" >
                    <el-option v-for="(item, index) in ruleList" :value="item.ruleName" :key="index">{{ item.ruleName }}</el-option>
                  </el-select>
                  <el-button type="primary" class="mr20" @click="confirm">确认</el-button>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item v-if="attrs.length!==0">
                <div  v-for="(item, index) in attrs" :key="index" style="width:100%;">
                  <div class="acea-row row-middle"><span class="mr5">{{item.value}}</span>
                    <Icon icon="ep:circle-close" @click="handleRemoveRole(index)" />
                  </div>
                  <div class="rulesBox">
                    <el-tag type="dot" closable color="primary" v-for="(j, indexn) in item.detail" :key="indexn" :name="j" class="mr20" @close="handleRemove2(item.detail,indexn)">{{j}}</el-tag>
                    <el-input placeholder="请输入属性名称" v-model="item.detail.attrsVal"
                              style="width: 150px">
                      <template #append>
                        <el-button  type="primary" @click="createAttr(item.detail.attrsVal,index)">添加</el-button>
                      </template>
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

                  <el-button type="primary" @click="createAttrName">确定</el-button>
                  <el-button type="danger" @click="offAttrName" >取消</el-button>

              </el-col>
            </el-col>
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
                          <!-- <single-pic v-model="scope.row[scope.column.property]" type="image" :num="1" :width="60" :height="60" /> -->
                        </div>
                        <div v-else-if="item.slot.indexOf('value') != -1" align="center">
                         {{ scope.row[item.slot] }}
                        </div>
                        <div v-else-if="item.slot == 'action'" align="center" >
                          <a @click="delAttrTable(scope.$index)" align="center">删除</a>
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
            <el-form-item >
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
                <!-- <el-table-column prop="volume" label="所需兑换积分" align="center">
                  <template #default="scope">
                    <el-input type="text" v-model="scope.row.integral"/>
                  </template>
                </el-table-column> -->
              </el-table>
            </el-form-item>
          </el-col>
      </el-tab-pane>
      <el-tab-pane label="商品详情" name="three"> 
        <el-form-item label="产品描述">
           <vue-ueditor-wrap v-model="formValidate.description" :config="myConfig" @before-init="addCustomDialog"    style="width: 90%;" />
        </el-form-item>
      </el-tab-pane>
      <!-- <el-tab-pane label="营销设置" name="four">
        <el-form-item label="获得积分" prop="give_integral">
          <el-input v-model="formValidate.give_integral" class="input-width" placeholder="请输入获得积分" />
        </el-form-item>
      </el-tab-pane> -->
    </el-tabs>
    </el-form>
    <template #footer>
      <el-button v-if="activeName !== 'one'" @click="upTab">上一步</el-button>
      <el-button type="primary"  v-if="activeName !== 'three'" @click="downTab">下一步</el-button>
      <el-button v-if="activeName == 'three'" @click="submitForm" type="primary" :disabled="formLoading">保 存</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as StoreProductApi from '@/api/mall/product/product'
import * as ProductCategoryApi from '@/api/mall/product/category'
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
const postageSet = ref(false) //false -固定  true 运费模板
const templateList = ref([])
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

const myConfig = reactive( {
      autoHeightEnabled: false, // 编辑器不自动被内容撑高
      initialFrameHeight: 500, // 初始容器高度
      initialFrameWidth: '100%', // 初始容器宽度
      UEDITOR_HOME_URL: '/UEditor/',
      serverUrl: '',
      zIndex: 9999,
      toolbars: [
        [
          'undo',
          'redo',
          '|',
          'bold',
          'italic',
          'underline',
          'fontborder',
          'strikethrough',
          'superscript',
          'subscript',
          'removeformat',
          'formatmatch',
          'autotypeset',
          'blockquote',
          'pasteplain',
          '|',
          'forecolor',
          'backcolor',
          'insertorderedlist',
          'insertunorderedlist',
          'selectall',
          'cleardoc',
          '|',
          'rowspacingtop',
          'rowspacingbottom',
          'lineheight',
          '|',
          'customstyle',
          'paragraph',
          'fontfamily',
          'fontsize',
          '|',
          'directionalityltr',
          'directionalityrtl',
          'indent',
          '|',
          'justifyleft',
          'justifycenter',
          'justifyright',
          'justifyjustify',
          '|',
          'touppercase',
          'tolowercase',
          '|',
          'imagenone',
          'imageleft',
          'imageright',
          'imagecenter',
        ],
      ],
})
const activeName = ref('one')
const shopList = ref([])

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  activeName.value = 'one'
  
  resetForm()

  // 修改时，设置数据
  if (id) {
    formLoading.value = false
  } else {
    id = 0
  }
  getInfo(id)
   // 获得分类树
  getTree()
 
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗


const selectShop = (val) => {
  getTree(val)
}

// const selectCate = () => {
//   if(!formValidate.value.shopId){
//     return message.warning('请先选择店铺')
//   }
// }



/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return 
 // const valid = await formRef.value.validate()
  formRef.value.validate((valid, fields) => {
  if (valid) {
    console.log(fields)
  } else {
    return message.warning('请添加基本信息')
    // console.log('error submit!', fields)
  }
  })


  // 提交请求
  formLoading.value = true
  try {
     if(formValidate.value.spec_type ===0 ){
        formValidate.value.attrs = oneFormValidate.value;
        formValidate.value.header = [];
        formValidate.value.items = [];
      }else{
        formValidate.value.items = attrs.value;
        formValidate.value.attrs = manyFormValidate.value;
      }
      if(formValidate.value.spec_type === 1 && manyFormValidate.value.length===0){
        message.warning('请点击生成规格！');
      }
      await StoreProductApi.createStoreProduct(formValidate.value)
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}


const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab.paneName, event)
}
const upTab = () => {
  if (activeName.value == 'two') {
    activeName.value = 'one'
    return
  }
  if (activeName.value == 'three') {
    activeName.value = 'two'
    return
  }
  if (activeName.value == 'four') {
    activeName.value = 'three'
    return
  }
  // if (activeName.value == 'five') {
  //   activeName.value = 'four'
  //   return
  // }

}
const downTab = () => {
if (activeName.value == 'one') {
    activeName.value = 'two'
    return
  }
  if (activeName.value == 'two') {
    activeName.value = 'three'
    return
  }
  if (activeName.value == 'three') {
    activeName.value = 'four'
    return
  }
  // if (activeName.value == 'four') {
  //   activeName.value = 'five'
  //   return
  // }
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
        console.log('data:', data)
      postageSet.value = false
      if(data){
       // let cate_id = parseInt(data.cate_id) || 0;
        if(data.temp_id > 0) postageSet.value = true
        attrs.value = data.items || [];
        formValidate.value = data;
        formValidate.value.cate_id = Number(data.cate_id);
        if(formValidate.value.shopId){
           console.log('shopId:',formValidate.value.shopId)
            await getTree(formValidate.value.shopId)
          }
        oneFormValidate.value = [data.attr];
        formValidate.value.header = [];
        generate();
        manyFormValidate.value = data.attrs;
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

           console.log('spec_type2:',formValidate.value.spec_type)
        }
      }

      ruleList.value = res.ruleList;
      templateList.value = res.tempList;

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

/** 获得分类树 */
const getTree = async (val) => {
  const data =  await ProductCategoryApi.getCategoryList({shopId: val})
 const tree = handleTree3(data, 'id', 'parentId')
 // console.log('tree:',tree)
  // const menu = { id: 0, name: '顶级分类', children: tree }
  categoryTree.value = data
}

const addCustomDialog  = () => {
      window.UE.registerUI('yshop', function (editor, uiName) {
        let dialog = new window.UE.ui.Dialog({
          iframeUrl: '/yshop/materia/index',
          editor: editor,
          name: uiName,
          title: '上传图片',
          cssRules: 'width:1200px;height:650px;padding:20px;'
        });
        this.dialog = dialog;

        var btn = new window.UE.ui.Button({
          name: 'dialog-button',
          title: '上传图片',
          cssRules: `background-image: url(../../../assets/imgs/icons.png);background-position: -726px -77px;`,
          onclick: function () {
            dialog.render();
            dialog.open();
          }
        });

        return btn;
      }, 37);
}

// 改变规格
const changeSpec = () => {
}

const confirm = () => {
  createBnt.value = true;
  if (formValidate.value.selectRule.trim().length <= 0) {
    message.error('请选择属性')
  }
  ruleList.value.forEach(function (item, index) {
     console.log(index)
    if (item.ruleName === formValidate.value.selectRule) {
      attrs.value = item.ruleValue;
    }
  })
}

// 删除规格
const handleRemoveRole = (index) => {
  attrs.value.splice(index, 1);
  manyFormValidate.value.splice(index, 1);
}

// 删除属性
const handleRemove2 = (item, index) => {
  item.splice(index, 1);
}

// 添加规则名称
const createAttrName = () =>{
  if (formDynamic.attrsName && formDynamic.attrsVal) {
    let data = {
      value: formDynamic.attrsName,
      detail: [
        formDynamic.attrsVal
      ]
    };
    attrs.value.push(data);
    var hash = {};
    attrs.value = attrs.value.reduce(function (item, next) {
      hash[next.value] ? '' : hash[next.value] = true && item.push(next);
      return item
    }, [])
    clearAttr();
    showIput.value = false;
    createBnt.value = true;
  } else {
     message.warning('请添加完整的规格！')
  }
}
// 添加属性
const createAttr = (num, idx) => {
  if (num) {
    attrs.value[idx].detail.push(num);
    var hash = {};
    attrs.value[idx].detail = attrs.value[idx].detail.reduce(function (item, next) {
      hash[next] ? '' : hash[next] = true && item.push(next);
      return item
    }, [])
  } else {
    message.warning('请添加属性！')
  }
}
// 取消
const offAttrName = () => {
  showIput.value = false
  createBnt.value = true
}
// 删除表格中的属性
const delAttrTable  = (index) => {
  manyFormValidate.value.splice(index, 1)
}
// 添加按钮
const addBtn =  () => {
  clearAttr()
  createBnt.value = false
  showIput.value = true
}

const clearAttr = () => {
  formDynamic.attrsName = ''
  formDynamic.attrsVal = ''
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
