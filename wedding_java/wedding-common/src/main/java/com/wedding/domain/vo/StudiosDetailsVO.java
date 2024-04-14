package com.wedding.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/14/20:29
 * @Description: 赢楼详情VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudiosDetailsVO {

    private Long id;
    private String StudioName;
    private String description;
    private String studioLocation;
    // 缩略图
    private String studioThumbnail;
    private Double rate;
    // 剩余最大预约数
    private Integer maxReservation;
    // 多少人评价
    private Integer rateCount;
    // 浏览量
    private Long viewCount;
    // 收藏量
    private Long collectCount;
    // 营业开始时间
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    // 营业结束时间
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    // 影楼图片列表
    private List<String> images;

    private List<PhotographerVO> photographerVOList;

}
