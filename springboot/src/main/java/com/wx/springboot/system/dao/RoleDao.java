package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.Role;
import com.wx.springboot.system.domain.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description RoleDao
 * @date 2022/4/26 17:37
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
    List<UserInfoVo> getRoleInfo(Long id);

    List<Long> getMenuList(Long roleId);
}