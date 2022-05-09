package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.MenuDao;
import com.wx.springboot.system.domain.entity.Menu;
import com.wx.springboot.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xwx
 * @version 1.0
 * @description MenuServiceImpl
 * @date 2022/4/26 17:38
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuMapper;


    @Override
    public IPage<Menu> pageList(IPage<Menu> page, Menu menu) {
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query.eq(Menu::getDeleted,0)
                .like(Menu::getName,menu.getName());
        IPage<Menu> pageList = menuMapper.selectPage(page, query);
        return pageList;
    }

    @Override
    public Result add(Menu menu) {
        int add = menuMapper.insert(menu);
        return Result.success("添加成功");
    }

    @Override
    public Result delete(Long id) {
        Menu menu = menuMapper.selectById(id);
        menu.setDeleted(1);
        int delete = menuMapper.updateById(menu);
        return Result.success();
    }

    @Override
    public Result update(Menu menu) {
        menu.setUpdateTime(new Date());
        int update = menuMapper.updateById(menu);
        return Result.success("修改成功");
    }

    @Override
    public List<Menu> getAllMenu(String name) {
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query.eq(Menu::getDeleted,0);
        if(!StringUtils.isEmpty(name)){
            query.like(Menu::getName,name);
        }
        //查询所有menu
        List<Menu> menuList = menuMapper.selectList(query);
        //找出一级菜单：parent_id=0
        List<Menu> parentNode = menuList.stream().filter(menu -> menu.getParentId() == 0).collect(Collectors.toList());
        //一级菜单的子菜单
        for (Menu menu : parentNode){
            //筛选所有数据中parent_id=父级id的数据就是二级菜单
            menu.setChildren(menuList.stream().filter(m -> menu.getId().equals(m.getParentId())).collect(Collectors.toList()));
        }
        return parentNode;
    }

    @Override
    public List<Menu> list() {
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query.eq(Menu::getDeleted,0);
        return menuMapper.selectList(query);
    }
}