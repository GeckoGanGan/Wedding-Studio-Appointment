package com.wedding.domain.vo;

import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Studios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/04/09/19:49
 * @Description: 收藏vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectVO {
    private List<Studios> studios;
    private List<Photographers> photographers;
}
