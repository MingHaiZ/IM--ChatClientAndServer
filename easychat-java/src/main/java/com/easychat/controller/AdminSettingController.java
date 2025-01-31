package com.easychat.controller;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.dto.SysSettingDto;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.redis.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class AdminSettingController extends ABaseController {

    @Resource
    private AppConfig appConfig;
    @Autowired
    private RedisComponent redisComponent;

    @RequestMapping("getSysSetting")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO getSysSetting() {
        return getSuccessResponseVO(redisComponent.getSysSetting());
    }

    @RequestMapping("saveSysSetting")
    @GlobalInterceptor
    public ResponseVO saveSysSetting(SysSettingDto sysSettingDto,
                                     MultipartFile robotFile,
                                     MultipartFile robotCover) throws IOException {

        if (robotFile != null && robotCover != null) {
            String baseFolder = appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFile = new File(baseFolder + Constants.FILE_FOLDER_AVATAR_NAME);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String filePath = targetFile.getPath() + "/" + Constants.ROBOT_UUID + Constants.IMAGE_SUFFIX;
            robotFile.transferTo(new File(filePath));
            robotCover.transferTo(new File(filePath + Constants.COVER_IMAGE_SUFFIX));
        }
        redisComponent.setSysSetting(sysSettingDto);
        return getSuccessResponseVO(null);
    }
}
