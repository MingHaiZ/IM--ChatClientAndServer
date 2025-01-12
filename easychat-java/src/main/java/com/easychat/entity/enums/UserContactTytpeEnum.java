package com.easychat.entity.enums;

import com.easychat.utils.StringTools;

public enum UserContactTytpeEnum {

    USER(0, "U", "好友"),
    GROUP(1, "G", "群组");

    private Integer type;
    private String prefix;
    private String desc;

    UserContactTytpeEnum(Integer type, String prefix, String desc) {
        this.type = type;
        this.prefix = prefix;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDesc() {
        return desc;
    }

    public static UserContactTytpeEnum getByName(String name) {
        try {
            if (StringTools.isEmpty(name)) {
                return null;
            }
            return UserContactTytpeEnum.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    public static UserContactTytpeEnum getByPrefix(String prefix) {
        try {
            if (StringTools.isEmpty(prefix) || prefix.trim().isEmpty()) {
                return null;
            }
            prefix = prefix.substring(0, 1).toUpperCase();
            for (UserContactTytpeEnum type : UserContactTytpeEnum.values()) {
                if (type.getPrefix().equals(prefix)) {
                    return type;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
