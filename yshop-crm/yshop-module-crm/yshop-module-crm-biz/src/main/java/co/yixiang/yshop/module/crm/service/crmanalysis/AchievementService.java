package co.yixiang.yshop.module.crm.service.crmanalysis;

import co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo.SalesVO;

import java.util.List;

/**
 * 业绩目标 Service 接口
 *
 * @author yshop
 */
public interface AchievementService {

    /**
     * 销售简报
     * @param relation
     * @return
     */
    List<SalesVO> getSales(String relation);


}