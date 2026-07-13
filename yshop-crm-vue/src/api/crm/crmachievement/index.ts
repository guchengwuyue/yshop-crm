import request from '@/config/axios'

// 业绩目标 VO
export interface AchievementVO {
  id: number // id
  name: string // 名称
  config: number // 1销售（目标）2回款（目标）
  type: number // 2部门3员工
  typeId: number // 对象ID
  year: number // 年
  january: number // 一月
  february: number // 二月
  march: number // 三月
  april: number // 四月
  may: number // 五月
  june: number // 六月
  july: number // 七月
  august: number // 八月
  september: number // 九月
  october: number // 十月
  november: number // 十一月
  december: number // 十二月
  yeartarget: number // 年目标
}

// 业绩目标 API
export const AchievementApi = {
  // 查询业绩目标分页
  getAchievementPage: async (params: any) => {
    return await request.get({ url: `/crm/achievement/page`, params })
  },

  getAchievementDetail: async (params: any) => {
    return await request.get({ url: `/crm/achievement/detailCount`, params })
  },

  getAchievementDept: async (params: any) => {
    return await request.get({ url: `/crm/achievement/depts`, params })
  },

  // 查询业绩目标详情
  getAchievement: async (id: number) => {
    return await request.get({ url: `/crm/achievement/get?id=` + id })
  },

  // 新增业绩目标
  createAchievement: async (data: AchievementVO) => {
    return await request.post({ url: `/crm/achievement/create`, data })
  },

  // 修改业绩目标
  updateAchievement: async (data: AchievementVO) => {
    return await request.put({ url: `/crm/achievement/update`, data })
  },

  // 删除业绩目标
  deleteAchievement: async (id: number) => {
    return await request.delete({ url: `/crm/achievement/delete?id=` + id })
  },

  // 业绩统计
  achievementCount: async (params) => {
    return await request.get({ url: `/crm/achievement/count`, params })
  }
}