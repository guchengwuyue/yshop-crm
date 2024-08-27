package co.yixiang.yshop.module.message.service.wechattemplate;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo.*;
import co.yixiang.yshop.module.message.dal.dataobject.wechattemplate.WechatTemplateDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 微信模板 Service 接口
 *
 * @author yshop
 */
public interface WechatTemplateService extends IService<WechatTemplateDO> {

    /**
     * 创建微信模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createWechatTemplate(@Valid WechatTemplateCreateReqVO createReqVO);

    /**
     * 更新微信模板
     *
     * @param updateReqVO 更新信息
     */
    void updateWechatTemplate(@Valid WechatTemplateUpdateReqVO updateReqVO);

    /**
     * 删除微信模板
     *
     * @param id 编号
     */
    void deleteWechatTemplate(Integer id);

    /**
     * 获得微信模板
     *
     * @param id 编号
     * @return 微信模板
     */
    WechatTemplateDO getWechatTemplate(Integer id);

    /**
     * 获得微信模板列表
     *
     * @param ids 编号
     * @return 微信模板列表
     */
    List<WechatTemplateDO> getWechatTemplateList(Collection<Integer> ids);

    /**
     * 获得微信模板分页
     *
     * @param pageReqVO 分页查询
     * @return 微信模板分页
     */
    PageResult<WechatTemplateDO> getWechatTemplatePage(WechatTemplatePageReqVO pageReqVO);

    /**
     * 获得微信模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 微信模板列表
     */
    List<WechatTemplateDO> getWechatTemplateList(WechatTemplateExportReqVO exportReqVO);

}
