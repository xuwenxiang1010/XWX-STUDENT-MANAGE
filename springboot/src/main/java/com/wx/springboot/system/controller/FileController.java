package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Files;
import com.wx.springboot.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  xwx
 */
@RestController
@RequestMapping("/system/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        Result result = fileService.upload(file);
        return result;
    }

    /**
     * 文件下载接口
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/{name}")
    public void download(@PathVariable String name, HttpServletResponse response) throws IOException {
        fileService.download(name,response);
    }

    @GetMapping("/pageList")
    public Result pageList(Files files, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Files> page = new Page<>(pageNum,pageSize);
        IPage<Files> pageList = fileService.pageList(page,files);
        return Result.success(pageList);
    }

   @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        Result result = fileService.delete(id);
        return result;
   }

   @PostMapping("/update")
    public Result update(@RequestBody Files files){
        Result result = fileService.update(files);
        return result;
   }

}
