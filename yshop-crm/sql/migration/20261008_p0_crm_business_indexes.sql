--  CRM 核心表业务组合索引
-- 用途：加速控制台统计、列表筛选、公海/跟进相关查询

-- ----------------------------
-- yshop_crm_customer
-- ----------------------------
ALTER TABLE `yshop_crm_customer`
  ADD INDEX `idx_owner_create_time` (`owner_user_id`, `create_time`),
  ADD INDEX `idx_owner_next_time` (`owner_user_id`, `next_time`),
  ADD INDEX `idx_deal_owner_follow` (`deal_status`, `owner_user_id`, `follow_time`),
  ADD INDEX `idx_creator_deal_time_status` (`creator`, `deal_time`, `deal_status`);

-- ----------------------------
-- yshop_crm_business
-- ----------------------------
ALTER TABLE `yshop_crm_business`
  ADD INDEX `idx_owner_create_time` (`owner_user_id`, `create_time`),
  ADD INDEX `idx_owner_next_time` (`owner_user_id`, `next_time`);

-- ----------------------------
-- yshop_crm_clues
-- ----------------------------
ALTER TABLE `yshop_crm_clues`
  ADD INDEX `idx_owner_create_time` (`owner_user_id`, `create_time`),
  ADD INDEX `idx_owner_next_time` (`owner_user_id`, `next_time`);

-- ----------------------------
-- yshop_crm_contract
-- ----------------------------
ALTER TABLE `yshop_crm_contract`
  ADD INDEX `idx_owner_create_time` (`owner_user_id`, `create_time`),
  ADD INDEX `idx_owner_check_order_time` (`owner_user_id`, `check_status`, `order_time`);

-- ----------------------------
-- yshop_crm_contract_receivables
-- ----------------------------
ALTER TABLE `yshop_crm_contract_receivables`
  ADD INDEX `idx_owner_check_return_time` (`owner_user_id`, `check_status`, `return_time`),
  ADD INDEX `idx_owner_create_time` (`owner_user_id`, `create_time`);

-- ----------------------------
-- yshop_crm_flow_log
-- ----------------------------
ALTER TABLE `yshop_crm_flow_log`
  ADD INDEX `idx_admin_types_status` (`admin_id`, `types`, `status`);

-- ----------------------------
-- yshop_crm_record
-- ----------------------------
ALTER TABLE `yshop_crm_record`
  ADD INDEX `idx_creator_create_time` (`creator`, `create_time`);
