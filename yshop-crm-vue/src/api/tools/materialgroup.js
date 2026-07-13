/*
 * @Author: Gaoxs
 * @Date: 2023-05-21 23:40:06
 * @LastEditors: Gaoxs
 * @Description:
 */
import request from '@/config/axios'

export async function getPage(query) {
  return await request.get({
    url: '/shop/material-group/page',

    params: query
  })
}

export async function getList(query) {
  return await request.get({
    url: '/shop/material-group/list',

    params: query
  })
}

export async function addObj(obj) {
  return await request.post({
    url: '/shop/material-group/create',

    data: obj
  })
}

export async function getObj(id) {
  return await request.ge({
    url: '/shop/material-group/' + id
  })
}

export async function delObj(id) {
  return await request.delete({
    url: '/shop/material-group/delete',
    params: { id }
  })
}

export async function putObj(obj) {
  return await request.put({
    url: '/shop/material-group/update',

    data: obj
  })
}
