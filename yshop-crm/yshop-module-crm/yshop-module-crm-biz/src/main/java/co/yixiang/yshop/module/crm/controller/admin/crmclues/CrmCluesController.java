package co.yixiang.yshop.module.crm.controller.admin.crmclues;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.CrmCluesPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.CrmCluesRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmclues.vo.CrmCluesSaveReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.service.crmclues.CrmCluesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 线索")
@RestController
@RequestMapping("/crm/clues")
@Validated
public class CrmCluesController {

    @Resource
    private CrmCluesService cluesService;

    @PostMapping("/create")
    @Operation(summary = "创建线索")
    @PreAuthorize("@ss.hasPermission('crm:clues:create')")
    public CommonResult<Long> createClues(@Valid @RequestBody CrmCluesSaveReqVO createReqVO) {
        return success(cluesService.createClues(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新线索")
    @PreAuthorize("@ss.hasPermission('crm:clues:update')")
    public CommonResult<Boolean> updateClues(@Valid @RequestBody CrmCluesSaveReqVO updateReqVO) {
        cluesService.updateClues(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除线索")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:clues:delete')")
    public CommonResult<Boolean> deleteClues(@RequestParam("id") Long id) {
        cluesService.deleteClues(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得线索")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:clues:query')")
    public CommonResult<CrmCluesRespVO> getClues(@RequestParam("id") Long id) {
        CrmCluesDO clues = cluesService.getClues(id);
        return success(BeanUtils.toBean(clues, CrmCluesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得线索分页")
    @PreAuthorize("@ss.hasPermission('crm:clues:query')")
    public CommonResult<PageResult<CrmCluesRespVO>> getCluesPage(@Valid CrmCluesPageReqVO pageReqVO) {
        return success(cluesService.getCluesPage(pageReqVO));
    }

    @GetMapping("/open")
    @Operation(summary = "放入线索池")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:clues:query')")
    public CommonResult<Boolean> openClues(@RequestParam("id") Long id) {
        cluesService.openClues(id);
        return success(true);
    }

    @GetMapping("/receive")
    @Operation(summary = "领取线索")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> receiveCustomer(@RequestParam("id") Long id) {
        cluesService.receiveClues(id);
        return success(true);
    }

    @PostMapping("/createCustomer")
    @Operation(summary = "转换客户")
    @PreAuthorize("@ss.hasPermission('crm:clues:create-customer')")
    public CommonResult<Boolean> createCustomer(@Valid @RequestBody CrmCustomerSaveReqVO createReqVO) {
        cluesService.createCustomer(createReqVO);
        return success(true);
    }



}