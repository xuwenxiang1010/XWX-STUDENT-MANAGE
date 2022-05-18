package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.domain.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: xwx
 * @createTime: 2022/5/18/018 10:08
 * @description:
 */
@Mapper
public interface DictItemDao extends BaseMapper<DictItem> {
	int getByText(String code, String text);
}
