import request from '@/config/axios'

// 客户 VO
export interface CustomerVO {
  id: number // ID
  name: string // 客户名称
  mobile: string // 手机
  telephone: string // 电话
  dealStatus: number // 成交状态0未成交1成交
  dealTime: number // 成交时间
  isLock: boolean // 1锁定
  level: number // 客户级别ID
  industry: number // 客户行业ID
  tags: string //  客户标签
  source: number // 客户来源ID
  remark: string // 备注
  userId: number // 前台用户ID
  province: number // 省份id
  city: number // 城市ID
  area: number // 区域ID
  detailAddress: string // 详细地址
  lng: number // 地理位置经度
  lat: number // 地理位置维度
  nextTime: Date // 下次联系时间
  followTime: Date // 最后跟进时间
  collectTime: Date // 领取时间
  weixin: string // 微信
  qq: number // QQ
  purchaseTotal: number // 消费总额
  purchaseTimes: number // 消费次数
  followStatus: string // 跟进状态
}

// 客户 API
export const CustomerApi = {
  // 查询客户分页
  getCustomerPage: async (params: any) => {
    return await request.get({ url: `/crm/customer/page`, params })
  },

  getCustomerPage2: async (params: any) => {
    return await request.get({ url: `/crm/customer/page2`, params })
  },

  // 查询客户详情
  getCustomer: async (id: number) => {
    return await request.get({ url: `/crm/customer/get?id=` + id })
  },

  // 新增客户
  createCustomer: async (data: CustomerVO) => {
    return await request.post({ url: `/crm/customer/create`, data })
  },

  // 修改客户
  updateCustomer: async (data: CustomerVO) => {
    return await request.put({ url: `/crm/customer/update`, data })
  },

  // 删除客户
  deleteCustomer: async (id: number) => {
    return await request.delete({ url: `/crm/customer/delete?id=` + id })
  },

  openCustomer: async (id: number) => {
    return await request.get({ url: `/crm/customer/do-open?id=` + id })
  },

  receCustomer: async (id: number) => {
    return await request.get({ url: `/crm/customer/receive?id=` + id })
  },


  // 导出客户 Excel
  exportCustomer: async (params) => {
    return await request.download({ url: `/crm/customer/export-excel`, params })
  },

   sendSms: async (data) => {
    return await request.post({ url: `/crm/customer/send-sms`, data })
  },
  sendMail: async (data) => {
    return await request.post({ url: `/crm/customer/send-mail`, data })
  },
  

// ==================== 子表（联系人） ====================

  // 获得联系人列表
  getCustomerContactsListByCustomerId: async (customerId) => {
    return await request.get({ url: `/crm/customer/customer-contacts/list-by-customer-id?customerId=` + customerId })
  }
}