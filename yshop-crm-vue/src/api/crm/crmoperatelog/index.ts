import request from '@/config/axios'

// 操作日志 VO
export interface OperatelogVO {
  id: number // ID
  customerId: number // 客户ID
  contractId: number // 合同ID
  receivableId: number // 回款ID
  nickname: string // 姓名
  username: string // 管理员账号
  url: string // 操作页面
  title: string // 日志标题
  content: string // 内容
  ip: string // IP
  useragent: string // User-Agent
}

// 操作日志 API
export const OperatelogApi = {
  // 查询操作日志分页
  getOperatelogPage: async (params: any) => {
    return await request.get({ url: `/crm/operatelog/page`, params })
  },

  // 查询操作日志详情
  getOperatelog: async (id: number) => {
    return await request.get({ url: `/crm/operatelog/get?id=` + id })
  },

  // 新增操作日志
  createOperatelog: async (data: OperatelogVO) => {
    return await request.post({ url: `/crm/operatelog/create`, data })
  },

  // 修改操作日志
  updateOperatelog: async (data: OperatelogVO) => {
    return await request.put({ url: `/crm/operatelog/update`, data })
  },

  // 删除操作日志
  deleteOperatelog: async (id: number) => {
    return await request.delete({ url: `/crm/operatelog/delete?id=` + id })
  },

  // 导出操作日志 Excel
  exportOperatelog: async (params) => {
    return await request.download({ url: `/crm/operatelog/export-excel`, params })
  }
}