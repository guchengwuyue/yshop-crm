package co.yixiang.yshop.module.crm.controller.admin.crmanalysis;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo.*;
import co.yixiang.yshop.module.crm.service.crmanalysis.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 业绩分析")
@RestController
@RequestMapping("/crm/analysis-achievement")
@Validated
public class AchievementController {

    @Resource
    private AchievementService achievementService;



    @GetMapping("/sales")
    @Operation(summary = "销售简报")
    public CommonResult<List<SalesVO>> getSales(@RequestParam(name = "relation",defaultValue = "my",required=false) String relation) {
        return success(achievementService.getSales(relation));
    }




}