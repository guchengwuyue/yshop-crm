package co.yixiang.yshop.module.crm.enums;

import co.yixiang.yshop.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(200000, "客户不存在");
    ErrorCode CUSTOMER_CONTACTS_NOT_EXISTS = new ErrorCode(200001, "联系人不存在");
    ErrorCode RECORD_NOT_EXISTS = new ErrorCode(200002, "跟进记录不存在");
    ErrorCode BUSINESS_NOT_EXISTS = new ErrorCode(200003, "商机不存在");
    ErrorCode CLUES_NOT_EXISTS = new ErrorCode(200004, "线索不存在");
    ErrorCode CONTRACT_NOT_EXISTS = new ErrorCode(200005, "合同不存在");
    ErrorCode FLOW_NOT_EXISTS = new ErrorCode(200006, "审批流程不存在");
    ErrorCode CONTRACT_RECEIVABLES_NOT_EXISTS = new ErrorCode(200007, "回款不存在");
    ErrorCode INVOICE_NOT_EXISTS = new ErrorCode(200008, "发票不存在");
    ErrorCode ACHIEVEMENT_NOT_EXISTS = new ErrorCode(200009, "业绩目标不存在");
    ErrorCode CLOUDCALL_LOG_NOT_EXISTS = new ErrorCode(200010, "呼叫记录不存在");
    ErrorCode CLOUDCALL_NOT_EXISTS = new ErrorCode(200011, "云呼坐席管理不存在");
    ErrorCode WORK_WX_CONFIG_NOT_EXISTS = new ErrorCode(200012, "企业微信配置不存在");
    ErrorCode WORK_WX_CUSTOMER_NOT_EXISTS = new ErrorCode(200013, "企业微信客户不存在");
    ErrorCode WORK_WX_USER_NOT_EXISTS = new ErrorCode(200014, "企业微信员工不存在");
    ErrorCode DING_USER_NOT_EXISTS = new ErrorCode(200015, "钉钉用户员工不存在");
    ErrorCode DING_CUSTOMER_NOT_EXISTS = new ErrorCode(200016, "钉钉客户不存在");
    ErrorCode DING_CONFIG_NOT_EXISTS = new ErrorCode(200017, "钉钉配置不存在");
}
