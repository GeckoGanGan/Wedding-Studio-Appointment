package com.wedding;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/1/19
 * @Description: 前台启动器
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@MapperScan("com.wedding.mapper")
@Slf4j
public class WeddingFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeddingFrontApplication.class, args);
        log.info("前台启动!");
    }
}