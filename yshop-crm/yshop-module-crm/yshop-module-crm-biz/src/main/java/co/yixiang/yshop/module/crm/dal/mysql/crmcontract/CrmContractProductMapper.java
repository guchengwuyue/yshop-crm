package co.yixiang.yshop.module.crm.dal.mysql.crmcontract;

import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractProductDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同产品关系 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmContractProductMapper extends BaseMapperX<CrmContractProductDO> {

    default List<CrmContractProductDO> selectListByContractId(Long contractId) {
        return selectList(CrmContractProductDO::getContractId, contractId);
    }

    default int deleteByContractId(Long contractId) {
        return delete(CrmContractProductDO::getContractId, contractId);
    }

}