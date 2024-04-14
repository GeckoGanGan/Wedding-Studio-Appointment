package com.wedding.contrloller;


import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Studios;
import com.wedding.service.StudiosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 影楼表;(studios)表控制层
* @author : zhudake
* @date : 2024-3-1
*/
@Api(tags = "影楼表对象功能接口")
@RestController
@RequestMapping("/studios")
@Slf4j
public class StudiosController {
   @Autowired
   private StudiosService studiosService;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @ApiOperation("通过ID查询单条数据")
   @GetMapping("{id}")
   public ResponseResult queryById(Long id){
       return ResponseResult.okResult(studiosService.queryById(id));
   }

   /**
    * 分页查询
    *
    * @param pageDTO 分页+条件查询
    * @return 查询结果
    */
   @ApiOperation("分页查询")
   @PostMapping("/page")
   public ResponseResult paginQuery(@RequestBody PageDTO<Studios> pageDTO){
       log.info("-----------进入studiosController.paginQuery方法-----------:{}",pageDTO);
       return studiosService.paginQuery(pageDTO);
   }

   /**
    * 新增数据
    *
    * @param studios 实例对象
    * @return 实例对象
    */
   @ApiOperation("新增数据")
   @PostMapping
   public ResponseResult add(Studios studios){
       return ResponseResult.okResult(studiosService.insert(studios));
   }

   /**
    * 更新数据
    *
    * @param studios 实例对象
    * @return 实例对象
    */
   @ApiOperation("更新数据")
   @PutMapping
   public ResponseResult edit(Studios studios){
       return ResponseResult.okResult(studiosService.update(studios));
   }

   /**
    * 通过主键删除数据
    *
    * @param id 主键
    * @return 是否成功
    */
   @ApiOperation("通过主键删除数据")
   @DeleteMapping
   public ResponseResult deleteById(Long id){
       return ResponseResult.okResult(studiosService.deleteById(id));
   }
}