package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.MenuDao;
import com.wx.springboot.system.dao.RoleDao;
import com.wx.springboot.system.dao.RoleMenuDao;
import com.wx.springboot.system.domain.entity.Menu;
import com.wx.springboot.system.domain.entity.Role;
import com.wx.springboot.system.domain.entity.RoleMenu;
import com.wx.springboot.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description RoleServiceImpl
 * @date 2022/4/26 17:38
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleMapper;
    @Autowired
    private RoleMenuDao roleMenuMapper;
    @Autowired
    private MenuDao menuMapper;

    @Override
    public IPage<Role> pageList(IPage<Role> page, Role role) {
        LambdaQueryWrapper<Role> query = new LambdaQueryWrapper<>();
        query.eq(Role::getDeleted,0)
                .like(Role::getRoleName,role.getRoleName());
        IPage<Role> pageList = roleMapper.selectPage(page,query);
        return pageList;
    }

    @Override
    public Result add(Role role) {
        role.setCreateTime(new Date());
        LambdaQueryWrapper<Role> query = new LambdaQueryWrapper<>();
        query.eq(Role::getDeleted,0)
                .eq(Role::getRoleName,role.getRoleName());
        Role role1 = roleMapper.selectOne(query);
        if(role1 != null){
            return Result.error(Constants.CODE_400,"角色名已存在");
        }else {
            int add = roleMapper.insert(role);
            return Result.success("添加成功");
        }
    }

    @Override
    public Result delete(Long id) {
        Role role = roleMapper.selectById(id);
        role.setDeleted(1);
        int delete = roleMapper.updateById(role);
        return Result.success();
    }

    @Override
    public Result update(Role role) {
        role.setUpdateTime(new Date());
        int update = roleMapper.updateById(role);
        return Result.success("修改成功");
    }

    @Override
    public Result getMenuList(Long roleId) {
        List<Long> menuList = roleMapper.getMenuList(roleId);
        return Result.success(menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result setRoleMenu(Long roleId, List<Long> menuIds) {
        //删除角色与菜单关系
        deleteRoleMenu(roleId);
        //再把菜单id数组绑定到角色id上去
        for (Long menuId : menuIds){
            Menu menu = menuMapper.selectById(menuId);
            //二级菜单，并且传的menuIds没有父级ID
            if(menu.getParentId() != 0 && !menuIds.contains(menu.getParentId())){
                //这里需要补上父级ID
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getParentId());
                roleMenuMapper.insert(roleMenu);
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
        return Result.success();
    }

    @Override
    public List<Role> list() {
        LambdaQueryWrapper<Role> query = new LambdaQueryWrapper<>();
        query.eq(Role::getDeleted,0);
        return roleMapper.selectList(query);
    }

    private void deleteRoleMenu(Long roleId){
        roleMenuMapper.deleteRoleMenu(roleId);
    }
}