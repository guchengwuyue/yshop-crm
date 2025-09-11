package co.yixiang.yshop.module.crm.service.crmrecord;

import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmrecord.CrmRecordMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 跟进记录 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmRecordServiceImpl implements CrmRecordService {

    @Resource
    private CrmRecordMapper recordMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CrmBusinessMapper crmBusinessMapper;
    @Resource
    private CrmCluesMapper cluesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRecord(CrmRecordSaveReqVO createReqVO) {
        // 插入
        CrmRecordDO record = BeanUtils.toBean(createReqVO, CrmRecordDO.class);
        recordMapper.insert(record);

        //更新客户
        if(TypesEnum.CUSTOMER.getValue().equals(createReqVO.getTypes())){
            customerMapper.updateById(CrmCustomerDO.builder()
                    .id(createReqVO.getTypesId())
                    .nextTime(createReqVO.getNextTime())
                    .followStatus(createReqVO.getFollowStatus())
                    .build());
        }else if(TypesEnum.BUSINESS.getValue().equals(createReqVO.getTypes())){
            //更新商机
            CrmBusinessDO crmBusinessDO = crmBusinessMapper.selectById(createReqVO.getTypesId());
            crmBusinessDO.setNextTime(createReqVO.getNextTime());
            crmBusinessMapper.updateById(crmBusinessDO);
            customerMapper.updateById(CrmCustomerDO.builder()
                    .id(crmBusinessDO.getCustomerId())
                    .nextTime(createReqVO.getNextTime())
                    .build());
        }else if(TypesEnum.CLUES.getValue().equals(createReqVO.getTypes())){
            //更新线索
            cluesMapper.updateById(CrmCluesDO.builder()
                    .id(createReqVO.getTypesId())
                    .followStatus(createReqVO.getFollowStatus())
                    .nextTime(createReqVO.getNextTime())
                    .build());
        }


        // 返回
        return record.getId();
    }

    @Override
    public void updateRecord(CrmRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateRecordExists(updateReqVO.getId());
        // 更新
        CrmRecordDO updateObj = BeanUtils.toBean(updateReqVO, CrmRecordDO.class);
        recordMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecord(Long id) {
        // 校验存在
        validateRecordExists(id);
        // 删除
        recordMapper.deleteById(id);
    }

    private void validateRecordExists(Long id) {
        if (recordMapper.selectById(id) == null) {
            throw exception(RECORD_NOT_EXISTS);
        }
    }

    @Override
    public CrmRecordDO getRecord(Long id) {
        return recordMapper.selectById(id);
    }

    @Override
    public PageResult<CrmRecordRespVO> getRecordPage(CrmRecordPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(adminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }
        PageResult<CrmRecordDO> pageResult = recordMapper.selectPage(pageReqVO,ids);
        PageResult<CrmRecordRespVO> pageResult1 = BeanUtils.toBean(pageResult, CrmRecordRespVO.class);
        for(CrmRecordRespVO crmRecordRespVO : pageResult1.getList()){
            if(TypesEnum.CUSTOMER.getValue().equals(pageReqVO.getTypes())){
                CrmCustomerDO customerDO = customerMapper.selectById(crmRecordRespVO.getTypesId());
                crmRecordRespVO.setTypesName(customerDO.getName());
            }else if(TypesEnum.BUSINESS.getValue().equals(pageReqVO.getTypes())){
                CrmBusinessDO crmBusinessDO = crmBusinessMapper.selectById(crmRecordRespVO.getTypesId());
                crmRecordRespVO.setTypesName(crmBusinessDO.getName());
            }else if(TypesEnum.CLUES.getValue().equals(pageReqVO.getTypes())){
                CrmCluesDO crmCluesDO = cluesMapper.selectById(crmRecordRespVO.getTypesId());
                crmRecordRespVO.setTypesName(crmCluesDO.getName());
            }

        }
        return pageResult1;
    }

    @Override
    public void deleteByType(String type, Long typeId) {
        recordMapper.delete(new LambdaQueryWrapper<CrmRecordDO>()
                .eq(CrmRecordDO::getTypes,type)
                .eq(CrmRecordDO::getTypesId,typeId));
    }

}