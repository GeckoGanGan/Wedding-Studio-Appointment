package com.wedding.config.sms.utils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2023/11/29/10:27
 * @Description: 生成验证码工具类
 */
public class GenerateCodeUtils {

    // 四位数验证码
    public static final int FOUR_DIGIT_CODE = 4;
    // 六位数验证码
    public static final int SIX_DIGIT_CODE = 6;

    /**
     * 随机生成指定长度的验证码
     * @param length 长度
     */
    public static Integer generateCode(Integer length){
        Integer code;
        Random random = new Random();
        // 生成四位数的验证码
        if (length == FOUR_DIGIT_CODE){
            code = random.nextInt(9999);
            if (code<1000){
                // 如果生成的随机雁阵吗长度小于4，保证生成四位数的验证码
                code = code+1000;
            }
        }else if (length == SIX_DIGIT_CODE){
            code = random.nextInt(999999);
            if (code<100000){
                // 保证生成六位数的验证码
                code = code+100000;
            }
        }else {
            throw new IllegalArgumentException("生成验证码长度只能为4或者6");
        }
        return code;
    }
}
