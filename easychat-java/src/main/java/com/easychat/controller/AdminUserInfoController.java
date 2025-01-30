package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.enums.UserStatusEnum;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.query.UserInfoQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import com.easychat.service.impl.UserInfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin")
public class AdminUserInfoController extends ABaseController {

    private final UserInfoServiceImpl userInfoService;

    public AdminUserInfoController(UserInfoServiceImpl userInfoService) {
        super();
        this.userInfoService = userInfoService;
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
        PaginationResultVO paginationResultVO = userInfoService.findListByPage(userInfoQuery);
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
