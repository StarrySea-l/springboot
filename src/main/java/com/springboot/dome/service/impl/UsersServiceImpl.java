package com.springboot.dome.service.impl;

import com.springboot.dome.entity.Users;
import com.springboot.dome.mapper.UsersMapper;
import com.springboot.dome.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wl
 * @since 2021-12-15
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public Users findUserById(Integer id) throws Exception {
        return usersMapper.findUserById(id);
    }

    @Override
    public int insertSelective(Users users) throws Exception {
        return usersMapper.insertSelective(users);
    }

    @Override
    public Users findUserByUserName(String uerName) throws Exception {
        return usersMapper.findUserByUserName(uerName);
    }

    @Override
    public int delUserById(Long id) throws Exception {
        return usersMapper.delUserById(id);
    }

}
