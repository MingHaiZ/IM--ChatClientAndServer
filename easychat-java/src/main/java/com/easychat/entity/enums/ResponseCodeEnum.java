package com.easychat.entity.enums;


public enum ResponseCodeEnum {
    CODE_200(200, "请求成功"),
    CODE_404(404, "请求地址不存在"),
    CODE_600(600, "请求参数错误"),
    CODE_601(601, "信息已经存在"),
    CODE_602(602, "账号或密码错误"),
    CODE_603(603, "账号已禁用"),
    CODE_604(604, "此账号已在别处登录,请退出后再登录"),
    CODE_605(605, "文件不存在"),
    CODE_500(500, "服务器返回错误，请联系管理员"),
    CODE_901(901, "登录超时"),
    CODE_902(902, "您不是对方的好友,请先向好友发送朋友的验证申请"),
    CODE_903(903, "您已经不在群聊,请重新加入群聊");


    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
