package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Menu;
import com.wx.springboot.system.service.DictService;
import com.wx.springboot.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description MenuController
 * @date 2022/4/26 17:37
 */
@RestController
@RequestMapping("system/menu")
@Api(tags = "MenuController")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private DictService dictService;

    @GetMapping("/pageList")
    @ApiOperation(value = "分页查询")
    public Result pageList(Menu menu, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Menu> page = new Page<>(pageNum,pageSize);
        IPage<Menu> pageList = menuService.pageList(page,menu);
        return Result.success(pageList);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "获取菜单列表")
    public Result findAll(@RequestParam(defaultValue = "") String name){
        List<Menu> menuList = menuService.getAllMenu(name);
        return Result.success(menuList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        Result result = menuService.add(menu);
        return result;
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        Result result = menuService.delete(id);
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        Result result = menuService.update(menu);
        return result;
    }

    @GetMapping("/icons")
    private Result getIcons(){
        Result result = dictService.getIconList();
        return Result.success(result);
    }

    @GetMapping("/ids")
    public Result getAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

}