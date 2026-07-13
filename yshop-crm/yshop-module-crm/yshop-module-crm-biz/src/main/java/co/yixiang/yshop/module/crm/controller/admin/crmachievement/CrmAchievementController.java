package co.yixiang.yshop.module.crm.controller.admin.crmachievement;

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

import co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmachievement.CrmAchievementDO;
import co.yixiang.yshop.module.crm.service.crmachievement.CrmAchievementService;

@Tag(name = "管理后台 - 业绩目标")
@RestController
@RequestMapping("/crm/achievement")
@Validated
public class CrmAchievementController {

    @Resource
    private CrmAchievementService achievementService;

    @PostMapping("/create")
    @Operation(summary = "创建业绩目标")
    @PreAuthorize("@ss.hasPermission('crm:achievement:create')")
    public CommonResult<Boolean> createAchievement(@Valid @RequestBody DeptAchieveSaveVO createReqVO) {
        achievementService.createAchievement(createReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新业绩目标")
    @PreAuthorize("@ss.hasPermission('crm:achievement:update')")
    public CommonResult<Boolean> updateAchievement(@Valid @RequestBody CrmAchievementSaveReqVO updateReqVO) {
        achievementService.updateAchievement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业绩目标")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:achievement:delete')")
    public CommonResult<Boolean> deleteAchievement(@RequestParam("id") Long id) {
        achievementService.deleteAchievement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业绩目标")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:achievement:query')")
    public CommonResult<CrmAchievementRespVO> getAchievement(@RequestParam("id") Long id) {
        CrmAchievementDO achievement = achievementService.getAchievement(id);
        return success(BeanUtils.toBean(achievement, CrmAchievementRespVO.class));
    }

    @GetMapping("/depts")
    @Operation(summary = "获得部门业绩目标")
    @PreAuthorize("@ss.hasPermission('crm:achievement:query')")
    public CommonResult<List<DeptAchieveRespVO>> getDeptsAchievement(@RequestParam(name = "type",required = false) Integer type,
                                                                     @RequestParam(name = "year",required = false) Integer year) {
        List<DeptAchieveRespVO> deptAchieveRespVOS = achievementService.getDeptAchieve(type,year);
        return success(deptAchieveRespVOS);
    }

    @GetMapping("/page")
    @Operation(summary = "获得员工业绩目标分页")
    @PreAuthorize("@ss.hasPermission('crm:achievement:query')")
    public CommonResult<PageResult<UserAchieveRespVO>> getAchievementPage(@Valid CrmAchievementPageReqVO pageReqVO) {
        return success(achievementService.getAchievementPage(pageReqVO));
    }

    @GetMapping("/count")
    @Operation(summary = "业绩目标统计")
    public CommonResult<AchieveCountRespVO> getAchievementCount(@RequestParam(name = "type",required = false) Integer type,
                                                                @RequestParam(name = "month",required = false) Integer month,
                                                                @RequestParam(name = "year",required = false) Integer year) {
        return success(achievementService.getCount(type,year,month));
    }

    @GetMapping("/detailCount")
    @Operation(summary = "员工详细业绩目标统计")
    public CommonResult<List<UserAchieveDetailRespVO>> getAchievementDetailCount(@Valid CrmAchievementPageReqVO pageReqVO) {
        return success(achievementService.getAchievementDetailCount(pageReqVO));
    }


}