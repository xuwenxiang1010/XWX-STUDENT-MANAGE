package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xuwenxiang
 * @create 2022/4/28~21:24
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {
    List<Long> getRoleList(Long id);
}
