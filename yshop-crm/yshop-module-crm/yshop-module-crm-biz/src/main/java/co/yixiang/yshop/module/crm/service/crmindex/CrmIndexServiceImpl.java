package co.yixiang.yshop.module.crm.service.crmindex;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import co.yixiang.yshop.framework.common.enums.CommonStatusEnum;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.BrieCountVO;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.CrmIndexRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomercontacts.CrmCustomerContactsMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmrecord.CrmRecordMapper;
import co.yixiang.yshop.module.crm.dal.redis.RedisKeyConstants;
import co.yixiang.yshop.module.crm.enums.CluesStatusEnum;
import co.yixiang.yshop.module.crm.enums.FlowLogStatusEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.dal.dataobject.notice.NoticeDO;
import co.yixiang.yshop.module.system.dal.mysql.notice.NoticeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 首页统计 Service 实现类
 *
 * <p>将今日/昨日对比改为 create_time BETWEEN；同一表用 SUM(CASE WHEN) 一次查出；
 * 对高频接口加短 TTL Redis 缓存（60s）。
 *
 * @author yshop
 */
@Service
@Validated
public class CrmIndexServiceImpl implements CrmIndexService {

    @Resource
    private CrmBusinessMapper businessMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmCluesMapper crmCluesMapper;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmRecordMapper crmRecordMapper;
    @Resource
    private CrmCustomerContactsMapper customerContactsMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private CrmFlowLogMapper crmFlowLogMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.CRM_INDEX_COUNT,
            key = "T(co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils).getLoginUserId()"
                    + " + ':' + #relation + ':' + T(cn.hutool.core.date.DateUtil).today()")
    public CrmIndexRespVO getIndexCount(String relation) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Date todayStart = DateUtil.beginOfDay(new Date());
        Date todayEnd = DateUtil.endOfDay(new Date());

        Long followCustomerCount = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .between(CrmCustomerDO::getNextTime, todayStart, todayEnd)
                .eq(CrmCustomerDO::getOwnerUserId, loginUserId));
        Long followBusinessCount = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .between(CrmBusinessDO::getNextTime, todayStart, todayEnd)
                .eq(CrmBusinessDO::getOwnerUserId, loginUserId));
        Long followCluesCount = crmCluesMapper.selectCount(new LambdaQueryWrapper<CrmCluesDO>()
                .between(CrmCluesDO::getNextTime, todayStart, todayEnd)
                .eq(CrmCluesDO::getStatus, CluesStatusEnum.STATUS_0.getValue())
                .eq(CrmCluesDO::getOwnerUserId, loginUserId));

        // 待审：合同 / 回款 / 发票 一次条件聚合
        Map<String, Object> checkMap = selectAggMap(crmFlowLogMapper, new QueryWrapper<CrmFlowLogDO>()
                .select(
                        "SUM(CASE WHEN types = '" + TypesEnum.CONTRACT.getValue() + "' THEN 1 ELSE 0 END) AS contractCheckCount",
                        "SUM(CASE WHEN types = '" + TypesEnum.RECEIVABLES.getValue() + "' THEN 1 ELSE 0 END) AS receivablesCheckCount",
                        "SUM(CASE WHEN types = '" + TypesEnum.INVOICE.getValue() + "' THEN 1 ELSE 0 END) AS invoiceCheckCount"
                )
                .eq("admin_id", loginUserId)
                .eq("status", FlowLogStatusEnum.STATUS_0.getValue())
                .in("types", TypesEnum.CONTRACT.getValue(), TypesEnum.RECEIVABLES.getValue(), TypesEnum.INVOICE.getValue()));

        return CrmIndexRespVO.builder()
                .followCustomerCount(followCustomerCount)
                .followBusinessCount(followBusinessCount)
                .followCluesCount(followCluesCount)
                .contractCheckCount(toLong(checkMap, "contractCheckCount"))
                .receivablesCheckCount(toLong(checkMap, "receivablesCheckCount"))
                .invoiceCheckCount(toLong(checkMap, "invoiceCheckCount"))
                .brieCountVO(getBrieCount(relation, loginUserId, todayStart, todayEnd))
                .build();
    }

    /**
     * 按表合并今日/昨日统计，目标将 DB 往返降到个位数
     */
    private BrieCountVO getBrieCount(String relation, Long loginUserId, Date todayStart, Date todayEnd) {
        Date yesterdayStart = DateUtil.beginOfDay(DateUtil.yesterday());
        Date yesterdayEnd = DateUtil.endOfDay(DateUtil.yesterday());
        String todayStartStr = DateUtil.formatDateTime(todayStart);
        String todayEndStr = DateUtil.formatDateTime(todayEnd);
        String yesterdayStartStr = DateUtil.formatDateTime(yesterdayStart);
        String yesterdayEndStr = DateUtil.formatDateTime(yesterdayEnd);
        String creator = String.valueOf(loginUserId);

        List<Long> ids = resolveOwnerIds(relation, loginUserId);

        String ownerInCond = buildOwnerInCond(ids);

        // 商机：今日/昨日新增 + 今日成交/未成交（create 统计需带 owner，避免与成交 OR 条件串数据）
        Map<String, Object> businessMap = selectAggMap(businessMapper, new QueryWrapper<CrmBusinessDO>()
                .select(
                        caseCount("create_time", todayStartStr, todayEndStr, "todayCreate", ownerInCond),
                        caseCount("create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayCreate", ownerInCond),
                        "SUM(CASE WHEN deal_time >= '" + todayStartStr + "' AND deal_time <= '" + todayEndStr
                                + "' AND creator = '" + creator + "' AND is_end = " + ShopCommonEnum.IS_STATUS_1.getValue()
                                + " THEN 1 ELSE 0 END) AS dealYes",
                        "SUM(CASE WHEN deal_time >= '" + todayStartStr + "' AND deal_time <= '" + todayEndStr
                                + "' AND creator = '" + creator + "' AND is_end = " + ShopCommonEnum.IS_STATUS_0.getValue()
                                + " THEN 1 ELSE 0 END) AS dealNo"
                )
                .and(w -> w
                        .nested(n -> n.in(!ids.isEmpty(), "owner_user_id", ids)
                                .between("create_time", yesterdayStart, todayEnd))
                        .or()
                        .nested(n -> n.eq("creator", creator)
                                .between("deal_time", todayStart, todayEnd))));
        Long count01 = toLong(businessMap, "todayCreate");
        Long count001 = toLong(businessMap, "yesterdayCreate");
        Long count11 = toLong(businessMap, "dealYes");
        Long count12 = toLong(businessMap, "dealNo");

        // 线索：今日/昨日新增
        Map<String, Object> cluesMap = selectAggMap(crmCluesMapper, buildDayCompareCountWrapper(
                ids, yesterdayStart, todayEnd, todayStartStr, todayEndStr, yesterdayStartStr, yesterdayEndStr));
        Long count02 = toLong(cluesMap, "todayCreate");
        Long count002 = toLong(cluesMap, "yesterdayCreate");

        // 客户：今日/昨日新增 + 今日成交/未成交
        Map<String, Object> customerMap = selectAggMap(customerMapper, new QueryWrapper<CrmCustomerDO>()
                .select(
                        caseCount("create_time", todayStartStr, todayEndStr, "todayCreate", ownerInCond),
                        caseCount("create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayCreate", ownerInCond),
                        "SUM(CASE WHEN deal_time >= '" + todayStartStr + "' AND deal_time <= '" + todayEndStr
                                + "' AND creator = '" + creator + "' AND deal_status = " + ShopCommonEnum.IS_STATUS_1.getValue()
                                + " THEN 1 ELSE 0 END) AS dealYes",
                        "SUM(CASE WHEN deal_time >= '" + todayStartStr + "' AND deal_time <= '" + todayEndStr
                                + "' AND creator = '" + creator + "' AND deal_status = " + ShopCommonEnum.IS_STATUS_0.getValue()
                                + " THEN 1 ELSE 0 END) AS dealNo"
                )
                .and(w -> w
                        .nested(n -> n.in(!ids.isEmpty(), "owner_user_id", ids)
                                .between("create_time", yesterdayStart, todayEnd))
                        .or()
                        .nested(n -> n.eq("creator", creator)
                                .between("deal_time", todayStart, todayEnd))));
        Long count03 = toLong(customerMap, "todayCreate");
        Long count003 = toLong(customerMap, "yesterdayCreate");
        Long count09 = toLong(customerMap, "dealYes");
        Long count10 = toLong(customerMap, "dealNo");

        // 跟进记录：今日/昨日（按 creator）
        Map<String, Object> recordMap = selectAggMap(crmRecordMapper, new QueryWrapper<CrmRecordDO>()
                .select(
                        caseCount("create_time", todayStartStr, todayEndStr, "todayCreate"),
                        caseCount("create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayCreate")
                )
                .in(!ids.isEmpty(), "creator", toCreatorIds(ids))
                .between("create_time", yesterdayStart, todayEnd));
        Long count04 = toLong(recordMap, "todayCreate");
        Long count004 = toLong(recordMap, "yesterdayCreate");

        // 合同：今日/昨日数量 + 金额
        Map<String, Object> contractMap = selectAggMap(contractMapper, new QueryWrapper<CrmContractDO>()
                .select(
                        caseCount("create_time", todayStartStr, todayEndStr, "todayCreate"),
                        caseCount("create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayCreate"),
                        caseSum("money", "create_time", todayStartStr, todayEndStr, "todayMoney"),
                        caseSum("money", "create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayMoney")
                )
                .in(!ids.isEmpty(), "owner_user_id", ids)
                .between("create_time", yesterdayStart, todayEnd));
        Long count05 = toLong(contractMap, "todayCreate");
        Long count005 = toLong(contractMap, "yesterdayCreate");
        BigDecimal count06 = toDecimal(contractMap, "todayMoney");
        BigDecimal count006 = toDecimal(contractMap, "yesterdayMoney");

        // 回款：今日/昨日金额
        Map<String, Object> receivablesMap = selectAggMap(contractReceivablesMapper, new QueryWrapper<CrmContractReceivablesDO>()
                .select(
                        caseSum("money", "create_time", todayStartStr, todayEndStr, "todayMoney"),
                        caseSum("money", "create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayMoney")
                )
                .in(!ids.isEmpty(), "owner_user_id", ids)
                .between("create_time", yesterdayStart, todayEnd));
        BigDecimal count07 = toDecimal(receivablesMap, "todayMoney");
        BigDecimal count007 = toDecimal(receivablesMap, "yesterdayMoney");

        // 联系人：今日/昨日新增
        Map<String, Object> contactsMap = selectAggMap(customerContactsMapper, buildDayCompareCountWrapper(
                ids, yesterdayStart, todayEnd, todayStartStr, todayEndStr, yesterdayStartStr, yesterdayEndStr));
        Long count08 = toLong(contactsMap, "todayCreate");
        Long count008 = toLong(contactsMap, "yesterdayCreate");

        Long count13 = noticeMapper.selectCount(new LambdaQueryWrapper<NoticeDO>()
                .between(NoticeDO::getCreateTime, todayStart, todayEnd)
                .eq(NoticeDO::getStatus, CommonStatusEnum.ENABLE.getStatus()));

        return BrieCountVO.builder()
                .count01(count01).count001(count001).per001(calcPercent(count01, count001))
                .count02(count02).count002(count002).per002(calcPercent(count02, count002))
                .count03(count03).count003(count003).per003(calcPercent(count03, count003))
                .count04(count04).count004(count004).per004(calcPercent(count04, count004))
                .count05(count05).count005(count005).per005(calcPercent(count05, count005))
                .count06(count06).count006(count006).per006(calcPercent(count06, count006))
                .count07(count07).count007(count007).per007(calcPercent(count07, count007))
                .count08(count08).count008(count008).per008(calcPercent(count08, count008))
                .count09(count09)
                .count10(count10)
                .count11(count11)
                .count12(count12)
                .count13(count13)
                .build();
    }

    private List<Long> resolveOwnerIds(String relation, Long adminId) {
        List<Long> ids = new ArrayList<>();
        if (RelationEnum.MY.getValue().equals(relation)) {
            ids.add(adminId);
        } else if (RelationEnum.SUB.getValue().equals(relation)) {
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }
        return ids;
    }

    /**
     * 跟进记录 creator 为 String，与 owner_user_id(Long) 列表对齐
     */
    private List<String> toCreatorIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> creators = new ArrayList<>(ids.size());
        for (Long id : ids) {
            creators.add(String.valueOf(id));
        }
        return creators;
    }

    private <T> QueryWrapper<T> buildDayCompareCountWrapper(List<Long> ids, Date yesterdayStart, Date todayEnd,
                                                            String todayStartStr, String todayEndStr,
                                                            String yesterdayStartStr, String yesterdayEndStr) {
        return new QueryWrapper<T>()
                .select(
                        caseCount("create_time", todayStartStr, todayEndStr, "todayCreate"),
                        caseCount("create_time", yesterdayStartStr, yesterdayEndStr, "yesterdayCreate")
                )
                .in(!ids.isEmpty(), "owner_user_id", ids)
                .between("create_time", yesterdayStart, todayEnd);
    }

    private static String buildOwnerInCond(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return "";
        }
        return " AND owner_user_id IN (" + CollUtil.join(ids, ",") + ")";
    }

    private static String caseCount(String timeColumn, String start, String end, String alias) {
        return caseCount(timeColumn, start, end, alias, "");
    }

    private static String caseCount(String timeColumn, String start, String end, String alias, String extraCond) {
        return "SUM(CASE WHEN " + timeColumn + " >= '" + start + "' AND " + timeColumn + " <= '" + end
                + "'" + extraCond + " THEN 1 ELSE 0 END) AS " + alias;
    }

    private static String caseSum(String moneyColumn, String timeColumn, String start, String end, String alias) {
        return "SUM(CASE WHEN " + timeColumn + " >= '" + start + "' AND " + timeColumn + " <= '" + end
                + "' THEN IFNULL(" + moneyColumn + ", 0) ELSE 0 END) AS " + alias;
    }

    @SuppressWarnings("unchecked")
    private static <T> Map<String, Object> selectAggMap(
            com.baomidou.mybatisplus.core.mapper.BaseMapper<T> mapper, QueryWrapper<T> wrapper) {
        List<Map<String, Object>> maps = mapper.selectMaps(wrapper);
        if (maps == null || maps.isEmpty() || maps.get(0) == null) {
            return Collections.emptyMap();
        }
        return maps.get(0);
    }

    private static Long toLong(Map<String, Object> map, String key) {
        Object value = getIgnoreCase(map, key);
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(value.toString());
    }

    private static BigDecimal toDecimal(Map<String, Object> map, String key) {
        Object value = getIgnoreCase(map, key);
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.toString());
    }

    private static Object getIgnoreCase(Map<String, Object> map, String key) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Object value = map.get(key);
        if (value != null) {
            return value;
        }
        return map.get(key.toLowerCase());
    }

    private static String calcPercent(Long today, Long yesterday) {
        if (yesterday == null || yesterday <= 0) {
            return "0";
        }
        return NumberUtil.div(NumberUtil.sub(today, yesterday), yesterday, 2)
                .multiply(new BigDecimal("100")).toString();
    }

    private static String calcPercent(BigDecimal today, BigDecimal yesterday) {
        if (yesterday == null || yesterday.compareTo(BigDecimal.ZERO) <= 0) {
            return "0";
        }
        return NumberUtil.div(NumberUtil.sub(today, yesterday), yesterday, 2)
                .multiply(new BigDecimal("100")).toString();
    }

}
