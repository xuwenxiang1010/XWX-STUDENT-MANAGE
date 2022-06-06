package com.wx.springboot.workplace.books.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.mapper.BookRecordsMapper;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dell
 */
@Service
public class BookRecordsServiceImpl implements BookRecordsService {
    @Autowired
    private BookRecordsMapper bookRecordsMapper;

    /**
     * 设置每层书架最多放置30本书
     */
    private static final Integer SAME_POSITION_CODE = 31;

    /**
     * 书籍数量达到可删除的值
     */
    private static final Integer STOCK_NUM = 0;

    /**
     *图书编码前缀：中国图书
     */
    private static final String BOOK_CODE_PREFIX = "ZhongGuoTuShu";


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
        bookRecords.setCreateTime(new Date());
        LambdaQueryWrapper<BookRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookRecords::getBookName,bookRecords.getBookName())
                .eq(BookRecords::getBookNature,bookRecords.getBookNature())
                .eq(BookRecords::getBookCategory,bookRecords.getBookCategory())
                .eq(BookRecords::getDeleted,0);
        BookRecords book = bookRecordsMapper.selectOne(queryWrapper);
        String total =  bookRecords.getPositionCode();
        if (book != null ){
            return Result.error("该图书已存在");
        }else {
           int x = bookRecordsMapper.selectPostCode(total);
            if (bookRecordsMapper.selectPostCode(total) < SAME_POSITION_CODE ){
                bookRecordsMapper.insert(bookRecords);
                return Result.success("添加成功");
            }
            return Result.error("该位置以放满图书，请更换位置");
        }
    }

    @Override
    public Result getCode() {
        String prefix = BOOK_CODE_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String goodsCode = findMaxCode(prefix);
        if (StringUtils.isBlank(goodsCode)) {
            return Result.success(prefix+"001");
        }
        String sum = goodsCode.substring(goodsCode.length() - 3);
        String code = String.format("%03d", Integer.parseInt(sum) + 1);
        return Result.success(prefix+code);
    }

    @Override
    public BookRecords selectById(String id) {
        return bookRecordsMapper.selectById(id);
    }

    @Override
    public Result delete(BookRecords bookRecords) {
        if (bookRecords.getStock() > STOCK_NUM){
            bookRecords.setDeleted(1);
            bookRecordsMapper.updateById(bookRecords);
            return Result.success("删除成功");
        }else {
            return Result.error("该图书还存在库存，无法删除");
        }
    }

    @Override
    public Result update(BookRecords bookRecords) {
        String code = bookRecords.getLibId()
                +String.format("%02d",bookRecords.getFlower())
                +String.format("%02d",bookRecords.getRoom())
                +String.format("%02d",bookRecords.getBookShelf())
                +String.format("%02d",bookRecords.getLayer());
        bookRecords.setPositionCode(code);
        bookRecords.setUpdateTime(new Date());
        LambdaQueryWrapper<BookRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookRecords::getBookName,bookRecords.getBookName())
                .eq(BookRecords::getBookNature,bookRecords.getBookNature())
                .eq(BookRecords::getBookCategory,bookRecords.getBookCategory())
                .ne(BookRecords::getId,bookRecords.getId())
                .eq(BookRecords::getDeleted,0);
        BookRecords book = bookRecordsMapper.selectOne(queryWrapper);
        if (book != null ){
            return Result.error("该图书已存在");
        }else {
            if (bookRecordsMapper.selectPositionCode(bookRecords.getPositionCode(),bookRecords.getId()) <= SAME_POSITION_CODE ){
                bookRecordsMapper.updateById(bookRecords);
                return Result.success("修改成功");
            }
            return Result.error("该位置以放满图书，请更换位置");
        }
    }

    private String findMaxCode(String prefix) {
        return bookRecordsMapper.findMaxCode(prefix);
    }

}
