package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.enums.UserContactStatusEnum;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.UserContact;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.UserContactQuery;
import com.easychat.entity.vo.GroupInfoVo;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.GroupInfoService;
import com.easychat.service.impl.UserContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupInfoController extends ABaseController {


    @Resource
    private GroupInfoService groupInfoService;
    @Autowired
    private UserContactServiceImpl userContactService;

    /**
     * 创建群组
     *
     * @param request     request请求
     * @param groupId     群组ID
     * @param groupName   群组名
     * @param groupNotice 群公告
     * @param joinType    加入类型
     * @param avatarFile  群头像文件
     * @param avatarCover 群头像封面
     * @return ResponseVo
     */
    @RequestMapping("saveGroup")
    @GlobalInterceptor
    public ResponseVO saveGroup(HttpServletRequest request, String groupId,
                                @NotEmpty String groupName,
                                String groupNotice,
                                @NotNull Integer joinType,
                                MultipartFile avatarFile,
                                MultipartFile avatarCover) throws IOException {

        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setGroupOwnerId(tokenUserInfo.getUserId());
        groupInfo.setGroupName(groupName);
        groupInfo.setGroupNotice(groupNotice);
        groupInfo.setJoinType(joinType);

        this.groupInfoService.saveGroup(groupInfo, avatarFile, avatarCover);

        return getSuccessResponseVO(null);

    }

    /**
     * 获取群组列表
     *
     * @param request
     * @return
     */
    @RequestMapping("loadMyGroup")
    @GlobalInterceptor
    public ResponseVO loadMyGroup(HttpServletRequest request) {

        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
        groupInfoQuery.setGroupOwnerId(tokenUserInfo.getUserId());

        List<GroupInfo> listByParam = this.groupInfoService.findListByParam(groupInfoQuery);

        return getSuccessResponseVO(listByParam);
    }


    /**
     * 获取群组详情
     *
     * @param request ThreadLocal
     * @param groupId 群组ID
     * @return
     */
    @RequestMapping("getGroupInfo")
    @GlobalInterceptor
    public ResponseVO getGroupInfo(HttpServletRequest request, @NotEmpty String groupId) {

        GroupInfo groupinfo = this.groupInfoService.getGroupDetailCommon(request, groupId);

        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(groupinfo.getGroupId());
        Integer countByParam = this.userContactService.findCountByParam(userContactQuery);
        groupinfo.setMemberCount(countByParam);

        return getSuccessResponseVO(groupinfo);
    }

    /**
     * @param request
     * @param groupId
     * @return
     */
    @RequestMapping("getGroupInfo4Chat")
    @GlobalInterceptor
    public ResponseVO getGroupInfo4Chat(HttpServletRequest request, @NotEmpty String groupId) {

        GroupInfo groupInfo = this.groupInfoService.getGroupDetailCommon(request, groupId);

        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(groupInfo.getGroupId());
        userContactQuery.setQueryUserInfo(true);
        userContactQuery.setOrderBy("create_time asc");
        userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());

        List<UserContact> userContactList = this.userContactService.findListByParam(userContactQuery);

        GroupInfoVo groupInfoVo = new GroupInfoVo();

        groupInfoVo.setGroupInfo(groupInfo);
        groupInfoVo.setUserContactList(userContactList);

        return getSuccessResponseVO(groupInfoVo);
    }

    @RequestMapping("leaveGroup")
    public ResponseVO leaveGroup(HttpServletRequest request, @NotEmpty String groupId) {
        return getSuccessResponseVO(null);
    }


}
