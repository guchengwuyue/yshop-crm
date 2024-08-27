package co.yixiang.yshop.module.message.controller.admin.wechattemplate;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import java.util.*;
import java.io.IOException;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.CommonResult;
import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

import co.yixiang.yshop.framework.excel.core.util.ExcelUtils;

import co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo.*;
import co.yixiang.yshop.module.message.dal.dataobject.wechattemplate.WechatTemplateDO;
import co.yixiang.yshop.module.message.convert.wechattemplate.WechatTemplateConvert;
import co.yixiang.yshop.module.message.service.wechattemplate.WechatTemplateService;

@Tag(name = "管理后台 - 微信模板")
@RestController
@RequestMapping("/message/wechat-template")
@Validated
public class WechatTemplateController {

    @Resource
    private WechatTemplateService wechatTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建微信模板")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:create')")
    public CommonResult<Integer> createWechatTemplate(@Valid @RequestBody WechatTemplateCreateReqVO createReqVO) {
        return success(wechatTemplateService.createWechatTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新微信模板")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:update')")
    public CommonResult<Boolean> updateWechatTemplate(@Valid @RequestBody WechatTemplateUpdateReqVO updateReqVO) {
        wechatTemplateService.updateWechatTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除微信模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('message:wechat-template:delete')")
    public CommonResult<Boolean> deleteWechatTemplate(@RequestParam("id") Integer id) {
        wechatTemplateService.deleteWechatTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得微信模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:query')")
    public CommonResult<WechatTemplateRespVO> getWechatTemplate(@RequestParam("id") Integer id) {
        WechatTemplateDO wechatTemplate = wechatTemplateService.getWechatTemplate(id);
        return success(WechatTemplateConvert.INSTANCE.convert(wechatTemplate));
    }

    @GetMapping("/list")
    @Operation(summary = "获得微信模板列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:query')")
    public CommonResult<List<WechatTemplateRespVO>> getWechatTemplateList(@RequestParam("ids") Collection<Integer> ids) {
        List<WechatTemplateDO> list = wechatTemplateService.getWechatTemplateList(ids);
        return success(WechatTemplateConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得微信模板分页")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:query')")
    public CommonResult<PageResult<WechatTemplateRespVO>> getWechatTemplatePage(@Valid WechatTemplatePageReqVO pageVO) {
        PageResult<WechatTemplateDO> pageResult = wechatTemplateService.getWechatTemplatePage(pageVO);
        return success(WechatTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出微信模板 Excel")
    @PreAuthorize("@ss.hasPermission('message:wechat-template:export')")
    public void exportWechatTemplateExcel(@Valid WechatTemplateExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<WechatTemplateDO> list = wechatTemplateService.getWechatTemplateList(exportReqVO);
        // 导出 Excel
        List<WechatTemplateExcelVO> datas = WechatTemplateConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "微信模板.xls", "数据", WechatTemplateExcelVO.class, datas);
    }

}
