package co.yixiang.yshop.module.crm.dal.mysql.crmrecord;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.*;

/**
 * 跟进记录 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmRecordMapper extends BaseMapperX<CrmRecordDO> {

    default PageResult<CrmRecordDO> selectPage(CrmRecordPageReqVO reqVO,List<Long> ids) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmRecordDO>()
                .eqIfPresent(CrmRecordDO::getTypes, reqVO.getTypes())
                .eqIfPresent(CrmRecordDO::getTypesId, reqVO.getTypesId())
                .eqIfPresent(CrmRecordDO::getContent, reqVO.getContent())
                .eqIfPresent(CrmRecordDO::getRecordType, reqVO.getRecordType())
                .betweenIfPresent(CrmRecordDO::getNextTime, reqVO.getNextTime())
                .inIfPresent(CrmRecordDO::getCreator,ids)
                .orderByDesc(CrmRecordDO::getId));
    }

}