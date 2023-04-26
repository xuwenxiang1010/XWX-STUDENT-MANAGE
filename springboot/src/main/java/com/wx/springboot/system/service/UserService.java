package com.wx.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.User;
import com.wx.springboot.system.domain.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xuwenxiang
 * @create 2022/4/18~16:46
 */
public interface UserService {

    List<User> list();

    Result add(User user);

    Result delete(Long id);

    Result update(UserDto dto);

    IPage<User> pageList(IPage<User> page, User user);

    Result batchDelete(List<Long> ids);

    Result login(UserDto user, HttpServletRequest request);

    Result getUserInfo(Long id);

    Result register(UserDto dto);

    List<Long> getRoles(Long id);
}
