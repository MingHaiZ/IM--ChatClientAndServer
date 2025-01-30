package com.easychat.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.easychat.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.entity.enums.*;
import com.easychat.entity.po.UserInfoBeauty;
import com.easychat.entity.vo.UserInfoVo;
import com.easychat.exception.BusinessException;
import com.easychat.mappers.UserInfoBeautyMapper;
import com.easychat.redis.RedisComponent;
import com.easychat.utils.CopyTools;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easychat.entity.query.UserInfoQuery;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.query.SimplePage;
import com.easychat.mappers.UserInfoMapper;
import com.easychat.service.UserInfoService;
import com.easychat.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户信息表 业务接口实现
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    private UserInfoBeautyMapper<UserInfoBeauty, UserInfoBeauty> userInfoBeautyMapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisComponent redisComponent;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserInfo> findListByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(param);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserInfo bean, UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.deleteByParam(param);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据Email获取对象
     */
    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email修改
     */
    @Override
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    @Override
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    /**
     * 注册接口实现
     *
     * @param email    邮箱
     * @param password 密码
     * @param nickName 昵称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String email, String password, String nickName) {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (Objects.nonNull(userInfo)) {
            throw new BusinessException("邮箱已存在");
        }
        String userId;
        UserInfoBeauty userInfoBeauty = userInfoBeautyMapper.selectByEmail(email);
        Boolean useBeautyAccount = Objects.nonNull(userInfoBeauty) && BeautyAccountStatusEnum.NO_USE.getStatus().equals(userInfoBeauty.getStatus());
        if (useBeautyAccount) {
            userId = UserContactTypeEnum.USER.getPrefix() + userInfoBeauty.getUserId();

        } else {
            userId = StringTools.getUserId();
        }
        Date curDate = new Date();

        userInfo = new UserInfo();

        userInfo.setUserId(userId);
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);
        userInfo.setPassword(StringTools.encodeMd5(password));
        userInfo.setCreateTime(curDate);
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setLastOffTime(curDate.getTime());
        userInfo.setJoinType(JoinTypeEnum.JOIN.getType());
        this.userInfoMapper.insert(userInfo);
        if (useBeautyAccount) {
            UserInfoBeauty updateBeauty = new UserInfoBeauty();
            updateBeauty.setStatus(BeautyAccountStatusEnum.USED.getStatus());
            this.userInfoBeautyMapper.updateById(updateBeauty, updateBeauty.getId());
        }
//        TODO 创建机器人好友


    }

    @Override
    public UserInfoVo login(String email, String password) {
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        if (Objects.isNull(userInfo) || !(password.equals(userInfo.getPassword()))) {
            throw new BusinessException(ResponseCodeEnum.CODE_602);
        }
        if (userInfo.getStatus().equals(UserStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_603);
        }
//        TODO 查询我的群组
//        TODO 查询我的联系人

        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto(userInfo);

        Long userHeartBeat = redisComponent.getUserHeartBeat(userInfo.getUserId());
        if (Objects.nonNull(userHeartBeat)) {
            throw new BusinessException(ResponseCodeEnum.CODE_604);
        }
        String token = StringTools.encodeMd5(tokenUserInfoDto.getUserId() + StringTools.getRandomString(Constants.LENGTH_20));
        tokenUserInfoDto.setToken(token);

        redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
        UserInfoVo userInfoVo = CopyTools.copy(userInfo, UserInfoVo.class);
        userInfoVo.setToken(tokenUserInfoDto.getToken());
        userInfoVo.setAdmin(tokenUserInfoDto.getAdmin());

        return userInfoVo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfo userInfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException {
        if (avatarFile != null) {
            String baseFolder = appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFolder + Constants.FILE_FOLDER_AVATAR_NAME);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePath = targetFileFolder.getPath() + "/" + userInfo.getUserId() + Constants.IMAGE_SUFFIX;
            avatarFile.transferTo(new File(filePath));
            avatarCover.transferTo(new File(filePath + Constants.COVER_IMAGE_SUFFIX));
        }
        UserInfo dbUserInfo = this.userInfoMapper.selectByUserId(userInfo.getUserId());

        this.userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());

        String contactName = null;
        if (!dbUserInfo.getNickName().equals(userInfo.getNickName())) {
            contactName = userInfo.getNickName();
        }

//        TODO 更新会话信息中的昵称信息


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String userId, String password) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (userInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String pwdAfterEncode = StringTools.encodeMd5(password);
        userInfo = new UserInfo();
        userInfo.setPassword(pwdAfterEncode);
        this.userInfoMapper.updateByUserId(userInfo, userId);

//        TODO 强制退出,重新登录
    }

    @Override
    public void forceOffLine(String userId) {
//        TODO 强制下线
    }

    private TokenUserInfoDto getTokenUserInfoDto(UserInfo userInfo) {
        TokenUserInfoDto tokenUserInfoDto = new TokenUserInfoDto();
        tokenUserInfoDto.setUserId(userInfo.getUserId());
        tokenUserInfoDto.setNickName(userInfo.getNickName());
        tokenUserInfoDto.setAdmin(!StringTools.isEmpty(appConfig.getAdminEmails()) && ArrayUtils.contains(appConfig.getAdminEmails().split(","), userInfo.getEmail()));
        return tokenUserInfoDto;
    }


}