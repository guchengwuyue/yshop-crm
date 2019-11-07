import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxCache',
    method: 'post',
    data
  })
}

export function del(key) {
  return request({
    url: 'api/yxCache/' + key,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxCache',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/yxCache',
    method: 'get',
  })
}
