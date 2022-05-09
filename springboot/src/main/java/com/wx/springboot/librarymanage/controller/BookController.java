package com.wx.springboot.librarymanage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.librarymanage.domain.Book;
import com.wx.springboot.librarymanage.service.BookService;
import com.wx.springboot.system.common.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xwx
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/librarymanage/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/pageList")
	@ApiOperation(value = "分页查询")
	public Result pageList(Book book, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
		IPage<Book> page = new Page<>(pageNum,pageSize);
		IPage<Book> pageList = bookService.pageList(page,book);
		return Result.success(pageList);
	}

	@PostMapping("/add")
	@ApiOperation(value = "增加")
	public Result add(@RequestBody Book book){
		Result result = bookService.add(book);
		return result;
	}

	@PostMapping("/delete/{id}")
	@ApiOperation(value = "删除")
	public Result delete(@PathVariable Long id){
		Result result = bookService.deleted(id);
		return result;
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改")
	public Result update(@RequestBody Book book){
		Result result = bookService.update(book);
		return result;
	}
}

