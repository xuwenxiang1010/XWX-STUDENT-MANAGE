package com.wx.springboot.workplace.books.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.tools.getuuid.GetUUId;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.service.LibrariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyb
 */
@RestController
@RequestMapping("books/libraries")
public class LibrariesController {

    @Autowired
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
            return Result.success("添加成功");
        }
        return Result.error();
    }


    @PutMapping("/edit")
    public Result edit(@RequestBody Libraries libraries){
        if (libraries!=null){
            librariesService.edit(libraries);
            return Result.success("添加成功");
        }
        return Result.error();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        Libraries libraries = librariesService.selectById(id);
        return librariesService.delete(libraries);
    }
}
