import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxWechatTemplate',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxWechatTemplate/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxWechatTemplate',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/yxWechatTemplate',
    method: 'get'
  })
}

