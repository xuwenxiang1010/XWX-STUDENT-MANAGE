package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author xwx
 * @version 1.0
 * @description Role角色管理
 * @date 2022/4/26 17:04
 */
@Data
@TableName("sys_role")
public class Role {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String roleName;
    private String description;
    private Long createBy;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}