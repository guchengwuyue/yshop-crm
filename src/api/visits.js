import request from '@/utils/request'

export function count() {
  return request({
    url: 'api/visits',
    method: 'post'
  })
}

export function get() {
  return request({
    url: 'api/visits',
    method: 'get'
  })
}

export function getChartData() {
  return request({
    url: 'api/visits/chartData',
    method: 'get'
  })
}

export function gett() {
  return request({
    url: 'api/data/count',
    method: 'get'
  })
}

export function chart() {
  return request({
    url: 'api/data/chart',
    method: 'get'
  })
}

export function getOrderCount() {
  return request({
    url: 'api/yxStoreOrder/orderCount',
    method: 'get'
  })
}
