package com.wx.springboot.system.domain.vo;

import lombok.Data;

/**
 * @author xwx
 * @version 1.0
 * @description UserInfoVo
 * @date 2022/4/27 10:41
 */
@Data
public class UserInfoVo {
    private Long userId;
    private Long roleId;
    private String roleName;
    private String description;
    private Long menuId;
    private String path;
}