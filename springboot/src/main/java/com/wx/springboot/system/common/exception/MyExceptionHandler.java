package com.wx.springboot.system.common.exception;

import com.wx.springboot.system.common.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: xwx
 * @createTime: 2022/5/9/009 16:08
 * @description:
 */
@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public Result handle(ServiceException se){
		return Result.error(se.getCode(),se.getMessage());
	}
}
