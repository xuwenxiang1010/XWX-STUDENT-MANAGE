package com.wx.springboot.system.dao;

import com.wx.springboot.system.domain.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xwx
 * @since 2023-04-25
 */

@Mapper
public interface LogMapper extends BaseMapper<Log> {
	List<Log> list();
}

