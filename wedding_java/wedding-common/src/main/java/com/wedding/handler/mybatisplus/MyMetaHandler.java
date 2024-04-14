package com.wedding.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wedding.domain.entity.Users;
import com.wedding.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/04/16:41
 * @Description: 自动字段填充
 */
@Component
@Slf4j
public class MyMetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try{
            Users user = SecurityUtils.getLoginUser().getUser();
            log.info("----------MyMetaHandler:{}--------",user);
            userId = user.getId();
        }catch (Exception e){
            e.printStackTrace();
            userId = -1L; //表示是自己创建的
        }
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("createdTime", new Date(), metaObject);
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = null;
        try{
            userId = SecurityUtils.getLoginUser().getUser().getId();
            log.info("----------MyMetaHandler:{}--------",userId);
        }catch (Exception e){
            e.printStackTrace();
            userId = -1L; //表示是自己创建的
        }
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", new Date(), metaObject);
    }
}
