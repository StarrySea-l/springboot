package com.springboot.dome.service.impl;

import com.springboot.dome.entity.User;
import com.springboot.dome.entity.UserPassword;
import com.springboot.dome.entity.bean.UserBean;
import com.springboot.dome.mapper.UserMapper;
import com.springboot.dome.mapper.UserPasswordMapper;
import com.springboot.dome.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2022/9/3 2:37
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    public UserBean getUserById(Integer id) throws Exception {

        User user = userMapper.selectByPrimaryKey(id);
        if (null == user) {
            return null;
        }
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());

        return convertFromDataObject(user, userPassword);

    }

    @Override
    public User selectUserByName(String name) throws Exception {
        return userMapper.selectUserByName(name);
    }


    private UserBean convertFromDataObject(User user, UserPassword userPassword) {

        if (null == user) {
            return null;
        }
        UserBean userBean = new UserBean();


        BeanUtils.copyProperties(user, userBean);
        if (null != userPassword) {
            userBean.setEncrptPassword(userPassword.getEncrptPassword());
        }

        return userBean;
    }
}
