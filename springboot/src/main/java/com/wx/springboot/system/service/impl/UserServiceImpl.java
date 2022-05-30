package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.util.TokenUtil;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.RoleDao;
import com.wx.springboot.system.dao.UserDao;
import com.wx.springboot.system.dao.UserRoleDao;
import com.wx.springboot.system.domain.entity.Menu;
import com.wx.springboot.system.domain.entity.Role;
import com.wx.springboot.system.domain.entity.User;
import com.wx.springboot.system.domain.entity.UserRole;
import com.wx.springboot.system.domain.dto.UserDto;
import com.wx.springboot.system.domain.dto.UserInfoDto;
import com.wx.springboot.system.domain.vo.UserInfoVo;
import com.wx.springboot.system.service.MenuService;
import com.wx.springboot.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description UserServiceImpl
 * @date 2022/4/18 16:46
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userMapper;
    @Autowired
    private RoleDao roleMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleDao userRoleMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public IPage<User> pageList(IPage<User> page, User user) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getDeleted,0)
                .like(User::getUserName,user.getUserName())
                .orderByDesc(User::getId);
        IPage<User> pageList = userMapper.selectPage(page, query);
        return pageList;
    }

    @Override
    public Result batchDelete(List<Long> ids) {
        for(Long id : ids){
            User user = userMapper.selectById(id);
            user.setDeleted(1);
            int update = userMapper.updateById(user);
        }
        return Result.success();
    }

    @Override
    public Result login(UserDto dto) {
        String userName = dto.getUserName();
        String password = dto.getPassword();
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper();
        query.eq(User::getUserName,userName)
                .eq(User::getPassword,password)
                .eq(User::getDeleted,0);
        User user = userMapper.selectOne(query);
        if(user != null){
            BeanUtils.copyProperties(user,dto);
            String token = TokenUtil.genToken(dto.getId().toString(),dto.getPassword());
            dto.setToken(token);
            List<Menu> roleMenu = getRoleMenu(dto.getId());
            dto.setMenuList(roleMenu);
            return Result.success(dto);
        }else {
            return Result.error(Constants.CODE_400,"用户名或密码错误");
        }

    }

    @Override
    public Result add(User user) {
        user.setCreateTime(new Date());
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getUserName,user.getUserName())
                .eq(User::getDeleted,0);
        User user1 = userMapper.selectOne(query);
        if(user1 != null){
            return Result.error(Constants.CODE_400,"用户名已存在");
        }else {
            int add = userMapper.insert(user);
            return Result.success("添加成功");
        }
    }

    @Override
    public Result delete(Long id) {
        User user = userMapper.selectById(id);
        user.setDeleted(1);
        int update = userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result update(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        user.setUpdateTime(new Date());
        int update = userMapper.updateById(user);

        List<Role> roleList = dto.getRoleList();
        return Result.success("修改成功");
    }

    @Override
    public Result getUserInfo(Long id) {
        List<UserInfoVo> roleList = roleMapper.getRoleInfo(id);
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getId,id)
                .eq(User::getDeleted,0);
        User user = userMapper.selectOne(query);
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(user,userInfoDto);
        userInfoDto.setList(roleList);
        return Result.success(userInfoDto);
    }

    @Override
    public Result register(UserDto dto) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getUserName,dto.getUserName())
                .eq(User::getDeleted,0);
        User user = userMapper.selectOne(query);
        if(user != null){
            return Result.error(Constants.CODE_400,"用户名已存在");
        }else {
            User user1 = new User() ;
            BeanUtils.copyProperties(dto,user1);
            int add = userMapper.insert(user1);
            LambdaQueryWrapper<User> query1 = new LambdaQueryWrapper<>();
            query1.eq(User::getUserName,user1.getUserName());
            User user2 = userMapper.selectOne(query1);
            setRole(user2.getId());
            return  Result.success("添加成功");
        }
    }

    @Override
    public Result setUserRole(Long userId, List<Long> roleIds) {


        return null;
    }

    public List<Menu> getRoleMenu(Long id) {
        //获取用户可以获得的所有菜单
        List<Long> menuList = userMapper.selectMenuList(id);
        //查出所有菜单
        List<Menu> allMenu = menuService.getAllMenu("");
        //最后接收的菜单集合
        List<Menu> roleMenu = new ArrayList<>();
        //筛选当前用户角色的菜单
        for(Menu menu : allMenu){
            if(menuList.contains(menu.getId())){
                roleMenu.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf 移除children里面不在menuList集合中的元素
            children.removeIf(child -> !menuList.contains(child.getId()));
        }
        return roleMenu;
    }

    public void setRole(Long userId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(new Long(2));
        userRoleMapper.insert(userRole);
    }

}