package com.wx.springboot.workplace.books.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import com.wx.springboot.workplace.books.service.LibrariesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @Autowired
    private LibrariesService librariesService;

    @GetMapping("/list")
    public Result list(BookRecordsVo vo,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<BookRecords> page = new Page<>(pageNo, pageSize);
        IPage<BookRecords> pageList = bookRecordsService.queryPageList(page, vo);
        return Result.success(pageList);
    }

    @PostMapping("/add")
    public Result add(BookRecords bookRecords){
        bookRecords.setBookCode(getCode());
        return bookRecordsService.add(bookRecords);
    }

    @GetMapping("/getCode")
    public String getCode(){
        return bookRecordsService.getCode();
    }


    @PostMapping("/address")
    public Result address(String id){
        BookRecords bookRecords = bookRecordsService.selectById(id);
        String t1 = bookRecords.getPositionId();
        String position = "";
        do{
            Libraries l1 = librariesService.selectById(t1);
            t1 = l1.getFatherId();
            String p2 = l1.getName();
            if (p2 != null){
                position = p2  + position;
            }
        }while(!t1.equals("1"));
        bookRecords.setPosition(position);
        return Result.success(bookRecords);
    }
}
