package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.enums.UserStatusEnum;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.query.UserInfoQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisComponent;
import com.easychat.redis.RedisConfig;
import com.easychat.service.impl.UserInfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserInfoController extends ABaseController {

    private final UserInfoServiceImpl userInfoService;
    private final RedisComponent redisComponent;
    private final RedisConfig redisConfig;

    public AdminUserInfoController(UserInfoServiceImpl userInfoService, RedisComponent redisComponent, RedisConfig redisConfig) {
        super();
        this.userInfoService = userInfoService;
        this.redisComponent = redisComponent;
        this.redisConfig = redisConfig;
    }

    @RequestMapping("loadUser")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO loadUser(HttpServletRequest request, Integer pageNo, Integer pageSize, String nickNameFuzzy, String userId) {
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        userInfoQuery.setUserIdFuzzy(userId);
        userInfoQuery.setNickNameFuzzy(nickNameFuzzy);
        userInfoQuery.setOrderBy("create_time desc ");
        userInfoQuery.setPageNo(pageNo);
        userInfoQuery.setPageSize(pageSize);
        PaginationResultVO<UserInfo> paginationResultVO = userInfoService.findListByPage(userInfoQuery);
//        for (UserInfo userInfo : paginationResultVO.getList()) {
//            if (redisComponent.getUserHeartBeat(userInfo.getUserId()) != null) {
//                userInfo.setOnlineType(1);
//            }
//        }

        return getSuccessResponseVO(paginationResultVO);
    }

    @RequestMapping("updateUserStatus")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO updateUserStatus(@NotEmpty String userId, @NotNull Integer status) {
        UserStatusEnum byStatus = UserStatusEnum.getByStatus(status);
        if (byStatus == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (userInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        userInfo = new UserInfo();
        userInfo.setStatus(byStatus.getStatus());
        this.userInfoService.updateUserInfoByUserId(userInfo, userId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("forceOffLine")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO forceOffLine(@NotEmpty String userId) {

        this.userInfoService.forceOffLine(userId);

        return getSuccessResponseVO(null);
    }


}
