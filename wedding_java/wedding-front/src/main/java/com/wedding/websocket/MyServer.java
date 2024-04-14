package com.wedding.websocket;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.constants.SysConstants;
import com.wedding.constants.WebSocketConstants;
import com.wedding.domain.entity.ChatMessages;
import com.wedding.domain.entity.Customer;
import com.wedding.domain.entity.Users;
import com.wedding.service.ChatMessagesService;
import com.wedding.service.CustomerService;
import com.wedding.service.UsersService;
import com.wedding.utils.BeanCopyUtils;
import com.wedding.utils.RedisCache;
import com.wedding.domain.vo.StudioOnlineUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/16/15:35
 * @Description: websocket服务类
 */
@Component
@Slf4j
@ServerEndpoint("/wedding/chat/{userName}")
public class MyServer {
    // 存储用户聊天信息
    public static ChatMessagesService chatMessagesService;
    // 客服服务类
    public static CustomerService customerService;
    public static UsersService usersService;
    public static RedisCache redisCache;

    // 存储所有连接的客户端
    private static final Map<String, Session> sessionMap = new HashMap<>();

    // 消息收集列表
    private final CopyOnWriteArrayList<ChatMessages> messages= new CopyOnWriteArrayList();

    // 计数器
    private Integer num = 0;

    /**
     * onOpen方法
     * @param session session
     * @param userName 用户名
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userName") String userName){
        sessionMap.put(userName, session);
        // 将在线用户存入redis中
        saveOnlineUserInfo(userName,session);
        log.info("--------------------用户：{}进入聊天室，当前在线人数：{}---------------",userName,sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        result.set("users",jsonArray);

        sessionMap.forEach((k,v) ->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.set(WebSocketConstants.USER,k);
            jsonArray.add(jsonObject);
        });
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * onMessage方法
     * @param message 消息内容
     * @param userName 用户名
     * @param session session
     * @throws ParseException
     */
    @OnMessage
    public void onMessage(String message,@PathParam("userName") String userName, Session session) throws ParseException {
        log.info("收到来自客户端userName={}的消息：{}",userName,message);
        JSONObject messageObj = JSONUtil.parseObj(message);
        Long senderId = Long.valueOf(messageObj.getStr(WebSocketConstants.SENDER_ID));
        String senderName = messageObj.getStr(WebSocketConstants.SENDER_NAME);
        String senderAvatar = messageObj.getStr(WebSocketConstants.SENDER_AVATAR);
        String receiverAvatar = messageObj.getStr(WebSocketConstants.RECEIVER_AVATAR);
        Long receiverId = Long.valueOf(messageObj.getStr(WebSocketConstants.RECEIVER_ID));
        String receiverName = messageObj.getStr(WebSocketConstants.RECEIVER_NAME);
        String messageText = messageObj.getStr(WebSocketConstants.MESSAGE_CONTEXT);
        String createdTime = messageObj.getStr(WebSocketConstants.CREATED_TIME);

        Session toUserSession = sessionMap.get(receiverName);

        Long studioId = Long.valueOf(getParamMap(session).get("studioId"));

        if(toUserSession != null){
            // 组装消息发送给消息接收者
            JSONObject jsonObject = new JSONObject();
            jsonObject.set(WebSocketConstants.SENDER_ID,senderId);
            jsonObject.set(WebSocketConstants.SENDER_NAME,senderName);
            // 前端消息显示得时候头像需要对换
            jsonObject.set(WebSocketConstants.SENDER_AVATAR,senderAvatar);
            jsonObject.set(WebSocketConstants.MESSAGE_CONTEXT, messageText);
            jsonObject.set(WebSocketConstants.RECEIVER_ID,receiverId);
            jsonObject.set(WebSocketConstants.RECEIVER_NAME,receiverName);
            // 前端消息显示得时候头像需要对换
            jsonObject.set(WebSocketConstants.RECEIVER_AVATAR,receiverAvatar);
            jsonObject.set(WebSocketConstants.CREATED_TIME,createdTime);
            jsonObject.set(WebSocketConstants.STUDIO_ID,studioId);

            this.sendMsg(toUserSession,JSONUtil.toJsonStr(jsonObject));
            ChatMessages chatMessage = new ChatMessages();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parseDate = format.parse(createdTime);
            chatMessage.setSenderId(senderId);
            chatMessage.setSenderName(userName);
            chatMessage.setReceiverId(receiverId);
            chatMessage.setMessageText(messageText);
            chatMessage.setReceiverName(receiverName);
            chatMessage.setCreatedTime(parseDate);
            chatMessage.setUpdatedTime(parseDate);
            chatMessage.setStudioId(studioId);
            chatMessagesService.save(chatMessage);
            messages.add(chatMessage);
            num++;
            if(num > 5){
                // 每隔5次进行存入redis
                redisCache.setCacheList(SysConstants.CHAT_MESSAGE,messages);
                messages.clear();
                num = 0;
            }
            log.info("------------消息列表：messages：{}",messages);
            log.info("------------发送给用户：{}，消息：{}",receiverName, messageText);
        }else {
            log.error("----------消息发送失败！未找到用户：{}的session",receiverName);
        }
    }

