import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxWechatUser',
    method: 'post',
    data
  })
}

export function del(uid) {
  return request({
    url: 'api/yxWechatUser/' + uid,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxWechatUser',
    method: 'put',
    data
  })
}
