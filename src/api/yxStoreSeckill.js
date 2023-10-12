import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxStoreSeckill',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/yxStoreSeckill/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/yxStoreSeckill',
    method: 'put',
    data
  })
}

export function getSecKillInfo(id) {
  return request({
    url: 'api/yxStoreSecKill/info/' + id,
    method: 'get'
  })
}
