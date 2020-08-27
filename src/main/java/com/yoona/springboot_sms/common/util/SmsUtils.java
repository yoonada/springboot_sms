package com.yoona.springboot_sms.common.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.yoona.springboot_sms.common.constants.SmsConstants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;


import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 2:21 下午
 * @Msg:
 */
@Slf4j
public class SmsUtils {

    public static boolean smsSend(String mobileNumber, ArrayList<String> params) throws com.github.qcloudsms.httpclient.HTTPException, IOException, HTTPException, JSONException {

        SmsSingleSender smsSingleSender = new SmsSingleSender(SmsConstants.APP_ID,SmsConstants.APP_KEY);
        SmsSingleSenderResult result = smsSingleSender.sendWithParam("86", mobileNumber, SmsConstants.TEMPLATE_ID, params, "", "", "");

        if (result != null&& result.result==0){
            log.info("短信发送成功");
            log.info("result"+result);
            return true;
        }else {
            log.error("短信发送失败");
        }
        return false;
    }


    /**
     * 创建验证码
     * @return
     */
    public static String getCode() {
        int max = 999999;
        int min = 111111;
        Random random = new Random();
        int code = random.nextInt(max);
        if (code < min) {
            code = code + min;
        }
        return String.valueOf(code);
    }
}
