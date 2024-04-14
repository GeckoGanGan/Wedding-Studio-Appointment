package com.wedding.contrloller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Menus;
import com.wedding.service.MenusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 /**
 * 菜单表;(menus)表控制层
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "菜单表对象功能接口")
@RestController
@RequestMapping("/menus")
@Slf4j
public class MenusController{
    @Autowired
    private MenusService menusService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{menuid}")
    public ResponseResult queryById(Long menuId){
        return menusService.queryById(menuId);
    }

     /**
      * 分页查询
      *
      * @param queryParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation("分页查询")
     @GetMapping
     public ResponseResult paginQuery(@RequestBody PageDTO<Menus> queryParam){
         log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
         return menusService.paginQuery(queryParam);
     }
    
    /** 
     * 新增数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseResult add(Menus menus){
        return menusService.insert(menus);
    }
    
    /** 
     * 更新数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseResult edit(Menus menus){
        return menusService.update(menus);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseResult deleteById(Long menuId){
        return menusService.deleteById(menuId);
    }
}