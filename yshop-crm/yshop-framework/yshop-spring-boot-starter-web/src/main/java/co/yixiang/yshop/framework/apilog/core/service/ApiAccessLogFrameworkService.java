package co.yixiang.yshop.framework.apilog.core.service;

import co.yixiang.yshop.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;

/**
 * API 访问日志 Framework Service 接口
 *
 * @author yshop
 */
public interface ApiAccessLogFrameworkService {

    /**
     * 创建 API 访问日志
     *
     * @param reqDTO API 访问日志
     */
    void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO);

}
