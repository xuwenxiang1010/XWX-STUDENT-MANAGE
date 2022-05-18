package com.wx.springboot.system.domain.dto;

import lombok.Data;

/**
 * @author: xwx
 * @createTime: 2022/5/17/017 14:40
 * @description:
 */
@Data
public class DictDto {
	private String code;
	private String description;
	private String text;
	private String value;
}
