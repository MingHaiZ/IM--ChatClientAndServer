package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.dto.UserContactSearchResultDto;
import com.easychat.entity.enums.*;
import com.easychat.entity.po.UserContact;
import com.easychat.entity.po.UserContactApply;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.query.UserContactApplyQuery;
import com.easychat.entity.query.UserContactQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVo;
import com.easychat.exception.BusinessException;
import com.easychat.service.UserContactApplyService;
import com.easychat.service.UserContactService;
import com.easychat.service.UserInfoService;
import com.easychat.utils.CopyTools;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/contact")
public class UserContactController extends ABaseController {

    @Resource
    private UserContactService userContactService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserContactApplyService userContactApplyService;

    @RequestMapping("search")
    @GlobalInterceptor
    public ResponseVO search(HttpServletRequest request, @NotEmpty String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);

        UserContactSearchResultDto resultDto = userContactService.searchContact(tokenUserInfo.getUserId(), contactId);

        return getSuccessResponseVO(resultDto);
    }

    @RequestMapping("applyAdd")
    @GlobalInterceptor
    public ResponseVO applyAdd(HttpServletRequest request, @NotEmpty String contactId, String applyInfo, @NotEmpty String contactType) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        Integer joinType = this.userContactService.applyAdd(tokenUserInfo, contactId, applyInfo);
        return getSuccessResponseVO(joinType);
    }

    @RequestMapping("loadApply")
    @GlobalInterceptor
    public ResponseVO loadApply(HttpServletRequest request, Integer pageNo) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        UserContactApplyQuery query = new UserContactApplyQuery();
        query.setOrderBy("last_apply_time desc");
        query.setReceivceUserId(tokenUserInfo.getUserId());
        query.setPageNo(pageNo);
        query.setPageSize(PageSize.SIZE15.getSize());
        query.setQueryContactInfo(true);
        PaginationResultVO<UserContactApply> listByPage = userContactApplyService.findListByPage(query);
        listByPage.getList().forEach((item) -> {
            UserContactApplyStatusEnum byStatus = UserContactApplyStatusEnum.getByStatus(item.getStatus());
            if (Objects.isNull(byStatus)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            item.setStatusName(byStatus.getDesc());
        });
        return getSuccessResponseVO(listByPage);
    }

    @RequestMapping("dealWithApply")
    @GlobalInterceptor
    public ResponseVO dealWithApply(HttpServletRequest request, @NotNull Integer applyId, @NotNull Integer status) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);

        this.userContactApplyService.dealWithApply(tokenUserInfo.getUserId(), applyId, status);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("loadContact")
    @GlobalInterceptor
    public ResponseVO loadContact(HttpServletRequest request, @NotEmpty String contactType) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        UserContactTypeEnum byName = UserContactTypeEnum.getByName(contactType);
        if (byName == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setUserId(tokenUserInfo.getUserId());
        userContactQuery.setContactType(byName.getType());
        if (UserContactTypeEnum.USER.equals(byName)) {
            userContactQuery.setQueryContactUserInfo(true);
        } else if (UserContactTypeEnum.GROUP.equals(byName)) {
            userContactQuery.setQueryGroupInfo(true);
            userContactQuery.setExcludeMyGroups(true);
        }
        userContactQuery.setOrderBy("last_update_time desc");
        userContactQuery.setStatusArray(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
                UserContactStatusEnum.BLACKLIST_BE.getStatus()
        });

        List<UserContact> listByParam = this.userContactService.findListByParam(userContactQuery);

        return getSuccessResponseVO(listByParam);
    }


    @RequestMapping("getContactInfo")
    @GlobalInterceptor
    public ResponseVO getContactInfo(HttpServletRequest request, @NotEmpty String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);

        UserInfo userInfo = userInfoService.getUserInfoByUserId(tokenUserInfo.getUserId());
        UserInfoVo userInfoVo = CopyTools.copy(userInfo, UserInfoVo.class);
        userInfoVo.setContactStatus(UserContactStatusEnum.NOT_FRIEND.getStatus());
        UserContact userContactByUserIdAndContactId = userContactService.getUserContactByUserIdAndContactId(tokenUserInfo.getUserId(), contactId);
        if (userContactByUserIdAndContactId != null) {
            userInfoVo.setContactStatus(UserContactStatusEnum.FRIEND.getStatus());
        }

        return getSuccessResponseVO(userInfoVo);
    }


    @RequestMapping("getContactUserInfo")
    @GlobalInterceptor
    public ResponseVO getContactUserInfo(HttpServletRequest request, @NotEmpty String contactId) {
        UserContact userContactByUserIdAndContactId = this.userContactService.getUserContactByUserIdAndContactId(getTokenUserInfo(request).getUserId(), contactId);
        if (userContactByUserIdAndContactId == null || !ArrayUtils.contains(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
                UserContactStatusEnum.BLACKLIST_BE.getStatus()
        }, userContactByUserIdAndContactId.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserInfo userInfoByUserId = userInfoService.getUserInfoByUserId(contactId);
        UserInfoVo copy = CopyTools.copy(userInfoByUserId, UserInfoVo.class);
        return getSuccessResponseVO(copy);
    }

    @RequestMapping("delContact")
    @GlobalInterceptor
    public ResponseVO delContact(HttpServletRequest request, @NotEmpty String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);

        this.userContactService.removeUserContact(tokenUserInfo.getUserId(), contactId, UserContactStatusEnum.DEL);

        return getSuccessResponseVO(null);

    }

}
