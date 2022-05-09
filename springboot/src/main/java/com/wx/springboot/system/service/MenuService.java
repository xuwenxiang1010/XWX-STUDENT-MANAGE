package com.wx.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Menu;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description MenuService
 * @date 2022/4/26 17:38
 */
public interface MenuService {
    IPage<Menu> pageList(IPage<Menu> page, Menu menu);

    Result add(Menu menu);

    Result delete(Long id);

    Result update(Menu menu);

    List<Menu> getAllMenu(String name);

    List<Menu> list();
}