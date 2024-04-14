package com.wedding.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/01/20:25
 * @Description: 分页查询参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private Long pageNum;
    private Long pageSize;
    // 查询条件
    private T queryConditions;

}
