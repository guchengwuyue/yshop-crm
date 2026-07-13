package co.yixiang.yshop.module.crm.dal.mysql.crmbusiness;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商机产品关联 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmBusinessProductMapper extends BaseMapperX<CrmBusinessProductDO> {

    default List<CrmBusinessProductDO> selectListByBusinessId(Long businessId) {
        return selectList(CrmBusinessProductDO::getBusinessId, businessId);
    }

    default int deleteByBusinessId(Long businessId) {
        return delete(CrmBusinessProductDO::getBusinessId, businessId);
    }

}