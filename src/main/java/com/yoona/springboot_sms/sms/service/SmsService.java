package com.yoona.springboot_sms.sms.service;

import com.yoona.springboot_sms.common.response.SystemResponse;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 2:25 下午
 * @Msg:
 */
public interface SmsService {

    /**
     * 传入mobilePhoneNumber,用来发送验证码
     * @param mobilePhoneNumber
     * @return
     *
     */
    SystemResponse sendMessage(String mobilePhoneNumber);


    /**
     * 校验用户的验证码
     * @param mobilePhoneNumber
     * @param verificationCode
     * @return
     */

    SystemResponse checkVerificationCode(String mobilePhoneNumber, String verificationCode);


}