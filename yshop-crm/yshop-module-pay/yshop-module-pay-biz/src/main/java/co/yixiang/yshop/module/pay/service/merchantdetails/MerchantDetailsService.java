package co.yixiang.yshop.module.pay.service.merchantdetails;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.*;
import co.yixiang.yshop.module.pay.dal.dataobject.merchantdetails.MerchantDetailsDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;

/**
 * 支付服务商配置 Service 接口
 *
 * @author yshop
 */
public interface MerchantDetailsService {

    /**
     * 创建支付服务商配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createMerchantDetails(@Valid MerchantDetailsCreateReqVO createReqVO);

    /**
     * 更新支付服务商配置
     *
     * @param updateReqVO 更新信息
     */
    void updateMerchantDetails(@Valid MerchantDetailsUpdateReqVO updateReqVO);

    /**
     * 删除支付服务商配置
     *
     * @param id 编号
     */
    void deleteMerchantDetails(String id);

    /**
     * 获得支付服务商配置
     *
     * @param id 编号
     * @return 支付服务商配置
     */
    MerchantDetailsDO getMerchantDetails(String id);

    /**
     * 获得支付服务商配置列表
     *
     * @param ids 编号
     * @return 支付服务商配置列表
     */
    List<MerchantDetailsDO> getMerchantDetailsList(Collection<String> ids);

    /**
     * 获得支付服务商配置分页
     *
     * @param pageReqVO 分页查询
     * @return 支付服务商配置分页
     */
    PageResult<MerchantDetailsDO> getMerchantDetailsPage(MerchantDetailsPageReqVO pageReqVO);

    /**
     * 获得支付服务商配置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 支付服务商配置列表
     */
    List<MerchantDetailsDO> getMerchantDetailsList(MerchantDetailsExportReqVO exportReqVO);

}
