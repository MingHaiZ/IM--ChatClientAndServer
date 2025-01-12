package com.easychat.service;

import java.util.List;

import com.easychat.entity.query.UserInfoBeauty;
import com.easychat.entity.vo.PaginationResultVO;


/**
 * 用户靓号表 业务接口
 */
public interface UserInfoBeautyService {

	/**
	 * 根据条件查询列表
	 */
	List<com.easychat.entity.po.UserInfoBeauty> findListByParam(UserInfoBeauty param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(UserInfoBeauty param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<com.easychat.entity.po.UserInfoBeauty> findListByPage(UserInfoBeauty param);

	/**
	 * 新增
	 */
	Integer add(com.easychat.entity.po.UserInfoBeauty bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<com.easychat.entity.po.UserInfoBeauty> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<com.easychat.entity.po.UserInfoBeauty> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(com.easychat.entity.po.UserInfoBeauty bean, UserInfoBeauty param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(UserInfoBeauty param);

	/**
	 * 根据Id查询对象
	 */
	com.easychat.entity.po.UserInfoBeauty getUserInfoBeautyById(Integer id);


	/**
	 * 根据Id修改
	 */
	Integer updateUserInfoBeautyById(com.easychat.entity.po.UserInfoBeauty bean, Integer id);


	/**
	 * 根据Id删除
	 */
	Integer deleteUserInfoBeautyById(Integer id);


	/**
	 * 根据Email查询对象
	 */
	com.easychat.entity.po.UserInfoBeauty getUserInfoBeautyByEmail(String email);


	/**
	 * 根据Email修改
	 */
	Integer updateUserInfoBeautyByEmail(com.easychat.entity.po.UserInfoBeauty bean, String email);


	/**
	 * 根据Email删除
	 */
	Integer deleteUserInfoBeautyByEmail(String email);


	/**
	 * 根据UserId查询对象
	 */
	com.easychat.entity.po.UserInfoBeauty getUserInfoBeautyByUserId(String userId);


	/**
	 * 根据UserId修改
	 */
	Integer updateUserInfoBeautyByUserId(com.easychat.entity.po.UserInfoBeauty bean, String userId);


	/**
	 * 根据UserId删除
	 */
	Integer deleteUserInfoBeautyByUserId(String userId);

}