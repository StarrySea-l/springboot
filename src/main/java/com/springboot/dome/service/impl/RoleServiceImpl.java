package com.springboot.dome.service.impl;

import com.springboot.dome.entity.Role;
import com.springboot.dome.mapper.RoleMapper;
import com.springboot.dome.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wl
 * @since 2022-08-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
