package com.easychat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.MessageSendDto;
import com.easychat.entity.dto.SysSettingDto;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.enums.*;
import com.easychat.entity.po.ChatSession;
import com.easychat.entity.query.ChatSessionQuery;
import com.easychat.exception.BusinessException;
import com.easychat.mappers.ChatSessionMapper;
import com.easychat.redis.RedisComponent;
import com.easychat.utils.CopyTools;
import com.easychat.utils.DeepSeekApi;
import com.easychat.webSocket.MessageHandler;
import okhttp3.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easychat.entity.query.ChatMessageQuery;
import com.easychat.entity.po.ChatMessage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.query.SimplePage;
import com.easychat.mappers.ChatMessageMapper;
import com.easychat.service.ChatMessageService;
import com.easychat.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;


/**
 * 聊天消息表 业务接口实现
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private DeepSeekApi deepSeekApi;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<ChatMessage> findListByParam(ChatMessageQuery param) {
        return this.chatMessageMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(ChatMessageQuery param) {
        return this.chatMessageMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<ChatMessage> findListByPage(ChatMessageQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ChatMessage> list = this.findListByParam(param);
        PaginationResultVO<ChatMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(ChatMessage bean) {
        return this.chatMessageMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(ChatMessage bean, ChatMessageQuery param) {
        StringTools.checkParam(param);
        return this.chatMessageMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(ChatMessageQuery param) {
        StringTools.checkParam(param);
        return this.chatMessageMapper.deleteByParam(param);
    }

    /**
     * 根据MessageId获取对象
     */
    @Override
    public ChatMessage getChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.selectByMessageId(messageId);
    }

    /**
     * 根据MessageId修改
     */
    @Override
    public Integer updateChatMessageByMessageId(ChatMessage bean, Long messageId) {
        return this.chatMessageMapper.updateByMessageId(bean, messageId);
    }

    /**
     * 根据MessageId删除
     */
    @Override
    public Integer deleteChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.deleteByMessageId(messageId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageSendDto saveMessage(ChatMessage chatMessage, TokenUserInfoDto tokenUserInfoDto) {
//		不是机器人,回复判断好友状态
        if (!Constants.ROBOT_UUID.equals(tokenUserInfoDto.getUserId())) {
            List<String> contactList = redisComponent.getUserContact(tokenUserInfoDto.getUserId());
            if (!contactList.contains(chatMessage.getContactId())) {
                UserContactTypeEnum userContactTypeEnum = UserContactTypeEnum.getByPrefix(chatMessage.getContactId());
                if (UserContactTypeEnum.USER.equals(userContactTypeEnum)) {
                    throw new BusinessException(ResponseCodeEnum.CODE_902);
                } else {
                    throw new BusinessException(ResponseCodeEnum.CODE_903);
                }
            }
        }


        String sessionId = null;
        String sendUserId = tokenUserInfoDto.getUserId();
        chatMessage.setSendUserId(sendUserId);
        String contactId = chatMessage.getContactId();
        Long curTime = System.currentTimeMillis();
        chatMessage.setSendTime(curTime);
        UserContactTypeEnum userContactTypeEnum = UserContactTypeEnum.getByPrefix(contactId);
        if (userContactTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (UserContactTypeEnum.USER.equals(userContactTypeEnum)) {
            sessionId = StringTools.getChatSessionId4User(new String[]{chatMessage.getSendUserId(), chatMessage.getContactId()});
        } else {
            sessionId = StringTools.getchatSessionId4Group(contactId);
        }
        chatMessage.setSessionId(sessionId);
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(chatMessage.getMessageType());
        if (messageTypeEnum == null || !ArrayUtils.contains(new Integer[]{MessageTypeEnum.CHAT.getType(), MessageTypeEnum.MEDIA_CHAT.getType()}, chatMessage.getMessageType())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        Integer status = MessageTypeEnum.MEDIA_CHAT == messageTypeEnum ? MessageStatusEnum.SENDING.getStatus() : MessageStatusEnum.SENDED.getStatus();
        chatMessage.setStatus(status);
//        处理聊天内容防止HTML注入攻击
        String messageContent = StringTools.cleanHtmlTag(chatMessage.getMessageContent());
        chatMessage.setMessageContent(messageContent);

//        更新会话
        ChatSession chatSession = new ChatSession();
        chatSession.setLastReceiveTime(curTime);
        chatSession.setLastMessage(messageContent);
        if (UserContactTypeEnum.GROUP.equals(userContactTypeEnum)) {
            chatSession.setLastMessage(tokenUserInfoDto.getNickName() + ": " + messageContent);
        }

        chatSession.setSessionId(sessionId);
        this.chatSessionMapper.updateBySessionId(chatSession, sessionId);

//        记录消息表
        chatMessage.setSendUserNickName(tokenUserInfoDto.getNickName());
        chatMessage.setContactType(userContactTypeEnum.getType());

        this.chatMessageMapper.insert(chatMessage);

        MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);

        if (Constants.ROBOT_UUID.equals(contactId)) {
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            TokenUserInfoDto robot = new TokenUserInfoDto();
            robot.setUserId(sysSetting.getRobotUid());
            robot.setNickName(sysSetting.getRobotNickName());
            ChatMessage robotChatMessage = new ChatMessage();
            robotChatMessage.setContactId(sendUserId);
//            这里可以对接AI实现AI聊天
            String s = deepSeekApi.useApi(messageContent);
            robotChatMessage.setMessageContent(s);
            robotChatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
            saveMessage(robotChatMessage, robot);
        } else {
            this.messageHandler.sendMessage(messageSendDto);
        }

        return messageSendDto;
    }
}