/*
 *  Copyright 2019-2020 Fang Jin Biao
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型：目录、菜单、按钮
 *
 * @author adyfang
 * @date 2020年9月12日
 */
@Getter
@AllArgsConstructor
public enum MenuType {
	FOLDER(0, "目录"),
	MENU(1, "菜单"),
	BUTTON(2, "按钮"),

	/*-------------------------- 删除状态 --------------------------*/
	IS_DEL_STATUS_0(0,"否"),
	IS_DEL_STATUS_1(1,"是"),
	/*-------------------------- 所有表isStart:状态 --------------------------*/
	IS_STATUS_0(0,"否"),
	IS_STATUS_1(1,"是"),

	/*-------------------------- 配送方式 1=快递 ，2=门店自提 --------------------------*/
	SHIPPING_TYPE_1(1,"快递"),
	SHIPPING_TYPE_2(2,"自提"),

	/*-------------------------- 用户记录: 0:支出, 1:获得 --------------------------*/
	BILL_MP_STATUS_0(0,"支出"),
	BILL_MP_STATUS_1(1,"获得"),

	/*-------------------------- 充值记录状态:  充值是否成功,0未成功,1充值成功  WooshopMoneyRecord --------------------------*/
	MONEY_RECORD_STATUS_0(0,"未充值成功"),
	MONEY_RECORD_STATUS_1(1,"充值成功"),

	/*-------------------------- 积分管理关联类型: 1订单积分、2签到积分、3系统添加  --------------------------*/
	INTEGRAL_LINK_TYPE_1(1,"订单积分"),
	INTEGRAL_LINK_TYPE_2(2,"签到积分"),
	INTEGRAL_LINK_TYPE_3(3,"系统添加"),

	/*-------------------------- 用户积分记录: 关联订单id，2签到、3系统  默认为0 WooshopIntegralRecord --------------------------*/
	INTEGRAL_RECORD_LINK_TYPE_0(0,"默认"),
	INTEGRAL_RECORD_LINK_TYPE_1(1,"签到"),
	INTEGRAL_RECORD_LINK_TYPE_2(2,"系统"),
	/*-------------------------- 积分状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款） --------------------------*/
	INTEGRAL_RECORD_STATUS_1(1,"订单创建"),
	INTEGRAL_RECORD_STATUS_2(2,"冻结期"),
	INTEGRAL_RECORD_STATUS_3(3,"完成"),
	INTEGRAL_RECORD_STATUS_4(4,"失效（订单退款/取消订单）"),

	/*--------------------------  积分类型：1-增加积分，2-扣减积分   --------------------------*/
	INTEGRAL_RECORD_ADD_1(1,"增加积分"),
	INTEGRAL_RECORD_SUBTRACT_2(2,"扣减积分"),

	/*-------------------------- 账单状态:0待确定、1有效、 -1无效 --------------------------*/
	BILL_STATUS_0(0,"待确定"),
	BILL_STATUS_1(1,"有效"),
	BILL_STATUS_NOT_1(-1,"无效"),

	/*-------------------------- 下单订单类型:0-普通订单，1-视频号订单 --------------------------*/
	ORDER_TYPE_0(0,"普通订单"),
	ORDER_TYPE_1(1,"视频号订单"),

	/*-------------------------- 购物车状态:0-失效，1-生效; 默认1 --------------------------*/
	CART_STATUS_0(0,"失效"),
	CART_STATUS_1(1,"生效"),

	/*-------------------------- 购物车是否为立即购买:0-不是，1-是; 默认0 --------------------------*/
	CART_IS_BUY_NOW_STATUS_0(0,"不是立即购买"),
	CART_IS_BUY_NOW_STATUS_1(1,"是立即购买"),

	/*-------------------------- 购物车状态:1-商品类型，2-礼品类型; 默认0 --------------------------*/
	CART_GOODS_TYPE_0(0,"默认类型"),
	CART_GOODS_TYPE_1(1,"商品类型"),
	CART_GOODS_TYPE_2(2,"礼品类型"),

	/*-------------------------- 活动类型 0-普通商品 1-秒杀商品 2-砍价商品 3-拼团商品") --------------------------*/
	GOODS_ACTIVITY_TYPE_0(0,"普通商品"),
	GOODS_ACTIVITY_TYPE_1(1,"秒杀商品"),
	GOODS_ACTIVITY_TYPE_2(2,"砍价商品"),
	GOODS_ACTIVITY_TYPE_3(3,"拼团商品"),

	/*-------------------------- 优惠券使用状态:0-未使用、1-已使用、2-已失效  默认0 --------------------------*/
	COUPON_INFO_STATUS_0(0,"未使用"),
	COUPON_INFO_STATUS_1(1,"已使用"),
	COUPON_INFO_STATUS_2(2,"已失效"),

	/*-------------------------- 订单收货方式:1-系统自动收货/2-用户手动收货 --------------------------*/
	AUTO_GOODS_ORDER_TYPE_1(1,"自动收货"),
	USER_GOODS_ORDER_2(2,"用户收货"),

	/*-------------------------- 支付状态: 0-未支付、1-已支付 --------------------------*/
	PAY_STATUS_0(0,"未支付"),
	PAY_STATUS_1(1,"已支付"),

