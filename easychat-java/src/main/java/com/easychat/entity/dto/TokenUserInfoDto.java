package com.easychat.entity.dto;

import java.io.Serializable;

public class TokenUserInfoDto implements Serializable {

    private String token;
    private String userId;
    private String nickName;
    private Boolean isAdmin;

    public TokenUserInfoDto() {
    }

    public TokenUserInfoDto(String token, String userId, String nickName, Boolean isAdmin) {
        this.token = token;
        this.userId = userId;
        this.nickName = nickName;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
