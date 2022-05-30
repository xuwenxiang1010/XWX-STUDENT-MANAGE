package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Role;
import com.wx.springboot.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description RoleController
 * @date 2022/4/26 17:36
 */
@RestController
@RequestMapping("system/role")
@Api(tags = "RoleController")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation(value = "list")
    public Result list(){
        List<Role> list = roleService.list();
        return Result.success(list);
    }

    @GetMapping("/pageList")
    @ApiOperation(value = "分页查询")
    public Result pageList(Role role, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Role> page = new Page<>(pageNum,pageSize);
        IPage<Role> pageList = roleService.pageList(page,role);
        return Result.success(pageList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role){
        Result result = roleService.add(role);
        return result;
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        Result result = roleService.delete(id);
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        Result result = roleService.update(role);
        return result;
    }

    @GetMapping("/getMenuList")
    public Result getMenuList(@RequestParam Long roleId){
        Result result = roleService.getMenuList(roleId);
        return Result.success(result);
    }

    /**
     * 角色和菜单关系
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/setRoleMenu/{roleId}")
    public Result setRoleMenu(@PathVariable Long roleId,@RequestBody List<Long> menuIds){
        Result result = roleService.setRoleMenu(roleId,menuIds);
        return result;
    }

}