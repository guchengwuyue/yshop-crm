package co.yixiang.yshop.module.crm.controller.admin.crmrecord;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.CrmRecordPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.CrmRecordRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo.CrmRecordSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.module.crm.service.crmrecord.CrmRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 跟进记录")
@RestController
@RequestMapping("/crm/record")
@Validated
public class CrmRecordController {

    @Resource
    private CrmRecordService crmRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建跟进记录")
    @PreAuthorize("@ss.hasPermission('crm:record:create')")
    public CommonResult<Long> createRecord(@Valid @RequestBody CrmRecordSaveReqVO createReqVO) {
        return success(crmRecordService.createRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新跟进记录")
    @PreAuthorize("@ss.hasPermission('crm:record:update')")
    public CommonResult<Boolean> updateRecord(@Valid @RequestBody CrmRecordSaveReqVO updateReqVO) {
        crmRecordService.updateRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除跟进记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:record:delete')")
    public CommonResult<Boolean> deleteRecord(@RequestParam("id") Long id) {
        crmRecordService.deleteRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得跟进记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:record:query')")
    public CommonResult<CrmRecordRespVO> getRecord(@RequestParam("id") Long id) {
        CrmRecordDO record = crmRecordService.getRecord(id);
        return success(BeanUtils.toBean(record, CrmRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得跟进记录分页")
    @PreAuthorize("@ss.hasPermission('crm:record:query')")
    public CommonResult<PageResult<CrmRecordRespVO>> getRecordPage(@Valid CrmRecordPageReqVO pageReqVO) {
        return success(crmRecordService.getRecordPage(pageReqVO));
    }



}