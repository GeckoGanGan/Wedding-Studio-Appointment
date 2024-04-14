package com.wedding.controller;


import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.CollectDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Collect;
import com.wedding.domain.entity.Studios;
import com.wedding.service.CollectService;
import com.wedding.service.StudiosService;
import com.wedding.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 影楼表;(studios)表控制层
 *
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
    @Autowired
    private CollectService collectService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("/{id}")
    public Studios queryById(@PathVariable("id") Long id) {
        return studiosService.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param queryParam 分页+条件查询
     * @return 查询结果
     */
    @ApiOperation("分页查询影楼接口")
    @PostMapping("/page")
    public ResponseResult paginQuery(@RequestBody PageDTO<Studios> queryParam) {
        log.info("-----------进入studiosController.paginQuery方法-----------:{}", queryParam);
        return studiosService.paginQuery(queryParam);
    }

    /**
     * 新增数据
     *
     * @param studios 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseResult add(Studios studios) {
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
    public ResponseResult edit(Studios studios) {
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
    public ResponseResult deleteById(Long id) {
        return ResponseResult.okResult(studiosService.deleteById(id));
    }


    @ApiOperation("通过id查询影楼详情")
    @GetMapping("/studioDetails/{id}")
    public ResponseResult getStudioDetailsById(@PathVariable("id") Long id){
        return studiosService.getStudioDetails(id);
    }
    /**
     * 浏览
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @ApiOperation("浏览")
    public ResponseResult view(@PathVariable("id") String id) {
        return studiosService.view(id);
    }
    /**
     * 浏览
     * @param param
     * @return
     */
    @PostMapping("/collect")
    @ApiOperation("收藏")
    public ResponseResult collect(@RequestBody CollectDTO param) {
        return studiosService.collect(param);
    }

    /**
     * 热榜排行
     * @param type
     * @return
     */
    @GetMapping("/hot/{type}")
    @ApiOperation("热榜")
    public ResponseResult hot(@PathVariable("type") String type) {
        return studiosService.hot(type);
    }

    /**
     * 热榜排行
     * @param collectPageDTO
     * @return
     */
    @PostMapping("/getCollectByUserId")
    @ApiOperation("查询当前登录用户收藏列表")
    public ResponseResult getCollectByUserId(@RequestBody PageDTO<Collect> collectPageDTO) {
        log.info("------getCollectByUserId:{}--------",collectPageDTO);
        return collectService.getCollectByUserId(collectPageDTO);
    }

    // 取消收藏
    @PostMapping("/collect/cancel")
    @ApiOperation("取消收藏")
    public ResponseResult collectCancel(@RequestParam("collectId") Long collectId) {
        return collectService.collectCancel(collectId);
    }

}