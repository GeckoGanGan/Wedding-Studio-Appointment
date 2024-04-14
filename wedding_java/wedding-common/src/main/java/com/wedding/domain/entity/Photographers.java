package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 摄影师表(Photographers)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:22
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("photographers")
public class Photographers implements Serializable {
    //主键唯一标识;主键id
    @TableId(type = IdType.AUTO)
    private Long id;
    //摄影师姓名
    private String photographerName;
    //摄影师风格特点
    private String style;
    //摄影师邮箱地址
    private String photographerEmail;
    // 摄影师简洁
    private String description;
    // 摄影师生涯
    private String experience;

    // 性别
    private String sex;
    // 点赞量
    private Long likes;
    // 年龄
    private Integer age;

    // 摄影师照片
    private String photo;
    //状态
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
    //逻辑删除标志（0正常 1删除）
    @TableLogic
    private Integer delFlag;
    // 摄影师作品集
    @TableField(exist = false)
    private List<String> portfolio;

}

