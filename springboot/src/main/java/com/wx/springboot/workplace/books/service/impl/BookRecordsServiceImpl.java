package com.wx.springboot.workplace.books.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.mapper.BookRecordsMapper;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service
public class BookRecordsServiceImpl implements BookRecordsService {
    @Autowired
    private BookRecordsMapper bookRecordsMapper;
    @Override
    public IPage<BookRecords> queryPageList(IPage<BookRecords> page, BookRecordsVo vo) {
        LambdaQueryWrapper<BookRecords> query = new LambdaQueryWrapper<>();
        query.eq(BookRecords::getDeleted,0)
                .like(StringUtils.isNotBlank(vo.getBookCode()),BookRecords::getBookCode,vo.getBookCode())
                .like(StringUtils.isNotBlank(vo.getBookName()),BookRecords::getBookName,vo.getBookName())
                .eq(vo.getBookNature()!=null,BookRecords::getBookNature,vo.getBookNature())
                .eq(vo.getBookCategory()!=null,BookRecords::getBookCategory,vo.getBookCategory())
                .ge(StringUtils.isNotBlank(vo.getStart()),BookRecords::getCreateTime,vo.getStart())
                .le(StringUtils.isNotBlank(vo.getEnd()),BookRecords::getCreateTime,vo.getEnd())
                .orderByDesc(BookRecords::getCreateTime);
        return bookRecordsMapper.selectPage(page, query);
    }
}
