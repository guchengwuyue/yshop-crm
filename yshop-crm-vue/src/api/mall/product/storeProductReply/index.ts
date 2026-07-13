import request from '@/config/axios'

export interface StoreProductReplyVO {
  id: number
  uid: number
  oid: number
  unique: string
  productId: number
  replyType: string
  productScore: boolean
  serviceScore: boolean
  comment: string
  pics: string
  merchantReplyContent: string
  merchantReplyTime: Date
  isReply: boolean
}

// 查询评论列表
export const getStoreProductReplyPage = async (params: StoreProductReplyPageReqVO) => {
  return await request.get({ url: `/product/store-product-reply/page`, params })
}

// 查询评论详情
export const getStoreProductReply = async (id: number) => {
  return await request.get({ url: `/product/store-product-reply/get?id=` + id })
}

// 新增评论
export const createStoreProductReply = async (data: StoreProductReplyVO) => {
  return await request.post({ url: `/product/store-product-reply/create`, data })
}

// 修改评论
export const updateStoreProductReply = async (data: StoreProductReplyVO) => {
  return await request.put({ url: `/product/store-product-reply/update`, data })
}

// 删除评论
export const deleteStoreProductReply = async (id: number) => {
  return await request.delete({ url: `/product/store-product-reply/delete?id=` + id })
}

// 导出评论 Excel
export const exportStoreProductReply = async (params) => {
  return await request.download({ url: `/product/store-product-reply/export-excel`, params })
}
