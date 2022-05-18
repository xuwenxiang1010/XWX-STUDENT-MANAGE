package com.wx.springboot.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.dto.DictDto;
import com.wx.springboot.system.domain.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xuwenxiang
 * @create 2022/4/28~17:25
 */
@Mapper
public interface DictDao extends BaseMapper<Dict> {

	List<DictDto> queryValueByCode(String code);

	List<Dict> list();
}
