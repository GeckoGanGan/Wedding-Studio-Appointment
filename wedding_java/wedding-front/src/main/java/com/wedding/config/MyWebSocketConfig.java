package com.wedding.config;

import com.wedding.mapper.CustomerMapper;
import com.wedding.service.ChatMessagesService;
import com.wedding.service.CustomerService;
import com.wedding.service.UsersService;
import com.wedding.service.impl.CustomerServiceImpl;
import com.wedding.utils.RedisCache;
import com.wedding.websocket.MyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/16/15:06
 * @Description:Websocket配置类
 */
@Configuration
public class MyWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setCustomerService(CustomerService customerService){
        MyServer.customerService = customerService;
    }

    @Autowired
    public void setUserService(UsersService usersService){
        MyServer.usersService = usersService;
    }

    @Autowired
    public void redisCache(RedisCache redisCache){
        MyServer.redisCache = redisCache;
    }

    @Autowired
    public void chatMessagesService(ChatMessagesService chatMessagesService){
        MyServer.chatMessagesService = chatMessagesService;
    }
}
