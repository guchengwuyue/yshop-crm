/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.yshop.module.system.controller.app.notice;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.system.controller.admin.notice.vo.NoticePageReqVO;
import co.yixiang.yshop.module.system.controller.admin.notice.vo.NoticeRespVO;
import co.yixiang.yshop.module.system.dal.dataobject.notice.NoticeDO;
import co.yixiang.yshop.module.system.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 新闻控制器
 * </p>
 *
 * @author hupeng
 * @since 2024-5-6
 */
@Slf4j
@RestController
@Tag(name = "用户 APP - 新闻")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/news")
public class AppNoticeController {

    private final NoticeService noticeService;


    @GetMapping("/list")
    @Operation(summary = "列表")
    public CommonResult<List<NoticeRespVO>> getList() {
        NoticePageReqVO noticePageReqVO = new NoticePageReqVO();
        noticePageReqVO.setPageSize(2);
        List<NoticeDO> noticeDOS = noticeService.getNoticePage(noticePageReqVO).getList();
        return success(BeanUtils.toBean(noticeDOS, NoticeRespVO.class));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "详情")
    public CommonResult<NoticeRespVO> getDetail(@PathVariable Long id) {
        return success(BeanUtils.toBean(noticeService.getNotice(id), NoticeRespVO.class));
    }





}

