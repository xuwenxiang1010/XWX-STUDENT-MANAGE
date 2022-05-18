package com.wx.springboot.workplace.books.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author wyb
 */
@RestController
@RequestMapping("books/records")
@Api(tags = "BookRecordsController")
@Slf4j
public class BookRecordsController {


    @Autowired
    private BookRecordsService bookRecordsService;

    @GetMapping("/list")
    public Result list(BookRecordsVo vo,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<BookRecords> page = new Page<>(pageNo, pageSize);
        IPage<BookRecords> pageList = bookRecordsService.queryPageList(page, vo);
        return Result.success(pageList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody BookRecords bookRecords){
        return bookRecordsService.add(bookRecords);
    }

    @GetMapping("/getCode")
    public String getCode(){
        return bookRecordsService.getCode();
    }
}
