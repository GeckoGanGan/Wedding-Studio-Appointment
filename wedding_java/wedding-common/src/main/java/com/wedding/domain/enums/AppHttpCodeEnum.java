package com.wedding.domain.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOTNULL(506,"评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传png文件"),
    USERNAME_NOT_NULL(508,"用户名不能为空"),
    NICKNAME_NOT_NULL(509,"昵称不能为空"),
    PASSWORD_NOT_NULL(510,"密码不能为空"),
    EMAIL_NOT_NULL(511,"邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    AUTHENTICATION_FAILED(513,"认证失败"),
    CANNOT_DELETE_CURRENT_USER(514,"不能删除当前操作用户"),
    MENU_NAME_EXISTS_OR_COMPONENT_EXISTS(515,"菜单名或组件名已存在"),
    MENU_NO_NULL(516,"菜单已存在" ),
    ARTICLE_NO_NULL(517,"文章不能为空！" ),
    COMMENT_IS_NULL(518,"没有更多评论了" ),
    GET_CAPTCHA_ERROR(519,"获取图形验证码失败"),
    IMAGE_CODE_IS_EXPIRE(520,"验证码已过期，请获取新的验证码！"),
    IMAGE_CODE_SUCCESS(521,"图形验证码验证成功！"),
    IMAGE_CODE_ERROR(522,"验证码错误！"),
    APPOINT_NO_DATA(523,"您暂时还没有预约信息" ),
    NOT_ALLOW_COLLECT_REPEATABLE(524,"您已经收藏过了，不能重复收藏！" ),
    UPDATE_ERROR(525,"更新失败！"),
    DELETE_ERROR(526,"删除失败！" ),
    USER_NOT_EXISTS(527,"您尚未注册账号，请先注册账号~" );


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
