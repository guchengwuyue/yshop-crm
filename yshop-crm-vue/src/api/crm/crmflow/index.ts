import request from '@/config/axios'

// 审批流程 VO
export interface FlowVO {
  id: number // id
  name: string // 审批流名称
  types: string // 关联对象 contract-合同 receivables-回款 invoice-发票
  groupIds: string // 组别ID（0为全部）
  adminIds: string // 员工ID
  remark: string // 流程说明
  status: number // 状态 1启用，0禁用
}

// 审批流程 API
export const FlowApi = {
  // 查询审批流程分页
  getFlowPage: async (params: any) => {
    return await request.get({ url: `/crm/flow/page`, params })
  },

  // 查询审批流程详情
  getFlow: async (id: number) => {
    return await request.get({ url: `/crm/flow/get?id=` + id })
  },

  // 新增审批流程
  createFlow: async (data: FlowVO) => {
    return await request.post({ url: `/crm/flow/create`, data })
  },

  // 修改审批流程
  updateFlow: async (data: FlowVO) => {
    return await request.put({ url: `/crm/flow/update`, data })
  },

  // 删除审批流程
  deleteFlow: async (id: number) => {
    return await request.delete({ url: `/crm/flow/delete?id=` + id })
  },

  // 导出审批流程 Excel
  exportFlow: async (params) => {
    return await request.download({ url: `/crm/flow/export-excel`, params })
  },

// ==================== 子表（审批步骤） ====================

  // 获得审批步骤列表
  getFlowStepListByFlowId: async (flowId) => {
    return await request.get({ url: `/crm/flow/flow-step/list-by-flow-id?flowId=` + flowId })
  },
  getFlowUserIds: async (type) => {
    return await request.get({ url: `/crm/flow/flow-users?flowType=` + type })
  },
  deleteFlowStep: async (id: number) => {
    return await request.delete({ url: `/crm/flow/delete-step?id=` + id })
  },
}