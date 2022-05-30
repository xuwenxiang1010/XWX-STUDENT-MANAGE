package com.wx.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Role;

import java.util.List;

/**
 * @author xuwenxiang
 * @create 2022/4/26~17:37
 */
public interface RoleService {
    IPage<Role> pageList(IPage<Role> page, Role role);

    Result add(Role role);

    Result delete(Long id);

    Result update(Role role);

    Result getMenuList(Long roleId);

    Result setRoleMenu(Long roleId, List<Long> menuIds);

	List<Role> list();
}
