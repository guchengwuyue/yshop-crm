import request from '@/utils/request'

// 读取画布
export function getCanvasData(params) {
  // console.log(qs.stringify(params, { indices: false }))
  return request({
    url: '/api/canvas/getCanvas',
    method: 'get',
    params
  })
}

// 保存画布
export function saveCanvasData(data) {
  return request({
    url: '/api/canvas/saveCanvas',
    method: 'post',
    data
  })
}

// 文件上传
export function uploadCanvas() {
  return request({
    url: '/api/canvas/upload',
    method: ''
  })
}

// 查询分类层级
export function getClassify() {
  return request({
    url: '/api/canvas/getClassify',
    method: 'get'
  })
}

// 选择商品查询
export function getProducts(params) {
  return request({
    url: '/api/canvas/getProducts',
    methods: 'get',
    params
  })
}

// 选择店铺查询
export function getShops(params) {
  return request({
    url: '/api/canvas/getShops',
    method: 'get',
    params
  })
}
