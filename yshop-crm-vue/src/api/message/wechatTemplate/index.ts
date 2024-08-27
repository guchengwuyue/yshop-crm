import request from '@/config/axios'

export interface WechatTemplateVO {
  id: number
  tempkey: string
  name: string
  content: string
  tempid: string
  status: byte
  type: string
}

// 查询微信模板列表
export const getWechatTemplatePage = async (params: WechatTemplatePageReqVO) => {
  return await request.get({ url: `/message/wechat-template/page`, params })
}

// 查询微信模板详情
export const getWechatTemplate = async (id: number) => {
  return await request.get({ url: `/message/wechat-template/get?id=` + id })
}

// 新增微信模板
export const createWechatTemplate = async (data: WechatTemplateVO) => {
  return await request.post({ url: `/message/wechat-template/create`, data })
}

// 修改微信模板
export const updateWechatTemplate = async (data: WechatTemplateVO) => {
  return await request.put({ url: `/message/wechat-template/update`, data })
}

// 删除微信模板
export const deleteWechatTemplate = async (id: number) => {
  return await request.delete({ url: `/message/wechat-template/delete?id=` + id })
}

// 导出微信模板 Excel
export const exportWechatTemplate = async (params) => {
  return await request.download({ url: `/message/wechat-template/export-excel`, params })
}
