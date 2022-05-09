package com.wx.springboot.system.domain.dto;

import com.wx.springboot.system.domain.vo.UserInfoVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description UserInfoDto
 * @date 2022/4/27 10:18
 */
@Data
public class UserInfoDto {
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
    private List<UserInfoVo> list;
}