    /**
     * 消息发送
     * @param toUserSession
     * @param message
     */
    private void sendMsg(Session toUserSession, String message) {
        try {
            toUserSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("---------消息发送失败！");
            throw new RuntimeException(e);
        }
    }

    /**
     * 消息发送（广播）
     * @param message 消息内容
     */
    private void sendAllMessage(String message) {
        sessionMap.forEach((k,v) ->{
            try {
                v.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void onError(Throwable error)  {
        error.printStackTrace();
    }

    @OnClose
    public void onClose(Session session,@PathParam("userName") String userName) throws IOException {
        // 获取影楼id
        String studioId = getParamMap(session).get("studioId");
        log.info("--------onClose studioId：{}---------",studioId);
        log.info("--------onClose：username{}---------",studioId);
        // 先从redis中获取在线用户列表
        Object cacheObject = redisCache.getCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY + studioId);
        // 类型转换
        StudioOnlineUserVo onlineStudioUser = BeanCopyUtils.copyBean(cacheObject, StudioOnlineUserVo.class);
        // 移除当前用户
        onlineStudioUser.getOnlineUsers()
                .removeIf(item -> item.getUsername().equals(userName));
        // 更新redis在线用户
        redisCache.setCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY + studioId, onlineStudioUser);
        session.close();
        sessionMap.remove(userName);
        log.info("--------------------用户：{}退出聊天室，当前在线人数：{}---------------",userName,sessionMap.size());
    }

    private List<Customer> getOnlineCustomers(Session session){
        Long studioId = Long.valueOf(getParamMap(session).get("studioId"));
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getStudioId,studioId);
        // 客服状态必须是正常的
        queryWrapper.eq(Customer::getStatus, SysConstants.CUSTOMER_NORMAL);
        List<Customer> customerList = customerService.list(queryWrapper);

        log.info("------getOnlineCustomers：{}--------",customerList);
        // 判断那些客服在线
        List<Customer> onlineCustomerList = customerList
                .stream()
                .filter(customer -> sessionMap.get(customer.getUsername()) != null)
                .collect(Collectors.toList());
        return (onlineCustomerList==null||onlineCustomerList.isEmpty())? Collections.emptyList():onlineCustomerList;

    }

    /**
     * 解析查询字符串
     * @param session session
     * @return 参数Map
     */
    private Map<String,String> getParamMap(Session session){
        String queryString = session.getQueryString();
        String[] params = queryString.split("&");
        Map<String,String> paramMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length==2){
                paramMap.put(keyValue[0],keyValue[1]);
            }
        }
        return paramMap;

    }

    /**
     * 将在线用户的信息存入redis
     * @param username 用户名
     * @param session session
     */
    private void saveOnlineUserInfo(String username,Session session){
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername,username);
        Users user = usersService.getOne(queryWrapper);
        log.info("-------------------user:{}",user);

        // 获取影楼id
        String studioId = getParamMap(session).get("studioId");
        // 先获取redis中的数据
        Object cacheObject = redisCache.getCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY + studioId);
        if (Objects.isNull(cacheObject)) {
            // 说明是第一个用户
            StudioOnlineUserVo studioOnlineUserVo = new StudioOnlineUserVo();
            List<Users> onlineUsers = new ArrayList<>();
            onlineUsers.add(user);
            studioOnlineUserVo.setStudioId(studioId);
            studioOnlineUserVo.setOnlineUsers(onlineUsers);
            redisCache.setCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY+studioId,studioOnlineUserVo);
        }
        // 判断是否已经存在
        // bean拷贝
        StudioOnlineUserVo onlineUserVo = BeanCopyUtils.copyBean(cacheObject, StudioOnlineUserVo.class);
        List<Users> collect = onlineUserVo.getOnlineUsers().stream()
                .filter(users -> users.getUsername().equals(username))
                .collect(Collectors.toList());
        if (collect.size()>0){
            return;
        }
        List<Users> onlineUsers = onlineUserVo.getOnlineUsers();
        onlineUsers.add(user);
        onlineUserVo.setOnlineUsers(onlineUsers);
        // 存入redis
        redisCache.setCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY+studioId,onlineUserVo);
    }
    // 用户离开聊天室后 更新在线客服数量




}
