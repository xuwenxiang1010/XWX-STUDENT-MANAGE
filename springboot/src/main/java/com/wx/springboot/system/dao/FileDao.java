package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.Files;
import org.apache.ibatis.annotations.Mapper;

/**
 * @authoer:xwx
 * @createTime: 2022/5/5/005 16:57
 * @description:
 */
@Mapper
public interface FileDao extends BaseMapper<Files> {
}
