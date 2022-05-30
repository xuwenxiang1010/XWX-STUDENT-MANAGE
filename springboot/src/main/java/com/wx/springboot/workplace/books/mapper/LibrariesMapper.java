package com.wx.springboot.workplace.books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.workplace.books.entity.Libraries;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wyb
 */
@Mapper
public interface LibrariesMapper extends BaseMapper<Libraries> {
    /**
     * 通过name查找ID
     * @param name
     * @return
     */
    Integer selectIdByName(String name);

    /**
     * 查找所有图书馆
     */
    List<Libraries> selectAll();
}
