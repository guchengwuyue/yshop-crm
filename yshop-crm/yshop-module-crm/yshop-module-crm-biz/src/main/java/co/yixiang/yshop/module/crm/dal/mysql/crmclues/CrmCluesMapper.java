package co.yixiang.yshop.module.crm.dal.mysql.crmclues;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.CrmCluesPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.CrmCluesRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 线索 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmCluesMapper extends BaseMapperX<CrmCluesDO> {

    default PageResult<CrmCluesRespVO> selectPage(CrmCluesPageReqVO reqVO,List<Long> ids) {

        return selectJoinPage(reqVO, CrmCluesRespVO.class,new MPJLambdaWrapper<CrmCluesDO>()
                .selectAll(CrmCluesDO.class)
                .selectAs(AdminUserDO::getNickname,CrmCluesRespVO::getOwnUserName)
                .leftJoin(AdminUserDO.class,AdminUserDO::getId,CrmCluesDO::getOwnerUserId)
                .likeIfExists(CrmCluesDO::getName,reqVO.getName())
                .eqIfExists(CrmCluesDO::getMobile,reqVO.getMobile())
                .eqIfExists(CrmCluesDO::getTelephone,reqVO.getTelephone())
                .eqIfExists(CrmCluesDO::getStatus,reqVO.getStatus())
                .eqIfExists(CrmCluesDO::getLevel,reqVO.getLevel())
                .eqIfExists(CrmCluesDO::getSource,reqVO.getSource())
                .eqIfExists(CrmCluesDO::getIndustry,reqVO.getIndustry())
                .likeIfExists(AdminUserDO::getNickname,reqVO.getOwnerUserName())
                .in(!ids.isEmpty(),CrmCluesDO::getOwnerUserId,ids)
                .orderByDesc(CrmCluesDO::getId)
        );

    }

    default PageResult<CrmCluesRespVO> selectPage2(CrmCluesPageReqVO reqVO) {

        return selectJoinPage(reqVO, CrmCluesRespVO.class,new MPJLambdaWrapper<CrmCluesDO>()
                .selectAll(CrmCluesDO.class)
                .selectAs(AdminUserDO::getNickname,CrmCluesRespVO::getCreateName)
                .leftJoin(AdminUserDO.class,AdminUserDO::getId,CrmCluesDO::getCreator)
                .likeIfExists(CrmCluesDO::getName,reqVO.getName())
                .eq(CrmCluesDO::getOwnerUserId,0L)
                .eqIfExists(CrmCluesDO::getMobile,reqVO.getMobile())
                .eqIfExists(CrmCluesDO::getTelephone,reqVO.getTelephone())
                .eqIfExists(CrmCluesDO::getStatus,reqVO.getStatus())
                .eqIfExists(CrmCluesDO::getLevel,reqVO.getLevel())
                .eqIfExists(CrmCluesDO::getSource,reqVO.getSource())
                .eqIfExists(CrmCluesDO::getIndustry,reqVO.getIndustry())
                .likeIfExists(AdminUserDO::getNickname,reqVO.getOwnerUserName())
                .orderByDesc(CrmCluesDO::getId)
        );

    }

}