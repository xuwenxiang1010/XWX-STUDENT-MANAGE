package com.wx.springboot.system.controller;

import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.dto.UserDto;
import com.wx.springboot.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xwx
 * @version 1.0
 * @description LoginController
 * @date 2022/4/18 16:14
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto dto){
        Result result = userService.login(dto);
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto dto){
        Result result = userService.register(dto);
        return result;
    }

}