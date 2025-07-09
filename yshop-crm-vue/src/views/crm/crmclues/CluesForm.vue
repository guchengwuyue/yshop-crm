<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="70%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
    <el-row>
      <el-col :span="12">
        <el-form-item label="线索名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入线索" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="客户手机" prop="mobile">
          <el-input v-model="formData.mobile" placeholder="请输入手机" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户电话" prop="telephone">
          <el-input v-model="formData.telephone" placeholder="请输入电话" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="客户级别" prop="level">
           <el-select v-model="formData.level" clearable placeholder="请选择客户级别">
              <el-option
                  v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_LEVEL)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
            </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户行业" prop="industry">
          <el-select v-model="formData.industry" clearable placeholder="请选择客户行业">
              <el-option
                  v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_INDUSTRY)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
            </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="客户来源" prop="source">
          <el-select v-model="formData.source" clearable placeholder="请选择客户行业">
              <el-option
                  v-for="dict in getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_SOURCE)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
          </el-select>
        </el-form-item> 
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="地址" prop="city"  @change="handleChange">
            <el-cascader
              v-model="cityArr"
              :options="areaList"
              :props="props"
              @change="handleChange"
            />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="详细地址" prop="detailAddress">
          <el-input v-model="formData.detailAddress" placeholder="请输入详细地址" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="下次跟进时间" prop="nextTime">
          <el-date-picker
            v-model="formData.nextTime"
            type="datetime"
            value-format="x"
            placeholder="选择下次联系时间"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="跟进状态" prop="followStatus">
          <el-select v-model="formData.followStatus" clearable placeholder="请选择跟进状态业">
              <el-option
                  v-for="dict in getIntDictOptions(DICT_TYPE.CRM_FOLLOW_STATUS)"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="客户标签" prop="tags">
          <el-input v-model="tagsStr"  @keydown.space="handleInput" placeholder="标签请按空格键" />
          <div style="margin-top:2px" class="gap-2">
            <el-tag v-for="(tag,idx) in tagsArr" :key="idx" closable type="danger"  @close="handleClose(idx)">
              {{ tag }}
            </el-tag>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="备注" prop="remark">
          <el-input  type="textarea" :rows="2" v-model="formData.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-col>
    </el-row>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { CluesApi, CluesVO } from '@/api/crm/crmclues'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as AreaApi from '@/api/system/area'

/** 线索 表单 */
defineOptions({ name: 'CluesForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  mobile: undefined,
  telephone: undefined,
  status: undefined,
  customerId: undefined,
  changeTime: undefined,
  level: undefined,
  industry: undefined,
  tags: undefined,
  source: undefined,
  remark: undefined,
  ownerUserId: undefined,
  province: undefined,
  city: undefined,
  area: undefined,
  detailAddress: undefined,
  nextTime: undefined,
  followTime: undefined,
  collectTime: undefined,
  followStatus: undefined
})
const formRules = reactive({
  name: [{ required: true, message: '线索不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const areaList = ref([])
const tagsStr = ref('')
const tagsArr = ref([])
const cityArr = ref([])

const props = {
  value: 'id',
  label: 'name'
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  areaList.value = await AreaApi.getAreaTree()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await CluesApi.getClues(id)
      tagsArr.value = formData.value.tags.split(',')
      cityArr.value = []
      cityArr.value.push(formData.value.province)
      cityArr.value.push(formData.value.city)
      cityArr.value.push(formData.value.area)
    } finally {
      formLoading.value = false
    }
  }
  
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as CluesVO
    if (formType.value === 'create') {
      await CluesApi.createClues(data)
      message.success(t('common.createSuccess'))
    } else {
      await CluesApi.updateClues(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const handleChange = (value) => {
  if(value){
    formData.value.province = value[0]
    formData.value.city = value[1]
    formData.value.area = value[2]
  }
}

const handleInput = (event) => {
  tagsArr.value = tagsStr.value.split(' ')
  formData.value.tags = tagsArr.value.toString()
}

const handleClose = (index) => {
  tagsArr.value.splice(index, 1);
  formData.value.tags = tagsArr.value.toString()
  tagsStr.value = tagsArr.value.join(' ')
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    mobile: undefined,
    telephone: undefined,
    status: undefined,
    customerId: undefined,
    changeTime: undefined,
    level: 3,
    industry: 9,
    tags: undefined,
    source: 0,
    remark: undefined,
    ownerUserId: undefined,
    province: undefined,
    city: undefined,
    area: undefined,
    detailAddress: undefined,
    nextTime: Date.parse(new Date()),
    followTime: undefined,
    collectTime: undefined,
    followStatus: undefined
  }
  formRef.value?.resetFields()
}
</script>