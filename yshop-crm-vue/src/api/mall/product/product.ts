import request from '@/config/axios'

export interface StoreProductVO {
  id: number
  image: string
  sliderImage: string
  storeName: string
  storeInfo: string
  keyword: string
  barCode: string
  cateId: string
  price: number
  vipPrice: number
  otPrice: number
  postage: number
  unitName: string
  sort: number
  sales: number
  stock: number
  isShow: boolean
  isHot: boolean
  isBenefit: boolean
  isBest: boolean
  isNew: boolean
  description: string
  isPostage: byte
  merUse: byte
  giveIntegral: number
  cost: number
  isSeckill: byte
  isBargain: byte
  isGood: boolean
  ficti: number
  browse: number
  codePath: string
  isSub: boolean
  tempId: number
  specType: boolean
  isIntegral: byte
  integral: number
}

// 查询商品列表
export const getStoreProductPage = async (params: StoreProductPageReqVO) => {
  return await request.get({ url: `/product/store-product/page`, params })
}

// 查询商品详情
export const getStoreProduct = async (id: number) => {
  return await request.get({ url: `/product/store-product/get?id=` + id })
}

// 查询商品详情
export const getStoreProductInfo = async (id: number) => {
  return await request.get({ url: `/product/store-product/info/` + id })
}

// 新增商品
export const createStoreProduct = async (data) => {
  return await request.post({ url: `/product/store-product/create`, data })
}

// 修改商品
export const updateStoreProduct = async (data: StoreProductVO) => {
  return await request.put({ url: `/product/store-product/update`, data })
}

// 删除商品
export const deleteStoreProduct = async (id: number) => {
  return await request.delete({ url: `/product/store-product/delete?id=` + id })
}

// 导出商品 Excel
export const exportStoreProduct = async (params) => {
  return await request.download({ url: `/product/store-product/export-excel`, params })
}
// 规格格式化
export const isFormatAttr = async (id, data) => {
  return await request.post({ url: '/product/store-product/isFormatAttr/' + id, data })
}

// 删除商品
export const saleStoreProduct = async (id,isShow) => {
  return await request.get({ url: `/product/store-product/sale?id=` + id + `&type=` + isShow })
}

