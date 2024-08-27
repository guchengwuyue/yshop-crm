import request from '@/config/axios'

// 跟进记录 VO
export interface RecordVO {
  id: number // id
  types: string // 关联类型(customer跟进,business商机跟进,clues线索)
  typesId: number // 类型ID（根据types变动而变动）
  content: string // 跟进内容
  recordType: number // 跟进类型/方式
  nextTime: Date // 下次联系时间
  lng: number // 经度
  lat: number // 维度
}

// 跟进记录 API
export const RecordApi = {
  // 查询跟进记录分页
  getRecordPage: async (params: any) => {
    return await request.get({ url: `/crm/record/page`, params })
  },

  // 查询跟进记录详情
  getRecord: async (id: number) => {
    return await request.get({ url: `/crm/record/get?id=` + id })
  },

  // 新增跟进记录
  createRecord: async (data: RecordVO) => {
    return await request.post({ url: `/crm/record/create`, data })
  },

  // 修改跟进记录
  updateRecord: async (data: RecordVO) => {
    return await request.put({ url: `/crm/record/update`, data })
  },

  // 删除跟进记录
  deleteRecord: async (id: number) => {
    return await request.delete({ url: `/crm/record/delete?id=` + id })
  },

  // 导出跟进记录 Excel
  exportRecord: async (params) => {
    return await request.download({ url: `/crm/record/export-excel`, params })
  }
}