package com.springboot.dome.controller;


import com.springboot.dome.base.ResultBean;
import com.springboot.dome.entity.Users;
import com.springboot.dome.interceptor.PermissionCheckInterceptor;
import com.springboot.dome.service.IUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wl
 * @since 2021-12-15
 */
@RestController
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IUsersService iUsersService;

    @RequestMapping("/getUserById")
    public ResultBean getUserById(Integer id) {
        if (null == id) {
            logger.info("缺少必要参数");
            return ResultBean.fail("缺少必要参数");
        }
        try {
            Users user = iUsersService.findUserById(id);
            if (null != user) {
                PermissionCheckInterceptor.setLoginUser(user);
                return ResultBean.success(user);
            } else {
                return ResultBean.fail("登陆失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录异常");
            return ResultBean.error("登录发生异常");
        }
    }

    @RequestMapping("/insertUser")
    public ResultBean insertUser(Users users) {
        System.out.println(users);
        try {
            iUsersService.insertSelective(users);
            return ResultBean.success("添加用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加异常");
            return ResultBean.error("添加异常");
        }
    }

    @RequestMapping("/delUserById")
    public ResultBean delUserById(Long id) {
        if (null == id) {
            return ResultBean.fail("参数不能为空");
        }
        try {
            iUsersService.delUserById(id);
            return ResultBean.success("删除用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常");
            return ResultBean.error("删除用户异常");
        }
    }
}
