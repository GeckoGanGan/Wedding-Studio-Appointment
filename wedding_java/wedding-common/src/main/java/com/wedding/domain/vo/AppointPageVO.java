package com.wedding.domain.vo;

import com.wedding.domain.entity.Appointments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/28/20:09
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointPageVO {
    private Long total;
    private List<Appointments> records;
    // 总金额
    private Long totalMoney;

}
