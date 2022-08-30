package com.springboot.dome.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 权限名称
     */
    @TableField("permissionsName")
    private String permissionsname;


}
