package com.springboot.dome.interceptor;

import com.alibaba.fastjson.JSON;
import com.springboot.dome.base.ResultBean;
import com.springboot.dome.entity.Users;
import com.springboot.dome.imagecode.ImageCodeValidator;
import com.springboot.dome.service.IUsersService;
import com.springboot.dome.util.ContextHelper;
import com.springboot.dome.util.JwtKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String codeParamName = "imageCode";

    public String getCodeParamName() {
        return codeParamName;
    }

    public void setCodeParamName(String codeParamName) {
        this.codeParamName = codeParamName;
    }

    @Autowired
    private ImageCodeValidator imageCodeValidator;

    /**
     * 登陆用户标识
     */
    private static final String LOGIN_USER_KEY = "_login_user_";

    /**
     * 设置登陆的用户
     *
     * @param user
     */
    public static void setLoginUser(Users user) {
        ContextHelper.setSessionAttr(LOGIN_USER_KEY, user);
    }

    /**
     * 获取登陆的用户
     *
     * @return
     */
    public static Users getLoginUser() {
        Object obj = ContextHelper.getSessionAttr(LOGIN_USER_KEY);
        if (obj instanceof Users) {
            return (Users) obj;
        }
        return null;
    }


    //在执行目标方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*response.setCharacterEncoding("utf-8");
        String requestMethod = request.getMethod();//请求方法
        if ("options".equalsIgnoreCase(requestMethod)) {
            System.out.println("option请求放开！");
            return true;
        }
        String requestToken = request.getHeader("token");
        System.out.println("requestToken:" + requestToken);
        if (requestToken == null) {
            logger.warn("**********未登录，禁止访问***********");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String sessionToken = (String) ContextHelper.getSessionAttr("token");
        if (null == sessionToken) {
            logger.warn("**********TOKEN已过期***********");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        if (!requestToken.equals(sessionToken)) {
            logger.warn("**********TOKEN验证失败***********");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }*/

        return true;
    }
}
