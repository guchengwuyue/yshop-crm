import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreOrder',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreOrder/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreOrder',
    method: 'put',
    data
  })
}

export function refund(data) {
  return request({
    url: 'api/yxStoreOrder/refund',
    method: 'post',
    data
  })
}
