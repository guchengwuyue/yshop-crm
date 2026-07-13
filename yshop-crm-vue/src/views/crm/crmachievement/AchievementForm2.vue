<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="选择员工" prop="deptId">
        <!-- <el-tree-select
          v-model="formData.deptId"
          :data="deptList"
          :props="defaultProps"
          check-strictly
          node-key="id"
          placeholder="请选择归属部门,不选择表示全部"
        /> -->
        <el-select v-model="formData.deptId" clearable placeholder="请选择员工,不选择表示全部">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.nickname"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业绩方式" prop="config">
        <el-select
          v-model="formData.config"
          placeholder="请选择"
        >
          <el-option label="合同金额" :value="1" />
          <el-option label="回款金额" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="年份" prop="year">
        <el-date-picker
          v-model="formData.year"
          type="year"
          value-format="YYYY"
        />
      </el-form-item>
      <el-form-item label="年目标" prop="yeartarget">
        <el-input v-model="formData.yeartarget" placeholder="请输入年目标">
          <template #append><el-button type="primary" @click="averrage()">平均分配</el-button></template>
        </el-input>
      </el-form-item>
      <el-form-item label="一月" prop="january">
        <el-input-number style="width:100%"  controls-position="right" v-model="formData.january" placeholder="请输入一月" />
      </el-form-item>
      <el-form-item label="二月" prop="february">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.february" placeholder="请输入二月" />
      </el-form-item>
      <el-form-item label="三月" prop="march">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.march" placeholder="请输入三月" />
      </el-form-item>
      <el-form-item label="四月" prop="april">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.april" placeholder="请输入四月" />
      </el-form-item>
      <el-form-item label="五月" prop="may">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.may" placeholder="请输入五月" />
      </el-form-item>
      <el-form-item label="六月" prop="june">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.june" placeholder="请输入六月" />
      </el-form-item>
      <el-form-item label="七月" prop="july">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.july" placeholder="请输入七月" />
      </el-form-item>
      <el-form-item label="八月" prop="august">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.august" placeholder="请输入八月" />
      </el-form-item>
      <el-form-item label="九月" prop="september">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.september" placeholder="请输入九月" />
      </el-form-item>
      <el-form-item label="十月" prop="october">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.october" placeholder="请输入十月" />
      </el-form-item>
      <el-form-item label="十一月" prop="november">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.november" placeholder="请输入十一月" />
      </el-form-item>
      <el-form-item label="十二月" prop="december">
        <el-input-number  style="width:100%" controls-position="right" v-model="formData.december" placeholder="请输入十二月" />
      </el-form-item>
 
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { AchievementApi, AchievementVO } from '@/api/crm/crmachievement'
import { formatDate } from '@/utils/formatTime'
import * as DeptApi from '@/api/system/dept'
import * as UserApi from '@/api/system/user'
import { defaultProps, handleTree } from '@/utils/tree'

/** 业绩目标 表单 */
defineOptions({ name: 'AchievementForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  config: undefined,
  type: 3,
  typeId: undefined,
  year: undefined,
  january: undefined,
  february: undefined,
  march: undefined,
  april: undefined,
  may: undefined,
  june: undefined,
  july: undefined,
  august: undefined,
  september: undefined,
  october: undefined,
  november: undefined,
  december: undefined,
  yeartarget: undefined
})
const formRules = reactive({
  
})
const userList = ref([])
const formRef = ref() // 表单 Ref


/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '成员业绩批量设置'
  formType.value = type
  resetForm()
  userList.value = await UserApi.getSimpleUserList()
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
    //const achievementRespVO = formData.value
    const data = {id:formData.value.deptId ? formData.value.deptId : 0,name:'',achievementRespVO:formData.value}
    console.log('data:',data)
    if (formType.value === 'create') {
      await AchievementApi.createAchievement(data)
      message.success(t('common.createSuccess'))
    } 

    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const averrage = () => {
  let res = formData.value.yeartarget / 12
  let num = Math.round(res*100) / 100
  formData.value.january = num
  formData.value.february = num
  formData.value.march = num
  formData.value.april = num
  formData.value.may = num
  formData.value.june = num
  formData.value.july = num
  formData.value.august = num
  formData.value.september = num
  formData.value.october = num
  formData.value.november = num
  formData.value.december = num
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    config: 1,
    type: 2,
    typeId: undefined,
    year: formatDate(new Date(),'YYYY'),
    january: 0,
    february: 0,
    march: 0,
    april: 0,
    may: 0,
    june: 0,
    july: 0,
    august: 0,
    september: 0,
    october: 0,
    november: 0,
    december: 0,
    yeartarget: 0
  }
  formRef.value?.resetFields()
}
</script>