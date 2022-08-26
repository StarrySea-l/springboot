package com.springboot.dome.mapper;


import com.springboot.dome.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wl
 * @since 2021-12-15
 */
public interface UsersMapper extends BaseMapper<Users> {

    Users findUserById(Integer id);

    int insertSelective(Users users);

    Users findUserByUserName(String uerName);

    int delUserById(Long id);
}
