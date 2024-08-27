package co.yixiang.yshop.module.crm.service.crmcontractreceivables;

import java.util.*;

import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 回款 Service 接口
 *
 * @author yshop
 */
public interface CrmContractReceivablesService {

    /**
     * 创建回款
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContractReceivables(@Valid CrmContractReceivablesSaveReqVO createReqVO);

    /**
     * 更新回款
     *
     * @param updateReqVO 更新信息
     */
    void updateContractReceivables(@Valid CrmContractReceivablesSaveReqVO updateReqVO);

    /**
     * 删除回款
     *
     * @param id 编号
     */
    void deleteContractReceivables(Long id);

    /**
     * 获得回款
     *
     * @param id 编号
     * @return 回款
     */
    CrmContractReceivablesRespVO getContractReceivables(Long id);

    /**
     * 获得回款分页
     *
     * @param pageReqVO 分页查询
     * @return 回款分页
     */
    PageResult<CrmContractReceivablesRespVO> getContractReceivablesPage(CrmContractReceivablesPageReqVO pageReqVO);

    /**
     * 生成回款编号
     * @return
     */
    String getCode();

    /**
     * 审核回款
     * @param checkInfoVO
     */
    void check(CheckInfoVO checkInfoVO);


}