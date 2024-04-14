package com.wedding.config.sms.service;


import com.aliyun.oss.common.comm.ResponseMessage;
import com.wedding.config.sms.constant.SmsConstant;
import com.wedding.config.sms.utils.ImageCodeGenerateUtils;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * @Author: zhuqi
 * @Date: 2023/12/05/10:09
 * @Description: 图形验证码service
 */
@Service
@Slf4j
public class ImageCodeService {
    @Autowired
    private ImageCodeGenerateUtils codeUtils;
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成图形验证码
     * @param request
     * @param response
     * @param key  时间戳
     */
    public ResponseResult<String> getImageCode(HttpServletRequest request, HttpServletResponse response, String key){
        log.info("---------时间戳：{}",key);
        try {
            BufferedImage image = codeUtils.getImage();
            String text = codeUtils.getText();
            log.info("---------验证码：{}",text);


            // 获取客户端ip地址
            //String ip  = oConvertUtils.getIpAddrByRequest(request);
        //            log.info("ip:{}",ip);
            // 将验证码存入redis中 并设置过期时间为2分钟
            redisCache.setCacheObject(SysConstants.WEDDING_IMAGE_CODE+key, text, SysConstants.IMAGE_CODE_EXPIRE, TimeUnit.MINUTES);
            codeUtils.output(image,response.getOutputStream());
            return ResponseResult.okResult();
        }catch (IOException e){
            e.printStackTrace();
            log.error("获取图形验证码失败:",e);
            return ResponseResult.errorResult(AppHttpCodeEnum.GET_CAPTCHA_ERROR);
        }
    }

    /**
     * 图形验证码校验
     * @param request
     * @param imageCode
     */
    public ResponseResult imageCodeCheck(HttpServletRequest request, String imageCode){
        log.info("imageCodeCheck:{}",imageCode);

        String ip = request.getRemoteAddr();
        String code = (String) redisCache.getCacheObject(ip + SmsConstant.IMAGE_CODE);
        long expire = redisCache.getExpire(ip + SmsConstant.IMAGE_CODE);
        log.info("expire:{}",expire);
        if (expire<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.IMAGE_CODE_IS_EXPIRE);
        }
        if (imageCode.equals(code)){
            log.info("图形验证码验证成功！");
            return ResponseResult.okResult(AppHttpCodeEnum.IMAGE_CODE_SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.IMAGE_CODE_ERROR);
        }
    }
}
