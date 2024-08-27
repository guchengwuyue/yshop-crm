package co.yixiang.yshop.module.crm.service.crmoperatelog;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.servlet.ServletUtils;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo.CrmOperatelogPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmoperatelog.CrmOperatelogDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmoperatelog.CrmOperatelogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 操作日志 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmOperatelogServiceImpl implements CrmOperatelogService {

    @Resource
    private CrmOperatelogMapper crmOperatelogMapper;


    @Override
    public void createLog(String title, Long customerId, Long contractId, Long receivableId) {
        CrmOperatelogDO crmOperatelogDO = CrmOperatelogDO.builder()
                .title(title)
                .customerId(customerId)
                .contractId(contractId)
                .receivableId(receivableId)
                .ip(ServletUtils.getClientIP())
                .useragent(ServletUtils.getUserAgent())
                .url(ServletUtils.getUrl())
                .nickname(SecurityFrameworkUtils.getLoginUser().getInfo().get("nickname"))
                .username(SecurityFrameworkUtils.getLoginUser().getInfo().get("nickname"))
                .build();

        crmOperatelogMapper.insert(crmOperatelogDO);
    }


    @Override
    public PageResult<CrmOperatelogDO> getOperatelogPage(CrmOperatelogPageReqVO pageReqVO) {
        return crmOperatelogMapper.selectPage(pageReqVO);
    }
}