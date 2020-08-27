package com.yoona.springboot_sms.sms.controller;

import com.yoona.springboot_sms.common.response.SystemResponse;
import com.yoona.springboot_sms.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 2:32 下午
 * @Msg: 对接腾讯云短信服务,Redis存储验证码
 */
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsController {

    private final SmsService smsService;

    /**
     * 发送手机验证码
     * @param mobilePhoneNumber
     * @return
     */

    @PostMapping(value = "/sendCode")
    public SystemResponse sendMessage(@RequestParam("mobilePhoneNumber") String mobilePhoneNumber) {
        return smsService.sendMessage(mobilePhoneNumber);
    }

    /**
     * 校验验证码
     * @param mobilePhoneNumber
     * @param verificationCode
     * @return
     */

    @PostMapping(value = "/checkIsCorrectCode")
    public SystemResponse checkVerificationCode(@RequestParam("mobilePhoneNumber") String mobilePhoneNumber, @RequestParam("verificationCode") String verificationCode) {
        return smsService.checkVerificationCode(mobilePhoneNumber, verificationCode);
    }

}
