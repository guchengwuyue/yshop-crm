import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreCombination',
    method: 'post',
    data
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreCombination',
    method: 'put',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreCombination/' + id,
    method: 'delete'
  })
}

export function getCombinationInfo(id) {
  return request({
    url: 'api/yxStoreCombination/info/' + id,
    method: 'get'
  })
}

export function onsale(id, data) {
  return request({
    url: 'api/yxStoreCombination/onsale/' + id,
    method: 'post',
    data
  })
}
