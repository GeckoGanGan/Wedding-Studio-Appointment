package com.wedding.config.sms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/04/11/16:01
 * @Description: 验证码登录结果类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponse {
    private Integer resCode;
    private String resMsg;
}
