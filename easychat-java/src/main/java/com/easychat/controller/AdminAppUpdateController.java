package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.enums.AppUpdateStatusEnum;
import com.easychat.entity.po.AppUpdate;
import com.easychat.entity.query.AppUpdateQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import com.easychat.service.impl.AppUpdateServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminAppUpdateController extends ABaseController {

    @Resource
    private AppUpdateServiceImpl appUpdateService;

    @RequestMapping("loadUpdateList")
    @GlobalInterceptor
    public ResponseVO loadUpdateList(Integer pageNo, Integer pageSize) {
        AppUpdateQuery appUpdateQuery = new AppUpdateQuery();
        appUpdateQuery.setOrderBy("id desc");
        appUpdateQuery.setPageNo(pageNo);
        appUpdateQuery.setPageSize(pageSize);
        PaginationResultVO<AppUpdate> listByPage = this.appUpdateService.findListByPage(appUpdateQuery);
        return getSuccessResponseVO(listByPage);
    }

    @RequestMapping("saveUpdate")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO saveUpdate(Integer id,
                                 @NotEmpty String version,
                                 @NotNull Integer fileType,
                                 MultipartFile file,
                                 @NotEmpty String fileName,
                                 String updateDesc,
                                 String outerLink) throws IOException {
        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setId(id);
        appUpdate.setVersion(version);
        appUpdate.setFileType(fileType);
        appUpdate.setUpdateDesc(updateDesc);
        appUpdate.setOuterLink(outerLink);
        this.appUpdateService.saveUpdate(appUpdate, file, fileName);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("delUpdate")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO delUpdate(@NotNull Integer id) {

        AppUpdate appUpdateById = this.appUpdateService.getAppUpdateById(id);
        if (!appUpdateById.getStatus().equals(AppUpdateStatusEnum.INIT.getStatus())) {
            throw new BusinessException("不能删除已经发布的版本");
        }

        this.appUpdateService.deleteAppUpdateById(id);

        return getSuccessResponseVO(null);
    }

    @RequestMapping("postUpdate")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO postUpdate(@NotNull Integer id, @NotEmpty String version, @NotNull Integer status, String grayscaleUid) {

        this.appUpdateService.postUpdate(id, status, grayscaleUid);

        return getSuccessResponseVO(null);
    }
}
