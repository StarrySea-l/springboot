package com.springboot.dome.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dome.base.ResultBean;
import com.springboot.dome.entity.Users;
import com.springboot.dome.imagecode.ImageCodeValidator;

import com.springboot.dome.service.IUsersService;
import com.springboot.dome.util.ContextHelper;
import com.springboot.dome.util.JwtKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/16 14:18
 * @description: 登陆
 */
@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Object lock = new Object();

    @Autowired
    IUsersService iUsersService;

    @RequestMapping("/login")
    public ResultBean login(String userName, String passWord, String imageCode, HttpSession httpSession, HttpServletRequest request) {
        logger.info("login userName:{},imageCode:{}", userName, imageCode);
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(passWord)) {
            return ResultBean.fail("登录失败，用户名或密码不能为空");
        }

        /*if (StrUtil.isBlank(passWord)) {
            return ResultBean.fail("登录失败，密码不能为空");
        }*/
        if ("1".equals(imageCode)) {
            if (StrUtil.isBlank(imageCode)) {
                return ResultBean.fail("图形验证码不能为空");
            }
        }
        JSONObject json = new JSONObject();
        try {
            ImageCodeValidator imageCodeValidator = new ImageCodeValidator();
            if (!imageCode.equals("1")) {
                boolean verifySession = imageCodeValidator.verifySession(request.getSession(), imageCode);
                if (!verifySession) {
                    return ResultBean.fail("验证码错误");
                }
            }
            Users user = iUsersService.findUserByUserName(userName);
            if (null == user) {
                return ResultBean.fail("账号或密码错误");
            }
            /*String generatePassword = PassWordUtil.generatePassword(userName, passWord);
            if (!StrUtil.equals(generatePassword, user.getPassword())) {
                return ResultBean.fail("账号或密码错误");
            }*/
            String toKen = JwtKit.createJwt(userName, 60 * 60 * 3);
            ContextHelper.setSessionAttr("token", toKen);
            json.put("token", toKen);
            json.put("userName", userName);
            System.out.println(json.toJSONString());
            return ResultBean.success(json);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录异常，用户名:" + userName, e);
            return ResultBean.error("登录发生异常");
        }
    }
}
