package com.wx.springboot.librarymanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.librarymanage.domain.Book;
import com.wx.springboot.librarymanage.dao.BookDao;
import com.wx.springboot.librarymanage.service.BookService;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwx
 * @since 2022-05-07
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookMapper;

	@Override
	public IPage<Book> pageList(IPage<Book> page, Book book) {
		LambdaQueryWrapper<Book> query = new LambdaQueryWrapper<>();
		query.eq(Book::getDeleted,0)
				.like(Book::getBookName,book.getBookName())
				.orderByDesc(Book::getInTime);
		IPage<Book> pageList = bookMapper.selectPage(page,query);
		return pageList;
	}

	@Override
	public Result add(Book book) {
		book.setInTime(new Date());
		LambdaQueryWrapper<Book> query = new LambdaQueryWrapper<>();
		query.eq(Book::getBookName,book.getBookName())
				.eq(Book::getDeleted,0);
		Book book1 = bookMapper.selectOne(query);
		if (book1 != null){
			int quantity = book1.getQuantity();
			book1.setQuantity(quantity + book.getQuantity());
			bookMapper.updateById(book1);
			return Result.success("添加成功");
		} else {
			int add = bookMapper.insert(book);
			return Result.success("添加成功");
		}
	}

	@Override
	public Result deleted(Long id) {
		Book book = bookMapper.selectById(id);
		if(book.getQuantity() != 0){
			return Result.error(Constants.CODE_400,"图书有库存，删除失败");
		} else {
			book.setDeleted(1);
			int delete = bookMapper.updateById(book);
			return Result.success("删除成功");
		}
	}

	@Override
	public Result update(Book book) {
		int update = bookMapper.updateById(book);
		return Result.success("修改成功");
	}
}
