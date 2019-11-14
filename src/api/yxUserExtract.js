import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxUserExtract',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxUserExtract/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxUserExtract',
    method: 'put',
    data
  })
}
