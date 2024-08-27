import request from '@/config/axios'

export interface MaterialGroupVO {
  id: number
  name: string
}

// 查询素材分组列表
export const getMaterialGroupPage = async (params: MaterialGroupPageReqVO) => {
  return await request.get({ url: `/shop/material-group/page`, params })
}

// 查询素材分组详情
export const getMaterialGroup = async (id: number) => {
  return await request.get({ url: `/shop/material-group/get?id=` + id })
}

// 新增素材分组
export const createMaterialGroup = async (data: MaterialGroupVO) => {
  return await request.post({ url: `/shop/material-group/create`, data })
}

// 修改素材分组
export const updateMaterialGroup = async (data: MaterialGroupVO) => {
  return await request.put({ url: `/shop/material-group/update`, data })
}

// 删除素材分组
export const deleteMaterialGroup = async (id: number) => {
  return await request.delete({ url: `/shop/material-group/delete?id=` + id })
}

// 导出素材分组 Excel
export const exportMaterialGroup = async (params) => {
  return await request.download({ url: `/shop/material-group/export-excel`, params })
}
