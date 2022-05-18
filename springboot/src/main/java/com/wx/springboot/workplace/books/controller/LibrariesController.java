package com.wx.springboot.workplace.books.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.service.LibrariesService;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyb
 */
@RestController
@RequestMapping("/books/libraries")
public class LibrariesController {

    private LibrariesService librariesService;


    @GetMapping("/list")
    public Result list(Libraries vo,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<Libraries> page = new Page<>(pageNo, pageSize);
        IPage<Libraries> pageList = librariesService.queryPageList(page, vo);
        return Result.success(pageList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Libraries libraries){
        if (libraries!=null){
            librariesService.add(libraries);
            librariesService.roomName(libraries.getFlower(),libraries.getRoom(),libraries.getName());
            return Result.success();
        }
        return Result.error();
    }
}
