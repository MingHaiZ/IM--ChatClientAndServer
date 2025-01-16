package com.easychat.entity.enums;


public enum ResponseCodeEnum {
    CODE_200(200, "请求成功"),
    CODE_404(404, "请求地址不存在"),
    CODE_600(600, "请求参数错误"),
    CODE_601(601, "信息已经存在"),
    CODE_602(602, "账号或密码错误"),
    CODE_603(603, "账号已禁用"),
    CODE_604(604, "此账号已在别处登录,请退出后再登录"),
    CODE_500(500, "服务器返回错误，请联系管理员");

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
