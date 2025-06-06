package com.easychat.service;

import java.io.IOException;
import java.util.List;

import com.easychat.entity.query.AppUpdateQuery;
import com.easychat.entity.po.AppUpdate;
import com.easychat.entity.vo.PaginationResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * app发布 业务接口
 */
public interface AppUpdateService {

	/**
	 * 根据条件查询列表
	 */
	List<AppUpdate> findListByParam(AppUpdateQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(AppUpdateQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<AppUpdate> findListByPage(AppUpdateQuery param);

	/**
	 * 新增
	 */
	Integer add(AppUpdate bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<AppUpdate> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<AppUpdate> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(AppUpdate bean,AppUpdateQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(AppUpdateQuery param);

	/**
	 * 根据Id查询对象
	 */
	AppUpdate getAppUpdateById(Integer id);


	/**
	 * 根据Id修改
	 */
	Integer updateAppUpdateById(AppUpdate bean,Integer id);


	/**
	 * 根据Id删除
	 */
	Integer deleteAppUpdateById(Integer id);

    void saveUpdate(AppUpdate appUpdate, MultipartFile file, @NotEmpty String fileName) throws IOException;

    void postUpdate(@NotNull Integer id, @NotNull Integer status, String grayscaleUid);

	AppUpdate getLatestedUpdate(@NotEmpty String appVersion, String uid);
}