	/*-------------------------- 订单:收货状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；） --------------------------*/
	ORDER_STATUS_0(0,"待发货"),
	ORDER_STATUS_1(1,"待收货"),
	ORDER_STATUS_2(2,"已收货/待评价"),
	ORDER_STATUS_3(3,"已完成"),

	/*-------------------------- 订单:退款状态（0 未退款 1 申请中 2 已退款 3 退款中 4 退货中） --------------------------*/
	REFUND_ORDER_STATUS_0(0,"未退款"),
	REFUND_ORDER_STATUS_1(1,"申请中"),
	REFUND_ORDER_STATUS_2(2,"已退款"),
	REFUND_ORDER_STATUS_3(3,"退款中"),
	REFUND_ORDER_STATUS_4(4,"退货中"),

	/*-------------------------- 发货类型:(1-快递、2-送货、3-虚拟发货 默认0未发货") --------------------------*/
	DELIVERY_ORDER_TYPE_0(0,"默认0未发货"),
	DELIVERY_ORDER_TYPE_1(1,"快递"),
	DELIVERY_ORDER_TYPE_2(2,"送货"),
	DELIVERY_ORDER_TYPE_3(3,"虚拟发货"),

	/*-------------------------- 砍价活动用户状态: 1参与中 2 活动结束参与失败 3活动结束参与成功 --------------------------*/
	BARGAIN_USER_IS_STATUS_1(1,"参与中"),
	BARGAIN_USER_IS_STATUS_2(2,"活动结束参与失败"),
	BARGAIN_USER_IS_STATUS_3(3,"活动结束参与成功"),

	/*-------------------------- 团购商品活动状态: （0：失效；1：生效；2） --------------------------*/
	GROUPBUYING_STATUS_0(0,"未开启"),
	GROUPBUYING_STATUS_1(1,"已开启"),

	/*-------------------------- 团购用户状态:1进行中 2已完成 3未完成 --------------------------*/
	GROUPBUYING_USER_STATUS_1(1,"进行中"),
	GROUPBUYING_USER_STATUS_2(2,"已完成"),
	GROUPBUYING_USER_STATUS_3(3,"未完成"),

	/*-------------------------- 团购用户退款状态:是否退款 0未退款 1已退款 isRefund--------------------------*/
	GROUPBUYING_USER_IS_REFUND_STATUS_0(0,"未退款"),
	GROUPBUYING_USER_IS_REFUND_STATUS_1(1,"已退款"),

	/*-------------------------- 用户签到获得类型:1积分，2经验  WooshopSignRecord --------------------------*/
	SIGN_USER_TYPE_1(1,"积分"),
	SIGN_USER_TYPE_2(2,"经验"),

	/*-------------------------- 类型：1-增加，2-扣减 WooshopUserExperienceRecord--------------------------*/
	EXP_USER_TYPE_1(1,"增加经验"),
	EXP_USER_TYPE_0(0,"扣减经验"),


	/*-------------------------- 评价商品类型(1普通商品、2秒杀商品、3团购商品、4砍价商品） --------------------------*/
	EVALUATION_GOODS_TYPE_1(1,"普通商品"),
	EVALUATION_GOODS_TYPE_2(2,"秒杀商品"),
	EVALUATION_GOODS_TYPE_3(3,"拼团商品"),
	EVALUATION_GOODS_TYPE_4(4,"砍价商品"),


	/*-------------------------- Relation 收藏类型:1商品收藏、2推荐商品、3点赞 --------------------------*/
	RELATION_TYPE_1(1,"商品收藏"),
	RELATION_TYPE_2(2,"推荐商品"),
	RELATION_TYPE_3(3,"点赞商品"),


	/*-------------------------- Relation 商品收藏 类型:1普通商品、2限时抢购商品、3团购商品、4砍价商品--------------------------*/
	RELATION_GOODS_TYPE_1(1,"普通商品"),
	ELATION_GOODS_TYPE_2(2,"秒杀商品"),
	ELATION_GOODS_TYPE_3(3,"拼团商品"),
	ELATION_GOODS_TYPE_4(4,"砍价商品"),


	/*-------------------------- BrokerageRecord 状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请 --------------------------*/
	BROKERAGE_RECORD_IS_START_1(1,"订单创建"),
	BROKERAGE_RECORD_IS_START_2(2,"冻结期"),
	BROKERAGE_RECORD_IS_START_3(3,"完成"),
	BROKERAGE_RECORD_IS_START_4(4,"失效（订单退款）"),
	BROKERAGE_RECORD_IS_START_5(5,"提现申请"),


	/*-------------------------- BrokerageRecord 分销佣金金额 类型：1-增加，2-扣减（提现） --------------------------*/
	BROKERAGE_RECORD_TYPE_1(1,"增加"),
	BROKERAGE_RECORD_TYPE_2(2,"扣减（提现）"),


	/*-------------------------- WooshopWithdrawRecord 审核状态:0审核中、1已提现、2未通过、3会员撤销  --------------------------*/
	WITHDRAW_IS_START_TYPE_0(0,"审核中"),
	WITHDRAW_IS_START_TYPE_1(1,"已提现"),
	WITHDRAW_IS_START_TYPE_2(2,"未通过"),
	WITHDRAW_IS_START_TYPE_3(3,"会员撤销");



	private final int value;
	private final String description;

	public static MenuType find(int code) {
		for (MenuType value : MenuType.values()) {
			if (code == value.getValue()) {
				return value;
			}
		}
		return null;
	}
}
