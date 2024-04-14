package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 菜单表(Menus)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("menus")
public class Menus implements Serializable  {
    //主键唯一标识
    @TableId(type = IdType.AUTO)
    private Long menuId;

    //菜单名称
    private String menuName;
    //父菜单id，标识菜单的层次结构
    private Long parentId;
    //路由地址
    private String path;
    //组件地址
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //图标
    private String icon;
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
    //逻辑删除标志
    @TableLogic
    private Integer delFlag;

}

