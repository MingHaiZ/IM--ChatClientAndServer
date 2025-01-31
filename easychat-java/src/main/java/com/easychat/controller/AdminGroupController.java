package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import com.easychat.service.impl.GroupInfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/admin")
public class AdminGroupController extends ABaseController {


    private final GroupInfoServiceImpl groupInfoService;

    public AdminGroupController(GroupInfoServiceImpl groupInfoService) {
        super();
        this.groupInfoService = groupInfoService;
    }

    @RequestMapping("loadGroup")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO loadGroup(Integer pageNo, Integer pageSize, String groupId, String groupNameFuzzy, String groupOwnerId) {

        GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
        groupInfoQuery.setPageNo(pageNo);
        groupInfoQuery.setPageSize(pageSize);
        groupInfoQuery.setGroupId(groupId);
        groupInfoQuery.setGroupNameFuzzy(groupNameFuzzy);
        groupInfoQuery.setGroupOwnerId(groupOwnerId);
        groupInfoQuery.setQueryAdminGroupInfo(true);

        PaginationResultVO<GroupInfo> listByPage = this.groupInfoService.findListByPage(groupInfoQuery);


        return getSuccessResponseVO(listByPage);

    }

    @RequestMapping("dissolutionGroup")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO dissolutionGroup(@NotEmpty String groupId) {

        GroupInfo groupInfoByGroupId = this.groupInfoService.getGroupInfoByGroupId(groupId);
        if (groupInfoByGroupId == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }


        this.groupInfoService.dissolutionGroup(groupInfoByGroupId.getGroupOwnerId(), groupId);
        return getSuccessResponseVO(null);
    }
}
