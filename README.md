# YSHOP 意象 CRM

> Spring Boot 3 + Vue3 前后端分离，支持企业微信的 CRM 销售管理系统，助力企业销售全流程精细化、数字化管理。

**官网：** [https://www.yixiang.co/](https://www.yixiang.co/)

---

## 产品简介

支持企业微信的前后端分离 CRM，盘活客户资源、量化销售行为、合理配置资源，覆盖销售与项目协同，建立科学销售体系，提升销售业绩。

**定位：** 线索 → 客户 → 商机 → 合同 → 回款 → 项目

**适用：** 销售团队 / 项目管理 / SCRM / 企业微信协同

**技术栈：** Spring Boot 3 · Spring Security OAuth2 · MyBatis Plus · JWT · Redis · Vue3 · uni-app

---

## 演示地址

| 入口 | 说明 |
|------|------|
| 演示地址 | https://www.yixiang.co/p/yixiangcrmxitong.html|
| **二开与定制** | ![联系二维码](assets/code.jpg) |

---

## 功能列表

| 模块 | 功能 |
|------|------|
| 控制台 | 销售简报、我的业绩统计 |
| 待办 | 审批、待跟进等待办事项 |
| 办公 | 审批管理、流程管理、日程管理、通知管理、工作报告 |
| 客户 | 线索 / 客户 / 商机 / 合同 / 发票、标签、呼叫中心、SCRM 企微群发、自定义字段、拜访签到 |
| 项目 | 项目管理、项目任务、项目文档、项目动态、任务日志 |
| 数据 | 业绩目标、员工业绩、客户级别 / 来源 / 行业、跟进分析、合同排行 |
| 企业 | 员工、角色、部门、岗位管理 |
| 商城 | 商品分类、规格、列表、支付配置 |
| 移动端 | uni-app 小程序 |
| 公众号 | 粉丝管理、模板消息等 |

---

## 视频资料

如果对您有帮助，欢迎点右上角 **Star** 支持一下，这样我们才有继续免费下去的动力，谢谢！

QQ 交流群（入群前请先 Star）：**544263002**  
群内有视频教程与开发文档。

---

## 项目说明

| 目录 | 说明 |
|------|------|
| `yshop-crm` | Java 后端工程 |
| `yshop-crm-vue` | 后台前端 Vue3 工程 |

---

## 本地快速启动

### 1. 环境要求

| 依赖 | 版本 |
|------|------|
| JDK | 17 |
| MySQL | 8 |
| Redis | 6+ |
| Node.js | 16+ |
| Maven | 3.8+ |

### 2. 开发工具

- IDEA（后端）
- VS Code（前端）

### 3. 后端启动

1. 使用 IDEA 打开 Java 工程，自动安装依赖  
2. 创建数据库，导入工程目录下 `sql/yshop-crm.sql`  
3. 修改 `yshop-server` 下 yml 中的数据库与 Redis 配置：

   ![配置示意](assets/3344.png)

4. 在工程目录执行：

   ```bash
   mvn clean install package '-Dmaven.test.skip=true'
   ```

5. 启动项目

### 4. 后台 Vue 启动

1. 用 VS Code 打开 Vue 工程，执行：

   ```bash
   pnpm install
   ```

2. 配置 API：

   ![API 配置](assets/3355.jpg)

3. 本地启动：

   ```bash
   npm run dev
   ```

---

## 手机端截图

| | |
|:---:|:---:|
| ![输入图片说明](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260801110135_303_52.png) | ![输入图片说明](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260801110134_302_52.png) |
| ![截图](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260328155447_615_6.png) | ![截图](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260328155445_613_6.png) |
| ![截图](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260328155442_610_6.png) | ![输入图片说明](assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260801110133_301_52.png) |

---

## 后台截图

| |
|:---:|
| ![后台](assets/3000.png) |
| ![后台](assets/3001.png) |
| ![后台](assets/3002.png) |
| ![后台](assets/3003.png) |
| ![后台](assets/3004.png) |
| ![后台](assets/3005.png) |

---

## 技术栈

**后端：** Spring Boot 3 · Spring Security OAuth2 · MyBatis / MyBatis Plus · Redis · Lombok · Hutool

**前端：** Vue 3 · Element Plus · uni-app（Vue3）

---

## 特别鸣谢

- [ruoyi-vue-pro](https://gitee.com/zhijiantianya/ruoyi-vue-pro)
- [Element Plus](https://element-plus.gitee.io/zh-CN/)
- [Vue](https://cn.vuejs.org/)
- [pay-java-parent](https://gitee.com/egzosn/pay-java-parent)

---

## 开源协议

本项目采用比 Apache 2.0 更宽松的 [MIT License](https://gitee.com/guchengwuyue/crm/blob/master/LICENSE) 开源协议，个人与企业可 100% 免费使用，不用保留类作者、Copyright 信息。
