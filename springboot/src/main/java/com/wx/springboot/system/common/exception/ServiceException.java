package com.wx.springboot.system.common.exception;

import lombok.Getter;

/**
 * @author: xwx
 * @createTime: 2022/5/9/009 16:10
 * @description:
 */
@Getter
public class ServiceException extends RuntimeException{
	private String code;

	public ServiceException(String code,String message){
		super(message);
		this.code = code;
	}
}
