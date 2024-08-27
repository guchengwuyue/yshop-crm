package co.yixiang.yshop.module.crm.controller.admin.crminvoice;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoicePageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import co.yixiang.yshop.module.crm.service.crminvoice.CrmInvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 发票")
@RestController
@RequestMapping("/crm/invoice")
@Validated
public class CrmInvoiceController {

    @Resource
    private CrmInvoiceService invoiceService;

    @PostMapping("/create")
    @Operation(summary = "创建发票")
    @PreAuthorize("@ss.hasPermission('crm:invoice:create')")
    public CommonResult<Long> createInvoice(@Valid @RequestBody CrmInvoiceSaveReqVO createReqVO) {
        return success(invoiceService.createInvoice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新发票")
    @PreAuthorize("@ss.hasPermission('crm:invoice:update')")
    public CommonResult<Boolean> updateInvoice(@Valid @RequestBody CrmInvoiceSaveReqVO updateReqVO) {
        invoiceService.updateInvoice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除发票")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:invoice:delete')")
    public CommonResult<Boolean> deleteInvoice(@RequestParam("id") Long id) {
        invoiceService.deleteInvoice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得发票")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:invoice:query')")
    public CommonResult<CrmInvoiceRespVO> getInvoice(@RequestParam("id") Long id) {
        return success(invoiceService.getInvoice(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得发票分页")
    @PreAuthorize("@ss.hasPermission('crm:invoice:query')")
    public CommonResult<PageResult<CrmInvoiceRespVO>> getInvoicePage(@Valid CrmInvoicePageReqVO pageReqVO) {
        PageResult<CrmInvoiceRespVO> pageResult = invoiceService.getInvoicePage(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/check")
    @Operation(summary = "审核发票")
    public CommonResult<Boolean> check(@Valid @RequestBody CheckInfoVO checkInfoVO) {
        invoiceService.check(checkInfoVO);
        return success(true);
    }

    @PutMapping("/issue")
    @Operation(summary = "开具发票")
    public CommonResult<Boolean> issueInvoice(@Valid @RequestBody CrmInvoiceSaveReqVO updateReqVO) {
        invoiceService.issueInvoice(updateReqVO);
        return success(true);
    }



}