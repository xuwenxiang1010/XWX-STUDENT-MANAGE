package com.wx.springboot.system.domain.dto;

import com.wx.springboot.system.domain.entity.Menu;
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
    List<Menu> menuList;
    private String token;
}