package com.wx.springboot.system.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @authoer:xwx
 * @createTime: 2022/5/6/006 9:21
 * @description:
 */
@Configuration
public class MulterFile {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("30960KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("309600KB");
        return factory.createMultipartConfig();
    }

}
