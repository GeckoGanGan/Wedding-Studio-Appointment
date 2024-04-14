package com.wedding.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/01/20:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {
    private Long total;
    private List records;
    private boolean hasData;
}
