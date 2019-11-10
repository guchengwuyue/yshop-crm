import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreCouponUser',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreCouponUser/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreCouponUser',
    method: 'put',
    data
  })
}
