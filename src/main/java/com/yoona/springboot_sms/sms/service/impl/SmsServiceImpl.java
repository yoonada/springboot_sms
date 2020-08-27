package com.yoona.springboot_sms.sms.service.impl;

import com.github.qcloudsms.httpclient.HTTPException;
import com.yoona.springboot_sms.common.enums.CommonEnums;
import com.yoona.springboot_sms.common.response.SystemResponse;
import com.yoona.springboot_sms.common.util.MobileUtil;
import com.yoona.springboot_sms.common.util.RedisUtils;
import com.yoona.springboot_sms.common.util.SmsUtils;
import com.yoona.springboot_sms.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 2:25 下午
 * @Msg:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsServiceImpl implements SmsService {

    private final RedisUtils redisUtils;

    /**
     * 发送短信
     * @param mobilePhoneNumber
     * @return
     */
    @Override
    public SystemResponse sendMessage(String mobilePhoneNumber) {

        if (StringUtils.isAnyEmpty(mobilePhoneNumber)) {
            return SystemResponse.response(CommonEnums.MOBILE_PHONE_NUMBER_IS_EMPTY);
        }

//        TODO 判断手机号码是否合法
        if (!MobileUtil.checkPhone(mobilePhoneNumber)){
            return SystemResponse.response(CommonEnums.MOBILE_PHONE_NUMBER_IS_INCORRECT);
        }

        /**
         * 判断redis中是否已经存在该手机号码对应的key,若存在,则先删除，再向redis添加
         */
        String a = redisUtils.get("smsVerificationCode" + ":" + mobilePhoneNumber);

        if (a != null) {
            redisUtils.del("smsVerificationCode" + ":" + mobilePhoneNumber);
            log.info("redis中存在该手机的验证码,已删除");
        }

        String verificationCode = SmsUtils.getCode();

        // 将验证码传入redis中
        redisUtils.set("smsVerificationCode" + ":" + mobilePhoneNumber, verificationCode, 600);
        log.info("已经将用户的验证码传入redis中");

        ArrayList<String> list = new ArrayList<>();
        // 验证码
        list.add(verificationCode);
        // 代表十分钟内失效
        list.add("10");
        try {
            SmsUtils.smsSend(mobilePhoneNumber, list);
        } catch (HTTPException | IOException e) {
            e.printStackTrace();
        }

        return SystemResponse.response(CommonEnums.SMS_SENT_SUCCESSFULLY);
    }

    /**
     * 建议验证码
     * @param mobilePhoneNumber
     * @param verificationCode
     * @return
     */
    @Override
    public SystemResponse checkVerificationCode(String mobilePhoneNumber, String verificationCode) {

        if (StringUtils.isAnyEmpty(mobilePhoneNumber) || StringUtils.isAnyEmpty(verificationCode)) {
            return SystemResponse.response(CommonEnums.PARAM_IS_NULL);
        }
        String code = redisUtils.get("smsVerificationCode" + ":" + mobilePhoneNumber);
        if (!StringUtils.isAnyEmpty(code) && verificationCode.equals(code)) {
            log.info("用户输入的验证码校验成功");
            return SystemResponse.response(CommonEnums.CHECK_VERIFICATION_CODE_SUCCESS);
        }

        return SystemResponse.response(CommonEnums.CHECK_VERIFICATION_CODE_FAILED);
    }
}
