import request from '@/config/axios'

export interface AccountVO {
  id?: number
  name: string
}

// 创建公众号账号
export const createAccount = async (data) => {
  return await request.post({ url: '/ma/account/create', data })
}

// 更新公众号账号
export const updateAccount = async (data) => {
  return request.put({ url: '/ma/account/update', data: data })
}

// 删除公众号账号
export const deleteAccount = async (id) => {
  return request.delete({ url: '/ma/account/delete?id=' + id, method: 'delete' })
}

// 获得公众号账号
export const getAccount = async (id) => {
  return request.get({ url: '/ma/account/get?id=' + id })
}

// 获得公众号账号分页
export const getAccountPage = async (query) => {
  return request.get({ url: '/ma/account/page', params: query })
}


export const setAccountMain = async (id) => {
  return request.put({ url: '/ma/account/set-main?id=' + id })
}
