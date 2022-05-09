package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.FileDao;
import com.wx.springboot.system.domain.entity.Files;
import com.wx.springboot.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @authoer :  xwx
 * @createTime: 2022/5/5/005 16:58
 * @description:
 */
@Service
public class FileServiceImpl  implements FileService {

    @Autowired
    private FileDao fileMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_");
    String path = "D:\\Work\\Code\\WorkSpace\\XWX-STUDENT-MANAGE\\springboot\\File\\";

    @Override
    public Result upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String name = filename.substring(0,filename.indexOf("."));
        String type = filename.substring(filename.indexOf(".")+1);
        long size = file.getSize();
        //上传到磁盘
        File uploadPath = new File(path);
        //文件的唯一标识位
        String str = sdf.format(new Date());
        File uploadFile = new File(path + str +  filename);
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //存储到数据库
        String url = "http://localhost:80/system/file/download/"  + str + filename;
        Files saveFile = new Files();
        saveFile.setName(name);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setStatus(true);
        fileMapper.insert(saveFile);
        return Result.success();
    }

    /**
     * 文件下载路径  "http://localhost:80/system/file/download/name"
     * @param name : uuid+"."+type
     * @param response
     * @throws IOException
     */
    @Override
    public void download(String name, HttpServletResponse response) throws IOException {
        //获取文件
        File file = new File(path+name);
        if (file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("content-disposition","attachment;filename="
                    + URLEncoder.encode(name,"UTF-8").replaceAll("\\+", "%20"));
            byte[] buffer = new byte[1024];
            try(FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis)) {
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1){
                    os.write(buffer,0,i);
                    i = bis.read(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IPage<Files> pageList(IPage<Files> page, Files files) {
        LambdaQueryWrapper<Files> query = new LambdaQueryWrapper<>();
        query.eq(Files::getDeleted,0)
                .like(Files::getName,files.getName())
                .orderByDesc(Files::getId);
        IPage<Files> pageList = fileMapper.selectPage(page,query);
        return pageList;
    }

    @Override
    public Result delete(Long id) {
        Files files = fileMapper.selectById(id);
        files.setDeleted(1);
        int update = fileMapper.updateById(files);
        return Result.success();
    }

    @Override
    public Result update(Files files) {
        int update = fileMapper.updateById(files);
        return Result.success();
    }
}
