import request from '@/config/axios'

// 商机 VO
export interface BusinessVO {
  id: number // id
  customerId: number // 客户ID
  status: number // 销售阶段
  statusTime: Date // 阶段推进时间
  isEnd: number // 0洽淡中，1成交2失败3无效
  nextTime: Date // 下次联系时间
  name: string // 商机名称
  money: number // 商机金额
  totalPrice: number // 产品总金额
  dealTime: Date // 预计成交日期
  discountRate: number // 整单折扣
  remark: string // 备注
  ownerUserId: number // 负责人ID
}

// 商机 API
export const BusinessApi = {
  // 查询商机分页
  getBusinessPage: async (params: any) => {
    return await request.get({ url: `/crm/business/page`, params })
  },

  // 查询商机详情
  getBusiness: async (id: number) => {
    return await request.get({ url: `/crm/business/get?id=` + id })
  },

  // 新增商机
  createBusiness: async (data: BusinessVO) => {
    return await request.post({ url: `/crm/business/create`, data })
  },

  // 修改商机
  updateBusiness: async (data: BusinessVO) => {
    return await request.put({ url: `/crm/business/update`, data })
  },

  // 删除商机
  deleteBusiness: async (id: number) => {
    return await request.delete({ url: `/crm/business/delete?id=` + id })
  },

  // 导出商机 Excel
  exportBusiness: async (params) => {
    return await request.download({ url: `/crm/business/export-excel`, params })
  },

// ==================== 子表（商机产品关联） ====================

  // 获得商机产品关联列表
  getBusinessProductListByBusinessId: async (businessId) => {
    return await request.get({ url: `/crm/business/business-product/list-by-business-id?businessId=` + businessId })
  }
}