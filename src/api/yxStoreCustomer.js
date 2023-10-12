import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreCustomer',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/yxStoreCustomer/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreCustomer',
    method: 'put',
    data
  })
}

export function getqrcode() {
  return request({
    url: 'api/wxmp/qrcode',
    method: 'get'
  })
}
export function getOpenId(key) {
  return request({
    url: 'api/wxmp/getOpenId?key=' + key,
    method: 'get'
  })
}
export function getwechatCode() {
  return request({
    url: 'api/wxmp/wechatCode',
    method: 'get'
  })
}

export default { add, edit, del, getqrcode, getOpenId, getwechatCode }
