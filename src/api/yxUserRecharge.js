import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxUserRecharge',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/yxUserRecharge/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/yxUserRecharge',
    method: 'put',
    data
  })
}

export default { add, edit, del }
