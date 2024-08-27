package co.yixiang.yshop.module.crm.service.crmclues;

import java.util.*;

import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerSaveReqVO;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 线索 Service 接口
 *
 * @author yshop
 */
public interface CrmCluesService {

    /**
     * 创建线索
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createClues(@Valid CrmCluesSaveReqVO createReqVO);

    /**
     * 更新线索
     *
     * @param updateReqVO 更新信息
     */
    void updateClues(@Valid CrmCluesSaveReqVO updateReqVO);

    /**
     * 删除线索
     *
     * @param id 编号
     */
    void deleteClues(Long id);

    /**
     * 获得线索
     *
     * @param id 编号
     * @return 线索
     */
    CrmCluesDO getClues(Long id);

    /**
     * 获得线索分页
     *
     * @param pageReqVO 分页查询
     * @return 线索分页
     */
    PageResult<CrmCluesRespVO> getCluesPage(CrmCluesPageReqVO pageReqVO);

    /**
     * 放入线索池
     * @param id
     */
    void openClues(Long id);

    /**
     * 领取线索
     * @param id
     */
    void receiveClues(Long id);

    /**
     * 线索转换成客户
     * @param createReqVO
     */
    void createCustomer(CrmCustomerSaveReqVO createReqVO);

}