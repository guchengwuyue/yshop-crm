package co.yixiang.yshop.module.crm.controller.admin.crmcustomer;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.service.crmcustomer.CrmCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 客户")
@RestController
@RequestMapping("/crm/customer")
@Validated
public class CrmCustomerController {

    @Resource
    private CrmCustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "创建客户")
    @PreAuthorize("@ss.hasPermission('crm:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody CrmCustomerSaveReqVO createReqVO) {
        return success(customerService.createCustomer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CrmCustomerSaveReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/do-open")
    @Operation(summary = "投入公海")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('crm:customer:doopen')")
    public CommonResult<Boolean> openCustomer(@RequestParam("id") Long id) {
        customerService.openCustomer(id);
        return success(true);
    }

    @GetMapping("/receive")
    @Operation(summary = "公海领取客户")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> receiveCustomer(@RequestParam("id") Long id) {
        customerService.receiveCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<CrmCustomerRespVO> getCustomer(@RequestParam("id") Long id) {
        CrmCustomerDO customer = customerService.getCustomer(id);
        return success(BeanUtils.toBean(customer, CrmCustomerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户分页")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<CrmCustomerRespVO>> getCustomerPage(@Valid CrmCustomerPageReqVO pageReqVO) {
        return success(customerService.getCustomerPage(pageReqVO));
    }

    @GetMapping("/page2")
    @Operation(summary = "获得查重客户分页")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<CrmCustomerRespVO>> getCustomerPage2(@Valid CrmCustomerPageReqVO pageReqVO) {
        return success(customerService.getCustomerPage2(pageReqVO));
    }



    @PostMapping("/send-sms")
    @Operation(summary = "发送短信")
    public CommonResult<Boolean> sendSms(@Valid @RequestBody SmsTemplateSendVO sendVO) {
        customerService.sendSms(sendVO);
        return success(true);
    }

    @PostMapping("/send-mail")
    @Operation(summary = "发送邮件")
    public CommonResult<Boolean> sendMail(@Valid @RequestBody MailTemplateSendVO sendVO) {
        customerService.sendMail(sendVO);
        return success(true);
    }



}