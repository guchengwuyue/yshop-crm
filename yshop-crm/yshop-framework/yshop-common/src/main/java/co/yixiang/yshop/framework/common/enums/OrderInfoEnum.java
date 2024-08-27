/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.yshop.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author hupeng
 * 订单相关枚举
 */
@Getter
@AllArgsConstructor
public enum OrderInfoEnum {

	STATUS_NE1(-1,"申请退款"),
	STATUS_NE2(-2,"退款成功"),
	STATUS_0(0,"默认"),
	STATUS_1(1,"待收货"),
	STATUS_2(2,"已收货"),
	STATUS_3(3,"已完成"),

	PAY_STATUS_0(0,"未支付"),
	PAY_STATUS_1(1,"已支付"),

	REFUND_STATUS_0(0,"正常"),
	REFUND_STATUS_1(1,"退款中"),
	REFUND_STATUS_2(2,"已退款"),

	BARGAIN_STATUS_1(1,"参与中"),
	BARGAIN_STATUS_2(2,"参与失败"),
	BARGAIN_STATUS_3(3,"参与成功"),

	PINK_STATUS_1(1,"进行中"),
	PINK_STATUS_2(2,"已完成"),
	PINK_STATUS_3(3,"未完成"),

	PINK_REFUND_STATUS_0(0,"拼团正常"),
	PINK_REFUND_STATUS_1(1,"拼团已退款"),

	CANCEL_STATUS_0(0,"正常"),
	CANCEL_STATUS_1(1,"已取消"),

	CONFIRM_STATUS_0(0,"正常"),
	CONFIRM_STATUS_1(1,"确认"),

	PAY_CHANNEL_0(0,"公众号/H5支付渠道"),
	PAY_CHANNEL_1(1,"小程序支付渠道"),

	DESK_ORDER_STATUS_CONFIRM(0,"确认完成"),
	DESK_ORDER_STATUS_ING(1,"就餐中"),


	SHIPPIING_TYPE_1(1,"快递"),
	SHIPPIING_TYPE_2(2,"门店自提");



	private Integer value;
	private String desc;

	public static OrderInfoEnum toType(int value) {
		return Stream.of(OrderInfoEnum.values())
				.filter(p -> p.value == value)
				.findAny()
				.orElse(null);
	}


}
