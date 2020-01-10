import request from '@/utils/request'

export function getPage(query) {
  return request({
    url: 'api/material/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: 'api/material',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: 'api/material/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: 'api/material/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: 'api/material',
    method: 'put',
    data: obj
  })
}
