package com.wedding;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/1/19
 * @Description: 管理员后台启动器
 */
@SpringBootApplication
@MapperScan("com.wedding.mapper")
@Slf4j
public class WeddingAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeddingAdminApplication.class,args);
        log.info("后台启动!");
    }
}