package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.AppointmentDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Appointments;
import com.wedding.service.AppointmentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 /**
 * 预约表;(appointments)表控制层
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "预约表对象功能接口")
@RestController
@RequestMapping("/appointments")
@Slf4j
public class AppointmentsController{
    @Autowired
    private AppointmentsService appointmentsService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Appointments> queryById(Long id){
        return ResponseEntity.ok(appointmentsService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ResponseResult paginQuery(@RequestBody PageDTO<Appointments> queryParam){
        log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
        return appointmentsService.paginQuery(queryParam);
    }
    
    /** 
     * 新增数据
     *
     * @param selectedPackage 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增预约")
    @PostMapping("/appoint")
    public ResponseResult add(@RequestBody AppointmentDTO selectedPackage){
        log.info("-----------进入studiosController.add方法-----------:{}",selectedPackage);
        return appointmentsService.insert(selectedPackage);
    }
     /**
      * 取消预约
      *
      * @param id 主键
      * @return 是否成功
      */
     @ApiOperation("取消预约")
     @DeleteMapping("/cancel/{id}")
     public ResponseResult deleteById(@PathVariable("id") Long id){
         log.info("-----------进入studiosController.deleteById方法-----------:{}",id);
         return ResponseResult.okResult(appointmentsService.deleteById(id));
     }
    
    /** 
     * 更新数据
     *
     * @param appointments 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseResult edit(Appointments appointments){
        return appointmentsService.update(appointments);
    }

}