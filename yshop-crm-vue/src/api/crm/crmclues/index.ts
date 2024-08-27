import request from '@/config/axios'

// 线索 VO
export interface CluesVO {
  id: number // ID
  name: string // 线索
  mobile: string // 手机
  telephone: string // 电话
  status: number // 状态1-无效线索，0未转客户,1转成客户
  customerId: number // 转成客户id
  changeTime: Date // 转客户时间
  level: number // 客户级别ID
  industry: number // 客户行业ID
  tags: string //  客户标签
  source: number // 客户来源ID
  remark: string // 备注
  ownerUserId: number // 负责人ID
  province: number // 省份id
  city: number // 城市ID
  area: number // 区域ID
  detailAddress: string // 详细地址
  nextTime: Date // 下次联系时间
  followTime: Date // 最后跟进时间
  collectTime: Date // 领取时间
  followStatus: boolean // 跟进状态
}

// 线索 API
export const CluesApi = {
  // 查询线索分页
  getCluesPage: async (params: any) => {
    return await request.get({ url: `/crm/clues/page`, params })
  },

  // 查询线索详情
  getClues: async (id: number) => {
    return await request.get({ url: `/crm/clues/get?id=` + id })
  },

  // 新增线索
  createClues: async (data: CluesVO) => {
    return await request.post({ url: `/crm/clues/create`, data })
  },

  // 修改线索
  updateClues: async (data: CluesVO) => {
    return await request.put({ url: `/crm/clues/update`, data })
  },

  // 删除线索
  deleteClues: async (id: number) => {
    return await request.delete({ url: `/crm/clues/delete?id=` + id })
  },

  openClues: async (id: number) => {
    return await request.get({ url: `/crm/clues/open?id=` + id })
  },

  receClues: async (id: number) => {
    return await request.get({ url: `/crm/clues/receive?id=` + id })
  },

  createCustomer: async (data) => {
    return await request.post({ url: `/crm/clues/createCustomer`, data })
  },

  // 导出线索 Excel
  exportClues: async (params) => {
    return await request.download({ url: `/crm/clues/export-excel`, params })
  }
}