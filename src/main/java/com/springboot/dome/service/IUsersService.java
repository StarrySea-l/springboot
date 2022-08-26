package com.springboot.dome.service;

import com.springboot.dome.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wl
 * @since 2021-12-15
 */
public interface IUsersService extends IService<Users> {

    Users findUserById(Integer id) throws Exception;

    int insertSelective(Users users) throws Exception;

    Users findUserByUserName(String uerName) throws Exception;

    int delUserById(Long id) throws Exception;
}
