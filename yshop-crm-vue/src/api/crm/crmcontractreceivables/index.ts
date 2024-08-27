import request from '@/config/axios'

// 回款 VO
export interface ContractReceivablesVO {
  id: number // id
  number: string // 回款编号
  customerId: number // 客户ID
  contractId: number // 合同ID
  returnTime: Date // 回款日期
  accountId: number // 收款账户ID
  money: number // 回款金额
  checkStatus: number // 0待审核、1审核中、2审核通过、3审核未通过
  flowId: number // 审核流程ID
  stepId: number // 审核步骤排序ID
  checkAdminId: string // 审批人IDs
  flowAdminId: string // 流程审批人ID
  remark: string // 备注
  ownerUserId: number // 负责人ID
  orderAdminId: number // 合同签约人
  payStatus: number // 在线支付状态1已付款0未付款
  payType: number // 收款方式1默认2在线收款3续费
}

// 回款 API
export const ContractReceivablesApi = {
  // 查询回款分页
  getContractReceivablesPage: async (params: any) => {
    return await request.get({ url: `/crm/contract-receivables/page`, params })
  },

  // 查询回款详情
  getContractReceivables: async (id: number) => {
    return await request.get({ url: `/crm/contract-receivables/get?id=` + id })
  },

  // 新增回款
  createContractReceivables: async (data: ContractReceivablesVO) => {
    return await request.post({ url: `/crm/contract-receivables/create`, data })
  },

  // 修改回款
  updateContractReceivables: async (data: ContractReceivablesVO) => {
    return await request.put({ url: `/crm/contract-receivables/update`, data })
  },

  // 删除回款
  deleteContractReceivables: async (id: number) => {
    return await request.delete({ url: `/crm/contract-receivables/delete?id=` + id })
  },

  getCode: async () => {
    return await request.get({ url: `/crm/contract-receivables/getCode` })
  },

  checkContract: async (data) => {
    return await request.post({ url: `/crm/contract-receivables/check`, data })
  },
  // 导出回款 Excel
  exportContractReceivables: async (params) => {
    return await request.download({ url: `/crm/contract-receivables/export-excel`, params })
  }
}