package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * app发布 数据库操作接口
 */
public interface AppUpdateMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据Id更新
	 */
	 Integer updateById(@Param("bean") T t,@Param("id") Integer id);


	/**
	 * 根据Id删除
	 */
	 Integer deleteById(@Param("id") Integer id);


	/**
	 * 根据Id获取对象
	 */
	 T selectById(@Param("id") Integer id);


    T selectLatestUpdate(@Param("appVersion") String appVersion, @Param("uid") String uid);
}
