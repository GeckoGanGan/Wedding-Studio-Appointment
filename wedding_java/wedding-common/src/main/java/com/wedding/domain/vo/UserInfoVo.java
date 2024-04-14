package com.wedding.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/09/22:31
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
    * 用户名
    */

    private String username;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String userEmail;

    private String phoneNumber;

    /**
     * 用户角色列表
     */
    private String userRole;


}
