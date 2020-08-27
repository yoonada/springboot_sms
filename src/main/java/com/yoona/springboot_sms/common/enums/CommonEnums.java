package com.yoona.springboot_sms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 11:05 上午
 * @Msg: 通用状态枚举
 */
@Getter
@AllArgsConstructor
public enum CommonEnums {

    /**
     * 状态枚举
     */
    NORMAL(1, "操作成功"),
    UN_KNOW_ERROR(-1, "未知错误"),
    PARAM_IS_NULL(999,"含有空参数,请重新输入"),
    MOBILE_PHONE_NUMBER_IS_EMPTY(1000,"手机号码为空"),
    MOBILE_PHONE_NUMBER_IS_INCORRECT(1001,"手机号码不正确"),
    CHECK_VERIFICATION_CODE_SUCCESS(1002,"验证码校验成功"),
    CHECK_VERIFICATION_CODE_FAILED(1003,"验证码校验失败"),
    SMS_SENT_SUCCESSFULLY(1004,"短信发送成功"),
    SMS_SENT_FAILED(1005,"短信发送失败"),

    ;

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
