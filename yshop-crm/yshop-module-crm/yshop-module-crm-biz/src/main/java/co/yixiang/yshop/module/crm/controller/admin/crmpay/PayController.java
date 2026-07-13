/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.yshop.module.crm.controller.admin.crmpay;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.util.servlet.ServletUtils;
import co.yixiang.yshop.framework.security.core.annotations.PreAuthenticated;
import co.yixiang.yshop.module.crm.controller.admin.crmpay.param.AppPayParam;
import co.yixiang.yshop.module.crm.service.crmpay.PayService;
import co.yixiang.yshop.module.pay.http.HttpRequestNoticeNewParams;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;
import static co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * <p>
 * 支付控制器
 * </p>
 *
 * @author hupeng
 * @since 2025-3-13
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "管理后台 - 支付模块")
@RequestMapping("/crm/order")
public class PayController {

    private final PayService payService;
    private final PayServiceManager manager;




    /**
     * 订单支付
     */
    @PreAuthenticated
    @PostMapping(value = "/pay")
    @Operation(summary = "订单支付")
    public CommonResult<String> pay(HttpServletRequest httpServletRequest,@RequestBody @Valid AppPayParam param) {
        Long uid = getLoginUserId();
        String url = payService.pay(uid,param);
        String normalQrcode = ServletUtils.getRequstHost(httpServletRequest) + "/admin-api/" + url;
        return success(normalQrcode);
    }

    /**
     * 支付回调地址
     *
     * @param request   请求
     * @param detailsId 列表id
     * @return 支付是否成功
     */
    @RequestMapping(value = "/notify/payBack{detailsId}.json")
    public String payBack(HttpServletRequest request, @PathVariable String detailsId)  {
        return manager.payBack(detailsId, new HttpRequestNoticeNewParams(request));
    }





}

