package co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.service.crmcontractreceivables.CrmContractReceivablesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 回款")
@RestController
@RequestMapping("/crm/contract-receivables")
@Validated
public class CrmContractReceivablesController {

    @Resource
    private CrmContractReceivablesService contractReceivablesService;

    @PostMapping("/create")
    @Operation(summary = "创建回款")
    @PreAuthorize("@ss.hasPermission('crm:contract-receivables:create')")
    public CommonResult<Long> createContractReceivables(@Valid @RequestBody CrmContractReceivablesSaveReqVO createReqVO) {
        return success(contractReceivablesService.createContractReceivables(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新回款")
    @PreAuthorize("@ss.hasPermission('crm:contract-receivables:update')")
    public CommonResult<Boolean> updateContractReceivables(@Valid @RequestBody CrmContractReceivablesSaveReqVO updateReqVO) {
        contractReceivablesService.updateContractReceivables(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除回款")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:contract-receivables:delete')")
    public CommonResult<Boolean> deleteContractReceivables(@RequestParam("id") Long id) {
        contractReceivablesService.deleteContractReceivables(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得回款")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:contract-receivables:query')")
    public CommonResult<CrmContractReceivablesRespVO> getContractReceivables(@RequestParam("id") Long id) {
        CrmContractReceivablesRespVO contractReceivables = contractReceivablesService.getContractReceivables(id);
        return success(contractReceivables);
    }

    @GetMapping("/page")
    @Operation(summary = "获得回款分页")
    @PreAuthorize("@ss.hasPermission('crm:contract-receivables:query')")
    public CommonResult<PageResult<CrmContractReceivablesRespVO>> getContractReceivablesPage(@Valid CrmContractReceivablesPageReqVO pageReqVO) {
        PageResult<CrmContractReceivablesRespVO> pageResult = contractReceivablesService.getContractReceivablesPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/getCode")
    @Operation(summary = "获得编号")
    public CommonResult<String> getCode() {
        return success(contractReceivablesService.getCode());
    }

    @PostMapping("/check")
    @Operation(summary = "审核回款")
    public CommonResult<Boolean> check(@Valid @RequestBody CheckInfoVO checkInfoVO) {
        contractReceivablesService.check(checkInfoVO);
        return success(true);
    }

}