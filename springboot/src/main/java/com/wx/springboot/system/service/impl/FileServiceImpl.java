package com.wx.springboot.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.FileDao;
import com.wx.springboot.system.domain.entity.Files;
import com.wx.springboot.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :  xwx
 * @createTime: 2022/5/5/005 16:58
 * @description:
 */
@Service
public class FileServiceImpl  implements FileService {

    @Autowired
    private FileDao fileMapper;

    @Value("${files.upload.path}")
    private String fileUploadPath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    public Result upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        String extName = FileUtil.extName(filename);
        long size = file.getSize();
        //上传到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，不存在就创建
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdir();
        }
        String date = sdf.format(new Date());
        String filePath = date+"_"+filename;
        File uploadFile = new File(fileUploadPath+filePath);
        try{
            //把获取的文件存储到磁盘目录
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "http://localhost:80/system/file/download/"+filePath;
        //存储到数据库
        Files saveFile = new Files();
        saveFile.setName(filename);
        saveFile.setType(extName);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setStatus(true);
        fileMapper.insert(saveFile);
        return Result.success();

        /*String name = filename.substring(0,filename.indexOf("."));
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
        fileMapper.insert(saveFile);*/

    }

    /**
     * 文件下载路径  "http://localhost:80/system/file/download/{filePath}"
     * @param filePath
     * @param response
     */
    @Override
    public void download(String filePath, HttpServletResponse response){
        //获取文件
        File file = new File(fileUploadPath+filePath);
        try {
            //设置输出流的格式
            ServletOutputStream os = response.getOutputStream();
            response.addHeader("content-disposition","attachment;filename="
                    + URLEncoder.encode(filePath,"UTF-8").replaceAll("\\+", "%20"));
            response.setContentType("application/octet-stream");
            //读取文件字节流
            os.write(FileUtil.readBytes(file));
            os.flush();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*if (file.exists()){
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
        }*/
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
