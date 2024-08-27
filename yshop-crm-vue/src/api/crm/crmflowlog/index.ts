import request from '@/config/axios'

// 审核日志 VO
export interface FlowLogVO {
  id: number // ID
  flowId: number // 审批流程ID
  typesId: number // 对象ID（如审批类型ID）
  adminId: number // 审核人
  types: string // 关联对象 contract-合同 receivables-回款 invoice-发票
  nickname: string // 审核人名称
  remark: string // 审核意见
  isEnd: number // 1审核结束
  status: number // 0审核未通过,1审核通过
}

// 审核日志 API
export const FlowLogApi = {
  // 查询审核日志分页
  getFlowLogPage: async (params: any) => {
    return await request.get({ url: `/crm/flow-log/page`, params })
  },

  // 查询审核日志详情
  getFlowLog: async (id: number) => {
    return await request.get({ url: `/crm/flow-log/get?id=` + id })
  },

  // 新增审核日志
  createFlowLog: async (data: FlowLogVO) => {
    return await request.post({ url: `/crm/flow-log/create`, data })
  },

  // 修改审核日志
  updateFlowLog: async (data: FlowLogVO) => {
    return await request.put({ url: `/crm/flow-log/update`, data })
  },

  // 删除审核日志
  deleteFlowLog: async (id: number) => {
    return await request.delete({ url: `/crm/flow-log/delete?id=` + id })
  },

  // 导出审核日志 Excel
  exportFlowLog: async (params) => {
    return await request.download({ url: `/crm/flow-log/export-excel`, params })
  }
}