package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.po.UserInfoBeauty;
import com.easychat.entity.query.UserInfoBeautyQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.impl.UserInfoBeautyServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("admin")
public class AdminUserInfoBeautyController extends ABaseController {


    private final UserInfoBeautyServiceImpl userInfoBeautyService;

    public AdminUserInfoBeautyController(UserInfoBeautyServiceImpl userInfoBeautyService) {
        super();
        this.userInfoBeautyService = userInfoBeautyService;
    }

    @RequestMapping("loadBeautyAccountList")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO loadBeautyAccountList(Integer pageNo, Integer pageSize, String userIdFuzzy, String emailFuzzy) {

        UserInfoBeautyQuery userInfoBeautyQuery = new UserInfoBeautyQuery();
        userInfoBeautyQuery.setUserIdFuzzy(userIdFuzzy);
        userInfoBeautyQuery.setEmailFuzzy(emailFuzzy);
        userInfoBeautyQuery.setOrderBy("id desc");
        userInfoBeautyQuery.setPageNo(pageNo);
        userInfoBeautyQuery.setPageSize(pageSize);

        PaginationResultVO<UserInfoBeauty> listByPage = userInfoBeautyService.findListByPage(userInfoBeautyQuery);

        return getSuccessResponseVO(listByPage);
    }

    @RequestMapping("saveBeautAccount")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO saveBeautAccount(Integer id, @NotEmpty @Email String email, @NotNull String userId, Integer status) {
        this.userInfoBeautyService.saveBeautAccount(id, email, userId, status);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("delBeautAccount")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO delBeautAccount(@NotNull Integer id) {
        this.userInfoBeautyService.delBeautAccount(id);
        return getSuccessResponseVO(null);
    }

}
