import request from '@/config/axios'

// 联系人 VO
export interface CustomerContactsVO {
  id: number // ID
  customerId: number // 客户ID
  name: string // 姓名
  mobile: string // 手机
  birthday: Date // 生日
  telephone: string // 电话
  wechat: string // 微信号
  email: string // 电子邮箱
  decision: number // 是否关键决策人1是0不是-1未知
  post: string // 职务
  sex: number // 性别0女1男-1未知
  detailAddress: string // 地址
  remark: string // 备注
  nextTime: Date // 下次联系时间
}

// 联系人 API
export const CustomerContactsApi = {
  // 查询联系人分页
  getCustomerContactsPage: async (params: any) => {
    return await request.get({ url: `/crm/customer-contacts/page`, params })
  },

  // 查询联系人详情
  getCustomerContacts: async (id: number) => {
    return await request.get({ url: `/crm/customer-contacts/get?id=` + id })
  },

  // 新增联系人
  createCustomerContacts: async (data: CustomerContactsVO) => {
    return await request.post({ url: `/crm/customer-contacts/create`, data })
  },

  // 修改联系人
  updateCustomerContacts: async (data: CustomerContactsVO) => {
    return await request.put({ url: `/crm/customer-contacts/update`, data })
  },

  // 删除联系人
  deleteCustomerContacts: async (id: number) => {
    return await request.delete({ url: `/crm/customer-contacts/delete?id=` + id })
  },

  // 导出联系人 Excel
  exportCustomerContacts: async (params) => {
    return await request.download({ url: `/crm/customer-contacts/export-excel`, params })
  }
}