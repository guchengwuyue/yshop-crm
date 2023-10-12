import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/yxWechatLiveGoods',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/yxWechatLiveGoods/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/yxWechatLiveGoods',
    method: 'put',
    data
  })
}
export function sync(ids) {
  return request({
    url: 'api/yxWechatLiveGoods/synchro',
    method: 'post',
    data: ids
  })
}
export default { add, edit, del, sync }
