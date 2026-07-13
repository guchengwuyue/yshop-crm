package co.yixiang.yshop.module.crm.service.crmachievement;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmachievement.CrmAchievementDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 业绩目标 Service 接口
 *
 * @author yshop
 */
public interface CrmAchievementService {

    /**
     * 创建业绩目标
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createAchievement(@Valid DeptAchieveSaveVO createReqVO);

    /**
     * 更新业绩目标
     *
     * @param updateReqVO 更新信息
     */
    void updateAchievement(@Valid CrmAchievementSaveReqVO updateReqVO);

    /**
     * 删除业绩目标
     *
     * @param id 编号
     */
    void deleteAchievement(Long id);

    /**
     * 获得业绩目标
     *
     * @param id 编号
     * @return 业绩目标
     */
    CrmAchievementDO getAchievement(Long id);

    /**
     * 获得用户业绩目标分页
     *
     * @param pageReqVO 分页查询
     * @return 业绩目标分页
     */
    PageResult<UserAchieveRespVO> getAchievementPage(CrmAchievementPageReqVO pageReqVO);


    /**
     * 获得用户业绩目标分页
     *
     * @param pageReqVO 分页查询
     * @return 业绩目标分页
     */
    List<UserAchieveDetailRespVO> getAchievementDetailCount(CrmAchievementPageReqVO pageReqVO);

    /**
     * 获取部门业绩
     * @param type
     * @param year
     * @return
     */
    List<DeptAchieveRespVO> getDeptAchieve(Integer type,Integer year);

    /**
     * 获取业绩完成度
     * @param type 2员工3部门
     * @param year 年度
     * @param month 月份
     * @return
     */
    AchieveCountRespVO getCount(Integer type,Integer year,Integer month);

}