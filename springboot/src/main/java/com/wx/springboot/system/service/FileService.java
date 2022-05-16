package com.wx.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @authoer:xwx
 * @createTime: 2022/5/5/005 16:58
 * @description:
 */
public interface FileService {

    Result upload(MultipartFile file);

    void download(String filePath, HttpServletResponse response);

    IPage<Files> pageList(IPage<Files> page, Files files);

    Result delete(Long id);

    Result update(Files files);
}
