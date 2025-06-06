package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  数据库操作接口
 */
public interface UserContactApplyMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据ApplyId更新
	 */
	 Integer updateByApplyId(@Param("bean") T t,@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyId删除
	 */
	 Integer deleteByApplyId(@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyId获取对象
	 */
	 T selectByApplyId(@Param("applyId") Integer applyId);


	/**
	 * 根据ApplyUserIdAndReceivceUserIdAndContactId更新
	 */
	 Integer updateByApplyUserIdAndReceivceUserIdAndContactId(@Param("bean") T t,@Param("applyUserId") String applyUserId,@Param("receivceUserId") String receivceUserId,@Param("contactId") String contactId);


	/**
	 * 根据ApplyUserIdAndReceivceUserIdAndContactId删除
	 */
	 Integer deleteByApplyUserIdAndReceivceUserIdAndContactId(@Param("applyUserId") String applyUserId,@Param("receivceUserId") String receivceUserId,@Param("contactId") String contactId);


	/**
	 * 根据ApplyUserIdAndReceivceUserIdAndContactId获取对象
	 */
	 T selectByApplyUserIdAndReceivceUserIdAndContactId(@Param("applyUserId") String applyUserId,@Param("receivceUserId") String receivceUserId,@Param("contactId") String contactId);


}
