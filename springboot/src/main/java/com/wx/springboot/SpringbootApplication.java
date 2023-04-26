package com.wx.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author xwx
 */
@SpringBootApplication
@MapperScan({"com.wx.springboot.**.dao" , "com.wx.springboot.**.mapper"})
public class SpringbootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        System.out.println("http://localhost:80/swagger-ui.html");
    }

    //   在启动类当中加上extends SpringBootServletInitializer并重写configure方法，这是为了打包springboot项目用的。

    @Override //为了打包springboot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
