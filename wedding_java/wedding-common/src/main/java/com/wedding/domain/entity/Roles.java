package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 角色表(Roles)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("roles")
public class Roles implements Serializable {
    //主键唯一标识
    @TableId(type = IdType.AUTO)
    private Long roleId;

    //角色名称
    private String roleName;
    //角色权限字符
    private String roleKey;
    //角色状态（0正常 1停用）
    private String status;
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
    //逻辑删除
    private Integer delFlag;

}

