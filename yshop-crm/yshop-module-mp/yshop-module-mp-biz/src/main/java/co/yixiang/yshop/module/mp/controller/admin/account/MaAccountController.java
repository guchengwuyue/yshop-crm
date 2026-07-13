package co.yixiang.yshop.module.mp.controller.admin.account;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountCreateReqVO;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountPageReqVO;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountRespVO;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountUpdateReqVO;
import co.yixiang.yshop.module.mp.convert.account.MpAccountConvert;
import co.yixiang.yshop.module.mp.dal.dataobject.account.MpAccountDO;
import co.yixiang.yshop.module.mp.enums.MpAccountEnum;
import co.yixiang.yshop.module.mp.service.account.MpAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 小程序账号")
@RestController
@RequestMapping("/ma/account")
@Validated
public class MaAccountController {

    @Resource
    private MpAccountService mpAccountService;

    @PostMapping("/create")
    @Operation(summary = "创建小程序账号")
    @PreAuthorize("@ss.hasPermission('ma:account:create')")
    public CommonResult<Long> createAccount(@Valid @RequestBody MpAccountCreateReqVO createReqVO) {
        createReqVO.setIsMiapp(MpAccountEnum.IS_MINI_1.getValue());
        return success(mpAccountService.createAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新小程序账号")
    @PreAuthorize("@ss.hasPermission('ma:account:update')")
    public CommonResult<Boolean> updateAccount(@Valid @RequestBody MpAccountUpdateReqVO updateReqVO) {
        mpAccountService.updateAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除小程序账号")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ma:account:delete')")
    public CommonResult<Boolean> deleteAccount(@RequestParam("id") Long id) {
        mpAccountService.deleteAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得小程序账号")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ma:account:query')")
    public CommonResult<MpAccountRespVO> getAccount(@RequestParam("id") Long id) {
        MpAccountDO wxAccount = mpAccountService.getAccount(id);
        return success(MpAccountConvert.INSTANCE.convert(wxAccount));
    }

    @GetMapping("/page")
    @Operation(summary = "获得小程序账号分页")
    @PreAuthorize("@ss.hasPermission('ma:account:query')")
    public CommonResult<PageResult<MpAccountRespVO>> getAccountPage(@Valid MpAccountPageReqVO pageVO) {
        pageVO.setIsMini(MpAccountEnum.IS_MAIN_1.getValue());
        PageResult<MpAccountDO> pageResult = mpAccountService.getAccountPage(pageVO);
        return success(MpAccountConvert.INSTANCE.convertPage(pageResult));
    }



    @PutMapping("/set-main")
    @Operation(summary = "设置小程序账号未主账户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ma:account:set-main')")
    public CommonResult<Boolean> setAccountMain(@RequestParam("id") Long id) {
        mpAccountService.setMain(id, MpAccountEnum.IS_MINI_1.getValue());
        return success(true);
    }

}
