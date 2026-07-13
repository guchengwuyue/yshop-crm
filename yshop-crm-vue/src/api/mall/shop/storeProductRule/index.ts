import request from '@/config/axios'

export interface StoreProductRuleVO {
  id: number
  ruleName: string
  ruleValue: string
}

// 查询商品规则值(规格)列表
export const getStoreProductRulePage = async (params: StoreProductRulePageReqVO) => {
  return await request.get({ url: `/product/store-product-rule/page`, params })
}

// 查询商品规则值(规格)详情
export const getStoreProductRule = async (id: number) => {
  return await request.get({ url: `/product/store-product-rule/get?id=` + id })
}

// 新增商品规则值(规格)
export const createStoreProductRule = async (data: StoreProductRuleVO,id: number) => {
  return await request.post({ url: `/product/store-product-rule/save/` + id, data })
}

// 修改商品规则值(规格)
export const updateStoreProductRule = async (data: StoreProductRuleVO) => {
  return await request.put({ url: `/product/store-product-rule/update`, data })
}

// 删除商品规则值(规格)
export const deleteStoreProductRule = async (id: number) => {
  return await request.delete({ url: `/product/store-product-rule/delete?id=` + id })
}

// 导出商品规则值(规格) Excel
export const exportStoreProductRule = async (params) => {
  return await request.download({ url: `/product/store-product-rule/export-excel`, params })
}
