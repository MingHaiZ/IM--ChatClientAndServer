package com.easychat.service;

import java.util.List;

import com.easychat.entity.query.UserContactApplyQuery;
import com.easychat.entity.po.UserContactApply;
import com.easychat.entity.vo.PaginationResultVO;

import javax.validation.constraints.NotNull;


/**
 * 业务接口
 */
public interface UserContactApplyService {

    /**
     * 根据条件查询列表
     */
    List<UserContactApply> findListByParam(UserContactApplyQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(UserContactApplyQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param);

    /**
     * 新增
     */
    Integer add(UserContactApply bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserContactApply> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<UserContactApply> listBean);

    /**
     * 多条件更新
     */
    Integer updateByParam(UserContactApply bean, UserContactApplyQuery param);

    /**
     * 多条件删除
     */
    Integer deleteByParam(UserContactApplyQuery param);

    /**
     * 根据ApplyId查询对象
     */
    UserContactApply getUserContactApplyByApplyId(Integer applyId);


    /**
     * 根据ApplyId修改
     */
    Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId);


    /**
     * 根据ApplyId删除
     */
    Integer deleteUserContactApplyByApplyId(Integer applyId);


    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId查询对象
     */
    UserContactApply getUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(String applyUserId, String receivceUserId, String contactId);


    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId修改
     */
    Integer updateUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(UserContactApply bean, String applyUserId, String receivceUserId, String contactId);


    /**
     * 根据ApplyUserIdAndReceivceUserIdAndContactId删除
     */
    Integer deleteUserContactApplyByApplyUserIdAndReceivceUserIdAndContactId(String applyUserId, String receivceUserId, String contactId);

    void dealWithApply(String userId, @NotNull Integer applyId, @NotNull Integer status);

    void addContact(String applyUserId, String receivceUserId, String contactId, Integer contactType, String applyInfo);
}