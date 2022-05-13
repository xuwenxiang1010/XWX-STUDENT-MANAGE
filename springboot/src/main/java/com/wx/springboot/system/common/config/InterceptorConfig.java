package com.wx.springboot.system.common.config;

import com.wx.springboot.system.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: xwx
 * @createTime: 2022/5/9/009 15:16
 * @description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor())
				// 拦截所有请求，通过判断token是否合法决定是否需要登录
				.addPathPatterns("/**")
				.excludePathPatterns("/login","/register","/system/file/download/{filePath}","/system/file/upload");
	}

	@Bean
	public JwtInterceptor jwtInterceptor() {
		return new JwtInterceptor();
	}

}
