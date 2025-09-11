package co.yixiang.yshop.module.crm.service.crmrecord;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 跟进记录 Service 接口
 *
 * @author yshop
 */
public interface CrmRecordService {

    /**
     * 创建跟进记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecord(@Valid CrmRecordSaveReqVO createReqVO);

    /**
     * 更新跟进记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRecord(@Valid CrmRecordSaveReqVO updateReqVO);

    /**
     * 删除跟进记录
     *
     * @param id 编号
     */
    void deleteRecord(Long id);

    /**
     * 获得跟进记录
     *
     * @param id 编号
     * @return 跟进记录
     */
    CrmRecordDO getRecord(Long id);

    /**
     * 获得跟进记录分页
     *
     * @param pageReqVO 分页查询
     * @return 跟进记录分页
     */
    PageResult<CrmRecordRespVO> getRecordPage(CrmRecordPageReqVO pageReqVO);

    /**
     * 跟进类型删除跟进记录
     * @param type 类型
     * @param typeId 类型ID
     */
    void deleteByType(String type,Long typeId);

}