package com.wooshop.utils.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "经验记录常量类")
public class UserExperienceRecordConstants {

    /** 经验记录类型—增加 */
    public static final Integer EXPERIENCE_RECORD_TYPE_ADD = 1;

    /** 经验记录类型—扣减 */
    public static final Integer EXPERIENCE_RECORD_TYPE_SUB = 2;

    /** 经验记录状态—创建 */
    public static final Integer EXPERIENCE_RECORD_STATUS_CREATE = 1;

    /** 经验记录关联类型—订单 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_ORDER = "order";

    /** 经验记录关联类型—签到 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_SIGN = "sign";

    /** 经验记录关联类型—系统后台 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_SYSTEM = "system";

    /** 经验记录标题—用户订单付款成功 */
    public static final String EXPERIENCE_RECORD_TITLE_ORDER = "用户订单付款成功";

    /** 经验记录标题—签到经验奖励 */
    public static final String EXPERIENCE_RECORD_TITLE_SIGN = "签到经验奖励";

    /** 经验记录标题—用户退款 */
    public static final String EXPERIENCE_RECORD_TITLE_REFUND = "用户退款";

    /** 经验记录标题—PC管理员操作 */
    public static final String EXPERIENCE_RECORD_TITLE_ADMIN = "管理员操作";
}
