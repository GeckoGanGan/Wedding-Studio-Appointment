package com.wedding.controller;

import com.wedding.constants.EmailConstant;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.Message;
import com.wedding.service.EmailService;
import com.wedding.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/24/19:08
 * @Description: 邮件发送控制类
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/contact")
    public ResponseResult contact(@RequestBody Message contactMessage) {
        messageService.save(contactMessage);
        emailService.sendEmail(contactMessage.getEmail(),EmailConstant.EMAIL_RECEIVE,EmailConstant.EMAIL_SUBJECT);
        return ResponseResult.okResult(EmailConstant.EMAIL_RECEIVE);
    }
}
