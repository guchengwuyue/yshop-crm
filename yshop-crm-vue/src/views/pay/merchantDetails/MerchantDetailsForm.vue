<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="150px"
      v-loading="formLoading"
    >
      <el-form-item label="支付类型(支付渠道)" prop="payType">
        <el-select v-model="formData.payType">
          <el-option label="支付宝支付" value="aliPay" />
          <el-option label="微信支付" value="wxPay" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付id" prop="detailsId">
        <el-select v-model="formData.detailsId">
          <!-- <el-option  :value="tenantId" /> -->
          <el-option label="微信支付小程序" :value="'wx_miniapp'+tenantId" />
          <el-option label="微信支付公众号" :value="'wx_wechat'+tenantId" />
          <el-option label="微信支付H5" :value="'wx_h5'+tenantId" />
          <el-option label="支付宝H5" :value="'ali_h5'+tenantId" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="支付id" prop="detailsId">
        <el-input v-model="formData.detailsId" placeholder="请输入支付id" />
      </el-form-item> -->
      <el-form-item label="应用id" prop="appid">
        <el-input v-model="formData.appid" placeholder="请输入应用id" />
      </el-form-item>
      <el-form-item label="微信商户id" prop="mchId">
        <el-input v-model="formData.mchId" placeholder="请输入微信商户id" />
      </el-form-item>
      <el-form-item label="支付宝商户id" prop="seller">
        <el-input v-model="formData.seller" placeholder="请输入支付宝商户id" />
      </el-form-item>
      <el-form-item label="证书存储类型" prop="certStoreType">
        <el-select v-model="formData.certStoreType" placeholder="请选择类型" clearable>
          <el-option label="PATH" value="PATH" />
          <el-option label="STR" value="STR" />
          <el-option label="INPUT_STREAM" value="INPUT_STREAM" />
          <el-option label="CLASS_PATH" value="CLASS_PATH" />
          <el-option label="URL" value="URL" />
        </el-select>
        <div style="color: red;">注意：需要证书的选择不需要不选择</div>
      </el-form-item>
      <el-form-item label="私钥或私钥证书" prop="keyPrivate">
        <el-input v-model="formData.keyPrivate" placeholder="请输入私钥或私钥证书" />
      </el-form-item>
      <el-form-item label="公钥或公钥证书" prop="keyPublic">
        <el-input v-model="formData.keyPublic" placeholder="请输入公钥或公钥证书" />
      </el-form-item>
      <el-form-item label="key证书" prop="keyCert">
        <el-input v-model="formData.keyCert" placeholder="请输入key证书,附加证书使用，如SSL证书，或者银联根级证书方面" />
      </el-form-item>
      <el-form-item label="证书的密码" prop="keyCertPwd">
        <el-input v-model="formData.keyCertPwd" placeholder="请输入私钥证书或key证书的密码" />
      </el-form-item>
      <el-form-item label="异步回调地址" prop="notifyUrl">
        <el-input v-model="formData.notifyUrl" placeholder="请输入异步回调" />
      </el-form-item>
      <el-form-item label="同步回调地址" prop="returnUrl">
        <el-input v-model="formData.returnUrl" placeholder="请输入同步回调地址，大部分用于付款成功后页面转跳" />
      </el-form-item>
      <el-form-item label="签名方式" prop="signType">
        <el-select v-model="formData.signType" placeholder="请选择签名方式MD5,RSA等等">
          <el-option label="RSA" value="RSA" />
          <el-option label="RSA2" value="RSA2" />
          <el-option label="MD5" value="MD5" />
        </el-select>
      </el-form-item>
      <el-form-item label="子appid" prop="subAppId">
        <el-input v-model="formData.subAppId" placeholder="请输入子appid" />
      </el-form-item>
      <el-form-item label="子商户id" prop="subMchId">
        <el-input v-model="formData.subMchId" placeholder="请输入子商户id" />
      </el-form-item>
      <el-form-item label="是否为测试环境" prop="isTest">
        <el-radio-group v-model="formData.isTest">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as MerchantDetailsApi from '@/api/pay/merchantDetails'
import { getTenantId } from '@/utils/auth'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const tenantId = ref(getTenantId())

console.log('tenantId:',tenantId.value)

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  detailsId: undefined,
  payType: undefined,
  appid: undefined,
  mchId: undefined,
  certStoreType: undefined,
  keyPrivate: undefined,
  keyPublic: undefined,
  keyCert: undefined,
  keyCertPwd: undefined,
  notifyUrl: undefined,
  returnUrl: undefined,
  signType: undefined,
  seller: undefined,
  subAppId: undefined,
  subMchId: undefined,
  inputCharset: undefined,
  isTest: undefined
})
const formRules = reactive({
  payType: [{ required: true, message: '支付类型(支付渠道)不能为空', trigger: 'change' }],
  detailsId: [{ required: true, message: '支付id不能为空', trigger: 'change' }],
  appid: [{ required: true, message: '应用id不能为空', trigger: 'change' }],
  signType: [{ required: true, message: '签名方式不能为空', trigger: 'change' }],
  notifyUrl: [{ required: true, message: '异步回调地址不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MerchantDetailsApi.getMerchantDetails(id)
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
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MerchantDetailsApi.MerchantDetailsVO
    if (formType.value === 'create') {
      await MerchantDetailsApi.createMerchantDetails(data)
      message.success(t('common.createSuccess'))
    } else {
      await MerchantDetailsApi.updateMerchantDetails(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    detailsId: undefined,
    payType: undefined,
    appid: undefined,
    mchId: undefined,
    certStoreType: undefined,
    keyPrivate: undefined,
    keyPublic: undefined,
    keyCert: undefined,
    keyCertPwd: undefined,
    notifyUrl: undefined,
    returnUrl: undefined,
    signType: undefined,
    seller: undefined,
    subAppId: undefined,
    subMchId: undefined,
    inputCharset: undefined,
    isTest: 1
  }
  formRef.value?.resetFields()
}
</script>
