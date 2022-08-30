package com.springboot.dome.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author wl
 * @since 2022-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 权限角色
     */
    @TableField("roleName")
    private String rolename;

    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

}
