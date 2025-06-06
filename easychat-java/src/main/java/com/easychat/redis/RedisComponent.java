package com.easychat.redis;

import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.SysSettingDto;
import com.easychat.entity.dto.TokenUserInfoDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RedisComponent {

    @Resource
    private RedisUtils redisUtils;

    public Long getUserHeartBeat(String userId) {
        return (Long) redisUtils.get(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
    }

    public void saveUserHeartBeat(String userId) {
        redisUtils.setex(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId, System.currentTimeMillis(), Constants.REDIS_KEY_EXPIRES_HEART_BEAR);
    }

    public void removeUserHeartBeat(String userId) {
        redisUtils.delete(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
    }

    public void saveTokenUserInfoDto(TokenUserInfoDto tokenUserInfoDto) {
        redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN + tokenUserInfoDto.getToken(), tokenUserInfoDto, Constants.REDIS_TIME_1DAY * 7);
        redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN_USERID + tokenUserInfoDto.getUserId(), tokenUserInfoDto.getToken(), Constants.REDIS_TIME_1DAY * 7);
    }

    public SysSettingDto getSysSetting() {
        SysSettingDto sysSettingDto = (SysSettingDto) redisUtils.get(Constants.REDIS_KEY_SYSSETTING);
        if (Objects.isNull(sysSettingDto)) {
            sysSettingDto = new SysSettingDto();
            redisUtils.set(Constants.REDIS_KEY_SYSSETTING, sysSettingDto);
        }
        return sysSettingDto;
    }

    public TokenUserInfoDto getTokenUserInfo(String token) {
        return (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);
    }

    public void cleanTokenUserInfoDtoById(String userId) {
        String userToken = (String) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN_USERID + userId);
        redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN + userToken);
        redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN_USERID + userId);
    }

    public void setSysSetting(SysSettingDto sysSettingDto) {
        redisUtils.set(Constants.REDIS_KEY_SYSSETTING, sysSettingDto);
    }

    //    清空联系人
    public void cleanUserContact(String userId) {
        redisUtils.delete(Constants.REDIS_KEY_USER_CONTACT + userId);
    }


    //    添加联系人
    public void addUserContact(String userId, String contactId) {
        List queueList = redisUtils.getQueueList(Constants.REDIS_KEY_USER_CONTACT + userId);
        if (queueList.contains(contactId)) {
            return;
        }
        redisUtils.lpush(Constants.REDIS_KEY_USER_CONTACT + userId, contactId, Constants.REDIS_TIME_1DAY * 2);

    }

    //    批量添加联系人
    public void addUserContactBatch(String userId, List<String> contactIdList) {
        redisUtils.lpushAll(Constants.REDIS_KEY_USER_CONTACT + userId, contactIdList, Constants.REDIS_TIME_1DAY * 2);
    }

    //    删除联系人
    public void removeUserContact(String userId, String contactId) {
        redisUtils.remove(Constants.REDIS_KEY_USER_CONTACT + userId, contactId);
    }

    public List<String> getUserContact(String userId) {
        return (List<String>) redisUtils.getQueueList(Constants.REDIS_KEY_USER_CONTACT + userId);
    }


}
