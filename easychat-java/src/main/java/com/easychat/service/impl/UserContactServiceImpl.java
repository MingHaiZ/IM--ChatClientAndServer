package com.easychat.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.easychat.entity.dto.UserContactSearchResultDto;
import com.easychat.entity.enums.UserContactStatusEnum;
import com.easychat.entity.enums.UserContactTytpeEnum;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.UserInfoQuery;
import com.easychat.mappers.GroupInfoMapper;
import com.easychat.mappers.UserInfoMapper;
import com.easychat.utils.CopyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easychat.entity.enums.PageSize;
import com.easychat.entity.query.UserContactQuery;
import com.easychat.entity.po.UserContact;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.query.SimplePage;
import com.easychat.mappers.UserContactMapper;
import com.easychat.service.UserContactService;
import com.easychat.utils.StringTools;


/**
 * 联系人 业务接口实现
 */
@Service("userContactService")
public class UserContactServiceImpl implements UserContactService {

    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserContact> findListByParam(UserContactQuery param) {
        return this.userContactMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserContactQuery param) {
        return this.userContactMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserContact> findListByPage(UserContactQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserContact> list = this.findListByParam(param);
        PaginationResultVO<UserContact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserContact bean) {
        return this.userContactMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserContact bean, UserContactQuery param) {
        StringTools.checkParam(param);
        return this.userContactMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserContactQuery param) {
        StringTools.checkParam(param);
        return this.userContactMapper.deleteByParam(param);
    }

    /**
     * 根据UserIdAndContactId获取对象
     */
    @Override
    public UserContact getUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
    }

    /**
     * 根据UserIdAndContactId修改
     */
    @Override
    public Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId) {
        return this.userContactMapper.updateByUserIdAndContactId(bean, userId, contactId);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    @Override
    public Integer deleteUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.deleteByUserIdAndContactId(userId, contactId);
    }

    @Override
    public UserContactSearchResultDto searchContact(String userId, String contactId) {
        UserContactTytpeEnum userContactTytpeEnum = UserContactTytpeEnum.getByPrefix(contactId);
        if (Objects.isNull(userContactTytpeEnum)) {
            return null;
        }
        UserContactSearchResultDto resultDto = new UserContactSearchResultDto();
        switch (userContactTytpeEnum) {
            case USER:
                UserInfo userInfo = this.userInfoMapper.selectByUserId(contactId);
                if (Objects.isNull(userInfo)) {
                    return null;
                }
                resultDto = CopyTools.copy(userInfo, UserContactSearchResultDto.class);
                break;
            case GROUP:
                GroupInfo groupInfo = this.groupInfoMapper.selectByGroupId(contactId);
                if (Objects.isNull(groupInfo)) {
                    return null;
                }
                resultDto.setNickName(groupInfo.getGroupName());
                break;
        }
        resultDto.setContactType(userContactTytpeEnum.toString());
        resultDto.setContactId(contactId);
        if (userId.equals(contactId)) {
            resultDto.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            return resultDto;
        }
//        查询是否为好友
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(userId, contactId);
        resultDto.setStatus(userContact == null ? null : userContact.getStatus());

        return resultDto;
    }
}