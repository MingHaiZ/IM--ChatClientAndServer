package com.easychat.controller;

import com.easychat.entity.constants.Constants;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVo;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisComponent;
import com.easychat.redis.RedisUtils;
import com.easychat.service.UserInfoService;
import com.wf.captcha.ArithmeticCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("accountController")
@RequestMapping("/account")
@Validated
public class AccountController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserInfoService userInfoService;
    @Autowired
    private RedisComponent redisComponent;

    @RequestMapping("/checkCode")
    public ResponseVO checkCode(Principal principal) {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);

        String code = captcha.text();
        String checkCodeKey = UUID.randomUUID().toString();

        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey, code, Constants.REDIS_TIME_1MIN);

        String checkCodeBase64 = captcha.toBase64();

        Map<String, String> resultVo = new HashMap<>();

        resultVo.put("checkCode", checkCodeBase64);
        resultVo.put("checkCodeKey", checkCodeKey);
        return getSuccessResponseVO(resultVo);
    }

    @RequestMapping("/register")
    public ResponseVO register(@NotEmpty String checkCodeKey,
                               @NotEmpty @Email String email,
                               @NotEmpty String password,
                               @NotEmpty String nickName,
                               @NotEmpty String checkCode) {


//        这里一开始疑问为什么要加这个try,仔细一项当程序运行到这里如果判断不正确则抛出异常,如果不加try的话程序就到抛出异常终止
//        从而不会再运行到后面的redisutils的delete了
        try {
            if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
                throw new BusinessException("图形验证码不正确");
            }
            userInfoService.register(email, password, nickName);
            return getSuccessResponseVO(null);
        } finally {
            redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
        }

    }

    @RequestMapping("/login")
    public ResponseVO login(@NotEmpty String checkCodeKey,
                            @NotEmpty @Email String email,
                            @NotEmpty String password,
                            @NotEmpty String checkCode) {

        try {
            if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
                throw new BusinessException("图形验证码不正确");
            }

            UserInfoVo login = userInfoService.login(email, password);

            return getSuccessResponseVO(login);
        } finally {
            redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
        }

    }

    @RequestMapping("/getSysSetting")
    public ResponseVO getSysSetting() {
        return getSuccessResponseVO(redisComponent.getSysSetting());
    }

}
