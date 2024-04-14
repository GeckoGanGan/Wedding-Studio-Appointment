package com.wedding.contrloller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Roles;
import com.wedding.service.RolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 /**
 * 角色表;(roles)表控制层
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "角色表对象功能接口")
@RestController
@RequestMapping("/roles")
@Slf4j
public class RolesController{
    @Autowired
    private RolesService rolesService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{roleid}")
    public ResponseResult queryById(Long roleId){
        return rolesService.queryById(roleId);
    }

     /**
      * 分页查询
      *
      * @param queryParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation("分页查询")
     @PostMapping("/page")
     public ResponseResult paginQuery(@RequestBody PageDTO<Roles> queryParam){
         log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
         return rolesService.paginQuery(queryParam);
     }
    
    /** 
     * 新增数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseResult add(Roles roles){
        return rolesService.insert(roles);
    }
    
    /** 
     * 更新数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseResult edit(Roles roles){
        return rolesService.update(roles);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseResult deleteById(Long roleId){
        return rolesService.deleteById(roleId);
    }
}