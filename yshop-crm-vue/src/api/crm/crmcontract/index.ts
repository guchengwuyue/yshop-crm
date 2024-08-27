import request from '@/config/axios'

// 合同 VO
export interface ContractVO {
  id: number // id
  customerId: number // 客户ID
  businessId: number // 商机ID
  contactsId: number // 客户签约人（联系人ID）
  sourceId: number // 客户来源ID
  name: string // 合同名称
  number: string // 合同编号
  orderTime: Date // 下单时间
  money: number // 合同金额
  totalPrice: number // 产品总金额
  returnMoney: number // 已收到款项
  discountRate: number // 整单折扣
  checkStatus: number // 0待审核、1审核中、2审核通过、3审核未通过
  flowId: number // 审核流程ID
  stepId: number // 审核步骤ID
  checkAdminId: string // 已经审批人IDs
  flowAdminId: string // 当前需要审批的人
  startTime: Date // 开始时间
  endTime: Date // 结束时间
  orderAdminId: number // 公司签约人
  remark: string // 备注
  ownerUserId: number // 负责人ID
  nextTime: Date // 下次联系时间
  expireHandle: number // 0合同过期未处理1已续签2不再合作
  invoiceMoney: number // 已开票金额
}

// 合同 API
export const ContractApi = {
  // 查询合同分页
  getContractPage: async (params: any) => {
    return await request.get({ url: `/crm/contract/page`, params })
  },

  // 查询合同详情
  getContract: async (id: number) => {
    return await request.get({ url: `/crm/contract/get?id=` + id })
  },

  // 新增合同
  createContract: async (data: ContractVO) => {
    return await request.post({ url: `/crm/contract/create`, data })
  },

  // 修改合同
  updateContract: async (data: ContractVO) => {
    return await request.put({ url: `/crm/contract/update`, data })
  },

  // 删除合同
  deleteContract: async (id: number) => {
    return await request.delete({ url: `/crm/contract/delete?id=` + id })
  },

  // 导出合同 Excel
  exportContract: async (params) => {
    return await request.download({ url: `/crm/contract/export-excel`, params })
  },

  getContractNo: async () => {
    return await request.get({ url: `/crm/contract/getCode`})
  },

  checkContract: async (data) => {
    return await request.post({ url: `/crm/contract/check`, data })
  },

  getIndexCount: async () => {
    return await request.get({ url: `/crm/index/getCount` })
  },


// ==================== 子表（合同产品关系） ====================

  // 获得合同产品关系列表
  getContractProductListByContractId: async (contractId) => {
    return await request.get({ url: `/crm/contract/contract-product/list-by-contract-id?contractId=` + contractId })
  }
}