package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.GroupInfoService;
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

    @RequestMapping("loadMyGroup")
    public ResponseVO loadMyGroup(HttpServletRequest request) {

        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
        groupInfoQuery.setGroupOwnerId(tokenUserInfo.getUserId());

        List<GroupInfo> listByParam = this.groupInfoService.findListByParam(groupInfoQuery);

        return getSuccessResponseVO(listByParam);
    }

}
