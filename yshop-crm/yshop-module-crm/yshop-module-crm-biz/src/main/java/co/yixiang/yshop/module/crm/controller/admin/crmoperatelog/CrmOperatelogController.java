package co.yixiang.yshop.module.crm.controller.admin.crmoperatelog;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo.CrmOperatelogPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo.CrmOperatelogRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmoperatelog.CrmOperatelogDO;
import co.yixiang.yshop.module.crm.service.crmoperatelog.CrmOperatelogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 操作日志")
@RestController
@RequestMapping("/crm/operatelog")
@Validated
public class CrmOperatelogController {

    @Resource
    private CrmOperatelogService operatelogService;



    @GetMapping("/page")
    @Operation(summary = "获得操作日志分页")
    public CommonResult<PageResult<CrmOperatelogRespVO>> getOperatelogPage(@Valid CrmOperatelogPageReqVO pageReqVO) {
        PageResult<CrmOperatelogDO> pageResult = operatelogService.getOperatelogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CrmOperatelogRespVO.class));
    }


}