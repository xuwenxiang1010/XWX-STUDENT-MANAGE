package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description menuDao
 * @date 2022/4/26 17:37
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> list();
}