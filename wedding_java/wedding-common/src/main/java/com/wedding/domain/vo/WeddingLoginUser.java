package com.wedding.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: zhuqi
 * @Date: 2024/03/09/22:30
 * @Description: 登录成功对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeddingLoginUser {
    private String token;
    private UserInfoVo userInfo;
}
