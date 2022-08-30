package com.springboot.dome.service.impl;

import com.springboot.dome.entity.Permissions;
import com.springboot.dome.mapper.PermissionsMapper;
import com.springboot.dome.service.IPermissionsService;
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
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

}
