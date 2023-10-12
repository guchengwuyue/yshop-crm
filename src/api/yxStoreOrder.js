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

export function updateDelivery(data) {
  return request({
    url: 'api/yxStoreOrder/updateDelivery',
    method: 'put',
    data
  })
}

export function editT(data) {
  return request({
    url: 'api/yxStoreOrder/check',
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

export function editOrder(data) {
  return request({
    url: 'api/yxStoreOrder/edit',
    method: 'post',
    data
  })
}

export function remark(data) {
  return request({
    url: 'api/yxStoreOrder/remark',
    method: 'post',
    data
  })
}

export function get() {
  return request({
    url: 'api/yxExpress',
    method: 'get'
  })
}

export function express(data) {
  return request({
    url: 'api/yxStoreOrder/express',
    method: 'post',
    data
  })
}

export function getOrderDetail(id) {
  return request({
    url: 'api/getStoreOrderDetail/' + id,
    method: 'get'
  })
}

export function getNowOrderStatus(id) {
  return request({
    url: '/api/getNowOrderStatus/' + id,
    method: 'get'
  })
}
