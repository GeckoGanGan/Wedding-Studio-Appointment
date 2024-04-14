package com.wedding.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/14/20:44
 * @Description: 摄影师信息展示VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerVO {
    //摄影师姓名
    private String photographerName;
    //摄影师风格特点
    private String style;
    //摄影师邮箱地址
    private String photographerEmail;
    // 摄影师介绍
    private String description;
    // 性别
    private String sex;
    // 年龄
    private Integer age;
    // 摄影师照片
    private String photo;
}
