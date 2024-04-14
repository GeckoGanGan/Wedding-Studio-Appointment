package com.wedding.domain.vo;

import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Studios;
import com.wedding.domain.entity.StudiosPackage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/23/15:56
 * @Description: 套餐VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioPackageVo {
    private Studios studio;
    private List<StudiosPackage> studiosPackageList;

    private List<Photographers> photographers;

}
