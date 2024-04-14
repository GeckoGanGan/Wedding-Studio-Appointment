package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Reviews;
import com.wedding.service.ReviewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


 /**
 * 评价表;(reviews)表控制层
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "评价表对象功能接口")
@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewsController{
    @Autowired
    private ReviewsService reviewsService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Reviews> queryById(Long id){
        return ResponseEntity.ok(reviewsService.queryById(id));
    }

     /**
      * 分页查询
      *
      * @param queryParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation("分页查询")
     @PostMapping("/page")
     public ResponseResult paginQuery(@RequestBody PageDTO<Reviews> queryParam){
         log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
         return reviewsService.paginQuery(queryParam);
     }
    
    /** 
     * 新增数据
     *
     * @param reviews 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<Reviews> add(Reviews reviews){
        return ResponseEntity.ok(reviewsService.insert(reviews));
    }
    
    /** 
     * 更新数据
     *
     * @param reviews 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<Reviews> edit(Reviews reviews){
        return ResponseEntity.ok(reviewsService.update(reviews));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id){
        return ResponseEntity.ok(reviewsService.deleteById(id));
    }
}