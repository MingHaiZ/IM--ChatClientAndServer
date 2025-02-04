package com.easychat.entity.enums;

public enum AppUpdateFileTypeEnum {
    LOCAL(0, "本地文件"),
    OUT_LINK(1, "外链");

    private Integer fileType;
    private String desc;

    AppUpdateFileTypeEnum(Integer fileType, String desc) {
        this.fileType = fileType;
        this.desc = desc;
    }

    public Integer getFileType() {
        return fileType;
    }

    public String getDesc() {
        return desc;
    }

    public static AppUpdateFileTypeEnum getByFileType(Integer fileType) {
        for (AppUpdateFileTypeEnum fileTypeEnum : AppUpdateFileTypeEnum.values()) {
            if (fileTypeEnum.getFileType().equals(fileType)) {
                return fileTypeEnum;
            }
        }
        return null;
    }
}
