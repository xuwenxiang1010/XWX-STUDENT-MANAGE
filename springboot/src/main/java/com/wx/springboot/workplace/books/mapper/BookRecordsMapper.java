package com.wx.springboot.workplace.books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.springboot.workplace.books.entity.BookRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dell
 */
@Mapper
public interface BookRecordsMapper extends BaseMapper<BookRecords> {

    /**
     * 图书编码（部分）
     * @param prefix
     * @return
     */
    String findMaxCode(@Param("prefix")String prefix);
}
