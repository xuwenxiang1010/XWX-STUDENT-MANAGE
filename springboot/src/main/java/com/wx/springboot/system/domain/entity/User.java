package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wx.springboot.system.common.anno.Dict;
import lombok.Data;

import java.util.Date;

/**
 * @author xwx
 * @version 1.0
 * @description User
 * @date 2022/4/18 16:38
 */
@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String userName;
    private String password;
    private Integer userAge;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private String phone;
    private String address;
    private String email;
    @Dict(dictCode = "sex")
    private String sex;
}