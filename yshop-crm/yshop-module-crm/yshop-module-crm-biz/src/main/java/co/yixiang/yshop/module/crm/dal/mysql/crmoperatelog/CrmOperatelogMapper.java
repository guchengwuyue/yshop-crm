package co.yixiang.yshop.module.crm.dal.mysql.crmoperatelog;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo.CrmOperatelogPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmoperatelog.CrmOperatelogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmOperatelogMapper extends BaseMapperX<CrmOperatelogDO> {

    default PageResult<CrmOperatelogDO> selectPage(CrmOperatelogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmOperatelogDO>()
                .eqIfPresent(CrmOperatelogDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(CrmOperatelogDO::getContractId, reqVO.getContractId())
                .eqIfPresent(CrmOperatelogDO::getReceivableId, reqVO.getReceivableId())
                .likeIfPresent(CrmOperatelogDO::getNickname, reqVO.getNickname())
                .likeIfPresent(CrmOperatelogDO::getUsername, reqVO.getUsername())
                .eqIfPresent(CrmOperatelogDO::getUrl, reqVO.getUrl())
                .eqIfPresent(CrmOperatelogDO::getTitle, reqVO.getTitle())
                .eqIfPresent(CrmOperatelogDO::getContent, reqVO.getContent())
                .eqIfPresent(CrmOperatelogDO::getIp, reqVO.getIp())
                .eqIfPresent(CrmOperatelogDO::getUseragent, reqVO.getUseragent())
                .betweenIfPresent(CrmOperatelogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmOperatelogDO::getId));
    }

}