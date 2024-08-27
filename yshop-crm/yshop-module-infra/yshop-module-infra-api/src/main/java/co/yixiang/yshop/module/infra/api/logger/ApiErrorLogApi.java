package co.yixiang.yshop.module.infra.api.logger;

import co.yixiang.yshop.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;

import jakarta.validation.Valid;

/**
 * API 错误日志的 API 接口
 *
 * @author yshop
 */
public interface ApiErrorLogApi {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO createDTO);

}
