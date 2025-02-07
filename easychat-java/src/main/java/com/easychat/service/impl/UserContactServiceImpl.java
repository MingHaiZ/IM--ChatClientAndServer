package com.easychat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.MessageSendDto;
import com.easychat.entity.dto.SysSettingDto;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.dto.UserContactSearchResultDto;
import com.easychat.entity.enums.*;
import com.easychat.entity.po.*;
import com.easychat.entity.query.*;
import com.easychat.exception.BusinessException;
import com.easychat.mappers.*;
import com.easychat.redis.RedisComponent;
import com.easychat.utils.CopyTools;
import com.easychat.webSocket.ChannelContextUtils;
import com.easychat.webSocket.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.UserContactService;
import com.easychat.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;


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
    @Autowired
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Autowired
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Autowired
    private ChannelContextUtils channelContextUtils;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private UserContactApplyServiceImpl userContactApplyService;

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
        UserContactTypeEnum userContactTypeEnum = UserContactTypeEnum.getByPrefix(contactId);
        if (Objects.isNull(userContactTypeEnum)) {
            return null;
        }
        UserContactSearchResultDto resultDto = new UserContactSearchResultDto();
        switch (userContactTypeEnum) {
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
        resultDto.setContactType(userContactTypeEnum.toString());
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applyAdd(TokenUserInfoDto tokenUserInfo, String contactId, String applyInfo) {
        UserContactTypeEnum userContactTypeEnum = UserContactTypeEnum.getByPrefix(contactId);
        if (Objects.isNull(userContactTypeEnum)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
//        申请人
        String applyUserId = tokenUserInfo.getUserId();

//        默认申请信息
        applyInfo = StringTools.isEmpty(applyInfo) ? String.format(Constants.APPLY_INFO_TEMPLATE, tokenUserInfo.getNickName()) : applyInfo;

        Long currentTime = System.currentTimeMillis();
        Integer joinType = null;
//        默认为用户,判断是否为群组,是群组则为群主ID
        String reciveUserId = contactId;

//        查询对方是否已经是好友,如果已经拉黑则无法添加
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(tokenUserInfo.getUserId(), reciveUserId);
        if (Objects.nonNull(userContact) && userContact.getContactType().equals(UserContactStatusEnum.BLACKLIST_BE.getStatus())) {
            throw new BusinessException("对方已将你拉黑");
        }

//        判断ID是否为群组以及群组状态
        if (UserContactTypeEnum.GROUP.equals(userContactTypeEnum)) {
            GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
            if (Objects.isNull(groupInfo) || groupInfo.getStatus().equals(GroupStatusEnum.DISSOLUTION.getStatus())) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            reciveUserId = groupInfo.getGroupOwnerId();
            joinType = groupInfo.getJoinType();
        } else {
            UserInfo userInfo = userInfoMapper.selectByUserId(contactId);
            if (Objects.isNull(userInfo)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            joinType = userInfo.getJoinType();
        }

//        直接加入不用申请进入
        if (JoinTypeEnum.JOIN.getType().equals(joinType)) {
//            TODO 添加联系人
            userContactApplyService.addContact(tokenUserInfo.getUserId(), reciveUserId, contactId, userContactTypeEnum.getType(), applyInfo);
            return joinType;
        }

        UserContactApply userContactApply = userContactApplyMapper.selectByApplyUserIdAndReceivceUserIdAndContactId(applyUserId, reciveUserId, contactId);
        if (Objects.isNull(userContactApply)) {
            userContactApply = new UserContactApply();
            userContactApply.setApplyUserId(applyUserId);
            userContactApply.setContactType(userContactTypeEnum.getType());
            userContactApply.setReceivceUserId(reciveUserId);
            userContactApply.setLastApplyTime(currentTime);
            userContactApply.setContactId(contactId);
            userContactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
            userContactApply.setApplyInfo(applyInfo);
            this.userContactApplyMapper.insert(userContactApply);
        } else {
//            更新状态
            UserContactApply contactApply = new UserContactApply();
            contactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
            contactApply.setLastApplyTime(currentTime);
            contactApply.setApplyInfo(applyInfo);
            this.userContactApplyMapper.updateByApplyId(contactApply, userContactApply.getApplyId());

        }

        if (Objects.isNull(userContactApply) || !UserContactApplyStatusEnum.BLACKLIST.getStatus().equals(userContactApply.getStatus())) {
            MessageSendDto messageSendDto = new MessageSendDto();
            messageSendDto.setMessageType(MessageTypeEnum.CONTACT_APPLY.getType());
            messageSendDto.setMessageContent(applyInfo);
            messageSendDto.setContactId(reciveUserId);
            messageHandler.sendMessage(messageSendDto);
        }

        return joinType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserContact(String userId, String contactId, UserContactStatusEnum userContactStatusEnum) {
        List<UserContact> userContactList = new ArrayList<>();
//        自己视角移除好友
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(userId, contactId);
        if (userContact == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        userContact.setStatus(userContactStatusEnum.getStatus());
        userContactList.add(userContact);

//        被删除的好友视角
        UserContact friendUserContact = userContactMapper.selectByUserIdAndContactId(contactId, userId);
        if (friendUserContact == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (UserContactStatusEnum.DEL == userContactStatusEnum) {
            friendUserContact.setStatus(UserContactStatusEnum.DEL_BE.getStatus());
            userContactList.add(friendUserContact);
        } else if (UserContactStatusEnum.BLACKLIST == userContactStatusEnum) {
            friendUserContact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
            userContactList.add(friendUserContact);
        }

        this.userContactMapper.insertOrUpdateBatch(userContactList);
//        TODO 从我的列表缓存中删除好友
//        TODO 从好友列表缓存中删除我

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContact4Robot(String userId) {
        Date currentDate = new Date();
        SysSettingDto sysSetting = redisComponent.getSysSetting();
        String robotUid = sysSetting.getRobotUid();
        String robotNickName = sysSetting.getRobotNickName();
        String robotWelcome = sysSetting.getRobotWelcome();
        robotWelcome = StringTools.cleanHtmlTag(robotWelcome);
//        增加机器人为好友
        UserContact userContact = new UserContact();
        userContact.setUserId(userId);
        userContact.setContactId(robotUid);
        userContact.setCreateTime(currentDate);
        userContact.setLastUpdateTime(currentDate);
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContact.setContactType(UserContactTypeEnum.USER.getType());
        userContactMapper.insert(userContact);
//        增加会话信息
        String sessionId = StringTools.getChatSessionId4User(new String[]{userId, robotUid});
        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(robotWelcome);
        chatSession.setSessionId(sessionId);
        chatSession.setLastReceiveTime(currentDate.getTime());

        this.chatSessionMapper.insert(chatSession);

//        增加会话人信息
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        chatSessionUser.setUserId(userId);
        chatSessionUser.setContactId(robotUid);
        chatSessionUser.setContactName(robotNickName);
        chatSessionUser.setSessionId(sessionId);
        this.chatSessionUserMapper.insert(chatSessionUser);
//        增加聊天消息
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
        chatMessage.setMessageContent(robotWelcome);
        chatMessage.setSendUserId(robotUid);
        chatMessage.setSendUserNickName(robotNickName);
        chatMessage.setSendTime(currentDate.getTime());
        chatMessage.setContactId(userId);
        chatMessage.setContactType(UserContactTypeEnum.USER.getType());
        chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
        this.chatMessageMapper.insert(chatMessage);


    }
}