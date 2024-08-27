package co.yixiang.yshop.module.pay.controller.admin.merchantdetails;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import java.util.*;
import java.io.IOException;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.CommonResult;
import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

import co.yixiang.yshop.framework.excel.core.util.ExcelUtils;

import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.*;
import co.yixiang.yshop.module.pay.dal.dataobject.merchantdetails.MerchantDetailsDO;
import co.yixiang.yshop.module.pay.convert.merchantdetails.MerchantDetailsConvert;
import co.yixiang.yshop.module.pay.service.merchantdetails.MerchantDetailsService;

@Tag(name = "管理后台 - 支付服务商配置")
@RestController
@RequestMapping("/pay/merchant-details")
@Validated
public class MerchantDetailsController {

    @Resource
    private MerchantDetailsService merchantDetailsService;

    @PostMapping("/create")
    @Operation(summary = "创建支付服务商配置")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:create')")
    public CommonResult<String> createMerchantDetails(@Valid @RequestBody MerchantDetailsCreateReqVO createReqVO) {
        return success(merchantDetailsService.createMerchantDetails(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付服务商配置")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:update')")
    public CommonResult<Boolean> updateMerchantDetails(@Valid @RequestBody MerchantDetailsUpdateReqVO updateReqVO) {
        merchantDetailsService.updateMerchantDetails(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付服务商配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:delete')")
    public CommonResult<Boolean> deleteMerchantDetails(@RequestParam("id") String id) {
        merchantDetailsService.deleteMerchantDetails(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付服务商配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:query')")
    public CommonResult<MerchantDetailsRespVO> getMerchantDetails(@RequestParam("id") String id) {
        MerchantDetailsDO merchantDetails = merchantDetailsService.getMerchantDetails(id);
        return success(MerchantDetailsConvert.INSTANCE.convert(merchantDetails));
    }

    @GetMapping("/list")
    @Operation(summary = "获得支付服务商配置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:query')")
    public CommonResult<List<MerchantDetailsRespVO>> getMerchantDetailsList(@RequestParam("ids") Collection<String> ids) {
        List<MerchantDetailsDO> list = merchantDetailsService.getMerchantDetailsList(ids);
        return success(MerchantDetailsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付服务商配置分页")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:query')")
    public CommonResult<PageResult<MerchantDetailsRespVO>> getMerchantDetailsPage(@Valid MerchantDetailsPageReqVO pageVO) {
        PageResult<MerchantDetailsDO> pageResult = merchantDetailsService.getMerchantDetailsPage(pageVO);
        return success(MerchantDetailsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出支付服务商配置 Excel")
    @PreAuthorize("@ss.hasPermission('pay:merchant-details:export')")
    public void exportMerchantDetailsExcel(@Valid MerchantDetailsExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<MerchantDetailsDO> list = merchantDetailsService.getMerchantDetailsList(exportReqVO);
        // 导出 Excel
        List<MerchantDetailsExcelVO> datas = MerchantDetailsConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "支付服务商配置.xls", "数据", MerchantDetailsExcelVO.class, datas);
    }

}
