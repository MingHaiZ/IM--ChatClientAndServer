package com.easychat.service.impl;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.easychat.entity.dto.MessageSendDto;
import com.easychat.entity.dto.SysSettingDto;
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
import com.easychat.service.UserContactApplyService;
import com.easychat.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;


/**
 * 业务接口实现
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private MessageHandler messageHandler;
    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;
    @Resource
    private ChannelContextUtils channelContextUtils;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserContactApply> findListByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserContactApply> list = this.findListByParam(param);
        PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserContactApply bean) {
        return this.userContactApplyMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserContactApply bean, UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.deleteByParam(param);
    }

    /**
     * 根据ApplyId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 根据ApplyId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
        return this.userContactApplyMapper.updateByApplyId(bean, applyId);
    }

    /**
     * 根据ApplyId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.deleteByApplyId(applyId);
    }

    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(String applyUserId, String receivceUserId, String contactId) {
        return this.userContactApplyMapper.selectByApplyUserIdAndReceivceUserIdAndContactId(applyUserId, receivceUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(UserContactApply bean, String applyUserId, String receivceUserId, String contactId) {
        return this.userContactApplyMapper.updateByApplyUserIdAndReceivceUserIdAndContactId(bean, applyUserId, receivceUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(String applyUserId, String receivceUserId, String contactId) {
        return this.userContactApplyMapper.deleteByApplyUserIdAndReceivceUserIdAndContactId(applyUserId, receivceUserId, contactId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealWithApply(String userId, Integer applyId, Integer status) {
        UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
        if (Objects.isNull(statusEnum) || statusEnum.getStatus().equals(UserContactApplyStatusEnum.INIT.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserContactApply userContactApply = this.userContactApplyMapper.selectByApplyId(applyId);
        if (Objects.isNull(applyId) || Objects.isNull(userContactApply)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserContactApplyQuery userContactApplyQuery = new UserContactApplyQuery();
        userContactApplyQuery.setApplyId(applyId);
        userContactApplyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        userContactApply.setStatus(statusEnum.getStatus());
        Integer i = this.userContactApplyMapper.updateByParam(userContactApply, userContactApplyQuery);
        if (i == 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (statusEnum.equals(UserContactApplyStatusEnum.PASS)) {
//            TODO 添加联系人
            this.addContact(userContactApply.getApplyUserId(), userContactApply.getReceivceUserId(), userContactApply.getContactId(), userContactApply.getContactType(), userContactApply.getApplyInfo());
            return;
        }
        if (UserContactApplyStatusEnum.BLACKLIST.equals(statusEnum)) {
            Date date = new Date();
            UserContact userContact = new UserContact();
            userContact.setUserId(userContactApply.getApplyUserId());
            userContact.setContactId(userId);
            userContact.setContactType(userContactApply.getContactType());
            userContact.setCreateTime(date);
            userContact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
            userContact.setLastUpdateTime(date);
            this.userContactMapper.insertOrUpdate(userContact);
        }
    }

    @Override
    public void addContact(String applyUserId, String receivceUserId, String contactId, Integer contactType, String applyInfo) {
//        判断是否是群聊,如果是则判断群聊人数是否已经达到上线
        if (UserContactTypeEnum.GROUP.getType().equals(contactType)) {
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setContactId(contactId);
            Integer groupMemberCount = this.userContactMapper.selectCount(userContactQuery);
            if (groupMemberCount >= sysSetting.getMaxGroupMemberCount()) {
                throw new BusinessException("群人数已满");
            }

        }
        Date date = new Date();
        List<UserContact> contacts = new ArrayList<>();
        UserContact userContact = new UserContact();
        userContact.setUserId(applyUserId);
        userContact.setContactId(contactId);
        userContact.setContactType(contactType);
        userContact.setCreateTime(date);
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContact.setLastUpdateTime(date);
        contacts.add(userContact);

//        如果是好友申请,那么接收人也添加申请人为好友,如果是群组的话接收人不用添加申请人为好友
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            UserContact beContact = new UserContact();

            beContact.setUserId(receivceUserId);
            beContact.setContactId(applyUserId);
            beContact.setContactType(contactType);
            beContact.setCreateTime(date);
            beContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            beContact.setLastUpdateTime(date);

            contacts.add(beContact);

        }

        this.userContactMapper.insertOrUpdateBatch(contacts);
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            redisComponent.addUserContact(receivceUserId, applyUserId);
        }
        redisComponent.addUserContact(applyUserId, receivceUserId);

        String sessionId = null;
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            sessionId = StringTools.encodeMd5(StringTools.getChatSessionId4User(new String[]{applyUserId, receivceUserId}));
        } else {
            sessionId = StringTools.getchatSessionId4Group(contactId);
        }

        List<ChatSessionUser> chatSessionUserList = new ArrayList<>();
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
//            创建会话
            ChatSession chatSession = new ChatSession();
            chatSession.setSessionId(sessionId);
            chatSession.setLastReceiveTime(date.getTime());
            chatSession.setLastMessage(applyInfo);
            chatSessionMapper.insertOrUpdate(chatSession);

            ChatSessionUser applySessionUser = new ChatSessionUser();
            applySessionUser.setUserId(applyUserId);
            applySessionUser.setContactId(receivceUserId);
            applySessionUser.setSessionId(sessionId);
            UserInfo receiveUserInfo = userInfoMapper.selectByUserId(receivceUserId);
            applySessionUser.setContactName(receiveUserInfo.getNickName());

            chatSessionUserList.add(applySessionUser);

            ChatSessionUser receiveSessionUser = new ChatSessionUser();
            receiveSessionUser.setUserId(receivceUserId);
            receiveSessionUser.setContactId(applyUserId);
            receiveSessionUser.setSessionId(sessionId);
            UserInfo applyUserInfo = userInfoMapper.selectByUserId(applyUserId);
            receiveSessionUser.setContactName(applyUserInfo.getNickName());

            chatSessionUserList.add(receiveSessionUser);

            this.chatSessionUserMapper.insertBatch(chatSessionUserList);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSessionId(sessionId);
            chatMessage.setMessageType(MessageTypeEnum.ADD_FRIEND.getType());
            chatMessage.setMessageContent(applyInfo);
            chatMessage.setSendUserId(applyUserId);
            chatMessage.setSendUserNickName(applyUserInfo.getNickName());
            chatMessage.setSendTime(date.getTime());
            chatMessage.setContactId(contactId);
            chatMessage.setContactType(UserContactTypeEnum.USER.getType());

            chatMessageMapper.insert(chatMessage);

            MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);
//            发送给接收好友申请的人
            messageHandler.sendMessage(messageSendDto);
//            还要发送给申请人,给申请人发送人就是接收人,联系人就是申请人
            messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEND_SELF.getType());
            messageSendDto.setContactId(applyUserId);
            messageSendDto.setExtendData(receiveUserInfo);

            messageHandler.sendMessage(messageSendDto);
        } else {
//            加入群组
            ChatSessionUser chatSessionUser = new ChatSessionUser();
            chatSessionUser.setUserId(applyUserId);
            chatSessionUser.setContactId(contactId);
            GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
            chatSessionUser.setSessionId(sessionId);
            chatSessionUser.setContactName(groupInfo.getGroupName());
            this.chatSessionUserMapper.insert(chatSessionUser);


            UserInfo userInfo = this.userInfoMapper.selectByUserId(applyUserId);
            String initMessage = String.format(MessageTypeEnum.ADD_GROUP.getInitMessage(), userInfo.getNickName());

//            增加session信息
            ChatSession chatSession = new ChatSession();
            chatSession.setSessionId(sessionId);
            chatSession.setLastMessage(initMessage);
            chatSession.setLastReceiveTime(date.getTime());

            chatSessionMapper.insertOrUpdate(chatSession);

//            增加聊天消息
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSessionId(sessionId);
            chatMessage.setMessageType(MessageTypeEnum.ADD_GROUP.getType());
            chatMessage.setMessageContent(initMessage);
            chatMessage.setSendTime(date.getTime());
            chatMessage.setContactType(UserContactTypeEnum.GROUP.getType());
            chatMessage.setContactId(contactId);
            chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
            this.chatMessageMapper.insert(chatMessage);

//            将群组添加到联系人
            redisComponent.addUserContact(applyUserId, contactId);
//            将联系人通道添加到群组通道
            channelContextUtils.addUser2Group(applyUserId, groupInfo.getGroupId());

//            发送群消息
            MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);
            messageSendDto.setContactId(contactId);

            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setContactId(contactId);
            userContactQuery.setContactType(UserContactTypeEnum.GROUP.getType());
            userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());

            messageSendDto.setMemberCount(userContactMapper.selectCount(userContactQuery));
            messageSendDto.setContactName(groupInfo.getGroupName());

            messageHandler.sendMessage(messageSendDto);


        }


    }
}