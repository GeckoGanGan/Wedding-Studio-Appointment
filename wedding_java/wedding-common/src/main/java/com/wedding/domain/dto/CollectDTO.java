package com.wedding.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/04/06/22:02
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectDTO {
    // 收藏类型
    private String type;
    // id
    private Long id;
    // 收场名字
    private String name;
}
