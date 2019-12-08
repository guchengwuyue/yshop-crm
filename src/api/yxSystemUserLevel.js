import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxSystemUserLevel',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxSystemUserLevel/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxSystemUserLevel',
    method: 'put',
    data
  })
}
