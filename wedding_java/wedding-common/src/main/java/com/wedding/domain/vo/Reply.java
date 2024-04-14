package com.wedding.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/16/23:18
 * @Description:
 */
@Setter
@Getter
public class Reply {
    private Integer total;
    private List<CommentCobyVo> list;
}
