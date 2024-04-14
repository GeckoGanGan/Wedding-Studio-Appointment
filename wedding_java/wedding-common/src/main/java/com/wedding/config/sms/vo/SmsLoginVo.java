package com.wedding.config.sms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2023/11/29/11:15
 * @Description: 验证码登录对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsLoginVo {

    // 手机号
    private String phone;

    // 图形验证码
    private String imageCode;
    // 短信验证码
    private String code;
    // 图形验证码redis中的key
    private String key;

}
