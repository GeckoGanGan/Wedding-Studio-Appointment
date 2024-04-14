package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Photographers;
import com.wedding.mapper.PhotographersMapper;
import com.wedding.service.PhotographersService;
import com.wedding.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 摄影师表;(photographers)表控制层
 *
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "摄影师表对象功能接口")
@RestController
@RequestMapping("/photographers")
@Slf4j
public class PhotographersController {
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private PhotographersMapper photographersMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Photographers> queryById(Long id) {
        return ResponseEntity.ok(photographersService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ResponseResult paginQuery(@RequestBody PageDTO<Photographers> queryParam) {
        log.info("-----------进入studiosController.paginQuery方法-----------:{}", queryParam);
        return photographersService.paginQuery(queryParam);
    }

    /**
     * 新增数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<Photographers> add(Photographers photographers) {
        return ResponseEntity.ok(photographersService.insert(photographers));
    }

    /**
     * 更新数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<Photographers> edit(Photographers photographers) {
        return ResponseEntity.ok(photographersService.update(photographers));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(photographersService.deleteById(id));
    }

    /**
     *
     * 查询摄影师列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询摄影师列表")
    public ResponseResult getPhotographerList() {

        return photographersService.listPhotographers();
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @GetMapping("/like/{id}")
    @ApiOperation("点赞")
    public ResponseResult like(@PathVariable("id") String id) {
        log.info("------like:{}--------",id);
        log.info("-----------------------：{}", SecurityUtils.getLoginUser().getUser());
        return photographersService.like(id);
    }

    /**
     * 查询摄影师个人信息
     * @param id
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("查询摄影师个人信息")
    public ResponseResult getPhotographerInfo(@RequestParam("id") Long id) {
        Photographers photographersById = photographersMapper.getPhotographersById(id);
        return ResponseResult.okResult(photographersById);
    }
}