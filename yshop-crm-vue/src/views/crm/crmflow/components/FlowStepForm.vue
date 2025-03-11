<template>
  <el-form
    ref="formRef"
    :model="formData"
    :rules="formRules"
    v-loading="formLoading"
    label-width="0px"
    :inline-message="true"
  >
    <el-table :data="formData" class="-mt-10px">
      <el-table-column label="序号" type="index" width="100" />
       <el-table-column label="类型" min-width="150">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.type`" :rules="formRules.type" class="mb-0px!">
            <el-select v-model="row.type" placeholder="请选择类型" @change="selectType(row)">
                <el-option label="指定用户" :value="2" />
                <el-option label="指定部门" :value="3" />
            </el-select>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="选择对象" min-width="200">
        <template #default="{ row, $index }">
          <el-form-item :prop="`${$index}.adminIds`" :rules="formRules.adminIds" class="mb-0px!">
            <el-select v-model="row.adminIds" clearable placeholder="选择用户" v-if="row.type == 2">
              <el-option
                v-for="item in userList"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              />
            </el-select>
            <el-tree-select
             v-else-if="row.type == 3"
              v-model="row.adminIds"
              :data="deptList"
              :props="defaultProps"
              check-strictly
              node-key="id"
              placeholder="请选择归属部门"
            />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="60">
        <template #default="{ row,$index }">
          <el-button @click="handleDelete($index,row.id)" link>—</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-form>
  <el-row justify="center" class="mt-3">
    <el-button @click="handleAdd" round>+ 添加审批步骤</el-button>
  </el-row>
</template>
<script setup lang="ts">
import { FlowApi } from '@/api/crm/crmflow'
import * as UserApi from '@/api/system/user'
import * as DeptApi from '@/api/system/dept'
import { defaultProps, handleTree } from '@/utils/tree'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const props = defineProps<{
  flowId: undefined // 审批流程ID（主表的关联字段）
}>()
const formLoading = ref(false) // 表单的加载中
const formData = ref([])
const formRules = reactive({
  flowId: [{ required: true, message: '审批流程ID不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '类型不能为空', trigger: 'change' }],
  adminIds: [{ required: true, message: '请选择对象', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref<UserApi.UserVO[]>([]) // 用户列表
const deptList = ref<Tree[]>([]) // 树形结构

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.flowId,
  async (val) => {
    // 1. 重置表单
    formData.value = []
    // 2. val 非空，则加载数据
    if (!val) {
      return;
    }
    try {
      formLoading.value = true
      formData.value = await FlowApi.getFlowStepListByFlowId(val)
      userList.value = await UserApi.getSimpleUserList()
      deptList.value = handleTree(await DeptApi.getSimpleDeptList())
    } finally {
      formLoading.value = false
    }
  },
  { immediate: true }
)

/** 新增按钮操作 */
const handleAdd = () => {
  const row = {
    id: undefined,
    flowId: undefined,
    type: undefined,
    adminIds: undefined,
    relation: undefined
  }
  row.flowId = props.flowId
  formData.value.push(row)
}

/** 删除按钮操作 */
const handleDelete = async(index,id) => {
  console.log(index,id)
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await FlowApi.deleteFlowStep(id)
    message.success(t('common.delSuccess'))
    formData.value.splice(index, 1)
  } catch(e) {
    console.log('e:',e)
  }

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

const selectType = async(val) => {
  val.adminIds = undefined
  console.log('val:',val)
  if(val.type == 2){
    userList.value = await UserApi.getSimpleUserList()
  }else if(val.type == 3){
    deptList.value = handleTree(await DeptApi.getSimpleDeptList())
  }
}

</script>