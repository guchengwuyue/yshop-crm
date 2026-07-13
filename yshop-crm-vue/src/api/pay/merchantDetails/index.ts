import request from '@/config/axios'

export interface MerchantDetailsVO {
  detailsId: string
  payType: string
  appid: string
  mchId: string
  certStoreType: string
  keyPrivate: string
  keyPublic: string
  keyCert: string
  keyCertPwd: string
  notifyUrl: string
  returnUrl: string
  signType: string
  seller: string
  subAppId: string
  subMchId: string
  inputCharset: string
  isTest: boolean
}

// 查询支付服务商配置列表
export const getMerchantDetailsPage = async (params: MerchantDetailsPageReqVO) => {
  return await request.get({ url: `/pay/merchant-details/page`, params })
}

// 查询支付服务商配置详情
export const getMerchantDetails = async (id: number) => {
  return await request.get({ url: `/pay/merchant-details/get?id=` + id })
}

// 新增支付服务商配置
export const createMerchantDetails = async (data: MerchantDetailsVO) => {
  return await request.post({ url: `/pay/merchant-details/create`, data })
}

// 修改支付服务商配置
export const updateMerchantDetails = async (data: MerchantDetailsVO) => {
  return await request.put({ url: `/pay/merchant-details/update`, data })
}

// 删除支付服务商配置
export const deleteMerchantDetails = async (id: number) => {
  return await request.delete({ url: `/pay/merchant-details/delete?id=` + id })
}

// 导出支付服务商配置 Excel
export const exportMerchantDetails = async (params) => {
  return await request.download({ url: `/pay/merchant-details/export-excel`, params })
}
