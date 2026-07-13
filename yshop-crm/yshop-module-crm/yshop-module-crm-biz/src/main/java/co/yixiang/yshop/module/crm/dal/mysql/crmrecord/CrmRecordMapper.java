package co.yixiang.yshop.module.crm.dal.mysql.crmrecord;

import java.time.LocalDateTime;
import java.util.*;

import cn.hutool.core.date.LocalDateTimeUtil;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime beginOfDay = null;
        LocalDateTime endOfDay = null;
        //System.out.println("reqVO:"+reqVO.getIsNextTime());
        if(reqVO.getIsNextTime()){
            beginOfDay = LocalDateTimeUtil.beginOfDay(nowTime);
            endOfDay = LocalDateTimeUtil.endOfDay(nowTime);
           // reqVO.setPageSize(1);
        }
       //selectCount(new LambdaQueryWrapper<CrmRecordDO>().eq().dis
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmRecordDO>()
               // .select(reqVO.getIsNextTime(),CrmRecordDO::getTypesId).di(true)
                //.select(" DISTINCT data_id").lambda()
               // .select(reqVO.getIsNextTime(),CrmRecordDO::getTypesId)
                .eqIfPresent(CrmRecordDO::getTypes, reqVO.getTypes())
                .eqIfPresent(CrmRecordDO::getTypesId, reqVO.getTypesId())
                .eqIfPresent(CrmRecordDO::getContent, reqVO.getContent())
                .eqIfPresent(CrmRecordDO::getRecordType, reqVO.getRecordType())
                .betweenIfPresent(CrmRecordDO::getNextTime, beginOfDay,endOfDay)
                .inIfPresent(CrmRecordDO::getCreator,ids)
               // .select(CrmRecordDO::getTypesId)

                .orderByDesc(CrmRecordDO::getId));
    }

}