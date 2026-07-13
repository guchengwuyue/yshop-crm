<template>
  <el-form
    ref="formRef"
    :model="formData"
    :rules="formRules"
    v-loading="formLoading"
    label-width="0px"
    :inline-message="true"
    :disabled="props.isCheck"
  >
    <el-table :data="formData" class="-mt-10px">
      <el-table-column label="商品录入" min-width="300">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.name`" :rules="formRules.productId" class="mb-0px!">
            <el-input v-model="row.name" placeholder="请输入商品">
              <template #append><span  @click="chooseProduct($index)" style="cursor:pointer;">选择商品</span></template>
            </el-input>
            <el-input v-model="row.productId" type="hidden" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="商品属性" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.productAttrUnique`" :rules="formRules.productAttrUnique" class="mb-0px!">
            <el-input v-model="row.sku" placeholder="请输入商品属性" />
            <el-input v-model="row.productAttrUnique" type="hidden" placeholder="请输入商品属性" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="单价" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.price`" :rules="formRules.price" class="mb-0px!">
            <el-input v-model="row.price" type="number" @input="changeUnitPrice(row)" placeholder="请输入产品单价" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="数量" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.nums`" :rules="formRules.nums" class="mb-0px!">
            <el-input v-model="row.nums" type="number" @input="changeNumber(row)" placeholder="请输入数量" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="小计" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.subtotal`" :rules="formRules.subtotal" class="mb-0px!">
            <el-input v-model="row.subtotal" readonly placeholder="请输入小计（折扣后价格）" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.remarks`" :rules="formRules.remarks" class="mb-0px!">
            <el-input v-model="row.remarks" placeholder="请输入备注" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="60">
        <template #default="{ $index }">
          <el-button @click="handleDelete($index)" link>—</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-form>
  <el-row justify="center" class="mt-3">
    <el-button @click="handleAdd" round>+ 添加合同产品关系</el-button>
  </el-row>
  <select-product ref="selectProductRef" @save-product="saveProduct"/>
</template>
<script setup lang="ts">
import { ContractApi } from '@/api/crm/crmcontract'
import { BusinessApi } from '@/api/crm/crmbusiness'
import SelectProduct from "@/views/components/product/SelectProduct.vue";

const props = defineProps<{
  contractId: undefined,// 合同ID（主表的关联字段）
  businessId: undefined,
  isCheck: undefined 
}>()
const formLoading = ref(false) // 表单的加载中
const formData = ref([])
const formRules = reactive({
  productId: [{ required: true, message: '请选择商品', trigger: 'blur' }],
  productAttrUnique: [{ required: true, message: '商品属性不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.contractId,
  async (val) => {
    // 1. 重置表单
    formData.value = []
    // 2. val 非空，则加载数据
    if (!val) {
      return;
    }
    try {
      formLoading.value = true
      formData.value = await ContractApi.getContractProductListByContractId(val)
    } finally {
      formLoading.value = false
    }
  },
  { immediate: true }
)

watch(
  () => props.businessId,
  async (val) => {
    // 1. 重置表单
    formData.value = []
    // 2. val 非空，则加载数据
    if (!val) {
      return;
    }
    try {
      formLoading.value = true
      formData.value = await BusinessApi.getBusinessProductListByBusinessId(val)
      formData.value.map((value) => {
        value.id = undefined
      })
      calMoney()
    } finally {
      formLoading.value = false
    }
  },
  { immediate: true }
)

const selectProductRef = ref()
const currentIndex = ref(0)
// 选择商品
const chooseProduct = (index) => {
  currentIndex.value = index
  selectProductRef.value.open()
}

const saveProduct = (good) => {
  console.log('goods:',good)
  formData.value[currentIndex.value].name = good.storeName
  formData.value[currentIndex.value].productId = good.id
  formData.value[currentIndex.value].sku = good.sku
  formData.value[currentIndex.value].productAttrUnique = good.skuNo
  formData.value[currentIndex.value].price = good.price
  formData.value[currentIndex.value].subtotal = good.price

  calMoney()
}

const emit = defineEmits(['getNewData'])
const calMoney = () => {
  var money =  0;
  formData.value.map((value) => {
     if(value.subtotal) {
      money = money + value.subtotal
     }
    
  })
  console.log('money:',money)
  emit('getNewData', money);
}

const changeNumber = (row) => {
  row.subtotal = row.nums*row.price
  calMoney()
}

const changeUnitPrice = (row) => {
  row.subtotal = row.nums*row.price

  calMoney()
}

/** 新增按钮操作 */
const handleAdd = () => {
  const row = {
    id: undefined,
    contractId: undefined,
    productId: undefined,
    name: undefined,
    productAttrUnique: undefined,
    price: 0,
    salesPrice: undefined,
    nums: 1,
    discount: undefined,
    subtotal: 0,
    remarks: undefined
  }
  row.contractId = props.contractId
  formData.value.push(row)
}

/** 删除按钮操作 */
const handleDelete = (index) => {
  formData.value.splice(index, 1)
}

/** 表单校验 */
const validate = () => {
  return formRef.value.validate()
}

/** 表单值 */
const getData = () => {
  return formData.value
}

defineExpose({ validate, getData })
</script>