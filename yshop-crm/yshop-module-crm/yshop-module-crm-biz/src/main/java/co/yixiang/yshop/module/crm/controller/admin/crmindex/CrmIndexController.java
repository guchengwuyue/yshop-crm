package co.yixiang.yshop.module.crm.controller.admin.crmindex;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.CrmIndexRespVO;
import co.yixiang.yshop.module.crm.service.crmindex.CrmIndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 首页统计")
@RestController
@RequestMapping("/crm/index")
@Validated
public class CrmIndexController {
    @Resource
    private CrmIndexService crmIndexService;

    @GetMapping("/getCount")
    @Operation(summary = "首页统计")
    public CommonResult<CrmIndexRespVO> getCount() {
        return success(crmIndexService.getIndexCount());
    }

}