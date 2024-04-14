package com.wedding.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.StudiosPackage;
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
 * @Date: 2024/03/24/15:44
 * @Description: 影楼预约DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long studioId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;
    private String appointmentRemark;
    private StudiosPackage selectedPackageInfo;

    private List<Photographers> selectedPhotographersInfo;

}
