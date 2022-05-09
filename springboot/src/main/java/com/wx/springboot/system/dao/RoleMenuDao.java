package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xuwenxiang
 * @create 2022/4/28~19:04
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {

    int deleteRoleMenu(Long roleId);
}
