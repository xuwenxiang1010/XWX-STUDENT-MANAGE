package com.wx.springboot.workplace.books.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
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

    /**
     * 添加
     * @param bookRecords
     * @return
     */
    Result add(BookRecords bookRecords);

    /**
     * 获取图书编码
     * @return
     */
    String getCode();

    BookRecords selectById(String id);
}
