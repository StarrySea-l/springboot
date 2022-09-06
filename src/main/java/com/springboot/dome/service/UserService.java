package com.springboot.dome.service;

import com.springboot.dome.entity.User;
import com.springboot.dome.entity.bean.UserBean;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2022/9/3 2:36
 * @description:
 */
public interface UserService {

    UserBean getUserById(Integer id) throws Exception;

    User selectUserByName(String name) throws Exception;
}
