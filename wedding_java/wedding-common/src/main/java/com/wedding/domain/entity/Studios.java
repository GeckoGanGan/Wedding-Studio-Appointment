package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 影楼表(Studios)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("studios")
public class Studios implements Serializable  {
    //主键唯一标识
    @TableId(type = IdType.AUTO)
    private Long id;

    //影楼名称
    private String studioName;
    //影楼地理位置
    private String studioLocation;
    //影楼提供的服务项目
    private String studioService;
    // 缩略图
    private String studioThumbnail;
    // 影楼描述
    private String description;
    // 浏览量
    private Long viewCount;
    // 收藏量
    private Long collectCount;
    //状态
    private String status;
    // 打分
    private Double rate;
    // 营业开始时间
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    // 营业结束时间
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    // 剩余最大预约数
    private Integer maxReservation;
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

}

