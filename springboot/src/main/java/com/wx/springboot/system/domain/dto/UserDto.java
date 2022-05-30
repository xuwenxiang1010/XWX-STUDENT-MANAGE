package com.wx.springboot.system.domain.dto;

import com.wx.springboot.system.domain.entity.Menu;
import com.wx.springboot.system.domain.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description 登录用户工具类
 * @date 2022/4/24 18:15
 */
@Data
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private Integer userAge;
    private String phone;
    private String address;
    private String email;
    List<Menu> menuList;
    List<Long> roleList;
    private String token;
}