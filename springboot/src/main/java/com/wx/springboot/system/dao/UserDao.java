package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description UserDao
 * @date 2022/4/18 16:45
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    List<User> list();

    List<User> pageList(Integer pageNum, Integer pageSize);

    List<Long> selectMenuList(Long id);
}