package co.yixiang.yshop.module.crm.service.crmpay;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import co.yixiang.yshop.framework.common.enums.OrderInfoEnum;
import co.yixiang.yshop.framework.common.enums.PayIdEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.util.servlet.ServletUtils;
import co.yixiang.yshop.framework.tenant.core.aop.TenantIgnore;
import co.yixiang.yshop.framework.tenant.core.context.TenantContextHolder;
import co.yixiang.yshop.module.crm.controller.admin.crmpay.param.AppPayParam;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.enums.AppFromEnum;
import co.yixiang.yshop.module.crm.enums.ContractStatusEnum;
import co.yixiang.yshop.module.crm.enums.PayTypeEnum;
import co.yixiang.yshop.module.message.mq.producer.WeixinNoticeProducer;
import co.yixiang.yshop.module.pay.dal.dataobject.merchantdetails.MerchantDetailsDO;
import co.yixiang.yshop.module.pay.service.merchantdetails.MerchantDetailsService;
import co.yixiang.yshop.module.product.service.storeproductattrvalue.StoreProductAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 订单 Service 实现类
 *
 * @author yshop
 */
@Slf4j
@Service
@Validated
public class PayServiceImpl  implements PayService {

    @Resource
    private PayServiceManager manager;
    @Resource
    private MerchantDetailsService merchantDetailsService;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmContractMapper contractMapper;

    @Value("${yshop.file-path}")
    private String path;




    /**
     * 第三方支付
     * @param uid  用户id
     * @param param 订单参数
     * @return
     */
    @Override
    public String pay(Long uid, AppPayParam param) {
        CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper
                .selectOne(new LambdaQueryWrapper<CrmContractReceivablesDO>()
                .eq(CrmContractReceivablesDO::getNumber,param.getUni()).eq(CrmContractReceivablesDO::getPayStatus,
                        OrderInfoEnum.PAY_STATUS_0));

        if (ObjectUtil.isNull(crmContractReceivablesDO)) {
            throw exception(new ErrorCode(202503130,"没有未支付回款订单"));
        }

        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal price =  crmContractReceivablesDO.getMoney();
        String msg = "回款单号:" + crmContractReceivablesDO.getNumber() + "付款";
        String detailsId = "";
        String qrcode = "";

        //查看支付二维码是否存在且有效期
        if(StrUtil.isNotEmpty(crmContractReceivablesDO.getCodeUrl())){
            LocalDateTime nowTime = LocalDateTime.now();
            Duration between = LocalDateTimeUtil.between(nowTime, crmContractReceivablesDO.getPayTime());
            Long hours = between.toHours();
            if(Math.abs(hours) < 2){
                return crmContractReceivablesDO.getCodeUrl();
            }
        }

        switch (PayTypeEnum.toType(param.getPaytype())){
            case WEIXIN:
                detailsId = PayIdEnum.WX_WECHAT.getValue();
                MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, "NATIVE", msg,
                        msg, price, param.getUni());
                try{
                    Map<String, Object> payOrderInfo = manager.getOrderInfo(payOrder);
                    map.put("data",payOrderInfo);
                    map.put("trade_type","QRCODE");
                    qrcode = payOrderInfo.get("code_url").toString();
                }catch (PayErrorException e){
                    log.error(e.getMessage());
                    throw exception(new ErrorCode(202503170,"支付失败，请联系管理"));
                }

                break;
            case ALI:
                detailsId = PayIdEnum.ALI_H5.getValue();
                MerchantPayOrder payOrder2 = new MerchantPayOrder(detailsId, "SWEEPPAY", msg,
                        msg, price, param.getUni());

                try{
                    Map<String, Object> mapRes =  manager.getOrderInfo(payOrder2);
                    map.put("data",mapRes);
                    map.put("trade_type","QRCODE");
                    qrcode = mapRes.get("qr_code").toString();
                }catch (PayErrorException e){
                    log.error(e.getMessage());
                    throw exception(new ErrorCode(202503171,"支付失败，请联系管理"));
                }
            default:
        }

        //存储二维码
        String filePath = path + "qrcode" + File.separator;
        String fileName = crmContractReceivablesDO.getNumber()  + ".jpg";
        File target = new File(filePath + fileName);
        QrCodeUtil.generate(qrcode, 300, 300, target);
        crmContractReceivablesDO.setCodeUrl("file" + File.separator  +"qrcode" + File.separator + fileName);
        crmContractReceivablesDO.setPayTime(LocalDateTime.now());
        contractReceivablesMapper.updateById(crmContractReceivablesDO);
        return crmContractReceivablesDO.getCodeUrl();
    }



    /**
     * 支付成功后操作
     *
     * @param orderId 订单号
     * @param payType 支付方式
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @TenantIgnore
    public void paySuccess(String orderId, String payType) {
        log.info("===========hu============");
        CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper
                .selectOne(new LambdaQueryWrapper<CrmContractReceivablesDO>()
                        .eq(CrmContractReceivablesDO::getNumber,orderId));

        crmContractReceivablesDO.setPayStatus(OrderInfoEnum.PAY_STATUS_1.getValue());
        crmContractReceivablesDO.setReturnTime(LocalDateTime.now());
        crmContractReceivablesDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
        crmContractReceivablesDO.setAccountId(4);
        contractReceivablesMapper.updateById(crmContractReceivablesDO);

        CrmContractDO crmContractDO = new CrmContractDO();
        crmContractDO.setReturnMoney(crmContractReceivablesDO.getMoney());
        crmContractDO.setId(crmContractReceivablesDO.getContractId());

        contractMapper.updateById(crmContractDO);
    }












}
