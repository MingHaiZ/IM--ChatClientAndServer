package com.easychat.entity.constants;

import com.easychat.entity.enums.UserContactTypeEnum;

public class Constants {

    public static final String REDIS_KEY_PREFIX = "easychat:";
    public static final String REDIS_KEY_WS_PREFIX = REDIS_KEY_PREFIX + "ws:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkCode:";
    public static final Integer REDIS_TIME_1MIN = 60;
    public static final long REDIS_TIME_1DAY = 60 * 60 * 24;
    public static final Integer LENGTH_11 = 11;
    public static final Integer LENGTH_20 = 20;
    public static final String REDIS_KEY_WS_USER_HEART_BEAT = REDIS_KEY_WS_PREFIX + "user:heartbeat:";
    public static final String REDIS_KEY_WS_TOKEN = REDIS_KEY_WS_PREFIX + "token:";
    public static final String REDIS_KEY_WS_TOKEN_USERID = REDIS_KEY_WS_TOKEN + "userid:";
    public static final String ROBOT_UUID = UserContactTypeEnum.USER.getPrefix() + "robot";
    public static final String REDIS_KEY_SYSSETTING = REDIS_KEY_PREFIX + "syssetting:";
    public static final String FILE_FOLDER_FILE = "/file/";
    public static final String FILE_FOLDER_AVATAR_NAME = "avatar/";
    public static final String IMAGE_SUFFIX = ".png";
    public static final String COVER_IMAGE_SUFFIX = "_cover.png";
    public static final String APPLY_INFO_TEMPLATE = "我是";
    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-z])[\\da-zA-z~!@#$%^&*_]{8,18}$";
    public static final String APP_UPDATE_FOLDER = "/app/";
    public static final String APP_EXE_SUFFIX = ".exe";
    public static final String APP_NAME = "EastChatSetup.";
    public static final String WS_PROTOCOL = "/ws";
    public static final long REDIS_KEY_EXPIRES_HEART_BEAR = 6;
    public static final String REDIS_KEY_USER_CONTACT = REDIS_KEY_WS_PREFIX + "user:contact:";
    public static final long MILLISSECONDS_3DAYS_BEFORE = 3 * 24 * 60 * 60 * 1000;
    public static final String[] IMAGE_SUFFIX_LIST = new String[]{".jpeg", ".jpg", ".png", ".gif", ".bmp", ".webp"};
    public static final String[] VIDEO_SUFFIX_LIST = new String[]{".mp4", ".avi", ".rmvb", ".mkv", ".mov"};
    public static final Long FILE_SIZE_MB = 1024 * 1024L;
}
