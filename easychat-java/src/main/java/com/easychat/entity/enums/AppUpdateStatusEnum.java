package com.easychat.entity.enums;

public enum AppUpdateStatusEnum {
    INIT(0, "未发布"),
    GRAYSCALE(1, "灰度发布"),
    ALL(2, "全网发布");

    private Integer status;
    private String desc;

    AppUpdateStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static AppUpdateStatusEnum getByStatus(Integer status) {
        for (AppUpdateStatusEnum e : AppUpdateStatusEnum.values()) {
            if (e.getStatus() == status) {
                return e;
            }
        }
        return null;
    }
}
