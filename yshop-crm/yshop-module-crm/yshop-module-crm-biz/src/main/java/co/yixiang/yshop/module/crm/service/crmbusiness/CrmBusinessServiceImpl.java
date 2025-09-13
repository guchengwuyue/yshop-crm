package co.yixiang.yshop.module.crm.service.crmbusiness;

import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.enums.CustomerTypesEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.crm.service.crmrecord.CrmRecordService;
import co.yixiang.yshop.module.product.dal.dataobject.storeproduct.StoreProductDO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproduct.StoreProductMapper;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue.StoreProductAttrValueMapper;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessProductDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessProductMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 商机 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmBusinessServiceImpl implements CrmBusinessService {

    @Resource
    private CrmBusinessMapper businessMapper;
    @Resource
    private CrmBusinessProductMapper businessProductMapper;
    @Resource
    private StoreProductMapper storeProductMapper;
    @Resource
    private StoreProductAttrValueMapper storeProductAttrValueMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CrmRecordService crmRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBusiness(CrmBusinessSaveReqVO createReqVO) {
        // 插入
        CrmBusinessDO business = BeanUtils.toBean(createReqVO, CrmBusinessDO.class);
        business.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        businessMapper.insert(business);

        // 插入子表
        createBusinessProductList(business.getId(), createReqVO.getBusinessProducts());
        // 返回
        return business.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBusiness(CrmBusinessSaveReqVO updateReqVO) {
        // 校验存在
        validateBusinessExists(updateReqVO.getId());
        // 更新
        CrmBusinessDO updateObj = BeanUtils.toBean(updateReqVO, CrmBusinessDO.class);
        businessMapper.updateById(updateObj);

        // 更新子表
        updateBusinessProductList(updateReqVO.getId(), updateReqVO.getBusinessProducts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBusiness(Long id) {
        // 校验存在
        validateBusinessExists(id);
        // 删除
        businessMapper.deleteById(id);

        crmRecordService.deleteByType(TypesEnum.BUSINESS.getValue(),id);

        // 删除子表
        deleteBusinessProductByBusinessId(id);
    }

    private void validateBusinessExists(Long id) {
        if (businessMapper.selectById(id) == null) {
            throw exception(BUSINESS_NOT_EXISTS);
        }
    }

    @Override
    public CrmBusinessRespVO getBusiness(Long id) {

        CrmBusinessDO businessDO = businessMapper.selectById(id);
        CrmBusinessRespVO businessRespVO = BeanUtils.toBean(businessDO,CrmBusinessRespVO.class);
        CrmCustomerDO customerDO = customerMapper.selectById(businessDO.getCustomerId());
        businessRespVO.setCustomerName(customerDO.getName());
        return businessRespVO;
    }

    @Override
    public PageResult<CrmBusinessRespVO> getBusinessPage(CrmBusinessPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(adminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }
        return businessMapper.selectPage(pageReqVO,ids);
    }

    // ==================== 子表（商机产品关联） ====================

    @Override
    public List<CrmBusinessProductDO> getBusinessProductListByBusinessId(Long businessId) {
        List<CrmBusinessProductDO> businessProductDOS = businessProductMapper.selectListByBusinessId(businessId);
        for (CrmBusinessProductDO businessProductDO : businessProductDOS){
            StoreProductAttrValueDO productAttrValueDO = storeProductAttrValueMapper
                    .selectOne(new LambdaQueryWrapper<StoreProductAttrValueDO>()
                            .eq(StoreProductAttrValueDO::getProductId,businessProductDO.getProductId())
                            .eq(StoreProductAttrValueDO::getUnique,businessProductDO.getProductAttrUnique()));
            businessProductDO.setSku(productAttrValueDO.getSku());
        }
        return businessProductDOS;
    }

    private void createBusinessProductList(Long businessId, List<CrmBusinessProductDO> list) {
        list.forEach(o -> o.setBusinessId(businessId));
        businessProductMapper.insertBatch(list);
    }

    private void updateBusinessProductList(Long businessId, List<CrmBusinessProductDO> list) {
        deleteBusinessProductByBusinessId(businessId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createBusinessProductList(businessId, list);
    }

    private void deleteBusinessProductByBusinessId(Long businessId) {
        businessProductMapper.deleteByBusinessId(businessId);
    }

}