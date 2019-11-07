import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreOrderStatus',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreOrderStatus/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreOrderStatus',
    method: 'put',
    data
  })
}
