import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreBargain',
    method: 'put',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreBargain/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreBargain',
    method: 'put',
    data
  })
}
