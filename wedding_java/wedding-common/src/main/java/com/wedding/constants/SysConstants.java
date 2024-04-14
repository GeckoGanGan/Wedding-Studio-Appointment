package com.wedding.constants;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/09/22:49
 * @Description:
 */

public class SysConstants {
    public static final String WEDDING_IMAGE_CODE = "wedding_image_code" ;
    // 图形验证码过期时间 2分钟
    public static final Integer IMAGE_CODE_EXPIRE = 2;
    public static final String CHAT_MESSAGE = "chat_message";
    public static final String CUSTOMER_NORMAL = "0";
    public static final String STUDIO_ONLINE_USER_KEY = "onlineUser";
    public static final Integer IS_PHOTOGRAPHER = 1;
    public static final String PHOTOGRAPHERS_LIKE = "photographers_like";
    public static final String STUDIO_STATUS_NORMAL =  "0" ;
    public static final String STUDIOS_COLLECT = "studios_collect";
    public static final String STUDIOS_VIEW = "studios_view";
    public static final String HOT_TYPE_VIEWCOUNT = "1";
    public static final String HOT_TYPE_POINT = "0";
    public static final String COLLECT_STUDIOS = "0";
    public static final String COLLECT_PHOTOGRAPHERS = "1";

    // 私有构造，防止实例化
    private SysConstants() {
    }
    /**
     * 验证码过期时间
     */
    public static final Integer EXPIRED_SMS_CODE = 24;
    /**
     * 套餐状态
     */
    public static final Integer PACKAGE_NORMAL = 0;

    /**
     *  预约的状态 0 待确认 1已确认 2已完成
     */
    public static final String APPOINT_STATUS_NORMAL = "0" ;
    /**
     *  预约的状态 0 待确认 1已确认 2已完成
     */
    public static final String APPOINT_STATUS_SUCCESS = "1" ;
    /**
     *  预约的状态 0 待确认 1已确认 2已完成
     */
    public static final String APPOINT_STATUS_FINISH = "2" ;

    /**
     * 评论类型 0影楼 1.。。
     */
    public static String COMMENT_TYPE = "0";

    /**
     * 评论是跟评论
     */
    public static final int COMMENT_ISROOT = -1;


    /**
     * 摄影师是否启用
     */
    public static final String PHOTOGRAPHER_STATUS_ENABLE = "0";
    /**
     * 注册用户默认头像
     */
    public static final String DEFAULT_AVATAR = "http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png";
    /**
     * 注册用户默认昵称
     */
    public static final String DEFAULT_NICKNAME = "王老五";
    /**
     * redis 用户信息前缀
     */
    public static final String WEDDING_LOGIN = "wedding_login";
    /**
     * 用户状态
     */
    public static final Integer USER_STATUS_NORMAL = 0;

    /**
     * 普通角色ID
     */
    public static final Long USER_ROLE_NORMAL = 2L;

    /**
     * 登录的时候用户信息存入redis过期时间
     */
    public static final Integer EXPIRED_USER = 24;
}
