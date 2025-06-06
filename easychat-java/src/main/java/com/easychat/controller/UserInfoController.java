package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVo;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisComponent;
import com.easychat.service.impl.UserInfoServiceImpl;
import com.easychat.utils.CopyTools;
import com.easychat.webSocket.ChannelContextUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.IOException;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController {


    private final UserInfoServiceImpl userInfoService;
    private final RedisComponent redisComponent;
    private final ChannelContextUtils channelContextUtils;

    public UserInfoController(UserInfoServiceImpl userInfoService, RedisComponent redisComponent, ChannelContextUtils channelContextUtils) {
        super();
        this.userInfoService = userInfoService;
        this.redisComponent = redisComponent;
        this.channelContextUtils = channelContextUtils;
    }

    @RequestMapping("getUserInfo")
    @GlobalInterceptor
    public ResponseVO getUserInfo(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        UserInfo userInfo = userInfoService.getUserInfoByUserId(tokenUserInfo.getUserId());
        UserInfoVo userInfoVo = CopyTools.copy(userInfo, UserInfoVo.class);

        userInfoVo.setAdmin(tokenUserInfo.getAdmin());

        return getSuccessResponseVO(userInfoVo);

    }

    @RequestMapping("saveUserInfo")
    @GlobalInterceptor
    public ResponseVO saveUserInfo(HttpServletRequest request, UserInfo userInfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException {

        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        userInfo.setUserId(tokenUserInfo.getUserId());
        userInfo.setCreateTime(null);
        userInfo.setStatus(null);
        userInfo.setPassword(null);
        userInfo.setLastLoginTime(null);

        UserInfo userInfoupdate = this.userInfoService.updateUserInfo(userInfo, avatarFile, avatarCover);
        tokenUserInfo.setNickName(userInfoupdate.getNickName());
        redisComponent.saveTokenUserInfoDto(tokenUserInfo);

        return getSuccessResponseVO(null);
    }

    @RequestMapping("updatePassword")
    @GlobalInterceptor
    public ResponseVO updatePassword(HttpServletRequest request, @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password, @NotEmpty String rePassword) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        if (!rePassword.equals(password)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        this.userInfoService.updatePassword(tokenUserInfo.getUserId(), password);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("logout")
    @GlobalInterceptor
    public ResponseVO logOut(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        channelContextUtils.closeContext(tokenUserInfo.getUserId());
        redisComponent.cleanTokenUserInfoDtoById(tokenUserInfo.getUserId());
        return getSuccessResponseVO(null);
    }
}
