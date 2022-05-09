package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description Menu 菜单管理
 * @date 2022/4/26 17:05
 */
@Data
@TableName("sys_menu")
public class Menu {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;

    private String name;
    private String path;
    private String icon;
    private Date createTime;
    private Date updateTime;
    private int type;
    private Integer deleted;
    private String pagePath;

    @TableField(exist = false)
    private List<Menu> children;
}