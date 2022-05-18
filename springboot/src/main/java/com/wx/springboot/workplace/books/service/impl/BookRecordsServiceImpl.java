package com.wx.springboot.workplace.books.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.tools.gettoken.GetToken;
import com.wx.springboot.tools.getusername.GetUserName;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.mapper.BookRecordsMapper;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @Override
    public Result add(BookRecords bookRecords) {
        HttpServletRequest request = GetUserName.getHttpServletRequest();
        String userName = GetToken.getUserNameByToken(request);
        bookRecords.setCreateTime(new Date());
        bookRecords.setCreateBy(userName);
        LambdaQueryWrapper<BookRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookRecords::getBookName,bookRecords.getBookName())
                .eq(BookRecords::getBookNature,bookRecords.getBookNature())
                .eq(BookRecords::getBookCategory,bookRecords.getBookCategory())
                .eq(BookRecords::getDeleted,0);
        BookRecords book = bookRecordsMapper.selectOne(queryWrapper);
        if (book != null ){
            return Result.error(Constants.CODE_400,"该图书已存在");
        }else {
            bookRecordsMapper.insert(bookRecords);
            return Result.success("添加成功");
        }
    }
}
