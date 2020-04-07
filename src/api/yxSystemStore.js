import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxSystemStore',
    method: 'post',
    data
  })
}

export function get() {
  return request({
    url: 'api/yxSystemStore',
    method: 'get'
  })
}

export function getAll() {
  return request({
    url: 'api/yxSystemStore/all',
    method: 'get'
  })
}

export function getL(data) {
  return request({
    url: 'api/yxSystemStore/getL',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/yxSystemStore/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/yxSystemStore',
    method: 'put',
    data
  })
}

export default { getL, add, edit, del, get, getAll }
