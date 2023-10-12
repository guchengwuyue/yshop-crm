import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreBargainUser',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreBargainUser/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreBargainUser',
    method: 'put',
    data
  })
}
