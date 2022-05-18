package com.wx.springboot.workplace.books.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;

/**
 * @author dell
 */
public interface BookRecordsService{

    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    IPage<BookRecords> queryPageList(IPage<BookRecords> page, BookRecordsVo vo);
}
