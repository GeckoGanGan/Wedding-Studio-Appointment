package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/16/16:41
 * @Description:
 */
@RestController
@RequestMapping("/wedding/chat")
public class MebSocketController {
    @GetMapping("/{userName}")
    public ResponseResult onOpen(){
        return ResponseResult.okResult();
    }
}
