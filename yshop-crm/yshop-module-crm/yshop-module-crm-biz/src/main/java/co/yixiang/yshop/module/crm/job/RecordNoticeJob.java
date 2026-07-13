package co.yixiang.yshop.module.crm.job;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import co.yixiang.yshop.framework.quartz.core.handler.JobHandler;
import co.yixiang.yshop.module.crm.dal.dataobject.crmmessage.CrmMessageDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmmessage.CrmMessageMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmrecord.CrmRecordMapper;
import co.yixiang.yshop.module.crm.enums.MessageTypesEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.message.enums.WechatTempateEnum;
import co.yixiang.yshop.module.message.mq.producer.WeixinNoticeProducer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 跟进提醒任务
 */
@Component
@Slf4j
public class RecordNoticeJob implements JobHandler {
    @Resource
    private CrmRecordMapper crmRecordMapper;
    @Resource
    private CrmMessageMapper crmMessageMapper;
    @Resource
    private WeixinNoticeProducer weixinNoticeProducer;

    @Override
    public String execute(String param) {
        log.info("=======执行任务start=======");
        //获取一天的开始与结束
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime beginOfDay = LocalDateTimeUtil.beginOfDay(nowTime);
        LocalDateTime endOfDay = LocalDateTimeUtil.endOfDay(nowTime);
        crmRecordMapper.selectList(new LambdaQueryWrapper<CrmRecordDO>()
                        .isNotNull(CrmRecordDO::getNextTime).between(CrmRecordDO::getNextTime, beginOfDay,endOfDay),
                new ResultHandler<CrmRecordDO>() {
            @Override
            public void handleResult(ResultContext<? extends CrmRecordDO> resultContext) {
                CrmRecordDO crmRecordDO = resultContext.getResultObject();
                String content = crmRecordDO.getContent();
                String type = "";
                if(TypesEnum.CUSTOMER.getValue().equals(crmRecordDO.getTypes())){
                    content = content + "(客户跟进)";
                    type = MessageTypesEnum.RECORD_CUSTOMER.getValue();
                }else if(TypesEnum.BUSINESS.getValue().equals(crmRecordDO.getTypes())){
                    content = content + "(商机跟进)";
                    type = MessageTypesEnum.RECORD_BUSINESS.getValue();
                }else if(TypesEnum.CLUES.getValue().equals(crmRecordDO.getTypes())){
                    content = content + "(线索跟进)";
                    type = MessageTypesEnum.RECORD_CLUES.getValue();
                }
                //添加消息通知
                CrmMessageDO crmMessageDO = CrmMessageDO.builder()
                        .relationType(type)
                        .relationId(crmRecordDO.getId())
                        .toUserId(Long.valueOf(crmRecordDO.getCreator()))
                        .fromUserId(0L)
                        .content(content)
                        .sendTime(LocalDateTime.now())
                        .build();
                crmMessageMapper.insert(crmMessageDO);
                //模板消息通知
                weixinNoticeProducer.sendNoticeMessage(Long.valueOf(crmRecordDO.getCreator()),
                        WechatTempateEnum.RECORD_NOTICE.getValue(),
                        WechatTempateEnum.TEMPLATES.getValue(),content,
                        LocalDateTimeUtil.format(crmRecordDO.getNextTime(), DatePattern.NORM_DATETIME_PATTERN));



            }
        });
        return "ok";
    }
}
