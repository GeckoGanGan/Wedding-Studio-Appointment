package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户表(Users)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class Users implements Serializable {
    //主键唯一标识
    @TableId(type =  IdType.AUTO)
    private Long id;

    //用户名
    private String username;
    //密码
    private String password;

    // 头像
    private String avatar;
    // 昵称
    private String nickName;

    // 性别
    private String sex;

    // 状态 0正常 1停用
    private String status;

    //邮箱
    private String userEmail;
    //电话
    private String phoneNumber;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    //备注
    private String remark;
    //逻辑删除标志（0正常 1删除）
    private Integer delFlag;

    // 用户角色
    @TableField(exist = false)
    private String userRole;

}

