import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxArticle',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxArticle/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxArticle',
    method: 'put',
    data
  })
}

export function publish(id) {
  return request({
    url: 'api/yxArticle/publish/' + id,
    method: 'get'
  })
}
