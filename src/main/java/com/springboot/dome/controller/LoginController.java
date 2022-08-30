package com.springboot.dome.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dome.base.ResultBean;
import com.springboot.dome.entity.Users;
import com.springboot.dome.imagecode.ImageCodeValidator;

import com.springboot.dome.service.IUsersService;
import com.springboot.dome.util.ContextHelper;
import com.springboot.dome.util.PassWordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.TimeUnit;


/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/16 14:18
 * @description: 登陆
 */
@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //验证码保存在session中的属性key
    public static final String SESSION_CODE_KEY = "IMAGE_VALIDATE_CODE";

    @Autowired
    IUsersService iUsersService;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("/login")
    public ResultBean login(String userName, String passWord, String imageCode) {
        logger.info("login userName:{},imageCode:{}", userName, imageCode);
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(passWord) || StrUtil.isBlank(imageCode)) {
            return ResultBean.fail("登录失败，参数不能为空");
        }
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        try {
            Users user = iUsersService.findUserByUserName(userName);
            if (null == user) {
                return ResultBean.fail("用户名不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据库查询失败，获取用户名失败");
            return ResultBean.error("系统异常，登录失败，请稍后重试");
        }
        Integer psdCount = (Integer) redisTemplate.opsForValue().get(PassWordUtil.KEY_USER_PSD_COUNT + userName);
        if (psdCount == null) {
            psdCount = PassWordUtil.MIN_COUNT;
        } else {
            System.out.println("psd_count : " + psdCount);
            if (psdCount >= PassWordUtil.MAX_COUNT) {
                return ResultBean.fail("密码连续超过5次输入错误，您的账号已经进入锁定状态，请耐心等待30分钟");
            }
        }

        String s = (String) ops.get(SESSION_CODE_KEY);
        JSONObject json = new JSONObject();
        try {
            ImageCodeValidator imageCodeValidator = new ImageCodeValidator();
            if (!imageCode.equals("1")) {
                boolean verifySession = imageCodeValidator.verifySession(imageCode, s);
                if (!verifySession) {
                    return ResultBean.fail("验证码错误");
                }
            }
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken userToken = new UsernamePasswordToken(userName, passWord);
            try {
                currentUser.login(userToken);
                redisTemplate.delete(SESSION_CODE_KEY);
                System.out.println(userName + "密码验证通过 :)");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("登录失败: " + e.getMessage());
                psdCount++;
                ops.set(PassWordUtil.KEY_USER_PSD_COUNT + userName, psdCount,
                        PassWordUtil.USER_LOCKING_TIME, TimeUnit.SECONDS);
                logger.error("用户: " + userName + "已密码输入错误: " + psdCount + "次");
                return ResultBean.error("用户名或密码错误，同一个账号,连续超过5次输入错误，账号会被锁定30分钟," + "您以输入错误" + psdCount + "次");
            }
            PassWordUtil.deletePsdCountIfNecessary(redisTemplate, psdCount, PassWordUtil.KEY_USER_PSD_COUNT + userName);

            String ipAddress = ContextHelper.getIpAddress();
            System.out.println("登录IP:" + ipAddress);

            json.put("token", "LOGIN_OK");
            json.put("userName", userName);
            return ResultBean.success(json);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录异常，用户名:" + userName, e);
            return ResultBean.error("登录发生异常");
        }
    }
}
