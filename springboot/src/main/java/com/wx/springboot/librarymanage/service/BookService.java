package com.wx.springboot.librarymanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.librarymanage.domain.Book;
import com.wx.springboot.system.common.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xwx
 * @since 2022-05-07
 */
public interface BookService {

	IPage<Book> pageList(IPage<Book> page, Book book);

	Result add(Book book);

	Result deleted(Long id);

	Result update(Book book);
}
