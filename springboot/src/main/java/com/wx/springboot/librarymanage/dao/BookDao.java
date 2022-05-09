package com.wx.springboot.librarymanage.dao;

import com.wx.springboot.librarymanage.domain.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xwx
 * @since 2022-05-07
 */
@Mapper
public interface BookDao extends BaseMapper<Book> {

}
