package co.yixiang.yshop.module.shop.controller.admin.materialgroup;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.*;
import co.yixiang.yshop.module.shop.convert.materialgroup.MaterialGroupConvert;
import co.yixiang.yshop.module.shop.dal.dataobject.materialgroup.MaterialGroupDO;
import co.yixiang.yshop.module.shop.service.materialgroup.MaterialGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 素材分组")
@RestController
@RequestMapping("/shop/material-group")
@Validated
public class MaterialGroupController {

    @Resource
    private MaterialGroupService materialGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建素材分组")
    //@PreAuthorize("@ss.hasPermission('shop:material-group:create')")
    public CommonResult<Long> createMaterialGroup(@Valid @RequestBody MaterialGroupCreateReqVO createReqVO) {
        return success(materialGroupService.createMaterialGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新素材分组")
    //@PreAuthorize("@ss.hasPermission('shop:material-group:update')")
    public CommonResult<Boolean> updateMaterialGroup(@Valid @RequestBody MaterialGroupUpdateReqVO updateReqVO) {
        materialGroupService.updateMaterialGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除素材分组")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('shop:material-group:delete')")
    public CommonResult<Boolean> deleteMaterialGroup(@RequestParam("id") Long id) {
        materialGroupService.deleteMaterialGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得素材分组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('shop:material-group:query')")
    public CommonResult<MaterialGroupRespVO> getMaterialGroup(@RequestParam("id") Long id) {
        MaterialGroupDO materialGroup = materialGroupService.getMaterialGroup(id);
        return success(MaterialGroupConvert.INSTANCE.convert(materialGroup));
    }

    @GetMapping("/list")
    @Operation(summary = "获得素材分组列表")
    //@Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    //@PreAuthorize("@ss.hasPermission('shop:material-group:query')")
    public CommonResult<List<MaterialGroupRespVO>> getMaterialGroupList() {
        MaterialGroupExportReqVO exportReqVO = new MaterialGroupExportReqVO();
        List<MaterialGroupDO> list = materialGroupService.getMaterialGroupList(exportReqVO);
        return success(MaterialGroupConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得素材分组分页")
    //@PreAuthorize("@ss.hasPermission('shop:material-group:query')")
    public CommonResult<PageResult<MaterialGroupRespVO>> getMaterialGroupPage(@Valid MaterialGroupPageReqVO pageVO) {
        PageResult<MaterialGroupDO> pageResult = materialGroupService.getMaterialGroupPage(pageVO);
        return success(MaterialGroupConvert.INSTANCE.convertPage(pageResult));
    }



}
