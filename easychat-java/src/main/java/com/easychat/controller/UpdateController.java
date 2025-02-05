package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.enums.AppUpdateFileTypeEnum;
import com.easychat.entity.po.AppUpdate;
import com.easychat.entity.vo.AppUpdateVo;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.AppUpdateService;
import com.easychat.utils.CopyTools;
import com.easychat.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.Arrays;

@RestController
@RequestMapping("/update")
public class UpdateController extends ABaseController {

    @Resource
    private AppConfig appConfig;
    @Resource
    private AppUpdateService appUpdateService;

    @RequestMapping("checkVersion")
    @GlobalInterceptor
    public ResponseVO checkVersion(@NotEmpty String appVersion, String token, String uid) {

        if (StringTools.isEmpty(appVersion)) {
            return getSuccessResponseVO(null);
        }

        AppUpdate latestUpdate = this.appUpdateService.getLatestedUpdate(appVersion, uid);
        if (latestUpdate == null) {
            return getSuccessResponseVO(null);
        }
        AppUpdateVo updateVo = CopyTools.copy(latestUpdate, AppUpdateVo.class);
        if (AppUpdateFileTypeEnum.LOCAL.getFileType().equals(latestUpdate.getFileType())) {
            File file = new File(appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.APP_UPDATE_FOLDER + updateVo.getId() + Constants.APP_EXE_SUFFIX);
            updateVo.setSize(file.length());
        } else {
            updateVo.setSize(0L);
        }

        updateVo.setUpdateList(Arrays.asList(latestUpdate.getUpdateDescArray()));
        updateVo.setFileName(Constants.APP_NAME + latestUpdate.getVersion() + Constants.APP_EXE_SUFFIX);
        return getSuccessResponseVO(updateVo);
    }
}
