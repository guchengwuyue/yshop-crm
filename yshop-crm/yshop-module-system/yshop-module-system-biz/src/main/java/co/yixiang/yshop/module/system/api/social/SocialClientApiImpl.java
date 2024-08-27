package co.yixiang.yshop.module.system.api.social;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
import co.yixiang.yshop.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import co.yixiang.yshop.module.system.service.social.SocialClientService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 社交应用的 API 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class SocialClientApiImpl implements SocialClientApi {

    @Resource
    private SocialClientService socialClientService;

    @Override
    public String getAuthorizeUrl(Integer socialType, Integer userType, String redirectUri) {
        return socialClientService.getAuthorizeUrl(socialType, userType, redirectUri);
    }

    @Override
    public SocialWxJsapiSignatureRespDTO createWxMpJsapiSignature(Integer userType, String url) {
        WxJsapiSignature signature = socialClientService.createWxMpJsapiSignature(userType, url);
        return BeanUtils.toBean(signature, SocialWxJsapiSignatureRespDTO.class);
    }

    @Override
    public SocialWxPhoneNumberInfoRespDTO getWxMaPhoneNumberInfo(Integer userType, String phoneCode) {
        WxMaPhoneNumberInfo info = socialClientService.getWxMaPhoneNumberInfo(userType, phoneCode);
        return BeanUtils.toBean(info, SocialWxPhoneNumberInfoRespDTO.class);
    }

}
