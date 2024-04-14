package com.wedding.service.impl;

import com.wedding.constants.EmailConstant;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/24/13:23
 * @Description:
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender javaMailSender;
    @Override
    public ResponseResult sendEmail(String toEmail,String content,String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(content);
            simpleMailMessage.setFrom(EmailConstant.FROM_EMAIL);
            javaMailSender.send(simpleMailMessage);
            return ResponseResult.okResult();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
    }
}
