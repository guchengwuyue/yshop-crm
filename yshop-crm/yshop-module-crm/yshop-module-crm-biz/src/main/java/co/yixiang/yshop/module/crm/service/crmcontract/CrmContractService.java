package co.yixiang.yshop.module.crm.service.crmcontract;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractProductDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 合同 Service 接口
 *
 * @author yshop
 */
public interface CrmContractService {

    /**
     * 创建合同
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContract(@Valid CrmContractSaveReqVO createReqVO);

    /**
     * 更新合同
     *
     * @param updateReqVO 更新信息
     */
    void updateContract(@Valid CrmContractSaveReqVO updateReqVO);

    /**
     * 删除合同
     *
     * @param id 编号
     */
    void deleteContract(Long id);

    /**
     * 获得合同
     *
     * @param id 编号
     * @return 合同
     */
    CrmContractRespVO getContract(Long id);

    /**
     * 获得合同分页
     *
     * @param pageReqVO 分页查询
     * @return 合同分页
     */
    PageResult<CrmContractRespVO> getContractPage(CrmContractPageReqVO pageReqVO);

    // ==================== 子表（合同产品关系） ====================

    /**
     * 获得合同产品关系列表
     *
     * @param contractId 合同ID
     * @return 合同产品关系列表
     */
    List<CrmContractProductDO> getContractProductListByContractId(Long contractId);

    /**
     * 生成合同编号
     * @return
     */
    String getCode();

    /**
     * 审核合同
     * @param checkInfoVO
     */
    void check(CheckInfoVO checkInfoVO);

}