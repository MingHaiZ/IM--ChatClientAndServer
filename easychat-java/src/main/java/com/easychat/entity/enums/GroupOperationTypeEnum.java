package com.easychat.entity.enums;

public enum GroupOperationTypeEnum {
    REMOVE(0, "移出群组"),
    ADD(1, "拉入群组");
    private Integer type;
    private String desc;

    GroupOperationTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static GroupOperationTypeEnum getbyType(Integer type) {
        for (GroupOperationTypeEnum item : GroupOperationTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
