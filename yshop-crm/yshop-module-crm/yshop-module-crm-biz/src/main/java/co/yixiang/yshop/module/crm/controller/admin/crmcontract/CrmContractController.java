package co.yixiang.yshop.module.crm.controller.admin.crmcontract;

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

import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractProductDO;
import co.yixiang.yshop.module.crm.service.crmcontract.CrmContractService;

@Tag(name = "管理后台 - 合同")
@RestController
@RequestMapping("/crm/contract")
@Validated
public class CrmContractController {

    @Resource
    private CrmContractService contractService;

    @PostMapping("/create")
    @Operation(summary = "创建合同")
    @PreAuthorize("@ss.hasPermission('crm:contract:create')")
    public CommonResult<Long> createContract(@Valid @RequestBody CrmContractSaveReqVO createReqVO) {
        return success(contractService.createContract(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同")
    @PreAuthorize("@ss.hasPermission('crm:contract:update')")
    public CommonResult<Boolean> updateContract(@Valid @RequestBody CrmContractSaveReqVO updateReqVO) {
        contractService.updateContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合同")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:contract:delete')")
    public CommonResult<Boolean> deleteContract(@RequestParam("id") Long id) {
        contractService.deleteContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合同")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:contract:query')")
    public CommonResult<CrmContractRespVO> getContract(@RequestParam("id") Long id) {
        return success(contractService.getContract(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合同分页")
    @PreAuthorize("@ss.hasPermission('crm:contract:query')")
    public CommonResult<PageResult<CrmContractRespVO>> getContractPage(@Valid CrmContractPageReqVO pageReqVO) {
        return success(contractService.getContractPage(pageReqVO));
    }



    // ==================== 子表（合同产品关系） ====================

    @GetMapping("/contract-product/list-by-contract-id")
    @Operation(summary = "获得合同产品关系列表")
    @Parameter(name = "contractId", description = "合同ID")
    @PreAuthorize("@ss.hasPermission('crm:contract:query')")
    public CommonResult<List<CrmContractProductDO>> getContractProductListByContractId(@RequestParam("contractId") Long contractId) {
        return success(contractService.getContractProductListByContractId(contractId));
    }

    @GetMapping("/getCode")
    @Operation(summary = "获得编号")
    public CommonResult<String> getCode() {
        return success(contractService.getCode());
    }

    @PostMapping("/check")
    @Operation(summary = "审核合同")
    public CommonResult<Boolean> check(@Valid @RequestBody CheckInfoVO checkInfoVO) {
        contractService.check(checkInfoVO);
        return success(true);
    }





}