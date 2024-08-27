package co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

import co.yixiang.yshop.framework.excel.core.util.ExcelUtils;

import co.yixiang.yshop.framework.apilog.core.annotation.ApiAccessLog;
import static co.yixiang.yshop.framework.apilog.core.enums.OperateTypeEnum.*;

import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.module.crm.service.crmcustomercontacts.CrmCustomerContactsService;

@Tag(name = "管理后台 - 联系人")
@RestController
@RequestMapping("/crm/customer-contacts")
@Validated
public class CrmCustomerContactsController {

    @Resource
    private CrmCustomerContactsService customerContactsService;

    @PostMapping("/create")
    @Operation(summary = "创建联系人")
    @PreAuthorize("@ss.hasPermission('crm:customer-contacts:create')")
    public CommonResult<Long> createCustomerContacts(@Valid @RequestBody CrmCustomerContactsSaveReqVO createReqVO) {
        return success(customerContactsService.createCustomerContacts(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新联系人")
    @PreAuthorize("@ss.hasPermission('crm:customer-contacts:update')")
    public CommonResult<Boolean> updateCustomerContacts(@Valid @RequestBody CrmCustomerContactsSaveReqVO updateReqVO) {
        customerContactsService.updateCustomerContacts(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除联系人")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer-contacts:delete')")
    public CommonResult<Boolean> deleteCustomerContacts(@RequestParam("id") Long id) {
        customerContactsService.deleteCustomerContacts(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得联系人")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer-contacts:query')")
    public CommonResult<CrmCustomerContactsRespVO> getCustomerContacts(@RequestParam("id") Long id) {
        CrmCustomerContactsRespVO customerContacts = customerContactsService.getCustomerContacts(id);
        return success(customerContacts);
    }

    @GetMapping("/page")
    @Operation(summary = "获得联系人分页")
    @PreAuthorize("@ss.hasPermission('crm:customer-contacts:query')")
    public CommonResult<PageResult<CrmCustomerContactsRespVO>> getCustomerContactsPage(@Valid CrmCustomerContactsPageReqVO pageReqVO) {
        PageResult<CrmCustomerContactsRespVO> pageResult = customerContactsService.getCustomerContactsPage(pageReqVO);
        return success(pageResult);
    }



}