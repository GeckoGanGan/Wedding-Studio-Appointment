package com.wedding.service;

import com.wedding.domain.ResponseResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/24/13:23
 * @Description:
 */
public interface EmailService {
    public ResponseResult sendEmail(String toEmail,String content,String subject);
}
