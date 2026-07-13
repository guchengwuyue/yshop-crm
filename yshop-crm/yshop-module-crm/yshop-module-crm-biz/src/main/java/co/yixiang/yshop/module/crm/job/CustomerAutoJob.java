package co.yixiang.yshop.module.crm.job;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.quartz.core.handler.JobHandler;
import co.yixiang.yshop.framework.redis.util.redis.RedisUtil;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.service.crmcustomer.CrmCustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 自动公海任务
 */
@Component
@Slf4j
public class CustomerAutoJob implements JobHandler {
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmCustomerService customerService;

    @Override
    public String execute(String param) {
        Integer notRecordDay = RedisUtil.get("notRecordDay");
        Integer notSuccessDay = RedisUtil.get("notSuccessDay");
        if((notRecordDay == null || notSuccessDay == null) || (notRecordDay == 0 && notSuccessDay == 0)){
            log.info("=======不需要执行后面任务=======");
            return "ok";
        }
        LocalDateTime nowTime = LocalDateTime.now();
        customerMapper.selectList(new LambdaQueryWrapper<CrmCustomerDO>()
                .eq(CrmCustomerDO::getDealStatus, ShopCommonEnum.IS_STATUS_0.getValue())
                        .ne(CrmCustomerDO::getOwnerUserId,0),
                new ResultHandler<CrmCustomerDO>() {
            int count = 0;
            @Override
            public void handleResult(ResultContext<? extends CrmCustomerDO> resultContext) {
                CrmCustomerDO customerDO = resultContext.getResultObject();
                log.info("当前处理第" + (++count) + "条记录ID: " + customerDO.getId());
                //处理不跟进 放入公海
                if(notRecordDay > 0){
                    LocalDateTime followTime = customerDO.getCreateTime();
                    if(customerDO.getFollowTime() != null) {
                        followTime = customerDO.getFollowTime();
                    }
                    Duration between = LocalDateTimeUtil.between(nowTime,followTime);
                    Long days = Math.abs(between.toDays());
                    //放入公海
                    if(NumberUtil.compare(days,notRecordDay) > 0){
                        customerService.openCustomer(customerDO.getId());
                        log.info("长时间未跟进，客户ID:{},投入公海成功 ", customerDO.getId());
                    }
                }

                //处理长时间未成交
                if(notSuccessDay > 0){
                    LocalDateTime createTime = customerDO.getCreateTime();
                    Duration between = LocalDateTimeUtil.between(nowTime,createTime);
                    Long days = Math.abs(between.toDays());
                    //放入公海
                    if(NumberUtil.compare(days,notSuccessDay) > 0){
                        customerService.openCustomer(customerDO.getId());
                        log.info("长时间未成交，客户ID:{},投入公海成功 ", customerDO.getId());
                    }
                }


            }
        });
        return null;
    }
}
