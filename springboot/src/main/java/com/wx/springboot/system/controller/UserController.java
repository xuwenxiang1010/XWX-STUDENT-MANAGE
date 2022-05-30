package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.dto.UserDto;
import com.wx.springboot.system.domain.entity.User;
import com.wx.springboot.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description UserController
 * @date 2022/4/18 16:43
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "UserController")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
       List<User> list = userService.list();
       return list;
    }

    @GetMapping("/pageList")
    @ApiOperation(value = "分页查询")
    public Result pageList(User user, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<User> page = new Page<>(pageNum,pageSize);
        IPage<User> pageList = userService.pageList(page,user);
        return Result.success(pageList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user){
       Result result = userService.add(user);
       return result;
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        Result result = userService.delete(id);
        return result;
    }

    @PostMapping("/update")
    public Result update(@RequestBody UserDto dto){
        Result result = userService.update(dto);
        return result;
    }

    @PostMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids){
        Result result = userService.batchDelete(ids);
        return result;
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam Long id){
        Result result = userService.getUserInfo(id);
        return result;
    }

    @PostMapping("/setUserRole/{userId}")
    public Result setUserRole(@PathVariable Long userId,@RequestBody List<Long> roleIds){
        Result result = userService.setUserRole(userId,roleIds);
        return result;
    }


}