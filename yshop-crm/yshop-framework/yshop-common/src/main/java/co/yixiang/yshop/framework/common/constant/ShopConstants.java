/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.yshop.framework.common.constant;

/**
 * 商城统一常量
 * @author hupeng
 * @since 2020-02-27
 */
public interface ShopConstants {

	/**
	 * 订单自动取消时间（分钟）
	 */
	long ORDER_OUTTIME_UNPAY = 30;
	/**
	 * 订单自动收货时间（分钟）
	 */
	long ORDER_OUTTIME_UNCONFIRM = 60;

	/**
	 * 2小时
	 */
	long ORDER_TWO_HOUR = 2;
	/**
	 * redis订单未付款key
	 */
	String REDIS_ORDER_OUTTIME_UNPAY_QUEUE = "order-unpay-cancel-queue";

	/**
	 * 扫码自动确认key
	 */
	String REDIS_ORDER_DESK_CONFIRM = "order:desk:confirm";

	/**
	 * 预约自动确认key
	 */
	String REDIS_ORDER_DUE_CONFIRM = "order:due:confirm";

	/**
	 * redis订单收货key
	 */
	String REDIS_ORDER_OUTTIME_UNCONFIRM = "order:unconfirm:";

	/**
	 * redis拼团key
	 */
	String REDIS_PINK_CANCEL_KEY = "pink:cancel:";

	/**
	 * 微信支付service
	 */
	String YSHOP_WEIXIN_PAY_SERVICE = "yshop_weixin_pay_service";

	/**
	 * 微信支付小程序service
	 */
	String YSHOP_WEIXIN_MINI_PAY_SERVICE = "yshop_weixin_mini_pay_service";

	/**
	 * 微信支付app service
	 */
	String YSHOP_WEIXIN_APP_PAY_SERVICE = "yshop_weixin_app_pay_service";

	/**
	 * 微信公众号service
	 */
	String YSHOP_WEIXIN_MP_SERVICE = "yshop_weixin_mp_service";
	/**
	 * 微信小程序service
	 */
	String YSHOP_WEIXIN_MA_SERVICE = "yshop_weixin_ma_service";

	/**
	 * 商城默认密码
	 */
	String YSHOP_DEFAULT_PWD = "123456";

	/**
	 * 商城默认注册图片
	 */
	String YSHOP_DEFAULT_AVATAR = "https://image.dayouqiantu.cn/5e79f6cfd33b6.png";

	/**
	 * 腾讯地图地址解析
	 */
	String QQ_MAP_URL = "https://apis.map.qq.com/ws/geocoder/v1/";

	/**
	 * redis首页键
	 */
	String YSHOP_REDIS_INDEX_KEY = "yshop:index_data";

	/**
	 * 配置列表缓存
	 */
	String YSHOP_REDIS_CONFIG_DATAS = "yshop:config_datas";

	/**
	 * 充值方案
	 */
	String YSHOP_RECHARGE_PRICE_WAYS = "yshop_recharge_price_ways";
	/**
	 * 首页banner
	 */
	String YSHOP_HOME_BANNER = "yshop_home_banner";
	/**
	 * 首页菜单
	 */
	String YSHOP_HOME_MENUS = "yshop_home_menus";
	/**
	 * 首页滚动新闻
	 */
	String YSHOP_HOME_ROLL_NEWS = "yshop_home_roll_news";
	/**
	 * 热门搜索
	 */
	String YSHOP_HOT_SEARCH = "yshop_hot_search";
	/**
	 * 个人中心菜单
	 */
	String YSHOP_MY_MENUES = "yshop_my_menus";
	/**
	 * 秒杀时间段
	 */
	String YSHOP_SECKILL_TIME = "yshop_seckill_time";
	/**
	 * 签到天数
	 */
	String YSHOP_SIGN_DAY_NUM = "yshop_sign_day_num";

	/**
	 * 打印机配置
	 */
	String YSHOP_ORDER_PRINT_COUNT = "order_print_count";
	/**
	 * 飞蛾用户信息
	 */
	String YSHOP_FEI_E_USER = "fei_e_user";
	/**
	 * 飞蛾用户密钥
	 */
	String YSHOP_FEI_E_UKEY= "fei_e_ukey";

	/**
	 * 打印机配置
	 */
	String YSHOP_ORDER_PRINT_COUNT_DETAIL = "order_print_count_detail";

	/**
	 * 短信验证码长度
	 */
	int YSHOP_SMS_SIZE = 6;

	/**
	 * 短信缓存时间
	 */
	long YSHOP_SMS_REDIS_TIME = 600L;

	//零标识
	String YSHOP_ZERO =  "0";

	//业务标识标识
	String YSHOP_ONE =  "1";

	//目前完成任务数量是3
	int TASK_FINISH_COUNT = 3;

	int YSHOP_ONE_NUM = 1;

	String YSHOP_ORDER_CACHE_KEY = "yshop:order";

	String YSHOP_ORDER_SALE_STATUS_KEY = "yshop:order:sale:status";

	long YSHOP_ORDER_CACHE_TIME = 3600L;

	String WECHAT_MENUS =  "wechat_menus";

	String YSHOP_EXPRESS_SERVICE = "yshop_express_service";

	String YSHOP_REDIS_SYS_CITY_KEY = "yshop:city_list";

	String YSHOP_REDIS_CITY_KEY = "yshop:city";

	String YSHOP_APP_LOGIN_USER = "app-online-token:";

	String YSHOP_WECHAT_PUSH_REMARK = "yshop为您服务！";

	String DEFAULT_UNI_H5_URL = "https://h5.yixiang.co";

	String YSHOP_MINI_SESSION_KET = "yshop:session_key:";

	/**公众号二维码*/
	String WECHAT_FOLLOW_IMG="wechat_follow_img";
	/**后台api地址*/
	String ADMIN_API_URL="admin_api_url";

	//快递查询接口Logistic
	String KDNIAO_LOGISTIC_QUERY="https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

	//跳转到扫码页面
	String PAGE_GOOD_HOME = "pages/components/pages/scan/scan";

	String DAY_COUNT_KEY = "day_count_key:";
}
