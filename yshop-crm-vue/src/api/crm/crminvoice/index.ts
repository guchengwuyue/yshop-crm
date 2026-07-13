import request from '@/config/axios'

// 发票 VO
export interface InvoiceVO {
  id: number // id
  contractId: number // 合同ID
  customerId: number // 客户ID
  invoiceBody: string // 开票主体
  content: string // 开票内容
  invoiceName: string // 发票抬头
  invoiceIssue: number // 抬头类型1企业2个人3事业单位
  invoiceType: number // 1增普2增专
  registerNo: string // 统一社会信用代码
  bankName: string // 开户行名称
  bankNo: string // 开户账号
  money: number // 发票税前金额
  taxRate: number // 税率
  remarks: string // 备注
  email: string // 接收发票邮箱
  userName: string // 收件人
  userPhone: string // 收件手机
  userAddress: string // 邮寄地址
  files: string // 开票附件
  invoiceAdminId: number // 开票人
  checkStatus: number // 0待审核、1审核中、2审核通过、3审核未通过
  flowId: number // 审核流程ID
  stepId: number // 审核步骤ID
  checkAdminId: string // 已经审批人IDs
  flowAdminId: string // 当前需要审批的人
  status: number // 状态0未开票1开票中2已开票3开票失败
}

// 发票 API
export const InvoiceApi = {
  // 查询发票分页
  getInvoicePage: async (params: any) => {
    return await request.get({ url: `/crm/invoice/page`, params })
  },

  // 查询发票详情
  getInvoice: async (id: number) => {
    return await request.get({ url: `/crm/invoice/get?id=` + id })
  },

  // 新增发票
  createInvoice: async (data: InvoiceVO) => {
    return await request.post({ url: `/crm/invoice/create`, data })
  },

  // 修改发票
  updateInvoice: async (data: InvoiceVO) => {
    return await request.put({ url: `/crm/invoice/update`, data })
  },

  // 删除发票
  deleteInvoice: async (id: number) => {
    return await request.delete({ url: `/crm/invoice/delete?id=` + id })
  },

  checkInvoice: async (data) => {
    return await request.post({ url: `/crm/invoice/check`, data })
  },

  issueInvoice: async (data: InvoiceVO) => {
    return await request.put({ url: `/crm/invoice/issue`, data })
  },

  // 导出发票 Excel
  exportInvoice: async (params) => {
    return await request.download({ url: `/crm/invoice/export-excel`, params })
  }
}