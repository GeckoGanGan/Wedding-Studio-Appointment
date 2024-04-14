package com.wedding.config.sms.config;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wedding.config.sms.constant.SmsConstant;
import com.wedding.config.sms.utils.GenerateCodeUtils;
import com.wedding.config.sms.vo.SmsLoginVo;
import com.wedding.constants.SysConstants;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/02/03/19:24
 * @Description:
 */
@Service
@Slf4j
public class AliyunSmsUtils {
    @Autowired
    private AliyunSmsConfig aliyunSmsConfig;
    @Autowired
    private RedisCache redisCache;

    public SendSmsResponse sendMsg(HttpServletRequest request, SmsLoginVo smsLoginVo) throws ClientException {
        // 判断验证码是否正确
        String key = smsLoginVo.getKey();

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSmsConfig.getAccessKeyId(), aliyunSmsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliyunSmsConfig.getProduct(), aliyunSmsConfig.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //必填:待发送手机号
        sendSmsRequest.setPhoneNumbers(smsLoginVo.getPhone());
        //必填:短信签名-可在短信控制台中找到
        sendSmsRequest.setSignName(aliyunSmsConfig.getSignatureName());    // TODO 修改成自己的
        //必填:短信模板-可在短信控制台中找到
        sendSmsRequest.setTemplateCode(aliyunSmsConfig.getTemplateCode());    // TODO 修改成自己的
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为

       //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        //生成随机验证码
        String smsCode = GenerateCodeUtils.generateCode(4).toString();
        sendSmsRequest.setTemplateParam("{\"code\":\"" + smsCode + "\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //sendSmsRequest.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(sendSmsRequest);
        log.error("短信发送响应：{}", JSON.toJSONString(sendSmsResponse));
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            log.info("短信：{}，验证码：{}，发送成功！",smsLoginVo.getPhone(),smsCode);
            // 设置过期时间为一天（方便测试）
            redisCache.setCacheObject(SmsConstant.SMS_CODE+ smsLoginVo.getPhone(), smsCode,SysConstants.EXPIRED_SMS_CODE, TimeUnit.HOURS);
        } else {
            log.error("短信发送失败！");
        }
        return sendSmsResponse;
    }

    //不删 留着 以后可能有用
    private QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSmsConfig.getAccessKeyId(), aliyunSmsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliyunSmsConfig.getProduct(), aliyunSmsConfig.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
    }


}
