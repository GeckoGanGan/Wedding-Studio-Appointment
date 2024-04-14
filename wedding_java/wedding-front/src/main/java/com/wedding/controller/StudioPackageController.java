package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.vo.StudioPackageVo;
import com.wedding.service.StudiosPackageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/23/16:31
 * @Description: 影楼套餐控制类
 */
@RestController
@RequestMapping("/package")
@Slf4j
public class StudioPackageController {
    @Autowired
    private StudiosPackageService studiosPackageService;


    @GetMapping("/{studioId}")
    @ApiOperation("影楼套餐查询接口")
    public ResponseResult getStudioPackages(@PathVariable("studioId") Long studioId){
        log.info("------------进入影楼套餐查询接口--------------");
        StudioPackageVo studioPackages = studiosPackageService.getStudioPackages(studioId);
        log.info("--------影楼：{}套餐查询结果：{}",studioId,studioPackages);
        return ResponseResult.okResult(studioPackages);
    }
}
