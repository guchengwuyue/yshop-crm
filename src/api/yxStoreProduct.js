import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreProduct/addOrSave',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreProduct/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreProduct',
    method: 'put',
    data
  })
}

export function onsale(id, data) {
  return request({
    url: 'api/yxStoreProduct/onsale/' + id,
    method: 'post',
    data
  })
}

export function recovery(id) {
  return request({
    url: 'api/yxStoreProduct/recovery/' + id,
    method: 'delete'
  })
}

export function isFormatAttr(id, data) {
  return request({
    url: 'api/yxStoreProduct/isFormatAttr/' + id,
    method: 'post',
    data
  })
}

export function isFormatAttrForActivity(id, data) {
  return request({
    url: 'api/yxStoreProduct/isFormatAttrForActivity/' + id,
    method: 'post',
    data
  })
}
export function setAttr(id, data) {
  return request({
    url: 'api/yxStoreProduct/setAttr/' + id,
    method: 'post',
    data
  })
}

export function clearAttr(id) {
  return request({
    url: 'api/yxStoreProduct/clearAttr/' + id,
    method: 'post'
  })
}

export function getAttr(id) {
  return request({
    url: 'api/yxStoreProduct/attr/' + id,
    method: 'get'
  })
}

export function getInfo(id) {
  return request({
    url: 'api/yxStoreProduct/info/' + id,
    method: 'get'
  })
}
